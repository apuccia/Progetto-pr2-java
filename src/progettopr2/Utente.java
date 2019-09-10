package progettopr2;

public class Utente implements Comparable<Utente> {
	/*
	 *  OVERVIEW: tipo di dati che rappresenta un id e la password associata.
	 *  TYPICAL DATA:
	 *  	<id, password> 
	 *  	dove id e password sono stringhe.
	 */
	
	private String user;
	private String passw;
	/*
	 * AF(user, passw):
	 * 		<user, passw>
	 * IR(user, passw):
	 * 		user != null && passw != null
	 */
	
	public Utente(String nome, String pass) {
		/*
		 * REQUIRES: nome != null && pass != null.
		 * THROWS: se nome == null || pass == null lancia NullPointerException unchecked.
		 * MODIFIES: this.
		 * EFFECTS: crea una nuova coppia <nome, pass>.
		 */
		
		if(nome == null || pass == null) {
			throw new NullPointerException();
		}
		user = nome;
		passw = pass;
	}
	
	public Utente(String nome) {
		/*
		 * REQUIRES: nome != null
		 * THROWS: se nome == null lancia NullPointerException unchecked.
		 * MODIFIES: this.
		 * EFFECTS: crea una nuova coppia <nome, ->.
		 */
		
		if(nome == null) {
			throw new NullPointerException();
		}
		user = nome;
	}
	
	public boolean checkId(String s) {
		/*
		 * REQUIRES: s != null.
		 * THROWS: se s == null lancia NullPointerException unchecked.
		 * EFFECTS: se s==id restituisce true altrimenti restituisce false.
		 */
		
		if(s == null) {
			throw new NullPointerException();
		}
		return user.equals(s);
	}
	
	public boolean checkPassword(String password) throws NullPointerException{
		/*
		 * REQUIRES: password != null.
		 * THROWS: se password == null lancia NullPointerException unchecked.
		 * EFFECTS: se password = passw restituisce true altrimenti restituisce false.
		 */
		
		if(password == null) {
			throw new NullPointerException();
		}
		
		if(passw.equals(password)){
			return true;
		}
		else {
			return false;
		}
	}
	
	public String getId() {
		/*
		 * EFFECTS: restituisce l'id dell'utente.
		 */
		
		return user;
	}
	
	/*
	 * Faccio override per usare Hashtable.
	 */
	@Override
	public boolean equals(Object u) {
		/*
		 * REQUIRES: u != null.
		 * THROWS: se o == null lancia NullPointerException unchecked disponibile in Java.
		 * EFFECTS: se u.getId() == this.getId() restituisce true altrimenti restituisce false.
		 */
		
		Utente a = (Utente) u;
		
		return a.getId().equals(user);
	}
	
	@Override
	public int hashCode(){
		/*
		 * EFFECTS: restituisce il risultato della funzione hashCode delle stringhe moltiplicato per 37.
		 */
		return 37 * user.hashCode();
	}
	
	@Override
	public String toString() {
		return "<" + this.user + ", " + this.passw + ">";
	}
	
	@Override
	public int compareTo(Utente u) {
		/*
		 * REQUIRES: u != null.
		 * THROWS: se u == null lancia NullPointerException unchecked disponibile in Java.
		 * EFFECTS: se u == this restituisce true, se u < this restituisce 1, se u > this restituisce -1.
		 */
		return u.getId().compareTo(user);
	}
}
