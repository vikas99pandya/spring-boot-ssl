package main.app.controller;


import main.app.token.UserTokenManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("authenticate")
@RestController
public class AuthenticationController {
    @Autowired
    UserTokenManager userTokenManager;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<String> authenticateUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String token = userTokenManager.generateJwtToken(user);
        //UsernamePasswordAuthenticationToken userAuthToken = new UsernamePasswordAuthenticationToken(token, null, new ArrayList<>());
        //SecurityContextHolder.getContext().setAuthentication(userAuthToken);
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }
}
