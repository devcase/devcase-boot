package br.com.devcase.boot.dwftaglibs.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspFragment;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.Ordered;

import br.com.devcase.boot.dwftaglibs.javascript.JavascriptLibs.JavascriptLib;

public class ImportJavascriptTag extends SimpleTagSupport {
	public static final String IMPORTED_JAVASCRIPT_ATTRIBUTE_NAME = "dwf.IMPORTED_JAVASCRIPT_ATTRIBUTE_NAME";
	public static final String JAVASCRIPT_LIBS_MAP_ATTRIBUTE_NAME = "dwf.JAVASCRIPT_LIBS_MAP";

	private String lib;
	private String src;
	private Integer order;

	public String getLib() {
		return lib;
	}

	public void setLib(String lib) {
		this.lib = lib;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	@Override
	public void doTag() throws JspException, IOException {
		AddedJavascript bean;
		if(StringUtils.isNotBlank(getLib())) {
			Map<String, JavascriptLib> libsMap = (Map<String, JavascriptLib>) getJspContext().findAttribute(JAVASCRIPT_LIBS_MAP_ATTRIBUTE_NAME);
			if(libsMap == null) {
				throw new JspException("Javascript Libs Map not found on request!");
			}
			if(!libsMap.containsKey(getLib())) {
				throw new JspException("Javascript Lib " + getLib() + " not available!");
			}
			bean = libsMap.get(getLib());
		} else {
			bean = new AddedJavascriptImpl(src, order, getJspBody());
		}
		
		
		Object javascriptList = getJspContext().findAttribute(IMPORTED_JAVASCRIPT_ATTRIBUTE_NAME);
		if (javascriptList != null) {
			((Collection<Object>) javascriptList).add(bean);
		} else {
			Set<AddedJavascript> list = new HashSet<>();
			list.add(bean);
			getJspContext().setAttribute(IMPORTED_JAVASCRIPT_ATTRIBUTE_NAME, list, PageContext.REQUEST_SCOPE);
		}
	}

	public static interface AddedJavascript extends Ordered {
		String getSrc();
		
		int getOrder();

		void writeScript(Writer out) throws IOException, JspException;
	}

	static class AddedJavascriptImpl implements AddedJavascript {
		private final String src;
		private final int order;
		private final JspFragment jspFragment;
		
		public AddedJavascriptImpl(String src, Integer order, JspFragment jspFragment) {
			super();
			this.src = src;
			this.order = order != null ? order.intValue() : Ordered.LOWEST_PRECEDENCE;
			this.jspFragment = jspFragment;
		}
		@Override
		public String getSrc() {
			return src;
		}
		@Override
		public int getOrder() {
			return order;
		}
		@Override
		public void writeScript(Writer out) throws IOException, JspException {
			if(StringUtils.isNotBlank(src)) {
				out.write("<script src=\"");
				out.write(src);
				out.write("\"></script>");
			} else {
				jspFragment.invoke(out);
			}
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((jspFragment == null) ? 0 : jspFragment.hashCode());
			result = prime * result + order;
			result = prime * result + ((src == null) ? 0 : src.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AddedJavascriptImpl other = (AddedJavascriptImpl) obj;
			if (jspFragment == null) {
				if (other.jspFragment != null)
					return false;
			} else if (!jspFragment.equals(other.jspFragment))
				return false;
			if (order != other.order)
				return false;
			if (src == null) {
				if (other.src != null)
					return false;
			} else if (!src.equals(other.src))
				return false;
			return true;
		}
	}
}
