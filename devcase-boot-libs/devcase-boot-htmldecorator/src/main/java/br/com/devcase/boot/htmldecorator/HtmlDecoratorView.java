package br.com.devcase.boot.htmldecorator;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.sitemesh.webapp.contentfilter.BasicSelector;
import org.sitemesh.webapp.contentfilter.HttpServletResponseBuffer;
import org.sitemesh.webapp.contentfilter.ResponseMetaData;
import org.sitemesh.webapp.contentfilter.Selector;
import org.springframework.web.servlet.view.JstlView;

public class HtmlDecoratorView extends JstlView {
	

	public HtmlDecoratorView() {
		super();
	}

	@Override
	protected RequestDispatcher getRequestDispatcher(final HttpServletRequest request, final String path) {
		final RequestDispatcher defaultDispatcher = request.getRequestDispatcher(path);
		return new RequestDispatcher() {
			@Override
			public void include(ServletRequest exposedRequest, ServletResponse servletResponse) throws ServletException, IOException {
				final HttpServletResponse response = (HttpServletResponse) servletResponse;
				
				String decorator = exposedRequest.getParameter("decorator");
				if(!StringUtils.isBlank(decorator)) {
					exposedRequest.setAttribute("decorator", decorator);
				}
				
				HttpServletResponseBuffer responseBuffer = wrapResponse((HttpServletResponse) response);
				defaultDispatcher.include(exposedRequest, responseBuffer);
				
				ParsedHtmlPage page = new ParsedHtmlPage(responseBuffer.getBuffer().array());
				decorateAndDispatch(page, exposedRequest, response);
			}

			@Override
			public void forward(ServletRequest exposedRequest, ServletResponse response) throws ServletException, IOException {
				String decorator = exposedRequest.getParameter("decorator");
				if(!StringUtils.isBlank(decorator)) {
					exposedRequest.setAttribute("decorator", decorator);
				}
				HttpServletResponseBuffer responseBuffer = wrapResponse((HttpServletResponse) response);
				defaultDispatcher.forward(exposedRequest, responseBuffer);

				ParsedHtmlPage page = new ParsedHtmlPage(responseBuffer.getBuffer().array());
				decorateAndDispatch(page, exposedRequest, response);
			}
			
			HttpServletResponseBuffer wrapResponse(HttpServletResponse response) {
				Selector selector = new BasicSelector("text/html");
		        final ResponseMetaData metaData = new ResponseMetaData();
		        HttpServletResponseBuffer responseBuffer = new HttpServletResponseBuffer(response, metaData, selector) {
		            @Override
		            public void preCommit() {
		                // Ensure both content and decorators are used to generate HTTP caching headers.
		                long lastModified = metaData.getLastModified();
		                long ifModifiedSince = request.getDateHeader("If-Modified-Since");
		                if (lastModified > -1 && !response.containsHeader("Last-Modified")) {
		                    if (ifModifiedSince < (lastModified / 1000 * 1000)) {
		                        response.setDateHeader("Last-Modified", lastModified);
		                    } else {
		                        response.reset();
		                        response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
		                    }
		                }
		            }
		        };
		        return responseBuffer;
			}


			private void decorateAndDispatch(ParsedHtmlPage page, ServletRequest exposedRequest, ServletResponse response) throws ServletException, IOException {

				if (page != null) {
					// guarda previous decorated page - multiple includes
					ParsedHtmlPage originalPage = (ParsedHtmlPage) request.getAttribute("dwf_decoratedPage");

					request.setAttribute("dwf_decoratedPage", page);
					
					//1) decorator set as a request parameter
					String decorator = exposedRequest.getParameter("decorator");
					if(StringUtils.isBlank(decorator)) {
						//2) decorator set as a meta-tag from HTML
						decorator = page.getProperty("meta.decorator");
					}
					if(decorator == null) {
						//3) ?
						decorator = (String) exposedRequest.getAttribute("decorator");
					}
					
					
					if(decorator == null || decorator.equals("none")) {
						page.writePage(response.getWriter());
						return;
					} else if(decorator.equals("table")) {
						page.writeTable(response.getWriter());
						return;
					} else if(decorator.equals("bodycontents")) {
						page.writeBody(response.getWriter());
						return;
					}
					
					String decoratorUrl = decorator;
					if(!decoratorUrl.endsWith(".jsp")) {
						decoratorUrl = "/WEB-INF/jsp/decorators/".concat(decoratorUrl).concat(".jsp");
					}
					RequestDispatcher dispatcher = request.getRequestDispatcher(decoratorUrl);
					dispatcher.include(request, response);
					request.removeAttribute("dwf_decoratedPage");

					// restaura antigo valor de dwf_decoratedPage
					request.setAttribute("dwf_decoratedPage", originalPage);
				}
			}
		};
	}
	
}