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

    // AWS BedRock
    implementation("org.springframework.ai:spring-ai-starter-model-bedrock")
    implementation("org.springframework.ai:spring-ai-starter-model-bedrock-converse")

    // MCP Server - Investment CDB
    implementation("org.springframework.ai:spring-ai-starter-mcp-server-webmvc")
}

tasks.bootJar {
    archiveFileName.set("credit_card.jar")
    mainClass.set("santannaf.demo.agents.credit_card.limit.ApplicationKt")
}
