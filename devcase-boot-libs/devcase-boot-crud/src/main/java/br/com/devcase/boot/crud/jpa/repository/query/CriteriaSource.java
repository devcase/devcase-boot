package br.com.devcase.boot.crud.jpa.repository.query;

public interface CriteriaSource {
	/**
	 * Get all criteria for a specified type
	 * 
	 * @param domainType
	 * @return
	 */
	<T> Iterable<Criteria<?>> getCriteriaFor(Class<T> domainType);
}
