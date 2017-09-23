package br.com.devcase.boot.dwftaglibs.javascript;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.bind.PropertiesConfigurationFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySources;

import br.com.devcase.boot.dwftaglibs.tag.NeedJavascriptTag.AddedJavascript;

@ConfigurationProperties(prefix = "javascript-libs")
public class JavascriptLibs implements InitializingBean {

	public static class JavascriptLib implements AddedJavascript {
		private String name;
		private String src;
		private String integrity;
		Integer order;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getSrc() {
			return src;
		}

		public void setSrc(String src) {
			this.src = src;
		}

		public String getIntegrity() {
			return integrity;
		}

		public void setIntegrity(String integrity) {
			this.integrity = integrity;
		}

		@Override
		public int getOrder() {
			return order != null ? order.intValue() : Ordered.LOWEST_PRECEDENCE - 1;
		}

		@Override
		public void writeScript(Writer out) throws IOException, JspException {
			out.write("<script src=\"");
			out.write(src);
			out.write("\" ");
			if(StringUtils.isNotBlank(integrity)) {
				out.write(" integrity=\"");
				out.write(integrity);
				out.write("\" crossorigin=\"anonymous\" ");
			}
			out.write("></script>");
		}
	}
	
	private List<String> libs = new ArrayList<String>();

	public List<String> getLibs() {
		return libs;
	}

	public void setLibs(List<String> libs) {
		this.libs = libs;
	}

	private Map<String, JavascriptLib> javascriptLibsMap;

	@Autowired
	private Environment environment;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		javascriptLibsMap = new HashMap<>(libs.size());
		
		for (int i = 0; i < libs.size(); i++) {
			//loads from environment the libs
			String libName = libs.get(i);
			
			//build JavascriptLib instance from environment properties
			JavascriptLib lib = new JavascriptLib();
			PropertiesConfigurationFactory<JavascriptLib> configFactory = new PropertiesConfigurationFactory<>(lib);
			configFactory.setPropertySources(propertySource());
			configFactory.setTargetName("javascript-libs.".concat(libName));
			configFactory.bindPropertiesToTarget();

			if(lib.order == null) lib.order = i;
			javascriptLibsMap.put(lib.name, lib);
		}
	}
	
	protected PropertySources propertySource() {
		if (this.environment instanceof ConfigurableEnvironment) {
			return ((ConfigurableEnvironment) this.environment).getPropertySources();
		}
		return new MutablePropertySources();
	}

	
	public Map<String, JavascriptLib> getJavascriptLibsMap() {
		return javascriptLibsMap;
	}
	

}
