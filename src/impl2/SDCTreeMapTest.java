package impl2;

import java.util.Iterator;

import progettopr2.*;

public class SDCTreeMapTest {
	private SDCTreeMap<Cellulare> datacontainer;
	
	public SDCTreeMapTest() {
		 datacontainer = new SDCTreeMap<Cellulare>();
	}
	
	public void addUser(String user, String password) {
		try {
			datacontainer.createUser(user, password);
			System.out.println("L'utente " + user + " è stato aggiunto");
			System.out.println(datacontainer);
		}catch(NullPointerException e) {
			System.out.println("Uno o più valori sono null");
		}catch(IllegalArgumentException e) {
			System.out.println("Una o più stringhe sono vuote");
		}catch(UserAlreadyAddedException e) {
			System.out.println("L'utente " + user + " è già registrato");
		}
		
	}
	
	public void remUser(String user, String password) {
		try {
			datacontainer.removeUser(user, password);
			System.out.println("L'utente " + user + " è stato rimosso");
			System.out.println(datacontainer);
		}catch(NullPointerException e) {
			System.out.println("Uno o più valori sono null");
		}catch(IllegalArgumentException e) {
			System.out.println("Una o più stringhe sono vuote");
		}catch(UserNotRegisteredException e) {
			System.out.println("L'utente " + user + " non è registrato");
		}catch(WrongPasswordException e) {
			System.out.println("Password sbagliata");
		}
	}
	
	public void size(String user, String password) {
		try {
			System.out.println("Numero di elementi posseduti da " + user + ": " + datacontainer.getSize(user, password));
		}catch(NullPointerException e) {
			System.out.println("Uno o più valori sono null");
		}catch(IllegalArgumentException e) {
			System.out.println("Una o più stringhe sono vuote");
		}catch(UserNotRegisteredException e) {
			System.out.println("L'utente " + user + " non è registrato");
		}catch(WrongPasswordException e) {
			System.out.println("Password sbagliata");
		}
	}
	
	public void addElt(String user, String password, Cellulare data) {
		try {
			if(datacontainer.put(user, password, data)) {
				System.out.println("L'elemento " + data.getMarca() + " " + data.getNome() + " è stato aggiunto all'utente " + user);
			}
			else {
				System.out.println("	L'elemento non è stato aggiunto all'utente " + user);
			}
			System.out.println(datacontainer);
		}catch(NullPointerException e) {
			System.out.println("Uno o più valori sono null");
		}catch(IllegalArgumentException e) {
			System.out.println("Una o più stringhe sono vuote");
		}catch(UserNotRegisteredException e) {
			System.out.println("L'utente " + user + " non è registrato");
		}catch(WrongPasswordException e) {
			System.out.println("Password sbagliata");
		}
	}
	
	public Cellulare getElt(String user, String password, Cellulare data) {
		try {
			Cellulare a = datacontainer.get(user, password, data);
			System.out.println("Marca del cellulare: " + a.getMarca() +  ", Nome del cellulare: " + a.getNome());
			return a;
		}catch(NullPointerException e) {
			System.out.println("Uno o più valori sono null");
		}catch(IllegalArgumentException e) {
			System.out.println("Una o più stringhe sono vuote");
		}catch(UserNotRegisteredException e) {
			System.out.println("L'utente " + user + " non è registrato");
		}catch(WrongPasswordException e) {
			System.out.println("Password sbagliata");
		}catch(UnavailableDataException e) {
			System.out.println("Dato non presente");
		}
		return null;
	}
	
	public Cellulare remElt(String user, String password, Cellulare data) {
		try {
			Cellulare a = datacontainer.remove(user, password, data);
			System.out.println(a.getNome() + " è stato rimosso");
			System.out.println(datacontainer);
			return a;
		}catch(NullPointerException e) {
			System.out.println("Uno o più valori sono null");
		}catch(IllegalArgumentException e) {
			System.out.println("Una o più stringhe sono vuote");
		}catch(UserNotRegisteredException e) {
			System.out.println("L'utente " + user + " non è registrato");
		}catch(WrongPasswordException e) {
			System.out.println("Password sbagliata");
		}catch(UnavailableDataException e) {
			System.out.println("Dato non presente");
		}
		return null;
	}
	
	public void copyElt(String user, String password, Cellulare data) {
		try {
			datacontainer.copy(user, password, data);
			System.out.println(data.getNome() + " è stato copiato");
			System.out.println(datacontainer);
		}catch(NullPointerException e) {
			System.out.println("Uno o più valori sono null");
		}catch(IllegalArgumentException e) {
			System.out.println("Una o più stringhe sono vuote");
		}catch(UserNotRegisteredException e) {
			System.out.println("L'utente " + user + " non è registrato");
		}catch(WrongPasswordException e) {
			System.out.println("Password sbagliata");
		}catch(UnavailableDataException e) {
			System.out.println("Dato non presente");
		}
	}
	
	public void shareElt(String user, String password, String other, Cellulare data) {
		try {
			datacontainer.share(user, password, other, data);
			System.out.println("L'elemento " + data.getMarca() + " " + data.getNome() + ", posseduto da " + user + ", è stato condiviso con " + other);
			System.out.println(datacontainer);
		}catch(NullPointerException e) {
			System.out.println("Uno o più valori sono null");
		}catch(IllegalArgumentException e) {
			if(user.isEmpty() || other.isEmpty()) {
				System.out.println("Una o più stringhe sono vuote");
			}
			else {
				System.out.println("Proprietario uguale ad autorizzato");
			}
		}catch(UserNotRegisteredException e) {
			System.out.println("Gli utenti " + user + " o " + other + " non sono registrati");
		}catch(WrongPasswordException e) {
			System.out.println("Password sbagliata");
		}catch(UnavailableDataException e) {
			System.out.println("Dato non presente");
		}catch(UserNotAuthorizedException e) {
			System.out.println("L'utente " + user + " non è autorizzato alla condivisione di " + data.getNome());
		}
	}
	
	public void checkIt(String user, String password) {
		try {
			Iterator<Cellulare> it = datacontainer.getIterator(user, password);
			System.out.println("Dati dell'utente " + user + ":");
			while(it.hasNext()) {
				Cellulare a = it.next();
				System.out.println("    -" + a.getMarca() + " " + a.getNome());
			}
		}catch(NullPointerException e) {
			System.out.println("Uno o più valori sono null");
		}catch(IllegalArgumentException e) {
			System.out.println("Una o più stringhe sono vuote");
		}catch(UserNotRegisteredException e) {
			System.out.println("L'utente " + user + " non è registrato");
		}catch(WrongPasswordException e) {
			System.out.println("Password sbagliata");
		}
	}
	
	/*public void testAut(String user, Cellulare data) {
		datacontainer.print(user, data);
	}*/
}
