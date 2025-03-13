package br.com.udemy.erudio_springboot.service;

import br.com.udemy.erudio_springboot.dto.security.AccountCredentialsDTO;
import br.com.udemy.erudio_springboot.dto.security.TokenDTO;
import br.com.udemy.erudio_springboot.repository.UserRepository;
import br.com.udemy.erudio_springboot.security.swt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
    }

    public ResponseEntity<TokenDTO> signIn(AccountCredentialsDTO credentialsDTO){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentialsDTO.getUsername(),
                        credentialsDTO.getPassword()
                )
        );

        var user = userRepository.findByUsername(credentialsDTO.getUsername());
        if(user == null) throw new UsernameNotFoundException("User not found!");

        var tokenResponse = jwtTokenProvider.createAccessToken(credentialsDTO.getUsername(),
                user.getRoles());

        return ResponseEntity.ok(tokenResponse);
    }
}
