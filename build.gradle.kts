plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version("7.1.2")
}

group = "io.github.thatsmusic99"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://ci.pluginwiki.us/plugin/repository/everything/")
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    compileOnly("com.velocitypowered:velocity-api:3.1.1")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.1")

    implementation("com.github.thatsmusic99:ConfigurationMaster-API:v2.0.0-BETA-5")
    implementation("net.kyori:adventure-text-minimessage:4.13.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}