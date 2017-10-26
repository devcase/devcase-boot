package br.com.devcase.boot.webcrud.criteria;

import java.beans.PropertyDescriptor;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

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
	@SuppressWarnings("rawtypes")
	public <T> List<Criteria> getCriteria(Class<T> domainClass) {
		
		List<Criteria> criteriaList = Lists.newArrayList();
		
		Set<String> presentProperties = Sets.newHashSet();
		for(Iterator<String> iter = webRequest.getParameterNames(); iter.hasNext();) {
			String parameterName = iter.next();
			String[] parameterPath = parameterName.split(".");
			if (parameterPath.length > 0) presentProperties.add(parameterPath[0]);
			presentProperties.add(parameterName);
		}
		
		for (PropertyDescriptor propertyDescriptor : PropertyUtils.getPropertyDescriptors(domainClass)) {
			String propertyName = propertyDescriptor.getName();
			if(!presentProperties.contains(propertyName)) {
				continue;
			}
			Class<?> propertyType = propertyDescriptor.getPropertyType();
			
			createCriteria(criteriaList, propertyType, propertyName);
			
			for (PropertyDescriptor propertyDescriptor2 : PropertyUtils.getPropertyDescriptors(propertyType)) {
				Class<?> propertyType2 = propertyDescriptor2.getPropertyType();
				String propertyName2 = propertyDescriptor2.getName();
				createCriteria(criteriaList, propertyType2, propertyName.concat(".").concat(propertyName2));
			}
			
		}
		return criteriaList;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	private void createCriteria(List<Criteria> criteriaList, Class<?> propertyType, String propertyName) {
		String[] values = webRequest.getParameterValues(propertyName);
		if(values != null && values.length == 1) {
			Object criteriaValue = webDataBinder.convertIfNecessary(values[0], propertyType);
			criteriaList.add(new Criteria(propertyName, Operation.EQ, criteriaValue, propertyType));
		}
	}

	
	
}
