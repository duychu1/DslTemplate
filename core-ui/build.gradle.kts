plugins {
    id(Plugins.android_library)
    kotlin(Plugins.kotlin_android)
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

    implementation(project(Modules.core_datastore))

    implementation(Dependencies.Androidx.core_ktx)
    api(Dependencies.Androidx.material3)
    api(Dependencies.Androidx.compose_ui_util)
    api(Dependencies.Androidx.compose_runtime)
    api(Dependencies.Androidx.compose_foundation)
    api(Dependencies.Androidx.compose_preview)
    implementation(Dependencies.store_review)
    implementation(Dependencies.exoplayer)


//    implementation("androidx.core:core-ktx:1.7.0")
//    implementation("androidx.appcompat:appcompat:1.4.2")
//    implementation("com.google.android.material:material:1.6.1")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
}