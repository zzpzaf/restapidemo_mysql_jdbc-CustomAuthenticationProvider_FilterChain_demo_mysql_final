package com.zzpzaf.restapidemo.Configuration;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

public class CustomResponseHeaderFilter extends GenericFilterBean {

  private String headerName = "PZ Response Header";
  private String headerValue = "Custom Header Filter";
  

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse httpServletResponse = (HttpServletResponse) response;
    httpServletResponse.setHeader(headerName, headerValue);
    //System.out.println("CustomResponseHeaderFilter ========>");  
    chain.doFilter(request, response);    
  }

  public void setHeader(String headerName, String headerValue) {
    if (headerName.trim().length()>0) this.headerName = headerName;
    if (headerValue.trim().length()>0) this.headerValue = headerValue;
  }
}
