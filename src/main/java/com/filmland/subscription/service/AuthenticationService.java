package com.filmland.subscription.service;

import com.filmland.subscription.security.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private  CustomerService customerService;

    /**
     *
     * @param userId
     * @param password
     * @return
     * @throws Exception
     */
    public String getToken(String userId, String password) throws Exception{
        authenticate(userId, password);
        final UserDetails userDetails = customerService.loadUserByUsername(userId);
        return jwtTokenUtil.generateToken(userDetails);
    }

    /**
     *
     * @param userId
     * @param password
     * @throws Exception
     */
    private void authenticate(String userId, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userId, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

    }

}
