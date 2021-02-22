package com.camico.springboot.proyect.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.camico.springboot.proyect.app.models.service.IUploadFileService;

@SpringBootApplication
public class SpringBootProyectApplication implements CommandLineRunner{
	
	@Autowired
	IUploadFileService uploadFileService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootProyectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//se implementa esta interfaz CommandLineRunner para crear y eliminar de forma automatica el directorio
		uploadFileService.deleteAll();
		uploadFileService.init();
		
		
		String password="12345";
		//aqui vamos agenerar nuestras contrase;as, nuestros password de ejemplo usando el passwordEncoder
		for (int i = 0; i < 2; i++) { //cree 2 password encriptadas
			String bcryptPassword= passwordEncoder.encode(password);
			System.out.println(bcryptPassword); //la cual va a agenerar 2, uno para admin y otro
		}
		
	}

}
