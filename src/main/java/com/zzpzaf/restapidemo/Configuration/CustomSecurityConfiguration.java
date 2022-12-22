package com.zzpzaf.restapidemo.Configuration;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class CustomSecurityConfiguration {

    @Autowired
    private Environment env;

    @Autowired
    private CustomAuthenticationProvider authProvider;
    
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .authorizeRequests(athReqs -> athReqs.antMatchers("/items").hasRole("ADMIN"))
            //.sessionManagement(sessMng -> sessMng.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .httpBasic(httpConf -> httpConf.realmName(env.getProperty("app.realm")))
            .httpBasic(httpConf -> httpConf.authenticationEntryPoint((request, response, authException) -> {
                response.setHeader("WWW-Authenticate", "Basic realm=" + env.getProperty("app.realm") + "");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Authentication !!!"); } ))
            //.addFilterAt(new CustomResponseHeaderFilter(), BasicAuthenticationFilter.class);    
            .apply(CuctomDslHttpSecurityConfigurer.customDsl()).setHeader("PZ Spring Demo" , "SecurityFilterChan-DSL")
            //.authenticationManager(authManager(http))
            ;

        return http.build();

    }         


    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.authenticationProvider(authProvider);

        return authenticationManagerBuilder.build();
    }

}

