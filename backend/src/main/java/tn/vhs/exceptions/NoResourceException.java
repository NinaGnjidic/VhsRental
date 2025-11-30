package tn.vhs.exceptions;

public class NoResourceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	String messageKey;

	public NoResourceException(String messageKey) {
		this.messageKey = messageKey;
	}

	public String getMessageKey() {
		return messageKey;
	}

}
