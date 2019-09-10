package progettopr2;

public class UserNotRegisteredException extends Exception {

	/*
	 *  Eccezione che indica l'errore in cui un utente non sia registrato e quindi aggiunto alla struttura
	 */
	private static final long serialVersionUID = 1L;
	
	public UserNotRegisteredException() {
		super();
	}
	
	public UserNotRegisteredException(String s) {
		super(s);
	}
}

