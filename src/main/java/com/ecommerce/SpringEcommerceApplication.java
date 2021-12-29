package com.ecommerce;

import com.ecommerce.service.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringEcommerceApplication {

    
    @Autowired
    private UsuarioServiceImpl uS;
	public static void main(String[] args) {
		SpringApplication.run(SpringEcommerceApplication.class, args);
	}
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(uS).passwordEncoder(new BCryptPasswordEncoder());
    }

}
