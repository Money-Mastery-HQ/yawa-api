plugins {
    kotlin("plugin.spring") version "1.5.31"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("org.openapi.generator") version "5.2.1"
}

version = "0.9-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    val kotlinVersion = "1.5.31"
    val jacksonVersion = "2.13.0"

    implementation(kotlin("reflect", kotlinVersion))
    implementation(kotlin("stdlib-jdk8", kotlinVersion))
    implementation("javax.validation:validation-api:2.0.1.Final")
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.springframework:spring-webmvc:5.3.10")
    implementation("io.swagger:swagger-core:1.6.3")
    implementation("com.fasterxml.jackson.core:jackson-annotations:$jacksonVersion")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}

tasks.compileKotlin {
    dependsOn("openApiGenerate")
}

sourceSets.main {
    java.srcDir("build/generate/src/main/kotlin")
}

tasks.openApiGenerate {
    generatorName.set("kotlin-spring")
    inputSpec.set("$rootDir/../../openapi-schema.yaml")
    outputDir.set("$buildDir/generate")
    generateApiTests.set(false)
    generateApiDocumentation.set(false)
    generateModelDocumentation.set(false)
    apiPackage.set("org.yawa.server.api.resource")
    modelPackage.set("org.yawa.server.api.model")
    configOptions.set(
        mapOf(
            "interfaceOnly" to "true",
            "gradleBuildFile" to "false",
            "exceptionHandler" to "false",
            "enumPropertyNaming" to "UPPERCASE",
            "swaggerAnnotations" to "true",
            "useBeanValidation" to "false",
        )
    )
    typeMappings.set(
        mapOf(
            "float" to "java.math.BigDecimal",
        )
    )
}
