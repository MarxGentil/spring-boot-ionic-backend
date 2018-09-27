package com.nelioalves.cursomc.resources.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardErro {
	private static final long serialVersionUID = 1L;
	
	private List<FieldMessage> erros = new ArrayList<>();
	

	public ValidationError(Long timeStamp, Integer status, String error, String message, String path) {
		super(timeStamp, status, error, message, path);
	}

	public List<FieldMessage> getErrors() {
		return erros;
	}

	public void addError(String fieldName, String messagem) {
		erros.add(new FieldMessage(fieldName, messagem));
	}
	

}
