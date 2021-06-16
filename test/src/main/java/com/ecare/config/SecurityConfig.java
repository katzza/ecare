
package com.ecare.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@ComponentScan(basePackages = "com.ecare")
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {  //https://www.baeldung.com/spring-security-login
    /*for redirect*/
    @Bean
    public AuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MyUrlAuthenticationSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new MyAuthenticationFailureHandler();
    }

    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/homepage", "/client/**").hasRole("CLIENT")
                .antMatchers("/employee/**", "/tariff/**", "/option/**").hasRole("EMPLOYEE")
                .antMatchers("/console", "/admin/**").hasRole("ADMIN")
                .antMatchers("/home", "/registration", "/saveuser", "/contract/**").permitAll()
                .and()
                .formLogin()
                .failureHandler(authenticationFailureHandler())
                .loginPage("/login")
                .successHandler(myAuthenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .clearAuthentication(true)
                .logoutSuccessUrl("/ecare").deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/errorAccessPage");  //security errors-handling*/
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

