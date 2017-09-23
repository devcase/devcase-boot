package br.com.devcase.boot.dwftaglibs.autoconfigure;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.devcase.boot.dwftaglibs.javascript.JavascriptLibs;
import br.com.devcase.boot.dwftaglibs.tag.NeedJavascriptTag;

@Configuration
@EnableConfigurationProperties
@PropertySource(value="classpath:/javascript-libs.properties")
public class JavascriptLibsAutoConfiguration  {
	
	@Bean
	@ConditionalOnMissingBean(JavascriptLibs.class)
	public JavascriptLibs javascriptLibs() {
		return new JavascriptLibs();
	}
	
	@Bean
	public OncePerRequestFilter includeJavascriptLibsMapFilter(JavascriptLibs javascriptLibs) {
		return new OncePerRequestFilter() {
			
			@Override
			protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
					throws ServletException, IOException {
				request.setAttribute(NeedJavascriptTag.JAVASCRIPT_LIBS_MAP_ATTRIBUTE_NAME, javascriptLibs.getJavascriptLibsMap());
				filterChain.doFilter(request, response);
			}
		};
	}
	
	

}
