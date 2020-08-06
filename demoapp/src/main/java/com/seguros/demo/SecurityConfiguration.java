package com.seguros.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter  {
	
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
	
	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
	    return new CustomLogoutSuccessHandler();
	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth)
      throws Exception {
        auth
          .inMemoryAuthentication()
          .withUser("user")
            .password(passwordEncoder().encode("user"))
            .roles("USER")
            .and()
          .withUser("admin")
            .password(passwordEncoder().encode("admin"))
            .roles( "ADMIN");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
          .authorizeRequests()
          .antMatchers("/private/**").access("isAuthenticated()")
          .antMatchers("/auth/**").permitAll()
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
                .logoutUrl("/logout")
                .logoutSuccessUrl("/Login.xhtml")
                .logoutSuccessHandler(logoutSuccessHandler())
                .and().httpBasic();
    }

	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
