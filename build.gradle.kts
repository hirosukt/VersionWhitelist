plugins {
    kotlin("jvm") version "1.8.10"
    id("com.github.johnrengelman.shadow") version "8.1.0"
    id("org.jlleitschuh.gradle.ktlint") version "10.3.0"
    `maven-publish`
    `kotlin-dsl`
}

group = "love.chihuyu"
version = "0.0.1"
val pluginVersion: String by project.ext

repositories {
    mavenCentral()
    maven("https://repo.codemc.org/repository/maven-public/")
    maven("https://repo.purpurmc.org/snapshots")
    maven("https://repo.hirosuke.me/repository/maven-public/")
}

dependencies {
    compileOnly("org.purpurmc.purpur:purpur-api:$pluginVersion-R0.1-SNAPSHOT")
    implementation("love.chihuyu:chihuyulib:0.1.0")
    implementation("dev.jorel:commandapi-core:8.7.6")
    implementation("dev.jorel:commandapi-kotlin:8.7.6")
    implementation("org.yaml:snakeyaml:2.0")
    implementation(kotlin("stdlib"))
}

ktlint {
    ignoreFailures.set(true)
    disabledRules.add("no-wildcard-imports")
}

tasks {
    test {
        useJUnitPlatform()
    }

    processResources {
        duplicatesStrategy = DuplicatesStrategy.INCLUDE

        from(sourceSets.main.get().resources.srcDirs) {
            filter(org.apache.tools.ant.filters.ReplaceTokens::class, mapOf("tokens" to mapOf(
                "version" to project.version.toString(),
                "name" to project.name,
                "mainPackage" to "love.chihuyu.${project.name.lowercase()}.${project.name}Plugin"
            )))
            filteringCharset = "UTF-8"
        }
    }

    shadowJar {
        val loweredProject = project.name.lowercase()
        dependencies {
            include("love.chihuyu:chihuyulib:0.1.0")
            include("org.jetbrains.kotlin:kotlin-stdlib")
            include("dev.jorel:commandapi-core:8.7.6")
            include("dev.jorel:commandapi-kotlin:8.7.6")
        }
        exclude("org/slf4j/**")
        relocate("love.chihuyu", "love.chihuyu.$loweredProject.lib.love.chihuyu")
        relocate("org.snakeyaml", "love.chihuyu.$loweredProject.lib.org.snakeyaml")
        relocate("kotlin", "love.chihuyu.$loweredProject.lib.kotlin")
        relocate("dev.jorel.commandapi", "love.chihuyu.$loweredProject.lib.dev.jorel.commandapi")
    }
}

publishing {
    repositories {
        maven {
            name = "repo"
            credentials(PasswordCredentials::class)
            url = uri("https://repo.hirosuke.me/repository/maven-central/")
        }
    }

    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}

kotlin {
    jvmToolchain(18)
}

open class SetupTask : DefaultTask() {

    @TaskAction
    fun action() {
        val projectDir = project.projectDir
        val srcDir = projectDir.resolve("src/main/kotlin/love/chihuyu/${project.name.lowercase()}").apply(File::mkdirs)
        srcDir.resolve("${project.name}Plugin.kt").writeText(
            """
                package love.chihuyu.${project.name.lowercase()}
                
                import org.bukkit.plugin.java.JavaPlugin

                class ${project.name}Plugin: JavaPlugin() {
                    companion object {
                        lateinit var ${project.name}Plugin: JavaPlugin
                    }
                
                    init {
                        ${project.name}Plugin = this
                    }
                }
            """.trimIndent()
        )
    }
}

task<SetupTask>("setup")