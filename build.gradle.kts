import org.asciidoctor.gradle.AsciidoctorTask
import org.gradle.api.tasks.bundling.Jar
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.SpringBootPluginExtension

buildscript {

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:1.5.7.RELEASE")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.1.51")
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.1.51")
        classpath("org.asciidoctor:asciidoctor-gradle-plugin:1.5.3")
    }
}

plugins {
    val kotlinVersion = "1.1.51"
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
    id("io.spring.dependency-management") version "1.0.3.RELEASE"
    id("org.asciidoctor.convert") version "1.5.3"
}

apply {
    plugin("kotlin")
    plugin("kotlin-spring")
    plugin("kotlin-jpa")
    plugin("groovy")
    plugin("org.springframework.boot")
}

version = "1.0.0-SNAPSHOT"

project.extensions.configure(SpringBootPluginExtension::class.java){
    setExecutable(true)
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }
}

repositories {
    mavenCentral()
    maven("https://repo.spring.io/milestone")
    maven("https://repo.spring.io/snapshot")
}

dependencies {
    compile("com.auth0:auth0-spring-security-api:1.0.0-rc.3")
    compile("org.springframework.boot:spring-boot-starter-actuator")
    compile("org.springframework.boot:spring-boot-starter-security")
    compile("org.springframework.boot:spring-boot-starter-web")
    compile("org.springframework.boot:spring-boot-starter-data-mongodb")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jre8:1.1.51")
    compile("org.jetbrains.kotlin:kotlin-reflect:1.1.51")

    testCompile("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    testCompile("org.spockframework:spock-core:1.1-groovy-2.4")
    testCompile("org.spockframework:spock-spring:1.1-groovy-2.4")
    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("org.springframework.restdocs:spring-restdocs-mockmvc")

    asciidoctor("org.springframework.restdocs:spring-restdocs-asciidoctor:1.2.2.RELEASE")

}

val snippetsDir = file("build/generated-snippets")

tasks {
    "jar"(Jar::class) {
        dependsOn("asciidoctor")
        from("build/asciidoc/html5"){
            include("**/index.html")
            include("**/images/*")
            into("static/docs")
        }
    }

    "test"(Test::class) {
        outputs.dir(snippetsDir)
    }

    "asciidoctor"(AsciidoctorTask::class) {
        dependsOn("test")
        backends("html5")
        options = mapOf("doctype" to "book")

        attributes = mapOf(
                "source-highlighter" to "highlightjs",
                "imagesdir" to "./images",
                "toc" to "left",
                "toclevels" to 3,
                "numbered" to "",
                "icons" to "font",
                "setanchors" to "",
                "idprefix" to "",
                "idseparator" to "-",
                "docinfo1" to "",
                "safe-mode-unsafe" to "",
                "allow-uri-read" to "",
                "snippets" to snippetsDir,
                "linkattrs" to true,
                "encoding" to "utf-8"
        )

        inputs.dir(snippetsDir)
        outputDir = File("build/asciidoc")
        sourceDir = File("src/docs/asciidoc")

        sources(delegateClosureOf<PatternSet> {
            include("index.adoc")
        })
    }
}
