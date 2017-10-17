package br.com.devcase.boot.users.security.social;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.ConnectionSignUp;
import org.springframework.social.connect.UsersConnectionRepository;

import br.com.devcase.boot.users.repositories.social.UserSocialConnectionRepository;

class UsersConnectionRepositoryJpaDataImpl implements UsersConnectionRepository {
	@Autowired
	private UserSocialConnectionRepository userSocialConnectionRepository;
	@Autowired
	private ConnectionFactoryLocator connectionFactoryLocator;
	@Autowired
	private TextEncryptor textEncryptor;
	@Autowired(required=false)
	private ConnectionSignUp connectionSignUp;

	@Override
	public List<String> findUserIdsWithConnection(Connection<?> connection) {
		List<String> localUserIds = userSocialConnectionRepository.findUserIdsByProviderIdAndProviderUserId(
				connection.getKey().getProviderId(), connection.getKey().getProviderUserId());
		if (localUserIds.size() == 0 && connectionSignUp != null) {
			String newUserId = connectionSignUp.execute(connection);
			if (newUserId != null) {
				createConnectionRepository(newUserId).addConnection(connection);
				return Arrays.asList(newUserId);
			}
		}
		return localUserIds;
	}

	@Override
	public Set<String> findUserIdsConnectedTo(String providerId, Set<String> providerUserIds) {
		return userSocialConnectionRepository.findUserIdsByProviderIdAndProviderUserIds(providerId, providerUserIds);
	}

	@Override
	public ConnectionRepository createConnectionRepository(String userId) {
		return new SingleUserConnectionRepositoryJpaDataImpl(userSocialConnectionRepository, userId,
				connectionFactoryLocator, textEncryptor);
	}

	@Override
	public void setConnectionSignUp(ConnectionSignUp connectionSignUp) {
		this.connectionSignUp = connectionSignUp;

	}

}
