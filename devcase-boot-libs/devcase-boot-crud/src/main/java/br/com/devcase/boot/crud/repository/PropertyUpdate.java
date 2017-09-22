package br.com.devcase.boot.crud.repository;

public interface PropertyUpdate<ID> {

	<P> void updateProperty(ID id, String propertyName, P value);
}
