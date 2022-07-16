plugins {
    id(Plugins.android_library)
    kotlin(Plugins.kotlin_android)
    id(Plugins.ksp)
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
}

dependencies {

    implementation(Dependencies.Androidx.room_ktx)
    ksp(Dependencies.Androidx.room_compiler)

    implementation(Dependencies.Hilt.android)
    kapt(Dependencies.Hilt.android_compiler)

}