// Project-level build.gradle.kts

plugins {
    // from your version catalog (Android Studio created these)
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // KSP for Room (Kotlin 2.0.21)
    id("com.google.devtools.ksp") version "2.0.21-1.0.27" apply false
}
