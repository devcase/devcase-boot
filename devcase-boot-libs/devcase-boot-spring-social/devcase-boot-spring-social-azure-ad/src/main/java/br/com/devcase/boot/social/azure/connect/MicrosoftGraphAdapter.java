package br.com.devcase.boot.social.azure.connect;

import java.util.HashMap;
import java.util.Map;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.web.client.RestOperations;

public class MicrosoftGraphAdapter implements ApiAdapter<RestOperations> {
	
	

	public MicrosoftGraphAdapter() {
		super();
	}

	@Override
	public boolean test(RestOperations api) {
		fetchUserProfile(api);
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void setConnectionValues(RestOperations api, ConnectionValues values) {
		HashMap<String, String> uriParams = new HashMap<>();
		uriParams.put("$select", "displayName,id,mySite");
		Map<String, String> r = api.getForObject("https://graph.microsoft.com/v1.0/me", Map.class, uriParams);

		values.setProviderUserId(r.get("id"));
		values.setDisplayName(r.get("displayName"));
		values.setProfileUrl(r.get("mySite"));
	}

	@Override
	@SuppressWarnings("unchecked")
	public UserProfile fetchUserProfile(RestOperations api) {
		Map<String, String> r = api.getForObject("https://graph.microsoft.com/v1.0/me", Map.class);
		UserProfile p = new UserProfile(r.get("id"), 
				r.get("displayName"), 
				r.get("givenName"), 
				r.get("surname"), 
				r.get("mail"), 
				r.get("userPrincipalName"));
		return p;
	}

	@Override
	public void updateStatus(RestOperations api, String message) {
		// TODO Auto-generated method stub
		
	}

}
