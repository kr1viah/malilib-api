pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        gradlePluginPortal()
        maven("https://maven.kikugie.dev/snapshots") {
            name = "KikuGie Snapshots"
        }
        maven("https://maven.kikugie.dev/releases") {
            name = "KikuGie Releases"
        }
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.10-alpha.11"
    id("dev.kikugie.loom-back-compat") version "0.4.2"
}

stonecutter {
    create(rootProject) {
        versions(
                "26.3", "26.2", "26.1",
                "1.21.5", "1.21.11", "1.21.10", "1.21.8", "1.21",
                "1.20.6", "1.20.5", "1.20.4", "1.20.2", "1.20.1",
                "1.19.4",// 1.18.X and 1.19.X are all compatible with 1.19.4
                "1.17.1",
                "1.16.5",
                "1.15.2",
        )
        vcsVersion = "26.3"
    }
}
