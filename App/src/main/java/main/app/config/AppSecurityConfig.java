package main.app.config;


import main.app.service.SecurityUserSetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    SecurityUserSetailsService securityUserSetailsService;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(securityUserSetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception{
        http.httpBasic()
                .and()
        .headers().frameOptions().disable()  // to allow /h2-console
        .and()
        .authorizeRequests().antMatchers(HttpMethod.POST, "/classes").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/classes").hasAuthority("USER")
                .antMatchers(HttpMethod.GET, "/classes/**").hasAuthority("USER")
                .antMatchers(HttpMethod.DELETE, "/classes/**").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/classes").hasAuthority("ADMIN")
                .antMatchers(HttpMethod.GET, "/students").hasAuthority("USER")
                .antMatchers("/oauth/token").hasAuthority("USER")
                //.antMatchers(HttpMethod.GET, "/h2").permitAll()
                .anyRequest().permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
