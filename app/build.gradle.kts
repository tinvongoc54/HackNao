import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(Plugins.androidApp)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinExt)
    kotlin(Plugins.kotlinApt)
    id(Plugins.kotlinNavSafeargs)
//    id(Plugins.googleServices)
//    id(Plugins.crashlytics)
    id(Plugins.checkDependencyUpdates) version (Versions.check_dependency_updates)
}

buildscript {
    apply(from = "../buildSrc/ktlint.gradle.kts")
}

android {
    compileSdkVersion(Versions.compile_sdk_version)
    buildToolsVersion(Versions.build_tools_version)

    flavorDimensions("default")

    defaultConfig {
        applicationId = "com.vtd.hacknao"
        minSdkVersion(Versions.min_sdk_version)
        targetSdkVersion(Versions.target_sdk_version)
        vectorDrawables.useSupportLibrary = true
        testInstrumentationRunner = "com.vtd.hacknao.app.CustomTestRunner"
    }

    productFlavors {
        create("APP") {
            versionCode = 1
            versionName = "1.0.0"

            resValue("string", "app_name", "3K Words")
            buildConfigField("String", "END_POINT", "\"http://api.tracau.vn/WBBcwnwQpV89/\"")
            addManifestPlaceholders(mapOf("appIcon" to "@mipmap/ic_launcher", "appIconRound" to "@mipmap/ic_launcher_round"))
        }

        applicationVariants.all {
            outputs.forEach { output ->
                if (output is com.android.build.gradle.internal.api.BaseVariantOutputImpl) {
                    output.outputFileName =
                        "HackNao-${name}_v$versionName(${this.versionCode}).${output.outputFile.extension}"
                }
            }
        }
    }

    signingConfigs {
        create("release") {
            keyAlias = gradleLocalProperties(rootDir).getProperty("RELEASE_KEY_ALIAS")
            keyPassword = gradleLocalProperties(rootDir).getProperty("RELEASE_KEY_PASSWORD")
            storeFile = file(gradleLocalProperties(rootDir).getProperty("RELEASE_STORE_FILE"))
            storePassword = gradleLocalProperties(rootDir).getProperty("RELEASE_STORE_PASSWORD")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
                file("proguard-rules.pro"),
                file("androidx-proguard-rules.pro")
            )
            signingConfig = signingConfigs.getByName("release")
        }
    }

    dexOptions {
        javaMaxHeapSize = "4g"
        preDexLibraries = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
    }

    buildFeatures {
        viewBinding = true
    }

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/license.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
        exclude("META-INF/notice.txt")
        exclude("META-INF/ASL2.0")
        exclude("META-INF/*.kotlin_module")
    }

    sourceSets {
        sourceSets {
            val sharedTestDir = "src/sharedTest/java"
            getByName("androidTest").java.srcDirs(sharedTestDir)
            getByName("test").java.srcDirs(sharedTestDir)
        }
    }

    testOptions {
        unitTests.isIncludeAndroidResources = true
    }
}

androidExtensions {
    isExperimental = true
}

kapt {
    useBuildCache = true
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    // Kotlin
    implementation(Dependencies.kotlin_stdlib)
    // App compat & design
    implementation(Dependencies.support_app_compat)
    implementation(Dependencies.support_core)
    implementation(Dependencies.support_design)
    implementation(Dependencies.constraint_layout)
    implementation(Dependencies.play_core)
    // Rxjava
    implementation(Dependencies.rx_java)
    implementation(Dependencies.rx_android)
    // Coroutines
    implementation(Dependencies.coroutines_core)
    implementation(Dependencies.coroutines_android)
    // Room
    implementation(Dependencies.room_runtime)
    implementation(Dependencies.room_compiler)
    implementation(Dependencies.room_ktx)
    // Retrofit
    implementation(Dependencies.retrofit)
    implementation(Dependencies.retrofit_converter_gson)
    // Okhttp
    implementation(Dependencies.ok_http)
    implementation(Dependencies.ok_http_logging)
    // Koin
    implementation(Dependencies.koin_view_model)
    implementation(Dependencies.koin_ext)
    // Glide
    implementation(Dependencies.glide)
    annotationProcessor(Dependencies.glide_compiler)
    kapt(Dependencies.glide_compiler)
    // Leak canary
    debugImplementation(Dependencies.leak_canary)
    // Timber
    implementation(Dependencies.timber)
    // KTX
    implementation(Dependencies.support_core_ktx)
    implementation(Dependencies.view_model_ktx)
    implementation(Dependencies.live_data_ktx)

    // Navigation
    implementation(Dependencies.nav_fragment_ktx)
    implementation(Dependencies.nav_ui_ktx)

    // Pdf viewer
    implementation(Dependencies.pdf_viewer)

    // Swipe layout
    implementation(Dependencies.swipe_layout)

    //SqliteHelper
    implementation(Dependencies.sqlite)

    //Worker
    implementation(Dependencies.worker)

    //Event bus
    implementation(Dependencies.event_bus)

    implementation(Dependencies.avoid_duplicate_listenable_future)

    // Unit Test
    implementLocalUnitTest()
    implementInstrumentationUnitTest()
}
