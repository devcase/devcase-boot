package br.com.devcase.boot.web.controllers;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.LocaleResolver;


@RestController
public class MessageSourceController {
	@Value("${spring.messages.basename:messages}")
	private String basename;
	@Value("${spring.messages.encoding:ISO-8859-1}")
	private Charset encoding;
	@Value("${spring.messages.cache-seconds:-1}")
	private int cacheSeconds = -1;
	@Autowired
	private LocaleResolver localeResolver;
	
	private final Map<Locale, Map<String, String>> messages = new HashMap<>();
	

	@PostConstruct
	public void loadBundles() {
		loadBundles(Locale.getDefault());
	}
	public void loadBundles(Locale locale) {
		List<ResourceBundle> defaultLocaleBundles = new ArrayList<>();
		for (String name : StringUtils.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(this.basename))) {
			defaultLocaleBundles.add(ResourceBundle.getBundle(name, locale));
		}
		messages.put(locale, extractMap(defaultLocaleBundles));
	}
	public Map<String, String> getMessagesMap(Locale locale) {
		//TODO - expiração de mensagens
		if(!messages.containsKey(locale) || cacheSeconds == 0) {
			loadBundles(locale);
		} 
		return messages.get(locale);
	}
	
	private static Map<String, String> extractMap(List<ResourceBundle> rb) {
		Map<String, String> m = new HashMap<>();
		for (ResourceBundle resourceBundle : rb) {
			Enumeration<String> keys = resourceBundle.getKeys();
			while (keys.hasMoreElements()) {
				String messagekey = keys.nextElement();
				m.put(messagekey, resourceBundle.getString(messagekey));
			}
		}
		return m;
	}

	@RequestMapping("/messagesource")
	public Map<String, String> messageSource(HttpServletRequest request) {
		Locale locale = localeResolver.resolveLocale(request);
		return getMessagesMap(locale);
	}
}
