import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.jetbrainsCompose)
    alias(libs.plugins.compose.compiler)
//    id("app.cash.sqldelight") version "2.0.2"  // Use the latest version
    alias(libs.plugins.sqlDelight)

}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.sqldelight.coroutines)
            implementation(libs.sqldelight.runtime)

        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation(libs.sqldelight.driver.sqlite)

        }
    }
}


compose.desktop {
    application {
        mainClass = "compose.project.demo.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "compose.project.demo"
            packageVersion = "1.0.0"
        }
    }
}
sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("compose.project.demo.data.database")
        }
    }
}