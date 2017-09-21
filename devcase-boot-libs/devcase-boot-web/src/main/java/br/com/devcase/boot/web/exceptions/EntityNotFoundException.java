package br.com.devcase.boot.web.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Not found")
public class EntityNotFoundException extends RuntimeException {

}
