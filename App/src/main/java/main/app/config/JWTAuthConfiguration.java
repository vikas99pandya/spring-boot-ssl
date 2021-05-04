//package main.app.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.EnableAspectJAutoProxy;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.provider.ClientDetailsService;
//import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
//import org.springframework.security.oauth2.provider.token.TokenStore;
//import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
//import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
//
//@Configuration
//@EnableAuthorizationServer
//public class JWTAuthConfiguration extends AuthorizationServerConfigurerAdapter {
//
//
//    @Autowired
//    UserDetailsService securityUserSetailsService;
//
//    @Autowired
//    AuthenticationManager authenticationManager;
//
//
//    @Bean
//    public TokenStore tokenStore() {
//        return new JwtTokenStore(accessTokenConverter());
//    }
//
//
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        converter.setSigningKey("123");
//        return converter;
//    }
//
//    @Bean
//    public DefaultTokenServices tokenServices(final TokenStore tokenStore,
//                                              final ClientDetailsService clientDetailsService) {
//        DefaultTokenServices tokenServices = new DefaultTokenServices();
//        tokenServices.setSupportRefreshToken(true);
//        tokenServices.setTokenStore(tokenStore);
//        tokenServices.setClientDetailsService(clientDetailsService);
//        tokenServices.setAuthenticationManager(this.authenticationManager);
//        return tokenServices;
//    }
//
//
//    @Override
//    public void configure(AuthorizationServerEndpointsConfigurer endPoints) throws Exception{
//        endPoints.tokenStore(tokenStore()).authenticationManager(authenticationManager)
//                .userDetailsService(securityUserSetailsService);
//    }
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer config) throws Exception{
//        config.inMemory().withClient("oauth").secret("oauth").authorizedGrantTypes("authorization_code","password")
//        .scopes("read","write","trust")
//        .accessTokenValiditySeconds(1*60*60)
//        .refreshTokenValiditySeconds(3*60*60);
//    }
//}
