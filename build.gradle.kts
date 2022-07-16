// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id(Plugins.application) version Versions.gradle apply false
    id(Plugins.android_library) version Versions.gradle apply false
    kotlin(Plugins.kotlin_android) version Versions.kotlin apply false
    id(Plugins.ksp) version Versions.ksp apply false
    id(Plugins.hilt_project) version Versions.hilt apply false
    id(Plugins.maps_secret_gradle) version Versions.maps_secret_gradle apply false

}

tasks.register("clean", Delete::class.java) {
    delete(rootProject.buildDir)
}