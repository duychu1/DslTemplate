object Dependencies {
    object Androidx {
        const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
        const val compose_ui = "androidx.compose.ui:ui:${Versions.compose_version}"
        const val material3 = "androidx.compose.material3:material3:${Versions.material3}"
        const val compose_preview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose_version}"
        const val compose_ui_util = "androidx.compose.ui:ui-util:${Versions.compose_version}"
        const val compose_runtime = "androidx.compose.runtime:runtime:${Versions.compose_version}"
        const val compose_foundation = "androidx.compose.foundation:foundation:${Versions.compose_version}"
        const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        const val activity_compose = "androidx.activity:activity-compose:${Versions.activity_compose}"
        const val lifecycle_vm_compose = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.lifecycle}"
        const val constraintlayout_compose = "androidx.constraintlayout:constraintlayout-compose:${Versions.constraintlayout_compose}"
        const val nav_compose = "androidx.navigation:navigation-compose:${Versions.nav_compose}"
        const val datastore_prefer = "androidx.datastore:datastore-preferences:${Versions.datastore_prefer}"
        const val room_compiler = "androidx.room:room-compiler:${Versions.room}"
        const val room_ktx = "androidx.room:room-ktx:${Versions.room}"


    }

    object Test {
        const val junit = "junit:junit:${Versions.junit}"
    }

    object AndroidTest {
        const val junit_ext = "androidx.test.ext:junit:${Versions.junit_ext}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val compose_ui = "androidx.compose.ui:ui-test-junit4:${Versions.compose_version}"

    }

    object Debug {
        const val ui_tooling = "androidx.compose.ui:ui-tooling:${Versions.compose_version}"
        const val ui_test_manifest = "androidx.compose.ui:ui-test-manifest:${Versions.compose_version}"
    }

    object Hilt {
        const val android = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val android_compiler = "com.google.dagger:hilt-android-compiler:${Versions.hilt}"
        const val nav_compose = "androidx.hilt:hilt-navigation-compose:${Versions.hilt_compose}"
        const val compiler = "androidx.hilt:hilt-compiler:${Versions.hilt_compose}"
    }

    const val exoplayer = "com.google.android.exoplayer:exoplayer:${Versions.exoplayer}"
    const val store_review = "com.google.android.play:review-ktx:${Versions.store_review}"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"

}