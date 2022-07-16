plugins {
    id(Plugins.android_library)
    kotlin(Plugins.kotlin_android)
    kotlin(Plugins.kotlin_kapt)
    id(Plugins.hilt_module)
}

android {
    compileSdk = Config.targetSdkVersion

    defaultConfig {
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion

        testInstrumentationRunner = Config.testInstrumentationRunner
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = Config.jvmTarget
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_version
    }
}

dependencies {
    implementation(project(Modules.core_ui))
    implementation(project(Modules.core_model))
    implementation(project(Modules.core_datastore))

    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.android_compiler)
}