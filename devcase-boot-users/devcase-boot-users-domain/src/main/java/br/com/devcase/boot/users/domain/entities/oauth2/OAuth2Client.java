package br.com.devcase.boot.users.domain.entities.oauth2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="oauth_client_details")
public class OAuth2Client {

	@Id
	@Column(name="id")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	
	@NaturalId
	@Column(name="client_id")
	private String clientId;
	
	@Column(name="client_secret")
	private String clientSecret;
	
	@Column(length=300)
	private String name;
	@Column(name="resource_ids", length=1000)
	private String resourceIds;
	@Column(name="scope", length=1000)
	private String scope;
	@Column(name="authorized_grant_types", length=1000)
	private String authorizedGrantTypes;
	@Column(name="web_server_redirect_uri", length=1000)
	private String webServerRedirectUri;
	@Column(name="authorities", length=1000)
	private String authorities;
	@Column(name="access_token_validity", length=1000)
	private Integer accessTokenValidity;
	@Column(name="refresh_token_validity", length=1000)
	private Integer refreshTokenValidity;
	@Column(name="additional_information", length=1000)
	private String additionalInformation;
	@Column(name="autoapprove")
	private Boolean autoApprove;


	private OAuth2Client(Builder builder) {
		this.id = builder.id;
		this.clientId = builder.clientId;
		this.clientSecret = builder.clientSecret;
		this.name = builder.name;
		this.resourceIds = builder.resourceIds;
		this.scope = builder.scope;
		this.authorizedGrantTypes = builder.authorizedGrantTypes;
		this.webServerRedirectUri = builder.webServerRedirectUri;
		this.authorities = builder.authorities;
		this.accessTokenValidity = builder.accessTokenValidity;
		this.refreshTokenValidity = builder.refreshTokenValidity;
		this.additionalInformation = builder.additionalInformation;
		this.autoApprove = builder.autoApprove;
	}
	
	
	public OAuth2Client() {
		super();
	}




	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String secret) {
		this.clientSecret = secret;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(String resourceIds) {
		this.resourceIds = resourceIds;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getAuthorizedGrantTypes() {
		return authorizedGrantTypes;
	}

	public void setAuthorizedGrantTypes(String authorizedGrantTypes) {
		this.authorizedGrantTypes = authorizedGrantTypes;
	}

	public String getWebServerRedirectUri() {
		return webServerRedirectUri;
	}

	public void setWebServerRedirectUri(String webServerRedirectUri) {
		this.webServerRedirectUri = webServerRedirectUri;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public Integer getAccessTokenValidity() {
		return accessTokenValidity;
	}

	public void setAccessTokenValidity(Integer accessTokenValidity) {
		this.accessTokenValidity = accessTokenValidity;
	}

	public Integer getRefreshTokenValidity() {
		return refreshTokenValidity;
	}

	public void setRefreshTokenValidity(Integer refreshTokenValidity) {
		this.refreshTokenValidity = refreshTokenValidity;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public Boolean getAutoApprove() {
		return autoApprove;
	}

	public void setAutoApprove(Boolean autoApprove) {
		this.autoApprove = autoApprove;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	/**
	 * Creates builder to build {@link OAuth2Client}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link OAuth2Client}.
	 */
	public static final class Builder {
		private String id;
		private String clientId;
		private String clientSecret;
		private String name;
		private String resourceIds;
		private String scope;
		private String authorizedGrantTypes;
		private String webServerRedirectUri;
		private String authorities;
		private Integer accessTokenValidity;
		private Integer refreshTokenValidity;
		private String additionalInformation;
		private Boolean autoApprove;

		private Builder() {
		}

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withClientId(String clientId) {
			this.clientId = clientId;
			return this;
		}

		public Builder withClientSecret(String clientSecret) {
			this.clientSecret = clientSecret;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withResourceIds(String resourceIds) {
			this.resourceIds = resourceIds;
			return this;
		}

		public Builder withScope(String scope) {
			this.scope = scope;
			return this;
		}

		public Builder withAuthorizedGrantTypes(String authorizedGrantTypes) {
			this.authorizedGrantTypes = authorizedGrantTypes;
			return this;
		}

		public Builder withWebServerRedirectUri(String webServerRedirectUri) {
			this.webServerRedirectUri = webServerRedirectUri;
			return this;
		}

		public Builder withAuthorities(String authorities) {
			this.authorities = authorities;
			return this;
		}

		public Builder withAccessTokenValidity(Integer accessTokenValidity) {
			this.accessTokenValidity = accessTokenValidity;
			return this;
		}

		public Builder withRefreshTokenValidity(Integer refreshTokenValidity) {
			this.refreshTokenValidity = refreshTokenValidity;
			return this;
		}

		public Builder withAdditionalInformation(String additionalInformation) {
			this.additionalInformation = additionalInformation;
			return this;
		}

		public Builder withAutoApprove(Boolean autoApprove) {
			this.autoApprove = autoApprove;
			return this;
		}

		public OAuth2Client build() {
			return new OAuth2Client(this);
		}
	}

	

}
