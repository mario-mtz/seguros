package com.seguros.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {
	
	@Autowired
    private CustomAuthenticationProvider authProvider;
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
    	auth.authenticationProvider(authProvider);
//        auth
//          .inMemoryAuthentication()
//          .withUser("user")
//            .password(passwordEncoder().encode("user"))
//            .roles("USER")
//            .and()
//          .withUser("admin")
//            .password(passwordEncoder().encode("admin"))
//            .roles( "ADMIN");
    }
    
    @Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
	    return new CustomLogoutSuccessHandler();
	}
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
          .authorizeRequests()
          .antMatchers("/private/**").access("isAuthenticated()")
          .antMatchers("/public/**","/resources/**").permitAll()
//                .antMatchers("/css/**", "/login.html", "/login", "/api/proyectos").permitAll()
//                .antMatchers("/proyecto", "/proyecto/consultar/**").hasAnyRole("ADMIN", "VISOR")
//                .antMatchers("/proyecto/**", "/api/auth/**").hasAnyRole("ADMIN")
//          .anyRequest()
//          .authenticated()
          .and()
                .formLogin()
                .loginPage("/Login.xhtml")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/private/index.xhtml")
          .and()
                .logout()                           
                .logoutSuccessHandler(logoutSuccessHandler())
                .logoutUrl("/logout")   
                .logoutSuccessUrl("/Login.xhtml")
                .and().httpBasic();
    }

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
