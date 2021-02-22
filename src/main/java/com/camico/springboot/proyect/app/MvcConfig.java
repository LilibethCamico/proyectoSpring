package com.camico.springboot.proyect.app;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	// se implementa un metodo para registrar un controlador de vista
	// parametrizable, la cual este mismo no tenga logica alguna sino solo para una
	// url
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/error_403").setViewName("error_403");

	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// para la ejecuciones de nuestro multilenguaje, para cambiar el nuevo idioma de
	// los textos de nuestar pagina, guardando el lenguaje por defecto
	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver localeResolver = new SessionLocaleResolver();
		localeResolver.setDefaultLocale(new Locale("es", "ES")); // espanal , espanol
		return localeResolver;
	}

	// creamos como un interceptor la cual se estara ejecutando justo antes de
	// llamar a los metodos handler de las peticiones
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
		localeInterceptor.setParamName("lang"); // cambiando de lenguaje, cada vez que se pase este como parametro por
												// url
		return localeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor()); // pasamos el metodo del intercentor, devolviendo el
															// localeInterceptor y registramos el interceptor
	}

	// Debemos crear el Bean para utilizarlo en nuestra clase Wrapper(envoltura) para convertir a XML a OBject y viceversas
	@Bean
	public Jaxb2Marshaller jaxb2Marshaller() {
		Jaxb2Marshaller marshaller= new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(new Class[] {com.camico.springboot.proyect.app.view.xml.ClienteListXml.class}); //con ToBeBound y acepta un arreglo, son las clases que vamos a convertir a xml, son las clases root
		return marshaller;
	}

}
