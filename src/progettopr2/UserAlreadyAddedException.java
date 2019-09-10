package progettopr2;

public class UserAlreadyAddedException extends Exception {

	/*
	 *	Eccezione che indica l'errore in cui un utente che si vuole registrare inserisca un id non disponibile 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyAddedException() {
		super();
	}
	
	public UserAlreadyAddedException(String s) {
		super(s);
	}
}
