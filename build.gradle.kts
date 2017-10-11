import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {

    repositories {
        mavenCentral()
    }

    dependencies {
        classpath("org.springframework.boot:spring-boot-gradle-plugin:1.5.7.RELEASE")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.1.51")
        classpath("org.jetbrains.kotlin:kotlin-allopen:1.1.51")
    }
}

plugins {
    val kotlinVersion = "1.1.51"
    id("org.jetbrains.kotlin.jvm") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
    id("org.jetbrains.kotlin.plugin.noarg") version kotlinVersion
    id("io.spring.dependency-management") version "1.0.3.RELEASE"
}

apply {
    plugin("kotlin")
    plugin("kotlin-spring")
    plugin("kotlin-jpa")
    plugin("groovy")
    plugin("org.springframework.boot")
}

version = "1.0.0-SNAPSHOT"

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

    testCompile("org.springframework.boot:spring-boot-starter-test")
    testCompile("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
    testCompile("org.spockframework:spock-core:1.1-groovy-2.4")
    testCompile("org.spockframework:spock-spring:1.1-groovy-2.4")
}
