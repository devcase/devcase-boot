package br.com.devcase.boot.sitemesh;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.sitemesh.webapp.SiteMeshFilter;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.view.JstlView;

public class SiteMeshView extends JstlView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		ApplicationContext ctx = getApplicationContext();
		Filter siteMeshFilter = ctx.getBean(SiteMeshFilter.class);		
		siteMeshFilter.doFilter(request, response, new InternalFilterChain(model, this));
	}
	
	private void parentRenderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.renderMergedOutputModel(model, request, response);
	}
	
	static class InternalFilterChain implements FilterChain {
		final Map<String, Object> model;
		final SiteMeshView v;
		
		public InternalFilterChain(Map<String, Object> model, SiteMeshView v) {
			super();
			this.model = model;
			this.v = v;
		}


		@Override
		public void doFilter(ServletRequest request, ServletResponse response)
				throws IOException, ServletException {
			try {
				v.parentRenderMergedOutputModel(model, (HttpServletRequest) request, (HttpServletResponse) response);
			} catch (IOException e) {
				throw e;
			} catch (ServletException e) {
				throw e;
			} catch (Exception e) {
				throw new RuntimeException (e);
			}
		}
	}
	
}
