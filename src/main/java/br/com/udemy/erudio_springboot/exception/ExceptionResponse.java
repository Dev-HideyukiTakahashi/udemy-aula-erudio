package br.com.udemy.erudio_springboot.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}
