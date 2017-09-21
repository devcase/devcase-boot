package br.com.devcase.boot.webcrud.criteria;

import java.beans.PropertyDescriptor;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;

import com.google.common.collect.Lists;

import br.com.devcase.boot.crud.repository.criteria.Criteria;
import br.com.devcase.boot.crud.repository.criteria.Operation;

public class RequestParametersCriteriaSourceImpl implements CriteriaSource {
	private WebDataBinder webDataBinder;
	private NativeWebRequest webRequest;
	
	public RequestParametersCriteriaSourceImpl(WebDataBinder webDataBinder, NativeWebRequest webRequest) {
		super();
		this.webDataBinder = webDataBinder;
		this.webRequest = webRequest;
	}

	@Override
	public <T> List<Criteria> getCriteria(Class<T> domainClass) {
		
//		EntityManagerFactory emf;
//		Metamodel metamodel = emf.getMetamodel();
//		ManagedType<T> entityType = metamodel.managedType(domainClass);
		
		List<Criteria> criteriaList = Lists.newArrayList();
		for (PropertyDescriptor propertyDescriptor : PropertyUtils.getPropertyDescriptors(domainClass)) {
			Class propertyType = propertyDescriptor.getPropertyType();
			String propertyName = propertyDescriptor.getName();
			
			
			String[] values = webRequest.getParameterValues(propertyName);
			if(values != null && values.length == 1) {
				Object criteriaValue = webDataBinder.convertIfNecessary(values[0], propertyType);
				criteriaList.add(new Criteria(propertyName, Operation.EQ, criteriaValue, propertyType));
			}
			
//			criteria.setValue(value);
			
		}
		return criteriaList;
	}

	
}
