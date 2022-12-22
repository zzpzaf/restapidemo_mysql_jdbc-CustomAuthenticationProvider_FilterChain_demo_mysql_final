package com.zzpzaf.restapidemo.Configuration;

//import org.springframework.context.ApplicationContext;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


public class CuctomDslHttpSecurityConfigurer extends AbstractHttpConfigurer<CuctomDslHttpSecurityConfigurer, HttpSecurity> {
	//private boolean flag;
    private String headerName;
    private String headerValue;

	@Override
	public void init(HttpSecurity http) throws Exception {
		// any method that adds another configurer
		// must be done in the init method
		//http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
        //ApplicationContext context = http.getSharedObject(ApplicationContext.class);
		// here we lookup from the ApplicationContext. You can also just create a new instance.
		//MyFilter myFilter = context.getBean(MyFilter.class);

        CustomResponseHeaderFilter myFilter = new CustomResponseHeaderFilter();
        myFilter.setHeader(headerName, headerValue);
		http.addFilterAt(myFilter, BasicAuthenticationFilter.class);
	}

	public CuctomDslHttpSecurityConfigurer setHeader(String headerName, String headerValue) {
        this.headerName = headerName;
        this.headerValue = headerValue;
 		return this;
	}

	public static CuctomDslHttpSecurityConfigurer customDsl() {
		return new CuctomDslHttpSecurityConfigurer();
	}
}
