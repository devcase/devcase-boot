package br.com.devcase.boot.crud.jpa.repository.query;

public class Criteria<P, V> {
	private String property;
	private Operation operation;
	private V value;
	private Class<P> propertyType;

	public Criteria() {
		super();
	}

	public Criteria(String property, Operation operation, V value, Class<P> propertyType) {
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

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	public Class<P> getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Class<P> propertyType) {
		this.propertyType = propertyType;
	}

}
