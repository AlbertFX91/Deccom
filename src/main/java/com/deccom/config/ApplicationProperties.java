package com.deccom.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to JHipster.
 * <p>
 * Properties are configured in the application.yml file.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

	private String twitterConsumerKey;
	private String twitterConsumerSecret;
	private String facebookApplicationKey;
	private String facebookSecretKey;

	public String getTwitterConsumerKey() {
		return twitterConsumerKey;
	}

	public void setTwitterConsumerKey(String twitterConsumerKey) {
		this.twitterConsumerKey = twitterConsumerKey;
	}

	public String getTwitterConsumerSecret() {
		return twitterConsumerSecret;
	}

	public void setTwitterConsumerSecret(String twitterConsumerSecret) {
		this.twitterConsumerSecret = twitterConsumerSecret;
	}

	public String getFacebookApplicationKey() {
		return facebookApplicationKey;
	}

	public void setFacebookApplicationKey(String facebookApplicationKey) {
		this.facebookApplicationKey = facebookApplicationKey;
	}

	public String getFacebookSecretKey() {
		return facebookSecretKey;
	}

	public void setFacebookSecretKey(String facebookSecretKey) {
		this.facebookSecretKey = facebookSecretKey;
	}

}
