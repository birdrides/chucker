plugins {
  `kotlin-dsl`
}

group = "co.bird"

repositories {
  maven {
    name = "codeartifact"
    url = uri("https://bird-168995956934.d.codeartifact.us-west-2.amazonaws.com/maven/maven/")
    credentials {
      username = "aws"
      password = ProcessBuilder(
        "aws", "codeartifact", "get-authorization-token",
        "--domain", "bird",
        "--domain-owner", "168995956934",
        "--region", "us-west-2",
        "--query", "authorizationToken",
        "--output", "text",
        "--profile", "bird-svc"
      )
        .start()
        .inputStream
        .bufferedReader()
        .readText()
        .trim()
    }
  }
  maven(url = "https://artifactory.svc.bird.co/artifactory/bird")
}
