package progettopr2;

import java.util.Iterator;

public interface SecureDataContainer<E> {
	/*
	 * OVERVIEW: Tipo di dati modificabile che rappresenta una collezione di oggetti di tipo E. Permette la memorizzazione e 
	 * 		la condivisione degli oggetti attraverso un meccanismo di sicurezza che:
	 * 			1) gestisce le identità dei proprietari dei dati.
	 * 			2) permette al proprietario dei dati di autorizzare altri utenti ad accedere ai suoi dati, gli utenti senza
	 * 			   autorizzazione non possono accedervi.
	 * 		
	 * TYPICAL DATA: 
	 * 		{
	 * 			<utente_0, pass_0, [<proprietario_0, dato_0, autorizzati_0>, ... , <proprietario_0, dato_n-1, autorizzati_n-1>]>
	 * 							 , ...,
	 * 			<utente_h-1, pass_h-1, [<proprietario_0, dato_0, autorizzati_0>, ..., <proprietario_0, dato_k-1, autorizzati_k-1>]> 
	 * 		}
	 * 		dove:
	 * 			I primi 2 campi delle triple sono: (forall 0<=i<h. utente_i e pass_i sono stringhe, in cui non esistono valori uguali di utente_i).
	 * 			Il terzo campo delle triple è: (forall 0<=i<h. collezione di coppie dove:
	 * 				(forall 0<=j<ind_i, ind_0=n, ..., ind_h-1=k]. <proprietario_j, dato_j, autorizzati_j> è una tripla in cui:
	 * 					1) proprietario_j è una stringa che indica il proprietario effettivo del dato, assume valori in [utente_0, ..., utente_h-1].
	 * 					2) dato_j è un elemento di tipo E. 
	 * 					3) autorizzati_j è un insieme di stringhe che rappresenta gli utenti autorizzati ad accedere a dato_j.
	 */
	
	/*
	 * Crea un nuovo utente unico nella collezione.
	 */
	public void createUser(String id, String passw)
			throws NullPointerException, UserAlreadyAddedException, IllegalArgumentException;
		/*
		 * REQUIRES: id != null && passw != null && (forall 0<=i<h. (id != utente_i)).
		 * THROWS: se id == null || passw == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se (exists 0<=i<h. (utente_i == id)) lancia UserAlreadyAddedException checked da implementare.
		 * 		   se id || passw sono vuote lancia IllegalArgumentException() unchecked disponibile in Java.
		 * MODIFIES: this.
		 * EFFECTS: crea una nuova tripla <id, passw, []>.
		 */
	
	/*
	 * Rimuove l'utente user se vengono rispettati i controlli di identità.
	 */
	public void removeUser(String id, String passw)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, IllegalArgumentException;
		/*
		 * REQUIRES: id != null && passw != null && (exists 0<=i<h (id == utente_i && passw == pass_i)).
		 * THROWS: se id == null || passw == null lancia NullPointerException unchecked da implementare.
		 * 		   se !(exists 0<=i<h. (id == user_i) lancia UserNotRegisteredException checked da implementare.
		 * 		   se (exists 0<=i<h. (id == user_i && passw != pass_i)) lancia WrongPasswordException checked da implementare.
		 * 		   se id || passw sono vuote lancia IllegalArgumentException unchecked disponibile in Java
		 * MODIFIES: this.
		 * EFFECTS: rimuove la tripla <id, passw, A> da this.
		 */
	
	/*
	 * Restituisce il numero degli elementi posseduti dall'utente se vengono rispettati i controlli d'identità.
	 */
	public int getSize(String owner, String passw)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, IllegalArgumentException;
		/*
		 * REQUIRES: owner != null && passw != null && (exists 0<=i<h. (owner == utente_i && passw == pass_i)).
		 * THROWS: se owner == null || passw == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se !(exists 0<=i<h. (owner == utente_i)) lancia UserNotRegisteredException checked da implementare.
		 * 		   se (exists 0<=i<h. (owner == utente_i && pass_i != passw)) lancia WrongPasswordException checked da implementare.
		 * 		   se owner || passw sono vuote lancia IllegalArugmentException.
		 * EFFECTS: restituisce il numero degli elementi dell'utente owner.
		 */
	
	/*
	 * Inserisce l'elemento nella collezione se vengono rispettati i controlli d'identità.
	 */
	public boolean put(String user, String passw, E data)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, IllegalArgumentException;
		/*
		 * REQUIRES: user != null && passw != null && data != null && (exists 0<=i<h. (user == utente_i && passw == pass_i)).
		 * THROWS: se user == null || passw == null || data == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se !(exists 0<=i<=h. (user == utente_i)) lancia UserNotRegisteredException checked da implementare.
		 * 		   se (exists 0<=i<h. (user == utente_i && passw != pass_i)) lancia WrongPasswordException checked da implementare.
		 * 		   se user || passw sono vuote lancia IllegalArgumentException.
		 * MODIFIES: this.
		 * EFFECTS: modifica la tripla <user, passw, A> inserendo una nuova tripla <_, data, _>.
		 */
	
	/*
	 * Ottiene una copia dell'elemento se vengono rispettati i controlli d'identità.
	 */
	public E get(String user, String passw, E data)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, UnavailableDataException, IllegalArgumentException;
		/*
		 * REQUIRES: user != null && passw != null && data != null &&  (exists 0<=i<h. (user == utente_i && passw == pass_i && (exists 0<=j<ind_i, (ind_0=n ... ind_h-1=k, i in [0,h-1]). (data == data_j))).
		 * THROWS: se user == null || passw == null || data == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se !(exists 0<=i<h. (user == utente_i)) lancia UserNotRegisteredException checked da implementare.
		 * 		   se (exists 0<=i<h. (user == utente_i && passw != pass_i)) lancia WrongPasswordException checked da implementare.
		 * 		   se (exists 0<=i<h. (user == utente_i && passw == pass_i && !(exists 0<=j<ind_i, (ind_0=n ... ind_h-1=k, i in [0,h-1]). (data == data_j)))) lancia UnavailableDataException checked da implementare.
		 * 		   se user || passw sono vuote lancia IllegalArgumentException unchecked disponibile in Java.
		 * EFFECTS:	restituisce una copia del primo elemento data di owner.
		 */
	
	/*
	 * Rimuove l'elemento dalla collezione se vengono rispettati i controlli d'identità.
	 */
	public E remove (String user, String passw, E data)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, UnavailableDataException, IllegalArgumentException;
		/*
		 * REQUIRES: user != null && passw != null && data != null && (exists 0<=i<h. (user == utente_i && passw == pass_i && (exists 0<=j<ind_i, (ind_0=n ... ind_h-1=k, i in [0,h-1]). (data == data_j)))).
		 * THROWS: se user == null || passw == null || data == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se !(exists 0<=i<h. (user == utente_i)) lancia UserNotRegisteredException checked da implementare.
		 * 		   se (exists 0<=i<h. (user == utente_i && passw != pass_i)) lancia WrongPasswordException checked da implementare.
		 * 		   se (exists 0<=i<h. (user == utente_i && passw == pass_i && !(exists 0<=j<ind_i, (ind_0=n ... ind_h-1=k, i in [0,h-1]). (data == data_j)))) lancia UnavailableDataException checked da implementare.
		 * 		   se user || passw sono vuote lancia IllegalArgumentException unchecked disponibile in Java.
		 * MODIFIES: this.
		 * EFFECTS: modifica la tripla <user, passw, A>, rimuovendo da A la coppia <proprietario_j, data_j, autorizzati_j>, con 0<=j<ind_i, (ind_0=n ... ind_h-1=k, i in [0,h-1]).
		 */
	
	/*
	 * Crea una nuova copia dell'elemento nella collezione se vengono rispettati i controlli dell'identità.
	 */
	public void copy (String user, String passw, E data)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, UnavailableDataException;
		/*
		 * REQUIRES: user != null && passw != null && data != null && (exists 0<=i<h. (user == utente_i && passw == pass_i && (exists 0<=j<ind_i, (ind_0 = n ... ind_h = k). (data == data_j)))).
		 * THROWS: se user == null || passw == null || data == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se !(exists 0<=i<h. (owner == utente_i)) lancia UserNotRegisteredException checked da implementare.
		 * 		   se (exists 0<=i<h. (owner == utente_i && passw != pass_i)) lancia WrongPasswordException checked da implementare.
		 * 		   se (exists 0<=i<h. (owner == utente_i && passw == pass_i && !(exists 0<=j<ind_i, (ind_0=n ... ind_h-1=k, i in [0,h-1]). (data == data_j)))) lancia UnavailableDataException checked da implementare.
		 * 		   se user || passw sono vuote lancia IllegalArgumentException unchecked disponibile in Java.
		 * MODIFIES: this.
		 * EFFECTS: modifica la tripla <user, passw, A> aggiungendo ad A una nuova coppia <_, data, _>.
		 */
	
	/*
	 * Condivide l'elemento della collezione con un altro utente se vengono rispettati i controlli dell'identità.
	 */
	public void share (String owner, String passw, String other, E data)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, UnavailableDataException, IllegalArgumentException, UserNotAuthorizedException;
		/*
		 * REQUIRES: owner != null && passw != null && other != null && data != null && owner != other &&
		 * 		 exists(0<=i<h, 0<=j<h, i!=j. (owner == users_i && passw == pass_i && other == users_j && (exists 0<=m<ind_i, (ind_0=n-1 ... ind_h-1=k-1, i in [0,h-1]). (data == data_m && owner == proprietario_m)))).
		 * THROWS: se owner == null || passw == null || other == null || data == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se owner == other lancia IllegalArgumentException unchecked disponibile in Java.
		 * 		   se !(exists (0<=i<h, 0<=j<h, i!=j. (owner == utente_i && other == utente_j))) lancia UserNotRegisteredException checked da implementare.
		 * 		   se exists (0<=i<h. (owner == utente_i && passw != pass_i) lancia WrongPasswordException checked da implementare.
		 * 		   se exists (0<=i<h. (owner == utente_i && passw == pass_i && !(exists 0<=j<ind_i, (ind_0 = n ... ind_h-1 = k). (data == data_j)))) lancia UnavailableDataException checked da implementare.
		 * 		   se exists (0<=i<h. (owner == utente_i && passw == pass_i && !(exists 0<=j<ind_i, (ind_0 = n ... ind_h-1 = k, i in [0,h-1]). (data == data_j && proprietario_j != owner)))) lancia UserNotAuthorizedException checked da implementare.
		 * 		   se owner || passw || other sono vuote lancia IllegalArgumentException unchecked disponibile in Java.
		 * MODIFIES: this.
		 * EFFECTS: condivide l'elemento data dell'utente owner con l'utente other. 
		 */
	
	/*
	 * Restituisce un iteratore, senza remove, che genera tutti gli elementi dell'utente in modo arbitrario se vengono rispettati i controlli
	 * dell'identità.
	 */
	public Iterator<E> getIterator(String user, String passw)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, IllegalArgumentException;
		/*
		 * REQUIRES: user != null && passw != null && (exists 0<=i<h. (owner = utente_i && passw == pass_i)).
		 * THROWS: se owner == null || passw == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se !exists 0<=i<h. (owner == utente_i) lancia UserNotRegistered checked da implementare.
		 * 		   se exists 0<=i<h. (owner == utente_i && pass_i != passw) lancia WrongPasswordException checked da implementare.
		 * 		   se user || passw sono vuote lancia IllegalArgumentException unchecked disponibile in Java.
		 * EFFECTS: restituisce un iteratore di tutti gli elementi dell'utente owner.
		 */
	
}
