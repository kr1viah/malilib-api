plugins {
    id("maven-publish")
    id("me.modmuss50.mod-publish-plugin") version "1.1.0"
    id("dev.kikugie.loom-back-compat")
}

fun prop(name: String): String = property(name) as String

fun requireEnv(name: String): String =
    providers.environmentVariable(name).orNull?.takeIf { it.isNotEmpty() }
        ?: throw GradleException("Missing $name")

val releaseGithub = rootProject.extra["releaseGithub"] as Boolean
val releaseModrinth = rootProject.extra["releaseModrinth"] as Boolean
val releaseMaven = rootProject.extra["releaseMaven"] as Boolean
val releaseCurseForge =  rootProject.extra["releaseCurseForge"] as Boolean

val requiredJava: JavaVersion = when {
    sc.current.parsed >= "26.1" -> JavaVersion.VERSION_25
    sc.current.parsed >= "1.20.5" -> JavaVersion.VERSION_21
    sc.current.parsed >= "1.18" -> JavaVersion.VERSION_17
    sc.current.parsed >= "1.17" -> JavaVersion.VERSION_16
    else -> JavaVersion.VERSION_1_8
}

val testmod = sourceSets.create("testmod") {
    compileClasspath += sourceSets.main.get().output + sourceSets.main.get().compileClasspath
    runtimeClasspath += sourceSets.main.get().output + sourceSets.main.get().runtimeClasspath
}

version = "${property("mod_version")}+${sc.current.version}"
base.archivesName = "malilib-api"

repositories {
    fun maven(_name: String, url: String) {
        maven(url) { name = _name }
    }
    maven("Terraformers", "https://maven.terraformersmc.com/releases/")
    maven("masa", "https://masa.dy.fi/maven/")
    maven("sakura-ryoko", "https://masa.dy.fi/maven/sakura-ryoko")
    maven("kr1v", "https://repo.repsy.io/kr1v/maven/")
    maven("fallen breath", "https://maven.fallenbreath.me/releases")
    maven("RelativityMC", "https://repo.codemc.io/repository/relativitymc/")
    maven("Modrinth", "https://api.modrinth.com/maven")
    mavenLocal()
    mavenCentral()
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft_version")}")
    loomx.applyMojangMappings()
    modImplementation("net.fabricmc:fabric-loader:${property("loader_version")}")

    modImplementation("${property("malilib")}") {
        exclude(group = "io.github.prospector")
    }

    modCompileOnly("maven.modrinth:modmenu:${property("modmenu_version")}")
    implementation("com.google.code.gson:gson:${property("gson_version")}")

    include(implementation("net.kr1v:malilib-api-processor:1.0.0")!!)
    "testmodAnnotationProcessor"("net.kr1v:malilib-api-processor:1.0.0")
}

loom {
    fabricModJsonPath.set(rootProject.file("src/main/resources/fabric.mod.json"))
    runConfigs.configureEach {
        ideConfigGenerated(false)
//        runDir("../../run")
    }
    decompilerOptions.named("vineflower") {
        options.put("mark-corresponding-synthetics", "1")
    }
    runs {
        register("testmodClient") {
            client()
            configName = "Testmod Client"
            source(testmod)
        }
    }
    mods {
        register("malilib-api") {
            sourceSet(sourceSets.main.get())
        }
        register("malilib-api-test") {
            sourceSet(testmod)
        }
    }

    runConfigs.all {
        preferGradleTask = true
        generateRunConfig = true
        runDirectory = rootProject.file("run")
        jvmArguments.add("-Dmixin.debug.export=true")
    }
}

java {
    withSourcesJar()
    sourceCompatibility = requiredJava
    targetCompatibility = requiredJava
}

tasks {
    processResources {
        val versions = when (sc.current.version) {
            "1.15.2" -> ">=1.14 <=1.15.2"
            "1.16.5" -> ">=1.16 <=1.16.5"
            "1.17.1" -> ">=1.17 <=1.17.1"
            "1.19.4" -> ">=1.18 <=1.19.4"
            "1.20.1" -> ">=1.20 <=1.20.1"
            "1.20.4" -> ">=1.20.3 <=1.20.4"
            "1.21" -> ">=1.21 <=1.21.1"
            "1.21.5" -> ">=1.21.2 <=1.21.5"
            "1.21.8" -> ">=1.21.6 <=1.21.8"
            "1.21.10" -> ">=1.21.9 <=1.21.10"
            "26.1" -> "~26.1"
            else -> sc.current.version
        }
        val projectVersion = project.version.toString()
        val loaderVersion = prop("loader_version")

        inputs.property("versions", versions)
        inputs.property("version", projectVersion)
        inputs.property("minecraft_version", { property("minecraft_version") })
        inputs.property("loader_version", loaderVersion)
        filteringCharset = "UTF-8"

        filesMatching("fabric.mod.json") {
            expand(
                "version" to projectVersion,
                "minecraft_version" to "${property("minecraft_version")}",
                "loader_version" to loaderVersion,
                "versions" to versions,
            )
        }
        val mixinJava = "JAVA_${requiredJava.majorVersion}"
        filesMatching("*.mixins.json") {
            expand("java" to mixinJava)
        }
    }

    register<Copy>("buildAndCollect") {
        group = "build"
        description = "Builds mod jars and copies results to `build/libs/{mod version}/`"

        inputs.property("version", project.property("mod_version"))
        from(loomx.modJar.flatMap { it.archiveFile }, loomx.modSourcesJar.flatMap { it.archiveFile })
        into(rootProject.layout.buildDirectory.file("libs/${project.property("mod_version")}"))
    }
}

publishMods {
    file.set(loomx.modJar.get().archiveFile)
    modLoaders.add("fabric")

    val (startV, endV) = when (sc.current.version) {
        "1.15.2" -> "1.14" to "1.15.2"
        "1.16.5" -> "1.16" to "1.16.5"
        "1.17.1" -> "1.17" to "1.17.1"
        "1.19.4" -> "1.18" to "1.19.4"
        "1.20.1" -> "1.20" to "1.20.1"
        "1.20.4" -> "1.20.3" to "1.20.4"
        "1.21" -> "1.21" to "1.21.1"
        "1.21.5" -> "1.21.2" to "1.21.5"
        "1.21.8" -> "1.21.6" to "1.21.8"
        "1.21.10" -> "1.21.9" to "1.21.10"
        "26.1" -> "26.1" to "26.1.2"
        else -> sc.current.version to null
    }

    if (releaseGithub) {
        github {
            accessToken = providers.environmentVariable("GITHUB_TOKEN")

            parent(project(":").tasks.named("publishGithub"))
        }
    }

    if (releaseCurseForge) {
        curseforge {
            accessToken = providers.environmentVariable("CURSEFORGE_TOKEN")
            projectId = "1471872"

            type = STABLE

            if (endV != null) {
                minecraftVersionRange {
                    start = startV
                    end = endV
                }
            } else {
                minecraftVersions.add(startV)
            }

            javaVersions.add(requiredJava)

            clientRequired = true
            serverRequired = false

            requires("malilib")

            changelog = rootProject.file("changelog.md").readText()
            changelogType = "markdown"
        }
    }

    if (releaseModrinth) {
        modrinth {
            accessToken = providers.environmentVariable("MODRINTH_TOKEN")
            projectId = "Qppvg0Y6"
            changelog = rootProject.file("changelog.md").readText()
            type = STABLE

            if (endV != null) {
                minecraftVersionRange {
                    start = startV
                    end = endV

                    includeSnapshots = true
                }
            } else {
                minecraftVersions.add(startV)
            }

            requires("malilib")
        }
    }
}

publishing {
    publications {
        register<MavenPublication>("mavenJava") {
            groupId = "net.kr1v"
            artifactId = "${property("archives_base_name")}"
            version = "${property("mod_version")}-${property("minecraft_version")}"

            from(components["java"])
        }
    }
    repositories {
        if (releaseMaven) {
            mavenLocal()
            val repsyUser = requireEnv("REPSY_USERNAME")
            val repsyToken = requireEnv("REPSY_TOKEN")
            maven {
                name = "repsy"
                url = uri("https://repo.repsy.io/kr1v/maven/")
                credentials {
                    username = repsyUser
                    password = repsyToken
                }
            }
        }
    }
}