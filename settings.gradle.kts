pluginManagement {
    // Fetched once per build and shared with every build script through the gradle extra properties.
    // CI can export CODEARTIFACT_AUTH_TOKEN to skip the aws call entirely.
    val codeArtifactToken: String =
        providers
            .environmentVariable("CODEARTIFACT_AUTH_TOKEN")
            .orElse(
                providers
                    .exec {
                        commandLine(
                            "aws", "codeartifact", "get-authorization-token",
                            "--domain", "bird",
                            "--domain-owner", "168995956934",
                            "--region", "us-west-2",
                            "--query", "authorizationToken",
                            "--output", "text",
                            "--profile", "bird-svc",
                        )
                    }.standardOutput.asText
                    .map { it.trim() },
            ).get()
    (gradle as ExtensionAware).extra["codeArtifactToken"] = codeArtifactToken

    repositories {
        maven {
            name = "codeartifact"
            url = uri("https://bird-168995956934.d.codeartifact.us-west-2.amazonaws.com/maven/maven/")
            credentials {
                username = "aws"
                password = codeArtifactToken
            }
        }
        gradlePluginPortal()
    }
}
plugins {
    // Auto-provisions the JDK 21 toolchain required by the library modules when it isn't installed.
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven {
            name = "codeartifact"
            url = uri("https://bird-168995956934.d.codeartifact.us-west-2.amazonaws.com/maven/maven/")
            credentials {
                username = "aws"
                password = (gradle as ExtensionAware).extra["codeArtifactToken"] as String
            }
        }
    }
}
include(":sample", ":library", ":library-no-op")
