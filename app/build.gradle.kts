plugins {
    id(Plugins.application)
    kotlin(Plugins.kotlin_android)
    kotlin(Plugins.kotlin_kapt)
    id(Plugins.hilt_module)
}

android {
    compileSdk = Config.compileSdkVersion

    defaultConfig {
        applicationId = Config.applicationId
        minSdk = Config.minSdkVersion
        targetSdk = Config.targetSdkVersion
        versionCode = Config.versionCode
        versionName = Config.versionName

        testInstrumentationRunner = Config.testInstrumentationRunner
        vectorDrawables {
            useSupportLibrary =  true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android.txt"),
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

    packagingOptions {
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {

    implementation(project(Modules.core_ui))
    implementation(project(Modules.feature_download))
    implementation(project(Modules.feature_filevideo))


    implementation(Dependencies.Androidx.core_ktx)
    implementation(Dependencies.Androidx.activity_compose)
    implementation(Dependencies.Androidx.lifecycle_runtime_ktx)
    implementation(Dependencies.Androidx.compose_ui)
    implementation(Dependencies.Androidx.nav_compose)
    implementation(Dependencies.system_ui_controller)


    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.android_compiler)

    testImplementation(Dependencies.Test.junit)

    androidTestImplementation(Dependencies.AndroidTest.junit_ext)
    androidTestImplementation(Dependencies.AndroidTest.espresso)
    androidTestImplementation(Dependencies.AndroidTest.compose_ui)

    debugImplementation(Dependencies.Debug.ui_tooling)
    debugImplementation(Dependencies.Debug.ui_test_manifest)
}