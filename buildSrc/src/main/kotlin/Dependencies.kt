object Dependencies {
    object Androidx {
        const val core_ktx = "androidx.core:core-ktx:${Versions.core_ktx}"
        const val compose_ui = "androidx.compose.ui:ui:${Versions.compose_version}"
        const val material3 = "androidx.compose.material3:material3:${Versions.material3}"
        const val compose_preview = "androidx.compose.ui:ui-tooling-preview:${Versions.compose_version}"
        const val lifecycle_runtime_ktx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
        const val activity_compose = "androidx.activity:activity-compose:${Versions.activity_compose}"
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

}