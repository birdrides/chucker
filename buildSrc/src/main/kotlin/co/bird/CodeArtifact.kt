package co.bird

object CodeArtifact {
  const val PUBLIC_URL = "https://bird-168995956934.d.codeartifact.us-west-2.amazonaws.com/maven/maven/"
  const val PRIVATE_URL = "https://bird-168995956934.d.codeartifact.us-west-2.amazonaws.com/maven/maven-private/"

  /**
   * Cached authorization token. Fetched once per Gradle daemon lifecycle.
   * CodeArtifact tokens expire after 12 hours, but Gradle daemon typically restarts before then.
   * 
   * To manually refresh the token, stop the Gradle daemon: ./gradlew --stop
   * 
   * Note: This cache is not used by settings.gradle pluginManagement block, as buildSrc
   * is not available during that phase. All other repository blocks benefit from this cache.
   */
  private val cachedToken: String by lazy {
    println("Fetching CodeArtifact authorization token...")
    fetchAuthTokenFromAWS()
  }

  @JvmStatic
  fun getAuthToken(): String {
    return cachedToken
  }

  private fun fetchAuthTokenFromAWS(
    profile: String = "bird-svc",
    region: String = "us-west-2",
    domain: String = "bird",
    domainOwner: String = "168995956934"
  ): String {
    return ProcessBuilder(
      "aws", "codeartifact", "get-authorization-token",
      "--profile", profile,
      "--region", region,
      "--domain", domain,
      "--domain-owner", domainOwner,
      "--query", "authorizationToken",
      "--output", "text"
    )
      .start()
      .inputStream
      .bufferedReader()
      .readText()
      .trim()
  }
}
