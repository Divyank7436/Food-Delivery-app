// Top-level build file where you can add configuration options common to all sub-projects/modules.


buildscript {
    repositories {
        // Make sure that you have the following two repositories
        google()  // Google's Maven repository

        mavenCentral()  // Maven Central repository

    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.0.4")
        // Add the dependency for the Google services Gradle plugin
        classpath ("com.google.gms:google-services:4.3.15")

    }
}


plugins {
    id("com.android.application") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
}