plugins {
    java
    application
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("org.graalvm.buildtools.native") apply true
    kotlin("jvm")
    kotlin("plugin.spring")
}

dependencies {
    // Spring Actuator
    implementation("org.springframework.boot:spring-boot-starter-actuator")
}

tasks.bootJar {
    archiveFileName.set("faq.jar")
    mainClass.set("santannaf.demo.agents.faq.ApplicationKt")
}