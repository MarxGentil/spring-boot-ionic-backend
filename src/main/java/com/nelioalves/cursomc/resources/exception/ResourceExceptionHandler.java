package com.nelioalves.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nelioalves.cursomc.services.exceptions.DataIntegrityException;

import javassist.tools.rmi.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {

	//objectNotFound poderia ser qualquer nome, o nome eu escolho
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardErro> objectNotFound(ObjectNotFoundException e, HttpServletRequest request){
		StandardErro err = new StandardErro(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	//dataIntegrity poderia ser qualquer nome, o nome eu escolho
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardErro> dataIntegrity(ObjectNotFoundException e, HttpServletRequest request){
		StandardErro err = new StandardErro(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	//validation poderia ser qualquer nome, o nome eu escolho
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandardErro> validation(MethodArgumentNotValidException e, HttpServletRequest request){
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de Validação", System.currentTimeMillis());		
		//percorre o erro e pega apenas o nome do campo e a mensagem do erro para não ficar com a mensagem gigantesca na tela
		for(FieldError x : e.getBindingResult().getFieldErrors()) {
			err.addError(x.getField(), x.getDefaultMessage());
		}		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
}
