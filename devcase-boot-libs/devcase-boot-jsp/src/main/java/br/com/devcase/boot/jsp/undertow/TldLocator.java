package br.com.devcase.boot.jsp.undertow;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.util.HashMap;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.apache.jasper.deploy.FunctionInfo;
import org.apache.jasper.deploy.TagAttributeInfo;
import org.apache.jasper.deploy.TagFileInfo;
import org.apache.jasper.deploy.TagInfo;
import org.apache.jasper.deploy.TagLibraryInfo;
import org.apache.jasper.deploy.TagLibraryValidatorInfo;
import org.apache.jasper.deploy.TagVariableInfo;
import org.jboss.annotation.javaee.Icon;
import org.jboss.metadata.javaee.spec.DescriptionGroupMetaData;
import org.jboss.metadata.javaee.spec.ParamValueMetaData;
import org.jboss.metadata.parser.jsp.TldMetaDataParser;
import org.jboss.metadata.parser.util.NoopXMLResolver;
import org.jboss.metadata.web.spec.AttributeMetaData;
import org.jboss.metadata.web.spec.FunctionMetaData;
import org.jboss.metadata.web.spec.TagFileMetaData;
import org.jboss.metadata.web.spec.TagMetaData;
import org.jboss.metadata.web.spec.TldMetaData;
import org.jboss.metadata.web.spec.VariableMetaData;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class TldLocator {
	public static HashMap<String, TagLibraryInfo> createTldInfos() throws IOException {
		// URLClassLoader loader = (URLClassLoader)
		// Thread.currentThread().getContextClassLoader();
		// URLClassLoader loader = (URLClassLoader) TldLocator.class.getClassLoader();
		// URL[] urls = loader.getURLs();
		// HashMap<String, TagLibraryInfo> tagLibInfos = new HashMap<String,
		// TagLibraryInfo>();
		// for (URL url : urls) {
		// if (url.toString().endsWith(".jar")) {
		// try (JarFile jarFile = new JarFile(url.getFile())) {
		// final Enumeration<JarEntry> entries = jarFile.entries();
		// while (entries.hasMoreElements()) {
		// final JarEntry entry = entries.nextElement();
		// if (entry.getName().endsWith(".tld")) {
		// InputStream is = null;
		// try {
		// JarEntry fileEntry = jarFile.getJarEntry(entry.getName());
		// is = jarFile.getInputStream(fileEntry);
		// final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
		// inputFactory.setXMLResolver(NoopXMLResolver.create());
		// XMLStreamReader xmlReader = inputFactory.createXMLStreamReader(is);
		// TldMetaData tldMetadata = TldMetaDataParser.parse(xmlReader);
		// TagLibraryInfo taglibInfo = getTagLibraryInfo(tldMetadata);
		// if (!tagLibInfos.containsKey(taglibInfo.getUri())) {
		// tagLibInfos.put(taglibInfo.getUri(), taglibInfo);
		// }
		// } catch (XMLStreamException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// try {
		// if (is != null) {
		// is.close();
		// }
		// } catch (IOException ignore) {
		// }
		// }
		// }
		// }
		// }
		// }
		// }
		final URLClassLoader loader = (URLClassLoader) Thread.currentThread().getContextClassLoader();
		final ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(loader);
		final Resource[] resources;
		final String locationPattern = "classpath*:**/META-INF/*.tld";
		try {
			resources = resolver.getResources(locationPattern);
		} catch (IOException e) {
			throw new IllegalStateException(String
					.format("Error while retrieving resources" + "for location pattern '%s'.", locationPattern, e));
		}

		HashMap<String, TagLibraryInfo> tagLibInfos = new HashMap<String, TagLibraryInfo>();
		for (Resource resource : resources) {
			try (InputStream is = resource.getInputStream()) {
				final XMLInputFactory inputFactory = XMLInputFactory.newInstance();
				inputFactory.setXMLResolver(NoopXMLResolver.create());
				XMLStreamReader xmlReader = inputFactory.createXMLStreamReader(is);
				TldMetaData tldMetadata = TldMetaDataParser.parse(xmlReader);
				
				String resourceName = resource.getURL().toString();
				
				String path = resourceName.substring(resourceName.indexOf("/META-INF/"));
				TagLibraryInfo taglibInfo = getTagLibraryInfo(tldMetadata, path);
				if (!tagLibInfos.containsKey(taglibInfo.getUri())) {
					tagLibInfos.put(taglibInfo.getUri(), taglibInfo);
				}
			} catch (XMLStreamException e) {
				throw new IllegalStateException(String
						.format("Error while reading tld metadata from %s", resource.toString(), e));
			}
		}

		return tagLibInfos;
	}

	static TagLibraryInfo getTagLibraryInfo(TldMetaData tldMetaData, String path) {
		TagLibraryInfo tagLibraryInfo = new TagLibraryInfo();
		
		tagLibraryInfo.setPath(path);
		
		tagLibraryInfo.setTlibversion(tldMetaData.getTlibVersion());
		if (tldMetaData.getJspVersion() == null) {
			tagLibraryInfo.setJspversion(tldMetaData.getVersion());
		} else {
			tagLibraryInfo.setJspversion(tldMetaData.getJspVersion());
		}
		tagLibraryInfo.setShortname(tldMetaData.getShortName());
		tagLibraryInfo.setUri(tldMetaData.getUri());
		if (tldMetaData.getDescriptionGroup() != null) {
			tagLibraryInfo.setInfo(tldMetaData.getDescriptionGroup().getDescription());
		}
		// Validator
		if (tldMetaData.getValidator() != null) {
			TagLibraryValidatorInfo tagLibraryValidatorInfo = new TagLibraryValidatorInfo();
			tagLibraryValidatorInfo.setValidatorClass(tldMetaData.getValidator().getValidatorClass());
			if (tldMetaData.getValidator().getInitParams() != null) {
				for (ParamValueMetaData paramValueMetaData : tldMetaData.getValidator().getInitParams()) {
					tagLibraryValidatorInfo.addInitParam(paramValueMetaData.getParamName(),
							paramValueMetaData.getParamValue());
				}
			}
			tagLibraryInfo.setValidator(tagLibraryValidatorInfo);
		}
		// Tag
		if (tldMetaData.getTags() != null) {
			for (TagMetaData tagMetaData : tldMetaData.getTags()) {
				TagInfo tagInfo = new TagInfo();
				tagInfo.setTagName(tagMetaData.getName());
				tagInfo.setTagClassName(tagMetaData.getTagClass());
				tagInfo.setTagExtraInfo(tagMetaData.getTeiClass());
				if (tagMetaData.getBodyContent() != null) {
					tagInfo.setBodyContent(tagMetaData.getBodyContent().toString());
				}
				tagInfo.setDynamicAttributes(tagMetaData.getDynamicAttributes());
				// Description group
				if (tagMetaData.getDescriptionGroup() != null) {
					DescriptionGroupMetaData descriptionGroup = tagMetaData.getDescriptionGroup();
					if (descriptionGroup.getIcons() != null && descriptionGroup.getIcons().value() != null
							&& (descriptionGroup.getIcons().value().length > 0)) {
						Icon icon = descriptionGroup.getIcons().value()[0];
						tagInfo.setLargeIcon(icon.largeIcon());
						tagInfo.setSmallIcon(icon.smallIcon());
					}
					tagInfo.setInfoString(descriptionGroup.getDescription());
					tagInfo.setDisplayName(descriptionGroup.getDisplayName());
				}
				// Variable
				if (tagMetaData.getVariables() != null) {
					for (VariableMetaData variableMetaData : tagMetaData.getVariables()) {
						TagVariableInfo tagVariableInfo = new TagVariableInfo();
						tagVariableInfo.setNameGiven(variableMetaData.getNameGiven());
						tagVariableInfo.setNameFromAttribute(variableMetaData.getNameFromAttribute());
						tagVariableInfo.setClassName(variableMetaData.getVariableClass());
						tagVariableInfo.setDeclare(variableMetaData.getDeclare());
						if (variableMetaData.getScope() != null) {
							tagVariableInfo.setScope(variableMetaData.getScope().toString());
						}
						tagInfo.addTagVariableInfo(tagVariableInfo);
					}
				}
				// Attribute
				if (tagMetaData.getAttributes() != null) {
					for (AttributeMetaData attributeMetaData : tagMetaData.getAttributes()) {
						TagAttributeInfo tagAttributeInfo = new TagAttributeInfo();
						tagAttributeInfo.setName(attributeMetaData.getName());
						tagAttributeInfo.setType(attributeMetaData.getType());
						tagAttributeInfo.setReqTime(attributeMetaData.getRtexprvalue());
						tagAttributeInfo.setRequired(attributeMetaData.getRequired());
						tagAttributeInfo.setFragment(attributeMetaData.getFragment());
						if (attributeMetaData.getDeferredValue() != null) {
							tagAttributeInfo.setDeferredValue("true");
							tagAttributeInfo.setExpectedTypeName(attributeMetaData.getDeferredValue().getType());
						} else {
							tagAttributeInfo.setDeferredValue("false");
						}
						if (attributeMetaData.getDeferredMethod() != null) {
							tagAttributeInfo.setDeferredMethod("true");
							tagAttributeInfo
									.setMethodSignature(attributeMetaData.getDeferredMethod().getMethodSignature());
						} else {
							tagAttributeInfo.setDeferredMethod("false");
						}
						tagInfo.addTagAttributeInfo(tagAttributeInfo);
					}
				}
				tagLibraryInfo.addTagInfo(tagInfo);
			}
		}
		// Tag files
		if (tldMetaData.getTagFiles() != null) {
			for (TagFileMetaData tagFileMetaData : tldMetaData.getTagFiles()) {
				TagFileInfo tagFileInfo = new TagFileInfo();
				tagFileInfo.setName(tagFileMetaData.getName());
				tagFileInfo.setPath(tagFileMetaData.getPath());
				tagLibraryInfo.addTagFileInfo(tagFileInfo);
			}
		}
		// Function
		if (tldMetaData.getFunctions() != null) {
			for (FunctionMetaData functionMetaData : tldMetaData.getFunctions()) {
				FunctionInfo functionInfo = new FunctionInfo();
				functionInfo.setName(functionMetaData.getName());
				functionInfo.setFunctionClass(functionMetaData.getFunctionClass());
				functionInfo.setFunctionSignature(functionMetaData.getFunctionSignature());
				tagLibraryInfo.addFunctionInfo(functionInfo);
			}
		}

		return tagLibraryInfo;
	}
}
