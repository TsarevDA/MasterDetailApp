package ru.tsarev.masterdetailapp.exceptions;

public class AlreadyExistException extends RuntimeException {
	
	public AlreadyExistException(String message) {
		super(message);
	}
}

