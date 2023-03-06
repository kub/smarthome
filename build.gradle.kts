import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.7.5"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	kotlin("jvm") version "1.6.21"
	kotlin("plugin.spring") version "1.6.21"
}

group = "com.dirigera"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("com.squareup.retrofit2:retrofit:2.9.0")
	implementation("net.niebes:retrofit-metrics-micrometer:1.13.0")
	implementation("io.github.resilience4j:resilience4j-retrofit:1.7.1")
	implementation("com.squareup.retrofit2:converter-jackson:2.9.0")
	implementation("org.springframework.cloud:spring-cloud-square-okhttp:0.4.1")
	implementation("org.springframework.cloud:spring-cloud-square-retrofit:0.4.1")
//	runtimeOnly("io.micrometer:micrometer-registry-datadog")
	implementation("io.github.microutils:kotlin-logging-jvm:2.1.21")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "17"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.processResources {
	exclude("**/frontend")
}