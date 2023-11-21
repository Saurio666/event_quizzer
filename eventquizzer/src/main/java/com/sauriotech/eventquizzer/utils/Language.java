/**
 * 
 */
package com.sauriotech.eventquizzer.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SAURIOTech Solutions.
 *
 */
public class Language {
	private static volatile Language instance = null;
	private Map<String, UIMessage> languageEs;
	
	
	private Language() {
		languageEs = new ConcurrentHashMap<>();
		languageEs.put("EVT_ATTENDANCE_OK", new UIMessage(UIMessage.TYPE_INFO, "Gracias por registrar su asistencia."));
		languageEs.put("EVT_ATTENDANCE_DUPLICATE", new UIMessage(UIMessage.TYPE_WARNING, "La asistencia para este evento ya fue registrada el d&iacute;a de hoy."));
		languageEs.put("EVT_NOT_FOUND", new UIMessage(UIMessage.TYPE_ERROR, "Lo sentimos pero el evento ya no est&aacute; activo o no existe."));
		languageEs.put("EVT_NOT_AUTHORIZED", new UIMessage(UIMessage.TYPE_ERROR, "El participante no se encuentra autorizado para registrarse en este evento."));
		
		languageEs.put("QUIZZ_NOT_FOUND", new UIMessage(UIMessage.TYPE_ERROR, "Lo sentimos pero el cuestionario ya no esta activo o no existe."));
		languageEs.put("QUIZZ_ALREADY_ANSWERED", new UIMessage(UIMessage.TYPE_INFO, "El questionario ya fue contestado."));
		languageEs.put("MAX_ATTEMPTS", new UIMessage(UIMessage.TYPE_ERROR, "Lo sentimos pero usted ya a cumplido con el n&uacute;mero m&aacute;ximo de intentos."));
		languageEs.put("OUT_OF_TIME", new UIMessage(UIMessage.TYPE_ERROR, "Lo sentimos pero el cuestionario solo est&aacute; disponible desde #start hasta #end."));
		
		languageEs.put("BAD_REQUEST", new UIMessage(UIMessage.TYPE_ERROR, "Lo sentimos pero no es posible procesar su solicitud."));
		
		
		
		//languageEs.put("", new UIMessage(UIMessage.TYPE_ERROR, ""));
		//languageEs.put("", new UIMessage(UIMessage.TYPE_ERROR, ""));
		
	}
	
	public static Language getInstance() {
		if (instance == null) {
			synchronized (Language.class) {
				if (instance == null) {
					instance = new Language();
				}
			}
		}
		return instance;
	}
	
	public UIMessage getMessage(String language, String key) {
		return languageEs.get(key);
	}
}
