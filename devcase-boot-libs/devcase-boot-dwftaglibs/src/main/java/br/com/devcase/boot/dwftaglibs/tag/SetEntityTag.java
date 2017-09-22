package br.com.devcase.boot.dwftaglibs.tag;

import java.io.IOException;

import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.web.util.TagUtils;

/**
 * 
 * @author hirata
 *
 */
public class SetEntityTag extends SimpleTagSupport {
	private Object entity;
	private String entityName;

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object target) {
		this.entity = target;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	@Override
	public void doTag() throws JspException, IOException {
		if(entity == null) {
			ELContext elContext = getJspContext().getELContext();
			ExpressionFactory expFactory = ExpressionFactory.newInstance();
			entity = expFactory.createValueExpression(elContext, "${" + entityName + "}", Object.class).getValue(elContext);
		}
		
		if("null".equals(entity)) {
			entity = null;
		}
		
		getJspContext().setAttribute("entityName", entityName, TagUtils.getScope(TagUtils.SCOPE_REQUEST));
		getJspContext().setAttribute("entity", entity, TagUtils.getScope(TagUtils.SCOPE_REQUEST));
		getJspContext().setAttribute(entityName, entity, TagUtils.getScope(TagUtils.SCOPE_REQUEST));
	}

}
