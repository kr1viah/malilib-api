plugins {
    id("dev.kikugie.stonecutter")
    id("net.fabricmc.fabric-loom-remap") version "1.18-SNAPSHOT" apply false
    id("me.modmuss50.mod-publish-plugin") version "1.1.0"
}

stonecutter active "26.3"

fun envFlag(name: String): Boolean = providers.environmentVariable(name).getOrElse("false").toBoolean()

fun requireEnv(name: String): String =
    providers.environmentVariable(name).orNull?.takeIf { it.isNotEmpty() }
        ?: throw GradleException("Missing $name")

val releaseGithub = envFlag("RELEASE_GITHUB")
val releaseModrinth = envFlag("RELEASE_MODRINTH")
val releaseMaven = envFlag("RELEASE_MAVEN")
val releaseCurseForge = envFlag("RELEASE_CURSEFORGE")

extra["releaseGithub"] = releaseGithub
extra["releaseModrinth"] = releaseModrinth
extra["releaseMaven"] = releaseMaven
extra["releaseCurseForge"] = releaseCurseForge

stonecutter.tasks {
    if (releaseModrinth) order("publishModrinth")
    if (releaseGithub) order("publishGithub")
    if (releaseCurseForge) order("publishCurseforge")
    if (releaseMaven) order("publish")
}

version = "${property("mod_version")}+${stonecutter.active?.version}"

publishMods {
    modLoaders.add("fabric")
    type = STABLE

    if (releaseModrinth) requireEnv("MODRINTH_TOKEN")
    if (releaseCurseForge) requireEnv("CURSEFORGE_TOKEN")

    if (releaseGithub) {
        requireEnv("GITHUB_TOKEN")

        github {
            accessToken = providers.environmentVariable("GITHUB_TOKEN")
            repository = providers.environmentVariable("GITHUB_REPO")
            commitish = "master"
            tagName = "${rootProject.property("mod_version")}"
            displayName = "${rootProject.property("mod_version")}"
            changelog = file("changelog.md").readText()
            allowEmptyFiles = true
        }
    }
}