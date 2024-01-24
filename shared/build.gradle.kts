plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true

            export(libs.decompose.navigation)

            export(libs.essenty.lifecycle)

            // Optional, only if you need state preservation on Darwin (Apple) targets
            export(libs.essenty.stateKeeper)
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                api(libs.kotlin.serialization)
                api(libs.kotlin.coroutines)
                api(libs.decompose.navigation)
            }
        }
    }
}

android {
    namespace = "com.gra_dus.uioschildsampleerror"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
}