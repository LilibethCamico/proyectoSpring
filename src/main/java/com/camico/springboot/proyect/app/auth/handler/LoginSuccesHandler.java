package com.camico.springboot.proyect.app.auth.handler;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.support.SessionFlashMapManager;

@Component
public class LoginSuccesHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
    private MessageSource messageSource;
	
	@Autowired
    private LocaleResolver localeResolver;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		
		
		/*lo primero es obtener el sesionFlashMAp Manager para poder registrar un mapflash, un arrelglo que contenga los mensajes flash en este 
		 * caso el success cuando inicie session. La cual utilizamos esta forma ya que no se puede inyectar como argumento en el metodo el RedirectAtributte
		 * como se puede hacer en el controlador
		 */
		
		SessionFlashMapManager flashManager= new SessionFlashMapManager();
		
		FlashMap flashMap= new FlashMap();
		
		Locale locale = localeResolver.resolveLocale(request);
		String mensaje = String.format(messageSource.getMessage("text.login.success", null, locale), authentication.getName());
		
		flashMap.put("success", mensaje);
		
		flashManager.saveOutputFlashMap(flashMap, request, response);
		
		//podemos validar
		if(authentication != null) {
			logger.info(mensaje);
		}
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
	
}
