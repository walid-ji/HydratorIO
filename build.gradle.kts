plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.10.1"
}

group = "com.app"
version = "1.0.0"

repositories {
    mavenCentral()
}

// Configure Gradle IntelliJ Plugin
// Read more: https://plugins.jetbrains.com/docs/intellij/tools-gradle-intellij-plugin.html
intellij {
    version.set("2022.1.4")
    type.set("IC") // Target IDE Platform

    plugins.set(listOf(/* Plugin Dependencies */))
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }

    patchPluginXml {
        sinceBuild.set("221")
        untilBuild.set("231.*")
    }

    signPlugin {
        certificateChainFile.set(file("certificates/chain.crt"))
        privateKeyFile.set(file("certificates/private.pem"))
        password.set("walid")
    }

    publishPlugin {
        token.set("perm:d2FsaWQtamk=.OTItMTA5MzQ=.ylf5o3clmpOae9vGKg6UhWA7xpV2hk")
    }
}
