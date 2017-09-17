package br.com.devcase.boot.crud.jpa.repository.query;

public class Criteria<T> {
	private String property;
	private Operation operation;
	private T value;
	private Class<T> propertyType;

	public Criteria() {
		super();
	}

	public Criteria(String property, Operation operation, T value, Class<T> propertyType) {
		super();
		this.property = property;
		this.operation = operation;
		this.value = value;
		this.propertyType = propertyType;
	}


	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Operation getOperation() {
		return operation;
	}

	public void setOperation(Operation operation) {
		this.operation = operation;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public Class<T> getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Class<T> propertyType) {
		this.propertyType = propertyType;
	}

}
