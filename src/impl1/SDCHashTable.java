package impl1;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import progettopr2.*;

public class SDCHashTable<E extends Serializable> implements SecureDataContainer<E>{
	private List<Utente> users;
	private Map<Utente, ArrayList<Dato<E>>> datas;
	// per compilare javac impl1/*.java progettopr2/*.java
	// per eseguire java impl1.SDCHashTableMain
	/*
	 * AF(users, datas):
	 * 		{	//primo utente 
	 * 			<	
	 * 				users.get(0).getId(),				
	 * 				users.get(0).getPassword(),
	 * 				[
	 * 					<datas.get(users.get(0)).get(0).getOwner(),
	 * 					 datas.get(users.get(0)).get(0).getData(),
	 * 					 datas.get(users.get(0)).get(0).getAuthorized()>
	 * 						, ... , 
	 * 					<datas.get(users.get(0)).get(datas.get(users.get(0)).size()-1).getOwner(),
	 * 					 datas.get(users.get(0)).get(datas.get(users.get(0)).size()-1).getData(),
	 * 					 datas.get(users.get(0)).get(datas.get(users.get(0)).size()-1).getAuthorized()>
	 * 				]
	 * 			>
	 * 				, ... ,
	 * 			//ultimo utente
	 * 			<
	 * 				users.get(users.size()-1).getId(),
	 * 				users.get(users.size()-1).getPassword(),
	 * 				[
	 * 					<datas.get(users.get(users.size()-1)).get(0).getOwner(), 
	 * 					 datas.get(users.get(users.size()-1)).get(0).getData(),
	 * 					 datas.get(users.get(users.size()-1)).get(0).getAuthorized()>
	 * 						, ... ,
	 * 					<datas.get(users.get(users.size()-1)).get(datas.get(users.size()-1).size()-1).getOwner(),
	 * 					 datas.get(users.get(users.size()-1)).get(datas.get(users.size()-1).size()-1).getData(),
	 * 					 datas.get(users.get(users.size()-1)).get(datas.get(users.size()-1).size()-1).getAuthorized()>
	 * 				]
	 * 			>
	 * 		}
	 */

	/*
	 * IR(users, datas):
	 * 		users != null && datas != null &&
	 * 		forall 0<=i<users.size(). (users.get(i) != null &&
	 * 								   users.get(i).getId() != null &&
	 *                                 users.get(i).getPassword() != null && !(exists 0<=j<users.size(). users.get(i).getId() == users.get(j).getId()))
	 *      
	 *      Set<Utente> ut = datas.keySet(), Iterator<Utente> it = ut.getIterator();
	 *      while (it.hasNext()). (Utente u = it.next(); u != null && u.getId() != null && u.getPassword() != null && datas.get(u) != null && users.contains(u) &&
	 *      					 for (Dato<E> I: datas.get(u)). (I != null && I.getOwner() != null && I.getData() != null && I.getAuthorized() != null &&
	 *      													for(String s: I.getAuthorized()). (s != null && s != I.getOwner() && 
	 *      																	users.contains(new Utente(s)) && datas.containsKey(new Utente(s))))). 
	 *      
	 */
	
	public SDCHashTable() {
		/*
		 * EFFECTS: crea una nuova istanza di SDCHashTable vuota.
		 */
		
		users = new ArrayList<Utente>();
		datas = new Hashtable<Utente, ArrayList<Dato<E>>>();
	}
	
	/*
	 * Crea un nuovo utente unico nella collezione.
	 */
	public void createUser(String id, String passw)
			throws NullPointerException, UserAlreadyAddedException, IllegalArgumentException{
		/*
		 * REQUIRES: id != null && passw != null && (forall 0<=i<h. (id != utente_i)).
		 * THROWS: se id == null || passw == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se (exists 0<=i<h. (utente_i == id)) lancia UserAlreadyAddedException checked da implementare.
		 * 		   se id || passw sono vuote lancia IllegalArgumentException() unchecked disponibile in Java.
		 * MODIFIES: this.
		 * EFFECTS: crea una nuova tripla <id, passw, []>.
		 */
		
		if(id == null || passw == null) {
			throw new NullPointerException();
		}
		
		if(id.isEmpty() || passw.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		if(users.size() != 0 && users.indexOf(new Utente(id)) != -1) {
			throw new UserAlreadyAddedException(id + " è un utente già registrato");
		}
		
		Utente ut = new Utente(id, passw);
		
		users.add(ut);	//aggiungo il nuovo utente
		datas.put(ut, new ArrayList<Dato<E>>());	//aggiungo la sua lista di elementi
	}
	
	/*
	 * Rimuove l'utente user se vengono rispettati i controlli di identità. La rimozione di un utente comporta una rimozione di
	 * tutti gli elementi posseduti compresi quelli condivisi ad altri utenti.
	 */
	public void removeUser(String id, String passw)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, IllegalArgumentException{
		/*
		 * REQUIRES: id != null && passw != null && (exists 0<=i<h (id == utente_i && passw == pass_i)).
		 * THROWS: se id == null || passw == null lancia NullPointerException unchecked da implementare.
		 * 		   se !(exists 0<=i<h. (id == user_i) lancia UserNotRegisteredException checked da implementare.
		 * 		   se (exists 0<=i<h. (id == user_i && passw != pass_i)) lancia WrongPasswordException checked da implementare.
		 * 		   se id || passw sono vuote lancia IllegalArgumentException unchecked disponibile in Java
		 * MODIFIES: this.
		 * EFFECTS: rimuove la tripla <id, passw, A> da this, rimuovendo agli altri utenti eventuali dati condivisi.
		 */
		
		if(id == null || passw == null) {
			throw new NullPointerException();
		}
		
		if(id.isEmpty() || passw.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		Utente ut = new Utente(id);
		int pos = users.indexOf(ut);
		
		if(pos == -1) {
			throw new UserNotRegisteredException(id + " non è un utente registrato");
		}
		
		if(users.get(pos).checkPassword(passw)) {
			while(!datas.get(ut).isEmpty()) {	//rimuovo tutti gli elementi dell'utente
				try {
					this.remove(id, passw, datas.get(ut).get(0).getData());
				}catch(UnavailableDataException e) {
					e.printStackTrace();
				}
			}
			
			datas.remove(ut);	//rimuovo la chiave e la lista degli elementi dell'utente
			users.remove(ut);	//rimuovo l'utente
		}
		throw new WrongPasswordException();
	}
	
	/*
	 * Restituisce il numero degli elementi posseduti dall'utente se vengono rispettati i controlli d'identità, non vengono contati eventuali
	 * elementi che erano stati condivisi nei suoi confronti.
	 */
	public int getSize(String owner, String passw)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, IllegalArgumentException{
		/*
		 * REQUIRES: owner != null && passw != null && (exists 0<=i<h. (owner == utente_i && passw == pass_i)).
		 * THROWS: se owner == null || passw == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se !(exists 0<=i<h. (owner == utente_i)) lancia UserNotRegisteredException checked da implementare.
		 * 		   se (exists 0<=i<h. (owner == utente_i && pass_i != passw)) lancia WrongPasswordException checked da implementare.
		 * 		   se owner || passw sono vuote lancia IllegalArugmentException.
		 * EFFECTS: restituisce il numero degli elementi che l'utente owner possiede all'interno della collezione.
		 */
		
		if(owner == null || passw == null) {
			throw new NullPointerException();
		}
		
		if(owner.isEmpty() || passw.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		Utente ut = new Utente(owner);
		int pos = users.indexOf(ut);
		
		if(pos == -1) {
			throw new UserNotRegisteredException(owner + "non è un utente registrato");
		}
		
		if(users.get(pos).checkPassword(passw)) {
			int cont = 0;
			
			for(Dato<E> I: datas.get(ut)) {
				if(I.getOwner().equals(owner)) {
					cont++;	//conto solo gli elementi posseduti.
				}
			}
			
			return cont;
		}
		else {
			throw new WrongPasswordException("Password sbagliata");
		}
	}
	
	/*
	 * Inserisce l'elemento nella collezione se vengono rispettati i controlli d'identità.
	 */
	public boolean put(String user, String passw, E data)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, IllegalArgumentException{
		/*
		 * REQUIRES: user != null && passw != null && data != null && (exists 0<=i<h. (user == utente_i && passw == pass_i)).
		 * THROWS: se user == null || passw == null || data == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se !(exists 0<=i<=h. (user == utente_i)) lancia UserNotRegisteredException checked da implementare.
		 * 		   se (exists 0<=i<h. (user == utente_i && passw != pass_i)) lancia WrongPasswordException checked da implementare.
		 * 		   se user || passw sono vuote lancia IllegalArgumentException.
		 * MODIFIES: this.
		 * EFFECTS: se data appartiene a un altro utente oppure non era inserito nella collezione modifica la tripla <user, passw, A>
		 * 			presente in this, aggiungendo ad A la coppia <user, data, {}> e restituendo true, altrimenti restituisce false.
		 */
		
		if(user == null || passw == null || data == null) {
			throw new NullPointerException();
		}
		
		if(user.isEmpty() || passw.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		Utente ut = new Utente(user);
		int pos = users.indexOf(ut);
		
		if(pos == -1) {
			throw new UserNotRegisteredException(user + "non è un utente registrato");
		}
		
		if(users.get(pos).checkPassword(passw)) {
			for(Dato<E> I: datas.get(ut)) {	//controllo se user vuole inserire un riferimento a un suo dato già presente nella collezione
				
				if(I.getData() == data) {
					return false;
				}
			}
			
			if(checkReference(data)) {	//controllo se user vuole inserire un riferimento di un dato posseduto da un altro utente
				datas.get(ut).add(new Dato<E>(user, deepClone(data)));
				
				return true;
			}
			
			datas.get(ut).add(new Dato<E>(user, data));	//caso in cui user inserisce un riferimento che non appartiene alla collezione
			
			return true;
		}
		
		throw new WrongPasswordException("Password sbagliata");
}
	
	/*
	 * Ottiene una copia dell'elemento se vengono rispettati i controlli d'identità. Può essere usata anche da parte di utenti autorizzati
	 * per poter lavorare sull'elemento.
	 */
	public E get(String user, String passw, E data)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, UnavailableDataException, IllegalArgumentException{
		/*
		 * REQUIRES: user != null && passw != null && data != null &&  (exists 0<=i<h. (user == utente_i && passw == pass_i && (exists 0<=j<ind_i, (ind_0=n ... ind_h-1=k, i in [0,h-1]). (data == data_j))).
		 * THROWS: se user == null || passw == null || data == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se !(exists 0<=i<h. (user == utente_i)) lancia UserNotRegisteredException checked da implementare.
		 * 		   se (exists 0<=i<h. (user == utente_i && passw != pass_i)) lancia WrongPasswordException checked da implementare.
		 * 		   se (exists 0<=i<h. (user == utente_i && passw == pass_i && !(exists 0<=j<ind_i, (ind_0=n ... ind_h-1=k, i in [0,h-1]). (data == data_j)))) lancia UnavailableDataException checked da implementare.
		 * 		   se user || passw sono vuote lancia IllegalArgumentException unchecked disponibile in Java.
		 * EFFECTS:	restituisce una copia del primo elemento data, posseduto o in condivisione, di owner.
		 */
		
		if(user == null || passw == null || data == null) {
			throw new NullPointerException();
		}
		
		if(user.isEmpty() || passw.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		Utente ut = new Utente(user);
		int pos = users.indexOf(ut);	//verifico se esiste l'utente
		
		if(pos == -1) {
			throw new UserNotRegisteredException(user + " non è un utente registrato");
		}
		
		if(users.get(pos).checkPassword(passw)) {
			int poselt = datas.get(ut).indexOf(new Dato<E>(user, data));
			
			if(poselt != -1) {	//controllo se esiste l'elemento
				return datas.get(ut).get(poselt).getData();
			}
			
			throw new UnavailableDataException("Dato non disponibile");
		}
		
		throw new WrongPasswordException("Password sbagliata");
	}
	
	/*
	 * Rimuove l'elemento dalla collezione se vengono rispettati i controlli d'identità. Può essere usata anche da parte di utenti autorizzati,
	 * in questo caso viene cancellata solo la loro copia (rinunciando alla condivisione). Se viene usata dal proprietario lo cancella insieme alle
	 * copie degli eventuali utenti autorizzati.
	 */
	public E remove (String user, String passw, E data)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, UnavailableDataException, IllegalArgumentException{
		/*
		 * REQUIRES: user != null && passw != null && data != null && (exists 0<=i<h. (user == utente_i && passw == pass_i && (exists 0<=j<ind_i, (ind_0=n ... ind_h-1=k, i in [0,h-1]). (data == data_j)))).
		 * THROWS: se user == null || passw == null || data == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se !(exists 0<=i<h. (user == utente_i)) lancia UserNotRegisteredException checked da implementare.
		 * 		   se (exists 0<=i<h. (user == utente_i && passw != pass_i)) lancia WrongPasswordException checked da implementare.
		 * 		   se (exists 0<=i<h. (user == utente_i && passw == pass_i && !(exists 0<=j<ind_i, (ind_0=n ... ind_h-1=k, i in [0,h-1]). (data == data_j)))) lancia UnavailableDataException checked da implementare.
		 * 		   se user || passw sono vuote lancia IllegalArgumentException unchecked disponibile in Java.
		 * MODIFIES: this.
		 * EFFECTS: modifica la tripla <user, passw, A>, rimuovendo da A la coppia <proprietario_j, data_j, autorizzati_j>, con 0<=j<ind_i, (ind_0=n ... ind_h-1=k, i in [0,h-1]).
		 * 			Se user == proprietario rimuove la condivisione a tutti gli utenti altrimenti viene rimossa solo la copia dell'utente autorizzato e il suo id viene rimosso da autorizzati_j.
		 * 			In entrambi i casi restituisce l'elemento eliminato dalla collezione.
		 */
		
		if(user == null || passw == null || data == null) {
			throw new NullPointerException();
		}
		
		if(user.isEmpty() || passw.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		Utente ut = new Utente(user);
		int pos = users.indexOf(ut);
		
		if(pos == -1) {
			throw new UserNotRegisteredException(user + " non è un utente registrato");
		}
		
		if(users.get(pos).checkPassword(passw)) {
			int poselt = datas.get(ut).indexOf(new Dato<E>(user, data));
			
			if(poselt != -1) {
				Dato<E> I = datas.get(ut).get(poselt);
				E clone = deepClone(I.getData());
					
				if(I.getOwner().equals(user)) {
					while(!I.getAuthorized().isEmpty()) {
						Utente a = new Utente(I.getAuthorized().first());
						int j=0;
						
						while(j<datas.get(a).size()){
							if(datas.get(a).get(j).getData() == I.getData()) {
								datas.get(a).remove(j);
							}
							else {
								j++;
							}
						}
							
						I.removeAuthorized(a.getId());
					}
				}
				else {
					I.removeAuthorized(user);
				}
					
				datas.get(ut).remove(I);
					
				return clone;
			}
			
			throw new UnavailableDataException("Dato non disponibile");
		}
		
		throw new WrongPasswordException("Password sbagliata");
	}
	
	/*
	 * Crea una nuova copia dell'elemento nella collezione se vengono rispettati i controlli dell'identità. Può essere usata sia dal proprietario del dato 
	 * che dagli utenti autorizzati.
	 */
	public void copy (String user, String passw, E data)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, UnavailableDataException{
		/*
		 * REQUIRES: user != null && passw != null && data != null && (exists 0<=i<h. (user == utente_i && passw == pass_i && (exists 0<=j<ind_i, (ind_0 = n ... ind_h = k). (data == data_j)))).
		 * THROWS: se user == null || passw == null || data == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se !(exists 0<=i<h. (owner == utente_i)) lancia UserNotRegisteredException checked da implementare.
		 * 		   se (exists 0<=i<h. (owner == utente_i && passw != pass_i)) lancia WrongPasswordException checked da implementare.
		 * 		   se (exists 0<=i<h. (owner == utente_i && passw == pass_i && !(exists 0<=j<ind_i, (ind_0=n ... ind_h-1=k, i in [0,h-1]). (data == data_j)))) lancia UnavailableDataException checked da implementare.
		 * 		   se user || passw sono vuote lancia IllegalArgumentException unchecked disponibile in Java.
		 * MODIFIES: this.
		 * EFFECTS: modifica la tripla <user, passw, A> aggiungendo ad A una nuova coppia <user, data, {}>.
		 */
		
		if(user == null || passw == null || data == null) {
			throw new NullPointerException();
		}
		
		if(user.isEmpty() || passw.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		Utente ut = new Utente(user);
		int pos = users.indexOf(ut);
		
		if(pos == -1) {
			throw new UserNotRegisteredException(user + " non è un utente registrato");
		}
		
		if(users.get(pos).checkPassword(passw)) {
			int poselt = datas.get(ut).indexOf(new Dato<E>(user, data));
			
			if(poselt != -1) {
				Dato<E> I = datas.get(ut).get(poselt);
				E clone = deepClone(I.getData());
					
				datas.get(ut).add(new Dato<E>(user, clone));
					
				return; 
			}
			
			throw new UnavailableDataException("Dato non disponibile");
		}
		
		throw new WrongPasswordException("Password sbagliata");	
	}
	
	/*
	 * Condivide l'elemento della collezione con un altro utente se vengono rispettati i controlli dell'identità.
	 * Può essere usata solo dal proprietario del dato.
	 */
	public void share (String owner, String passw, String other, E data)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, UnavailableDataException, IllegalArgumentException, UserNotAuthorizedException{
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
		 * EFFECTS: se other non era autorizzato: modifica il campo A della tripla <owner, passw, A>, aggiungendo ad A al campo autorizzati_ind_j della prima tripla <owner, data, autorizzati_ind_j> la stringa other.
		 * 			Modifica il campo B della tripla <other, _, B>, aggiungendo la tripla <owner, data, autorizzati_ind_j>. 
		 */
		
		if(owner == null || passw == null || other == null || data == null) {
			throw new NullPointerException();
		}
		
		if(owner.equals(other) || owner.isEmpty() || passw.isEmpty() || other.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		Utente ut1 = new Utente(owner);	//controllo se esistono gli utenti
		Utente ut2 = new Utente(other);
		int posut1 = users.indexOf(ut1);
		int posut2 = users.indexOf(ut2);
		
		if(posut1 == -1 || posut2 == -1) {
			throw new UserNotRegisteredException(owner + " o " + other + " non sono utenti registrati");
		}
		
		if(users.get(posut1).checkPassword(passw)) {
			int poselt = datas.get(ut1).indexOf(new Dato<E>(owner, data));

			if(poselt != -1) {
				Dato<E> I = datas.get(ut1).get(poselt);
				
				if(I.getOwner().equals(owner)) {	//se owner è proprietario dell'elemento.
					datas.get(ut2).add(I);
					I.insertAuthorized(other);
					
					return;
				}
				else {	//owner non proprietario.
					throw new UserNotAuthorizedException(owner + " non può condividere dati di cui non è proprietario");
				}
			}
			
			throw new UnavailableDataException("Dato non disponibile");
		}
		
		throw new WrongPasswordException("Password sbagliata");
	}
	
	/*
	 * Restituisce un iteratore, senza remove, che genera tutti gli elementi dell'utente (posseduti e condivisi) in modo arbitrario se vengono rispettati i controlli
	 * dell'identità.
	 */
	public Iterator<E> getIterator(String user, String passw)
			throws NullPointerException, UserNotRegisteredException, WrongPasswordException, IllegalArgumentException{
		/*
		 * REQUIRES: user != null && passw != null && (exists 0<=i<h. (owner = utente_i && passw == pass_i)).
		 * THROWS: se owner == null || passw == null lancia NullPointerException unchecked disponibile in Java.
		 * 		   se !exists 0<=i<h. (owner == utente_i) lancia UserNotRegistered checked da implementare.
		 * 		   se exists 0<=i<h. (owner == utente_i && pass_i != passw) lancia WrongPasswordException checked da implementare.
		 * 		   se user || passw sono vuote lancia IllegalArgumentException unchecked disponibile in Java.
		 * EFFECTS: restituisce un iteratore di tutti gli elementi posseduti e condivisi dell'utente owner.
		 */
		
		if(user == null || passw == null) {
			throw new NullPointerException();
		}
		
		if(user.isEmpty() || passw.isEmpty()) {
			throw new IllegalArgumentException();
		}
		
		int pos = users.indexOf(new Utente(user));
		
		if(pos == -1) {
			throw new UserNotRegisteredException(user + " non è un utente registrato");
		}
		
		if(users.get(pos).checkPassword(passw)) {
			Utente ut = users.get(pos);
			Iterator<E> it = new EGenerator(datas.get(ut));
			
			return it;
		}
		else {
			throw new WrongPasswordException("Password sbagliata");
		}
	
	}
	
	private class EGenerator implements Iterator<E>{
		private List<Dato<E>> poss;
		int i;
		
		public EGenerator(ArrayList<Dato<E>> elementi) {
			poss = elementi;
			i = 0;
		}
		
		public boolean hasNext() {
			return i != poss.size();
		}
		
		public E next() {
			return poss.get(i++).getData();
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	private E deepClone(E data){
		/*
		 * 	REQUIRES: data != null && data serializzabile.
		 * 	THROWS: se data == null lancia NullPointerException unchecked disponibile in Java.
		 * 			se ho errore I/O lancia IOException checked disponibile in Java.
		 * 			se la classe dell'oggetto serializzabile non è stata trovata lancia ClassNotFoundException checked disponibile in Java.
		 *  EFFECTS: ritorna una copia deep di data.
		 */
		
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();	
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(data);	//serializzo il dato.

			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			E b = (E) ois.readObject();	//deserializzo il dato.
			
			return b;
		}
		catch(IOException e) {
		     e.printStackTrace();
		   
		     return null;
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
			
			return null;
		}
		
	}
	
	private boolean checkReference(E data) {
		/*
		 * REQUIRES: data != null.
		 * THROWS: se data == null lancia NullPointerException unchecked disponibile in Java.
		 * EFFECTS: se data è presente in this (esiste un riferimento nella collezione uguale a data) restituisce true altrimenti restituisce false.
		 */
		Set<Utente> uts = datas.keySet();
		
		for(Utente I: uts) {
			for(Dato<E> J: datas.get(I)) {
				if(J.getData() == data) {
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "	{\n	  " + users.toString() + "\n	  " + datas.toString() + "\n	}";
	}
	
}
