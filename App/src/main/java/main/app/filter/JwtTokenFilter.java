package main.app.filter;


import main.app.exception.AuthenticatedException;
import main.app.service.SecurityUserSetailsService;
import main.app.token.UserTokenManager;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {

    @Autowired
    SecurityUserSetailsService securityUserSetailsService;

    @Autowired
    UserTokenManager userTokenManager;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

//    @Autowired
//    TokenStore tokenStore;
//
//    @Autowired
//    DefaultTokenServices defaultTokenServices;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException{

        Authentication authentication1 = SecurityContextHolder.getContext().getAuthentication();
        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(request.getRequestURI().contains("/h2")){
            chain.doFilter(request, response);
            return;
        }

        if (header == null
                || (! header.startsWith("Basic ")
                && ! header.startsWith("Bearer "))
                || !(header.split(" ").length > 1) ) {
             resolver.resolveException(request, response, null, new AuthenticatedException("AUTH","No authentication token provided"));
             return;
        }

        if(header !=null && header.startsWith("Basic ") && request.getRequestURI().equalsIgnoreCase("/authenticate")){
            chain.doFilter(request, response);
            return;
        }

       try {
           final String  token = header.split(" ")[1].trim();
           UserDetails userDetails = securityUserSetailsService.loadUserByUsername(userTokenManager.getUsernameFromToken(token));

           if (!userTokenManager.validateJwtToken(token, userDetails)) {
               resolver.resolveException(request, response, null, new AuthenticatedException("AUTH","Invalid JWT token"));
               return;
           }


           UsernamePasswordAuthenticationToken
                   authentication = new UsernamePasswordAuthenticationToken(
                   userDetails, null,
                   userDetails == null ?
                           new ArrayList<>() : userDetails.getAuthorities()
           );

           authentication.setDetails(
                   new WebAuthenticationDetailsSource().buildDetails(request)
           );
           SecurityContextHolder.getContext().setAuthentication(authentication);

           chain.doFilter(request, response);
       }
       catch(SignatureException | ExpiredJwtException | MalformedJwtException ex ){
           resolver.resolveException(request, response, null, new AuthenticatedException("AUTH",ex.getMessage(),ex));
       }

    }

}
