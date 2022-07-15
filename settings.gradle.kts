include(":core-ui")

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "ReelSave"
include(":app")
include(":core-ui")
include(":core-database")
include(":core-model")
include(":download-video")
include(":core-di")
include(":feature-download")
include(":feature-filevideo")
include(":core-datastore")
