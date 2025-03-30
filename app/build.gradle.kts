plugins {
    id("java")
    id("org.springframework.boot") version "3.1.0"
    id("io.spring.dependency-management") version "1.1.7"
}

tasks.named<org.springframework.boot.gradle.tasks.run.BootRun>("bootRun") {
    mainClass.set("org.example.BewerbungsDemoApp")
}

group = "com.example.BewerbungsDemoApp"
version = "0.0.1_ds"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-logging")

    implementation("org.junit.jupiter:junit-jupiter:5.12.0")
    implementation("com.jayway.jsonpath:json-path")
    implementation("org.hibernate:hibernate-core:6.2.2.Final")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.21.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")

    runtimeOnly("com.h2database:h2")
    
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-parameters")
}