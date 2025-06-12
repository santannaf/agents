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

    // MCP Server - Investment CDB
    implementation("org.springframework.ai:spring-ai-starter-mcp-server-webmvc")

    // PG Vector RAG
    implementation("org.springframework.ai:spring-ai-starter-vector-store-pgvector")
    implementation("org.springframework.ai:spring-ai-advisors-vector-store")
    implementation("org.springframework.ai:spring-ai-tika-document-reader")
}

tasks.bootJar {
    archiveFileName.set("faq.jar")
    mainClass.set("santannaf.demo.agents.faq.ApplicationKt")
}
