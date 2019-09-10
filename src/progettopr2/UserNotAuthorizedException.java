package progettopr2;

public class UserNotAuthorizedException extends Exception {

	/*
	 *	Eccezione che indica l'errore in cui un utente tenta di accedere ad un dato per cui non ha un'autorizzazione 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserNotAuthorizedException() {
		super();
	}
	
	public UserNotAuthorizedException(String s) {
		super(s);
	}
}
