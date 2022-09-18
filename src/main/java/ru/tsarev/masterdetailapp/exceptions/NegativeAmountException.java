package ru.tsarev.masterdetailapp.exceptions;

public class NegativeAmountException extends PositionException {
	
	public NegativeAmountException(String message) {
		super(message);
	}
}
