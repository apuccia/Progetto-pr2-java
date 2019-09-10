package progettopr2;

public class UnavailableDataException extends Exception {

	/*
	 * Eccezione che indica l'errore in cui un utente tenta di accedere ad un dato che non è disponibile 
	 */
	private static final long serialVersionUID = 1L;
	
	public UnavailableDataException() {
		super();
	}
	
	public UnavailableDataException(String s) {
		super(s);
	}
}
