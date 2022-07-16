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
include(":core-model")
include(":core-database")
include(":core-datastore")
include(":download-video")
include(":feature-download")
include(":feature-filevideo")

