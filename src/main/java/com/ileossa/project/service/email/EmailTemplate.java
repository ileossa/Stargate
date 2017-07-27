package com.ileossa.project.service.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * Created by v.lafosse on 27/07/2017.
 */
public class EmailTemplate {

	private static org.slf4j.Logger logger = LoggerFactory.getLogger(EmailTemplate.class);

	String template;
	private Map<String, String> replacementParams;

	public EmailTemplate(String nameTemplate){
		try {
			this.template = loadTemplate(nameTemplate);
		} catch(IOException e) {
			logger.warn("Cannot load template {}", nameTemplate);
		}
	}



	// TODO passer la valeur isHtml à TRUE
	private String loadTemplate(String nameTemplate) throws IOException {
		ClassLoader classLoader = this.getClass().getClassLoader();
		File file = new File(classLoader.getResource("email/" + nameTemplate).getFile());
		String content = null;
		try {
			content = new String(Files.readAllBytes(file.toPath()));
		} catch (IOException e) {
			logger.warn("Error cannot loading template {}", nameTemplate);
		}
		return content;
	}


	public String getTemplate(Map<String, String> replacementParams){
		String currentTemplate = this.getTemplate();

		if (!isObjectEmpty(currentTemplate)) {
			for (Map.Entry<String, String> entry : replacementParams.entrySet()) {
				currentTemplate = currentTemplate.replace("{{" + entry.getKey() + "}}", entry.getValue());
			}
		}
		return currentTemplate;
	}




	public static String concatenate(List<String> listOfItems, String separator) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> stit = listOfItems.iterator();

		while (stit.hasNext()) {
			sb.append(stit.next());
			if (stit.hasNext()) {
				sb.append(separator);
			}
		}

		return sb.toString();
	}


	/**
	 * Checks if is collection empty.
	 *
	 * @param collection the collection
	 * @return true, if is collection empty
	 */
	private static boolean isCollectionEmpty(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is object empty.
	 *
	 * @param object the object
	 * @return true, if is object empty
	 */
	public static boolean isObjectEmpty(Object object) {
		if(object == null) return true;
		else if(object instanceof String) {
			if (((String)object).trim().length() == 0) {
				return true;
			}
		} else if(object instanceof Collection) {
			return isCollectionEmpty((Collection<?>)object);
		}
		return false;
	}




	public static Logger getLogger() {
		return logger;
	}

	public static void setLogger(Logger logger) {
		EmailTemplate.logger = logger;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public Map<String, String> getReplacementParams() {
		return replacementParams;
	}

	public void setReplacementParams(Map<String, String> replacementParams) {
		this.replacementParams = replacementParams;
	}
}
