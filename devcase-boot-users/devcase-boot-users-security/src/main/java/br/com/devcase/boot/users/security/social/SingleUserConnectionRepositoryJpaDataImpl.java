package br.com.devcase.boot.users.security.social;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionKey;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.NoSuchConnectionException;
import org.springframework.social.connect.NotConnectedException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import br.com.devcase.boot.users.domain.entities.social.UserSocialConnection;
import br.com.devcase.boot.users.security.repositories.UserSocialConnectionRepository;

class SingleUserConnectionRepositoryJpaDataImpl implements ConnectionRepository {
	private final UserSocialConnectionRepository userSocialConnectionRepository;

	private final String userId;

	private final ConnectionFactoryLocator connectionFactoryLocator;

	private final TextEncryptor textEncryptor;

	public SingleUserConnectionRepositoryJpaDataImpl(UserSocialConnectionRepository userSocialConnectionRepository,
			String userId, ConnectionFactoryLocator connectionFactoryLocator, TextEncryptor textEncryptor) {
		super();
		this.userSocialConnectionRepository = userSocialConnectionRepository;
		this.userId = userId;
		this.connectionFactoryLocator = connectionFactoryLocator;
		this.textEncryptor = textEncryptor;
	}

	protected Connection<?> getConnection(UserSocialConnection it) {
		if (it == null)
			return null;
		ConnectionData cd = new ConnectionData(it.getProviderId(), it.getProviderUserId(), it.getDisplayName(),
				it.getProfileUrl(), it.getImageUrl(), textEncryptor.decrypt(it.getAccessToken()), it.getSecret(), it.getRefreshToken(),
				it.getExpireTime());
		return (Connection<?>) connectionFactoryLocator.getConnectionFactory(it.getProviderId()).createConnection(cd);
	}

	@Override
	public MultiValueMap<String, Connection<?>> findAllConnections() {

		Map<String, List<Connection<?>>> map = userSocialConnectionRepository
				.findByUserIdOrderByRank(userId).stream().map(this::getConnection)
				.collect(Collectors.groupingBy(it -> it.getKey().getProviderId()));

		return new LinkedMultiValueMap<>(map);
	}

	@Override
	public List<Connection<?>> findConnections(String providerId) {
		return userSocialConnectionRepository
				.findByUserIdAndProviderIdOrderByRank(userId, providerId).stream()
				.map(this::getConnection).collect(Collectors.toList());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> List<Connection<A>> findConnections(Class<A> apiType) {
		List<?> c = findConnections(getProviderId(apiType));
		return (List<Connection<A>>) c;
	}

	private <A> String getProviderId(Class<A> apiType) {
		return connectionFactoryLocator.getConnectionFactory(apiType).getProviderId();
	}

	@Override
	public MultiValueMap<String, Connection<?>> findConnectionsToUsers(MultiValueMap<String, String> providerUserIds) {
		MultiValueMap<String, Connection<?>> results = new LinkedMultiValueMap<>();

		providerUserIds.forEach((providerId, v) -> {
			v.forEach((providerUserId) -> {
				results.add(providerId, getConnection(userSocialConnectionRepository
						.findByUserIdAndProviderIdAndProviderUserId(userId, providerId, providerUserId)));
			});
		});
		return results;
	}

	@Override
	public Connection<?> getConnection(ConnectionKey connectionKey) {
		Connection<?> c = getConnection(userSocialConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(
				userId, connectionKey.getProviderId(), connectionKey.getProviderUserId()));
		if (c == null) {
			throw new NoSuchConnectionException(connectionKey);
		}
		return c;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <A> Connection<A> getConnection(Class<A> apiType, String providerUserId) {
		Object c = getConnection(userSocialConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(userId,
				getProviderId(apiType), providerUserId));
		if (c == null) {
			throw new NoSuchConnectionException(new ConnectionKey(getProviderId(apiType), providerUserId));
		}
		return (Connection<A>) c;
	}

	@Override
	public <A> Connection<A> getPrimaryConnection(Class<A> apiType) {
		List<Connection<A>> c = findConnections(apiType);
		if (c.size() > 0)
			return c.get(0);
		throw new NotConnectedException(getProviderId(apiType));
	}

	@Override
	public <A> Connection<A> findPrimaryConnection(Class<A> apiType) {
		List<Connection<A>> c = findConnections(apiType);
		if (c.size() > 0)
			return c.get(0);
		return null;
	}

	@Override
	public void addConnection(Connection<?> connection) {
		final ConnectionData cd = connection.createData();
		UserSocialConnection c = UserSocialConnection.builder().withUserId(userId).withProviderId(cd.getProviderId())
				.withProviderUserId(cd.getProviderUserId()).withDisplayName(cd.getDisplayName())
				.withProfileUrl(cd.getProfileUrl()).withImageUrl(cd.getImageUrl())
				.withAccessToken(textEncryptor.encrypt(cd.getAccessToken())).withExpireTime(cd.getExpireTime())
				.withRefreshToken(cd.getRefreshToken()).withSecret(cd.getSecret())
				.withRank(
						userSocialConnectionRepository.findMaxRankByUserIdAndProviderId(userId, cd.getProviderId()) + 1)
				.build();

		userSocialConnectionRepository.save(c);
	}

	@Override
	public void updateConnection(Connection<?> connection) {
		final ConnectionData cd = connection.createData();
		UserSocialConnection c = userSocialConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(userId,
				cd.getProviderId(), cd.getProviderUserId());

		c = UserSocialConnection.builder().withId(c.getId()).withUserId(userId).withProviderId(cd.getProviderId())
				.withProviderUserId(cd.getProviderUserId()).withDisplayName(cd.getDisplayName())
				.withProfileUrl(cd.getProfileUrl()).withImageUrl(cd.getImageUrl())
				.withAccessToken(textEncryptor.encrypt(cd.getAccessToken())).withExpireTime(cd.getExpireTime())
				.withRefreshToken(cd.getRefreshToken()).withSecret(cd.getSecret())
				.withRank(c.getRank())
				.build();

		userSocialConnectionRepository.save(c);
	}

	@Override
	public void removeConnections(String providerId) {
		userSocialConnectionRepository
				.deleteAll(userSocialConnectionRepository.findByUserIdAndProviderId(userId, providerId));
	}

	@Override
	public void removeConnection(ConnectionKey connectionKey) {
		userSocialConnectionRepository.delete(userSocialConnectionRepository.findByUserIdAndProviderIdAndProviderUserId(
				userId, connectionKey.getProviderId(), connectionKey.getProviderUserId()));
	}

}
