package main.app.filter;


import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@Order(2)
@WebFilter(urlPatterns = "/classes")
public class CustomSecondFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        log.info("########## Initiating CustomSecondFilter filter ##########");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("CustomSecondFilter  is only called when request is mapped for /filterExample resource");

        //call next filter in the filter chain
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

        log.info("########## Destroying CustomSecondFilter filter ##########");
    }
}
