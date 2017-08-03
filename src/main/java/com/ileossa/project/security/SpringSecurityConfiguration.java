package com.ileossa.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;

/**
 * Created by ileossa on 24/07/2017.
 */
@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{

    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SpringSecurityConfiguration(AccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }



    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf().disable()
                .authorizeRequests()
                    .antMatchers("/**").permitAll()
//                    .antMatchers("/", "/registration", "/error/**").permitAll()
//                    .antMatchers("/admin/**").hasAnyRole("ADMIN")
//                    .antMatchers("/user/**").hasAnyRole("USER")
//                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/login").permitAll()
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .deleteCookies("auth_code", "JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll()
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler);

    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
//        authenticationManagerBuilder.inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER")
//                .and()
//                .withUser("admin").password("password").roles("ADMIN");
    }



}
