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

    // OpenAI
    implementation("org.springframework.ai:spring-ai-starter-model-openai")

    // MCP Server - Investment Profile
    implementation("org.springframework.ai:spring-ai-starter-mcp-server-webmvc")
}

tasks.bootJar {
    archiveFileName.set("investment_profile.jar")
    mainClass.set("santannaf.demo.agents.investment.profile.ApplicationKt")
}
