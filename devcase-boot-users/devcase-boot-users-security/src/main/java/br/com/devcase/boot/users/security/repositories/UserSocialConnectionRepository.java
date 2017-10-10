package br.com.devcase.boot.users.security.repositories;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import br.com.devcase.boot.users.domain.entities.social.UserSocialConnection;

@RepositoryRestResource(exported=true)
public interface UserSocialConnectionRepository extends CrudRepository<UserSocialConnection, String> {
	
	@Query(value="select u.userId from UserSocialConnection u where u.providerId = :providerId and u.providerUserId = :providerUserId  order by rank")
	List<String> findUserIdsByProviderIdAndProviderUserId(@Param("providerId") String providerId, @Param("providerUserId") String providerUserId);

	@Query(value="select u.userId from UserSocialConnection u where u.providerId = :providerId and u.providerUserId in (:providerUserIds) order by rank")
	Set<String> findUserIdsByProviderIdAndProviderUserIds(@Param("providerId") String providerId,  @Param("providerUserIds")  Set<String> providerUserIds);

	
	List<UserSocialConnection> findByUserIdOrderByRank(String userId);
	List<UserSocialConnection> findByUserIdAndProviderId(String userId, String providerId);
	List<UserSocialConnection> findByUserIdAndProviderIdOrderByRank(String userId, String providerId);
	UserSocialConnection findByUserIdAndProviderIdAndProviderUserId(String userId, String providerId, String providerUserId);
	
	@Query(value="select coalesce(max(u.rank), 0) from UserSocialConnection u where u.userId = :userId and u.providerId = :providerId")
	int findMaxRankByUserIdAndProviderId(@Param("userId") String userId, @Param("providerId") String providerId);
}
