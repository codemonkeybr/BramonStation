/*
 * 
 */
package br.eti.tmc.ufoviewer.util;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.convert.Converter;
import javax.faces.model.SelectItem;

/**
 * The Class JsfUtil.
 */
public class JsfUtil implements Serializable {

	private static final long serialVersionUID = -6468222215832036302L;

	/**
	 * Gets the select items.
	 * 
	 * @param entities
	 *            the entities
	 * @param selectOne
	 *            the select one
	 * @return the select items
	 */
	public static SelectItem[] getSelectItems(List<?> entities,
			boolean selectOne) {
		int size = selectOne ? entities.size() + 1 : entities.size();
		SelectItem[] items = new SelectItem[size];
		int i = 0;
		if (selectOne) {
			items[0] = new SelectItem("", "---");
			i++;
		}
		for (Object x : entities) {
			items[i++] = new SelectItem(x, x.toString());
		}
		return items;
	}

	/**
	 * Ensure add error message.
	 * 
	 * @param ex
	 *            the ex
	 * @param defaultMsg
	 *            the default msg
	 */
	public static void ensureAddErrorMessage(Exception ex, String defaultMsg) {
		String msg = ex.getLocalizedMessage();
		if (msg != null && msg.length() > 0) {
			addErrorMessage(msg);
		} else {
			addErrorMessage(defaultMsg);
		}
	}

	/**
	 * Adds the warn message.
	 * 
	 * @param msg
	 *            the msg
	 */
	public static void addWarnMessage(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_WARN, "AVISO", msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		
	}

	/**
	 * Adds the error messages.
	 * 
	 * @param messages
	 *            the messages
	 */
	public static void addErrorMessages(List<String> messages) {
		for (String message : messages) {
			addErrorMessage(message);
		}
	}
	
	/**
	 * Adds the error message.
	 * 
     * @return 
	 */
	public static Integer getParamById() {
		return Integer.parseInt((String) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
	}

	/**
	 * Adds the error message.
	 * 
	 * @param msg
	 *            the msg
	 */
	public static void addErrorMessage(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
				"ERRO", msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
	}

	/**
	 * Adds the success message.
	 * 
	 * @param msg
	 *            the msg
	 */
	public static void addSuccessMessage(String msg) {
		FacesMessage facesMsg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", msg);
		FacesContext.getCurrentInstance().addMessage(null, facesMsg);
		
	}

	/**
	 * Gets the request parameter.
	 * 
	 * @param key
	 *            the key
	 * @return the request parameter
	 */
	public static String getRequestParameter(String key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(key);
	}

	/**
	 * Gets the object from request parameter.
	 * 
	 * @param requestParameterName
	 *            the request parameter name
	 * @param converter
	 *            the converter
	 * @param component
	 *            the component
	 * @return the object from request parameter
	 */
	public static Object getObjectFromRequestParameter(
			String requestParameterName, Converter converter,
			UIComponent component) {
		String theId = JsfUtil.getRequestParameter(requestParameterName);
		return converter.getAsObject(FacesContext.getCurrentInstance(),
				component, theId);
	}

	/**
	 * Array to list.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param arr
	 *            the arr
	 * @return the list
	 */
	public static <T> List<T> arrayToList(T[] arr) {
		if (arr == null) {
			return new ArrayList<>();
		}
		return Arrays.asList(arr);
	}

	/**
	 * Array to set.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param arr
	 *            the arr
	 * @return the sets the
	 */
	public static <T> Set<T> arrayToSet(T[] arr) {
		if (arr == null) {
			return new HashSet<>();
		}
		return new HashSet<>(Arrays.asList(arr));
	}

	/**
	 * Collection to array.
	 * 
	 * @param c
	 *            the c
	 * @return the object[]
	 */
	public static Object[] collectionToArray(Collection<?> c) {
		if (c == null) {
			return new Object[0];
		}
		return c.toArray();
	}

	/**
	 * Sets the to list.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param set
	 *            the set
	 * @return the list
	 */
	public static <T> List<T> setToList(Set<T> set) {
		return new ArrayList<>(set);
	}

	/**
	 * Gets the as converted string.
	 * 
	 * @param object
	 *            the object
	 * @param converter
	 *            the converter
	 * @return the as converted string
	 */
	public static String getAsConvertedString(Object object, Converter converter) {
		return converter.getAsString(FacesContext.getCurrentInstance(), null,
				object);
	}

	/**
	 * Gets the collection as string.
	 * 
	 * @param collection
	 *            the collection
	 * @return the collection as string
	 */
	public static String getCollectionAsString(Collection<?> collection) {
		if (collection == null || collection.isEmpty()) {
			return "(No Items)";
		}
		StringBuilder sb = new StringBuilder();
		int i = 0;
		for (Object item : collection) {
			if (i > 0) {
				sb.append("<br />");
			}
			sb.append(item);
			i++;
		}
		return sb.toString();
	}

	/**
	 * Gets the http request parameter.
	 * 
	 * @param key
	 *            the key
	 * @return the http request parameter
	 */
	public static String getHttpRequestParameter(Object key) {
		return FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap().get(key);
	}

	/**
	 * Gets the http request parameter long.
	 * 
	 * @param key
	 *            the key
	 * @return the http request parameter long
	 */
	public static Long getHttpRequestParameterLong(Object key) {
		String param = getHttpRequestParameter(key);
		return param != null ? Long.parseLong(param) : null;
	}

	/**
	 * Gets the http request parameter int.
	 * 
	 * @param key
	 *            the key
	 * @return the http request parameter int
	 */
	public static Integer getHttpRequestParameterInt(Object key) {
		String param = getHttpRequestParameter(key);
		return param != null ? Integer.parseInt(param) : null;
	}


	/**
	 * Faz o redirecionamento para uma pagina (caminho relativo)
	 * 
	 * @param pagina
	 */
	@SuppressWarnings("unused")
	public	static void redirectPage(String pagina) {
		try {
			getFacesContext().getExternalContext().redirect(pagina);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Recebe como parametro uma chave, faz a traducao da chave para a mensagem
	 * olhando o bundle e a adiciona no escopo {@link Flash} da aplicacao.
	 * 
	 * @param chave
	 * @param severidade
	 */
	public static void addMessageFromBundleInFlash(Severity severidade, String chave) {
		Flash flash = getFacesContext().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		getFacesContext().addMessage(null,new FacesMessage(severidade, null, getBundle().getString(chave)));
	}
	
	public static ResourceBundle getBundle() {
	        return ResourceBundle.getBundle("messages", getFacesContext().getViewRoot().getLocale());
	}

	public static FacesContext getFacesContext() {
		return FacesContext.getCurrentInstance();
	}
}