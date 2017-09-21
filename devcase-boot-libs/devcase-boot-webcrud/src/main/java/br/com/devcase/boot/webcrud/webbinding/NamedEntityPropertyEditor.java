package br.com.devcase.boot.webcrud.webbinding;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.PropertyAccessorFactory;

import br.com.devcase.boot.crud.repository.FindByNameExecutor;

/**
 * Convert a string to an entity instance. The entity must have a property named
 * "name".
 * 
 * @author hirata
 *
 */
public class NamedEntityPropertyEditor<E> extends PropertyEditorSupport {
	private final FindByNameExecutor<E> repository;
	private final Class<E> domainClass;

	public NamedEntityPropertyEditor(FindByNameExecutor<E> repository, Class<E> domainClass) {
		super();
		this.repository = repository;
		this.domainClass = domainClass;
	}

	@Override
	public String getAsText() {
		Object value = getValue();
		if (value == null) {
			return "";
		} else {
			return (String) PropertyAccessorFactory.forBeanPropertyAccess(value).getPropertyValue("name");
		}
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		E instance = repository.findByName(text);
		if(instance != null) {
			setValue(instance);
		} else {
			try {
				instance = domainClass.newInstance();
				PropertyAccessorFactory.forBeanPropertyAccess(instance).setPropertyValue("name", text);
				setValue(instance);
			} catch (InstantiationException | IllegalAccessException e) {
				setValue(null);
			}
		}
	}

}
