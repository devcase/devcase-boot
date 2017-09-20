package br.com.devcase.boot.crud.repository;

public interface FindByNameExecutor<E> {
	E findByName(String name);
}
