// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.application) version Versions.gradle apply false
    id(Plugins.android_library) version Versions.gradle apply false
    kotlin(Plugins.kotlin_android) version Versions.kotlin apply false
}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}