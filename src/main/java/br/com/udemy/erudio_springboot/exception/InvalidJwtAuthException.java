package br.com.udemy.erudio_springboot.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidJwtAuthException extends AuthenticationException {

    public InvalidJwtAuthException(String msg) {
        super(msg);
    }
}
