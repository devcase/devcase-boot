package br.com.devcase.boot.crud.repository;

public interface IdentifierExtractor<T, ID> {
	ID extractIdentifier(T value);

}
