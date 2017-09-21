package br.com.devcase.boot.crud.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.hibernate.validator.internal.engine.DefaultClockProvider;
import org.springframework.beans.PropertyAccessorFactory;

import br.com.devcase.boot.crud.validation.constraints.PropertyNotNull;

public class PropertyNotNullValidator implements ConstraintValidator<PropertyNotNull, Object>{

	private String propertyName;
	
	@Override
	public void initialize(PropertyNotNull constraintAnnotation) {
		propertyName = constraintAnnotation.value();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext context) {
		if(value == null) {
			return false;
		}
		return PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue(propertyName) != null;
	}

	public static void main(String[] args) {
		DefaultClockProvider c = DefaultClockProvider.INSTANCE;
	}
}
