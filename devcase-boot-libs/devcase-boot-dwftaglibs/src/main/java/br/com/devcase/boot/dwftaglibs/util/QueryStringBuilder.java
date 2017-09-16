package br.com.devcase.boot.dwftaglibs.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * Example: <jsp:useBean id="queryStringBuilder" class=
 * "br.com.devcase.boot.dwftaglibs.util.QueryStringBuilder"/>
 * ${queryStringBuilder.fromRequest(request).without('fetchSize', 'pageNumber',
 * 'decorator').buildStartingWith('?')}
 * 
 * resultado: ?searchString=nonono
 * 
 * @author hirata
 *
 */
public class QueryStringBuilder {
	private final Map<String, String[]> params;
	private final QueryStringBuilder original;

	public QueryStringBuilder() {
		this.params = new HashMap<String, String[]>();
		this.original = null;
	}

	public QueryStringBuilder(QueryStringBuilder original) {
		super();
		this.params = new HashMap<String, String[]>();
		this.original = original;
	}


	public String build() {
		try {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String[]> parameter : consolidateParams().entrySet()) {
				for (String value : parameter.getValue()) {
					sb.append("&").append(parameter.getKey()).append("=").append(URLEncoder.encode(value, "UTF-8"));
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException ("Inesperado - deveria suportar UTF-8", e);
		}
	}
	public String buildStartingWith(char firstChar) {
		try {
			StringBuilder sb = new StringBuilder();
			for (Map.Entry<String, String[]> parameter : consolidateParams().entrySet()) {
				for (String value : parameter.getValue()) {
					sb.append("&").append(parameter.getKey()).append("=").append(URLEncoder.encode(value, "UTF-8"));
				}
			}
			if(sb.length() > 0)
				sb.setCharAt(0, firstChar);
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException ("Inesperado - deveria suportar UTF-8", e);
		}
	}
	
	
	
	protected QueryStringBuilder putAll(Map<String, String[]> otherMap) {
		this.params.putAll(otherMap);
		return this;
	}

	protected Map<String, String[]> consolidateParams() {
		if(original != null) {
			for (Map.Entry<String, String[]> element : original.consolidateParams().entrySet()) {
				if(!this.params.containsKey(element.getKey())) {
					this.params.put(element.getKey(), element.getValue());
				}
			}
		}
		return params;
	}

	public QueryStringBuilder fromRequest(HttpServletRequest request) {
		if(request != null)
			return new QueryStringBuilder(this).putAll(request.getParameterMap());
		else return new QueryStringBuilder(this);
	}
	
	public boolean containsKey(String key) {
		if(this.params.containsKey(key))
			return true;
		else if(this.original != null)
			return consolidateParams().containsKey(key);
		else return false;
	}
	
	/**
	 * 
	 * @param keys
	 * @return
	 */
	public QueryStringBuilder remove(final String key) {
		return new QueryStringBuilder(this) {
			@Override
			protected Map<String, String[]> consolidateParams() {
				Map<String, String[]> p = super.consolidateParams();
				p.remove(key);
				return p;
			}
		};
	}
	
	public QueryStringBuilder put(final String key, final String value) {
		return new QueryStringBuilder(this) {
			@Override
			protected Map<String, String[]> consolidateParams() {
				Map<String, String[]> p = super.consolidateParams();
				p.put(key, new String[] {value});
				return p;
			}
		};
	}
}