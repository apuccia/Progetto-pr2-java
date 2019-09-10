package progettopr2;

public class WrongPasswordException extends Exception {

	/*
	 * 	Eccezione che indica l'errore in cui un utente appartenente alla struttura users inserisca la sua corrispondente password sbagliata
	 */
	private static final long serialVersionUID = 1L;
	
	public WrongPasswordException() {
		super();
	}
	
	public WrongPasswordException(String s) {
		super(s);
	}
	
}
