package com.ileossa.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Created by ileossa on 24/07/2017.
 */
@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter{

    private AccessDeniedHandler accessDeniedHandler;
    private UserDetailsService userDetailsService;

    @Autowired
    public SpringSecurityConfiguration(AccessDeniedHandler accessDeniedHandler, UserDetailsService userDetailsService) {
        this.accessDeniedHandler = accessDeniedHandler;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/", "index", "/register", "/confirm", "/reset", "/resources/**", "/home/**", "/admin/**", "/error/**").permitAll()
                    .antMatchers("/v1/**", "/files/**").permitAll()
                    .antMatchers("/health").permitAll()
                    .antMatchers("/admin/**").hasAnyRole("ROLE_ADMIN")
                    .antMatchers("/user/**").hasAnyRole("Role_USER")
                    .anyRequest().authenticated()
                .and()
                    .formLogin().defaultSuccessUrl("/index", true)
                    .loginPage("/login").failureUrl("/login?error").permitAll()
                .and()
                .logout()
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                    .deleteCookies("auth_code", "JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll()
                .and()
                .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler);

        httpSecurity.authorizeRequests().antMatchers("/").permitAll().and()
                .authorizeRequests().antMatchers("/console/**").permitAll();

        httpSecurity.headers().frameOptions().disable();

    }


//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
//        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
//    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
