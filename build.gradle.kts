buildscript {
    apply(from = "$rootDir/team-props/git-hooks.gradle.kts")

    repositories {
        google()
        jcenter()
        mavenCentral()
    }
    dependencies {
        classpath(ClassPaths.android_gradle_plugin)
        classpath(ClassPaths.kotlin_gradle_plugin)
        classpath(ClassPaths.nav_safe_args)
//        classpath(ClassPaths.google_service)
//        classpath(ClassPaths.crashlytics)
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven ("https://jitpack.io")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
