package main.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

@Component
public class SecurityUserSetailsService implements UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        // ideally retrive this info from IDP or DB or service
        if(name.equals("user1")){
            Collection<GrantedAuthority>  rolesList = new HashSet<GrantedAuthority>();
            rolesList.add(new SimpleGrantedAuthority("USER"));
            rolesList.add(new SimpleGrantedAuthority("ADMIN"));

            return new User("user1",passwordEncoder.encode("password1"),rolesList);
        }
        else if(name.equals("user2")){
            Collection<GrantedAuthority>  rolesList = new HashSet<GrantedAuthority>();
            rolesList.add(new SimpleGrantedAuthority("USER"));

            return new User("user2",passwordEncoder.encode("password2"),rolesList);
        }
        throw new UsernameNotFoundException("user information not present");

    }
}
