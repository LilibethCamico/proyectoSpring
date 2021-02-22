package com.camico.springboot.proyect.app;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.camico.springboot.proyect.app.auth.handler.LoginSuccesHandler;
import com.camico.springboot.proyect.app.models.service.JpaUserDetailsService;

@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) /*adicional de habiliotar la anotacion @Secured tamnbien podemos habiltar
prePostEnabled para habilitar la anotacion @PreAuthorize pero en esta se invoca el metodo hasRole en la misma anotacion
 en el controlador @PreAuthorize("hasRole('ROLE_ADMIN')")*/ 
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter{
	
	//como cambiamos el passwordEncoder en la clase MvcConfig, y ya lo teemos en el contenedor lo podemos inyectar
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private LoginSuccesHandler successHandler;
	
	@Autowired
	private JpaUserDetailsService userDetailsService;
	
	/*Manejo para la Base de Datos con JDBC para usar la autenticacion, ya no se usara la configuracion inMemory se 
	 * estara comentando. y debemos inyectar el PasswordEncoder aun que lo tenemos se obvia. 
	 
	@Autowired
	private DataSource dataSource;*/
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/listar", "/locale", "/api/clientes/**").permitAll()
		/*.antMatchers("/ver/**").hasAnyRole("USER")
		.antMatchers("/uploads/**").hasAnyRole("USER")
		.antMatchers("/form/**").hasAnyRole("ADMIN")
		.antMatchers("/eliminar/**").hasAnyRole("ADMIN")
		.antMatchers("/factura/**").hasAnyRole("ADMIN")*/ //se comenta dejando solo los recursos, para agregar seguridad desde el controlador con anotaciones
		.anyRequest().authenticated()
		.and()
		    .formLogin()
		        .successHandler(successHandler) //aqui inyectamos el flashMap creado para pasar el mensaje con LoginSuccesHandler anotada como @component
		        .loginPage("/login")
		    .permitAll()
		.and()
		.logout().permitAll()
		.and()
		.exceptionHandling().accessDeniedPage("/error_403");
		
	}

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception {
		
		builder.userDetailsService(userDetailsService) //inyectamos el userDetailsService
		.passwordEncoder(passwordEncoder);
		
		/* con JDBC, para una consulta simple nativa con en DataSource este se debe de comentar tambien, arriba implementamos con JPA e UserDetailsService
		 builder.jdbcAuthentication()
		.dataSource(dataSource) //inyectamos el datasource, para conectarnos a la base datos
		.passwordEncoder(passwordEncoder) 
		.usersByUsernameQuery("select username , password, enabled from users where username=?") //hace una simple consulta nativa, + adelante se implementara otra forma
		.authoritiesByUsernameQuery("select u.username, a.authority from authorities a inner join users u on (a.user_id=u.id) where u.username=?"); 
		*/
		
		/*PasswordEncoder encoder=this.passwordEncoder;
		UserBuilder users= org.springframework.security.core.userdetails.User.builder().passwordEncoder(encoder :: encode);
		
		builder.inMemoryAuthentication()
		.withUser(users.username("admin").password("12345").roles("ADMIN" , "USER"))
		.withUser(users.username("ana").password("12345").roles("USER"));*/
	}

}
