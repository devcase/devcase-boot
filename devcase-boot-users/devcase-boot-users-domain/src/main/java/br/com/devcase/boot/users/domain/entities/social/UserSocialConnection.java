package br.com.devcase.boot.users.domain.entities.social;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name = "user_social_connection")
public class UserSocialConnection {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	@NaturalId
	@NotNull
	private String userId;
	@NaturalId
	@NotNull
	private String providerId;
	@NaturalId
	@NotNull
	private String providerUserId;
	private int rank;
	private String displayName;
	private String profileUrl;
	private String imageUrl;
	@Column(length=2000)
	private String accessToken;
	@Column(length=500)
	private String secret;
	@Column(length=2000)
	private String refreshToken;
	private Long expireTime;


	private UserSocialConnection(Builder builder) {
		this.id = builder.id;
		this.userId = builder.userId;
		this.providerId = builder.providerId;
		this.providerUserId = builder.providerUserId;
		this.rank = builder.rank;
		this.displayName = builder.displayName;
		this.profileUrl = builder.profileUrl;
		this.imageUrl = builder.imageUrl;
		this.accessToken = builder.accessToken;
		this.secret = builder.secret;
		this.refreshToken = builder.refreshToken;
		this.expireTime = builder.expireTime;
	}

	
	public UserSocialConnection() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getProviderUserId() {
		return providerUserId;
	}

	public void setProviderUserId(String providerUserId) {
		this.providerUserId = providerUserId;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public Long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Long expireTime) {
		this.expireTime = expireTime;
	}

	/**
	 * Creates builder to build {@link UserSocialConnection}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link UserSocialConnection}.
	 */
	public static final class Builder {
		private String id;
		private String userId;
		private String providerId;
		private String providerUserId;
		private int rank;
		private String displayName;
		private String profileUrl;
		private String imageUrl;
		private String accessToken;
		private String secret;
		private String refreshToken;
		private Long expireTime;

		private Builder() {
		}

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withUserId(String userId) {
			this.userId = userId;
			return this;
		}

		public Builder withProviderId(String providerId) {
			this.providerId = providerId;
			return this;
		}

		public Builder withProviderUserId(String providerUserId) {
			this.providerUserId = providerUserId;
			return this;
		}

		public Builder withRank(int rank) {
			this.rank = rank;
			return this;
		}

		public Builder withDisplayName(String displayName) {
			this.displayName = displayName;
			return this;
		}

		public Builder withProfileUrl(String profileUrl) {
			this.profileUrl = profileUrl;
			return this;
		}

		public Builder withImageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
			return this;
		}

		public Builder withAccessToken(String accessToken) {
			this.accessToken = accessToken;
			return this;
		}

		public Builder withSecret(String secret) {
			this.secret = secret;
			return this;
		}

		public Builder withRefreshToken(String refreshToken) {
			this.refreshToken = refreshToken;
			return this;
		}

		public Builder withExpireTime(Long expireTime) {
			this.expireTime = expireTime;
			return this;
		}

		public UserSocialConnection build() {
			return new UserSocialConnection(this);
		}
	}

}
