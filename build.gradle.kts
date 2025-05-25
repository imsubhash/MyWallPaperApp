// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    id("com.google.devtools.ksp") version "2.1.20-2.0.0" apply false
    id("com.google.dagger.hilt.android") version "2.56.2" apply false
    id("androidx.room") version "2.7.1" apply false
}

buildscript {
    dependencies {
        classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.9.0")
    }
    repositories {
        google()
        mavenCentral()

    }
}