plugins {
    `java-library`
    `maven-publish`
    idea
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("com.google.code.gson:gson:2.13.2")
    compileOnly("org.jspecify:jspecify:1.0.0")
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = Charsets.UTF_8.name()
    options.release = 25
    options.isFork = true
    options.compilerArgs.addAll(listOf("-Xlint:-deprecation", "-Xlint:-removal"))
}

tasks.withType<Javadoc>().configureEach {
    options.encoding = Charsets.UTF_8.name()
}

tasks.withType<ProcessResources>().configureEach {
    filteringCharset = Charsets.UTF_8.name()
}

java {
    withSourcesJar()
}

extensions.configure<PublishingExtension> {
    repositories {
        maven("https://maven.canvasmc.io/releases") {
            name = "canvasReleases"
            credentials {
                username = providers.environmentVariable("PUBLISH_USER").orNull
                password = providers.environmentVariable("PUBLISH_TOKEN").orNull
            }
        }
    }
    publications {
        register<MavenPublication>("mavenJava") {
            from(components["java"])
        }
    }
}
