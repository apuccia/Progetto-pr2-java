package impl2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

import progettopr2.*;

public class SDCTreeMap<E extends Serializable> implements SecureDataContainer<E> {
	private TreeMap<Utente, ArrayList<Dato<E>>> container;
	
	/*
	 * AF(container):
	 * 		{	//primo utente
	 * 			<container.firstKey().getId(),
	 * 			 container.firstKey().getPassword(),
	 * 			 [
	 * 				<container.get(container.firstKey()).get(0).getOwner(),
	 * 				 container.get(container.firstKey()).get(0).getData(),
	 * 				 container.get(container.firstKey()).get(0).getAuthorized()>
	 * 						, ... ,
	 * 				<container.get(container.firstKey()).get(container.get(container.firstKey()).size()-1).getOwner(),
	 * 				 container.get(container.firstKey()).get(container.get(container.firstKey()).size()-1).getData(),
	 * 				 container.get(container.firstKey()).get(container.get(container.firstKey()).size()-1).getAuthorized()>
	 * 			 ]>
	 * 						, ... ,
	 * 			//ultimo utente
	 * 			<container.lastKey().getId(),
	 * 			 container.lastKey().getPassword(),
	 *			 [
	 * 				<container.get(container.lastKey()).get(0).getOwner(),
	 * 				 container.get(container.lastKey()).get(0).getData(),
	 * 				 container.get(container.lastKey()).get(0).getAuthorized()>
	 * 						, ... ,
	 * 				<container.get(container.lastKey()).get(container.get(container.lastKey()).size()-1).getOwner(),
	 * 				 container.get(container.lastKey()).get(container.get(container.lastKey()).size()-1).getData(),
	 * 				 container.get(container.lastKey()).get(container.get(container.lastKey()).size()-1).getAuthorized()>
	 * 			 ]>
	 * 		}
	 */
	
	/*
	 * IR(container): container != null;
	 * 				  Iterator<Utente> it1 = container.keySet().getIterator();
	 * 				  forall Utente ut = it1.next(). (ut != null && ut.getId() != null && ut.getPassword() != null;
	 * 
	 * 						Iterator<Utente> it2 = container.keySet().getIterator();
	 * 						while(it2.hasNext()). (Utente u = it2.next(), ut!=u; (ut == u));
	 * 
	 * 						container.get(ut) != null &&
	 * 					    container.get(ut).getData() != null &&
	 * 						container.get(ut).getOwner() != null && container.containsKey(new Utente(container.get(ut).getOwner())) &&
	 * 						container.get(ut).getAuthorized() != null
	 * 
	 * 						Iterator<String> its = container.get(ut).getAuthorized().getIterator();
	 * 						while(its.hasNext()). String aut = its.next(); (aut != null &&
	 * 						  	aut != container.get(ut).getOwner() && container.containsKey(new Utente(aut))
	 * 				  
	 */
	
	public SDCTreeMap() {
		/*
		 * Crea una nuova istanza di SDCTreeMap vuota.
		 */
		
		container = new TreeMap<Utente, ArrayList<Dato<E>>>();
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
		
		Utente ut = new Utente(id, passw);
		if(container.containsKey(ut)) {
			throw new UserAlreadyAddedException(id + " è un utente già registrato");
		}
		
		container.put(ut, new ArrayList<Dato<E>>());
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
		
		Utente ut = container.floorKey(new Utente(id));
		
		if(ut == null || !ut.checkId(id)) {
			throw new UserNotRegisteredException(id + " non è un utente registrato");
		}
		
		if(ut.checkPassword(passw)) {
			while(!container.get(ut).isEmpty()) {
				try {
					this.remove(id, passw, container.get(ut).get(0).getData());
				}
				catch(Exception e) {
					e.printStackTrace();
				}
			}
			
			container.remove(ut);
			return;
		}
		
		throw new WrongPasswordException("Password sbagliata");
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
		
		Utente ut = container.floorKey(new Utente(owner));
		
		if(ut == null || !ut.checkId(owner)) {
			throw new UserNotRegisteredException(owner + " non è un utente registrato");
		}
		
		if(ut.checkPassword(passw)) {
			int cont = 0;
			
			for(Dato<E> I: container.get(ut)) {
				if(I.getOwner().equals(owner)) {
					cont++;
				}
			}
			
			return cont;
		}
		
		throw new WrongPasswordException("Password sbagliata.");
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
		
		Utente ut = container.floorKey(new Utente(user));
		
		if(ut == null || !ut.checkId(user)) {
			throw new UserNotRegisteredException(user + " non è un utente registrato");
		}
		
		if(ut.checkPassword(passw)) {
			for(Dato<E> I: container.get(ut)) {
				if(I.getData() == data) {
					return false;
				}
			}
			
			if(checkReference(data)) {
				container.get(ut).add(new Dato<E>(user, deepClone(data)));
				
				return true;
			}
			
			//altrimenti aggiungo il dato.
			container.get(ut).add(new Dato<E>(user, data));
					
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
		
		Utente ut = container.ceilingKey(new Utente(user));
		
		if(ut == null) {
			throw new UserNotRegisteredException(user + " non è un utente registrato");
		}
		
		if(ut.checkPassword(passw)) {
			int poselt = container.get(ut).indexOf(new Dato<E>(user, data));
			
			if(poselt != -1) {
				Dato<E> I = container.get(ut).get(poselt);
				
				return I.getData();
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
		
		Utente ut = container.floorKey(new Utente(user));
		
		if(ut == null || !ut.checkId(user)) {
			throw new UserNotRegisteredException(user + " non è un utente registrato");
		}
		
		if(ut.checkPassword(passw)) {
			int poselt = container.get(ut).indexOf(new Dato<E>(user, data));
			
			if(poselt != -1) {
				Dato<E> I = container.get(ut).get(poselt);
				E clone = deepClone(I.getData());
					
				if(I.getOwner().equals(user)) {
					while(!I.getAuthorized().isEmpty()) {
						int j = 0;
						Utente a = new Utente(I.getAuthorized().first());
						
						while(j<container.get(a).size()){
							if(container.get(a).get(j).getData() == I.getData()) {
								container.get(a).remove(j);
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
					
				container.get(ut).remove(I);
					
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
		
		Utente ut = container.floorKey(new Utente(user));
		
		if(ut == null || !ut.checkId(user)) {
			throw new UserNotRegisteredException(user + " non è un utente registrato");
		}
		
		if(ut.checkPassword(passw)) {
			int poselt = container.get(ut).indexOf(new Dato<E>(user, data));
			
			if(poselt != -1) {
				Dato<E> I = container.get(ut).get(poselt);
				E clone = deepClone(I.getData());
					
				container.get(ut).add(new Dato<E>(user, clone));
					
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
		
		if(owner.equals(other) || owner.isEmpty() || other.isEmpty()){
			throw new IllegalArgumentException();
		}
		
		Utente ut1 = container.floorKey(new Utente(owner));
		Utente ut2 = container.floorKey(new Utente(other));
		
		if(ut1 == null || ut2 == null || !ut1.checkId(owner) || !ut2.checkId(other)) {
			throw new UserNotRegisteredException(owner + " o " + other + " non sono utenti registrati");
		}
		
		if(ut1.checkPassword(passw)) {
			int poselt = container.get(ut1).indexOf(new Dato<E>(owner, data));
			
			if(poselt != -1) {
				Dato<E> I = container.get(ut1).get(poselt);
				
				if(I.getOwner().equals(owner)) {
					container.get(ut2).add(I);
					I.insertAuthorized(other);
					
					return;
				}
					
					throw new UserNotAuthorizedException(owner + " non può condividere dati di cui non è proprietario");
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
		
		Utente ut = container.floorKey(new Utente(user));
		
		if(ut == null || !ut.checkId(user)) {
			throw new UserNotRegisteredException(user + " non è un utente registrato");
		}
		
		if(ut.checkPassword(passw)) {
			Iterator<E> it = new EGenerator(container.get(ut));
			
			return it;
		}
		
		throw new WrongPasswordException("Password sbagliata");
	}
	
	private class EGenerator implements Iterator<E>{
		private ArrayList<Dato<E>> elts = new ArrayList<Dato<E>>();
		int i;
		
		public EGenerator(ArrayList<Dato<E>> elts) {
			this.elts = elts;
			i = 0;
		}
		
		public boolean hasNext() {
			if(i == elts.size()) {
				return false;
			}
			else {
				return true;
			}
		}

		public E next() {
			return elts.get(i++).getData();
		}
		
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	private boolean checkReference(E data) {
		/*
		 * REQUIRES: data != null.
		 * THROWS: se data == null lancia NullPointerException unchecked disponibile in Java.
		 * EFFECTS: se data è presente in this (esiste un riferimento nella collezione uguale a data) restituisce true altrimenti restituisce false.
		 */
		
		Set<Utente> uts = container.keySet();
		
		for(Utente I: uts) {
			for(Dato<E> J: container.get(I)) {
				if(J.getData() == data) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private E deepClone(E data) {
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
		} catch (Exception e) {
		     e.printStackTrace();
		     
		     return null;
		}
	}
	
	@Override
	public String toString() {
		return "	{\n	  " + container.toString() + "\n	}";
	}
}
