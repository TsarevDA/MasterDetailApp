package ru.tsarev.masterdetailapp.exceptions;

public class NegativePriceException extends RuntimeException {
	
	public NegativePriceException(String message) {
		super(message);
	}
}
