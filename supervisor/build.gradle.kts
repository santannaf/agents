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

    // MCP Client - Supervisor
    implementation("org.springframework.ai:spring-ai-starter-mcp-client")
}

tasks.bootJar {
    archiveFileName.set("supervisor.jar")
    mainClass.set("santannaf.demo.agents.supervisor.ApplicationKt")
}
