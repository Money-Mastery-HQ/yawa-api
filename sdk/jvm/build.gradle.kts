plugins {
    kotlin("jvm") version "1.7.10" apply false
}

subprojects {
    group = "org.yawa.sdk"

    apply(plugin = "kotlin")
    apply(plugin = "maven-publish")
    configure<PublishingExtension> {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/kascoder/yawa-api")
                credentials {
                    username = System.getenv("GITHUB_ACTOR")
                    password = System.getenv("GITHUB_TOKEN")
                }
            }
        }

        publications {
            register<MavenPublication>("default") {
                from(components["java"])
            }
        }
    }
}
