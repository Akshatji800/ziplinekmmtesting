import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.kotlinCocoapods)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.zipline)
    alias(libs.plugins.kotlinSerialization)
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    applyDefaultHierarchyTemplate()

    js {
        browser()
        binaries.executable()
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "16.0"
        podfile = project.file("../ios/Podfile")
        framework {
            baseName = "presenters"
            isStatic = true
        }
    }

    kotlin.sourceSets.named("jsMain") {
        dependencies {
            implementation(npm("package-name", "version"))
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                api("app.cash.zipline:zipline:1.7.0")
            }
        }
        val hostMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation("app.cash.zipline:zipline-loader:1.7.0")
                api("com.squareup.okio:okio:3.7.0")
            }
        }

        val androidMain by getting {
            dependsOn(hostMain)
            dependencies {
                implementation("com.squareup.okhttp3:okhttp:4.9.2")
            }
        }
    }
}

android {
    namespace = "com.hyperboot.ziplinekmmtesting.presenters"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
}

zipline {
    mainFunction.set("com.hyperboot.ziplinekmmtesting.main")
}

plugins.withType<YarnPlugin> {
    the<YarnRootExtension>().yarnLockAutoReplace = true
}



