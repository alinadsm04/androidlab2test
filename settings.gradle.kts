pluginManagement {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/alinadsm04/chat")

            credentials {
                username = "alinadsm04"
                password = "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"
            }
        }
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)

    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/alinadsm04/chat")
            credentials {
                username = "alinadsm04"
                password = "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"
            }
        }
        google()
        mavenCentral()
    }
}

rootProject.name = "My Application"
include(":app")
include(":chat")
