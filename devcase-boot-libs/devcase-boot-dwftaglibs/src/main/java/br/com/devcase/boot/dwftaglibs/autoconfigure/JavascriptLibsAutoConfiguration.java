package br.com.devcase.boot.dwftaglibs.autoconfigure;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.devcase.boot.dwftaglibs.tag.AddJavascriptTag;
import br.com.devcase.boot.dwftaglibs.tag.AddJavascriptTag.AddedJavascript;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "javascript-libs")
@PropertySource(value="classpath:/javascript-libs.properties")
public class JavascriptLibsAutoConfiguration implements InitializingBean {

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
	
	private List<JavascriptLib> libs = new ArrayList<>();

	public List<JavascriptLib> getLibs() {
		return libs;
	}

	public void setLibs(List<JavascriptLib> libs) {
		this.libs = libs;
	}
	
	private Map<String, JavascriptLib> javascriptLibsMap;

	@Override
	public void afterPropertiesSet() throws Exception {
		javascriptLibsMap = new HashMap<>(libs.size());
		
		for (int i = 0; i < libs.size(); i++) {
			JavascriptLib javascriptLib = libs.get(i);
			if(javascriptLib.order == null)
				javascriptLib.order = i;
			javascriptLibsMap.put(javascriptLib.getName(), javascriptLib);
		}
	}
	
	public Map<String, JavascriptLib> getJavascriptLibsMap() {
		return javascriptLibsMap;
	}
	
	private final OncePerRequestFilter includeJavascriptLibsMapFilter = new OncePerRequestFilter() {
		
		@Override
		protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
			request.setAttribute(AddJavascriptTag.JAVASCRIPT_LIBS_MAP_ATTRIBUTE_NAME, getJavascriptLibsMap());
			filterChain.doFilter(request, response);
		}
	};
	
	@Bean
	public OncePerRequestFilter includeJavascriptLibsMapFilter() {
		return includeJavascriptLibsMapFilter;
	}
	
	

}
