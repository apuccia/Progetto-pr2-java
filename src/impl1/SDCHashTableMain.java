package impl1;

import progettopr2.Cellulare;

public class SDCHashTableMain {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SDCHashTableTest test = new SDCHashTableTest();
		
		System.out.println("TEST METODO createUser, requires non rispettata");
		System.out.printf("  1)");test.addUser(null, "sdf");
		System.out.printf("  2)");test.addUser("sdf", null);
		System.out.printf("  3)");test.addUser(null, null);
		System.out.printf("  4)");test.addUser("", "");
		System.out.printf("  5)");test.addUser("sdf", "");
		System.out.printf("  6)");test.addUser("", "sdf");
		System.out.printf("  7)");test.addUser("Freddy", "dialUscr");
		System.out.printf("  8)");test.addUser("Freddy", "dialUscr");
		System.out.printf("  9)");test.addUser("Freddy", "asdfasdf");
		
		System.out.println("\nAGGIUNGO UN PO' DI UTENTI");
		System.out.printf("  1)");test.addUser("Lawrence", "5LYa8FCtk");
		System.out.printf("  2)");test.addUser("Hannah", "A3rkYZcTQ");
		System.out.printf("  3)");test.addUser("Orson", "eMap59PHp");
		
		
		System.out.println("\nTEST METODO put, requires non rispettata");
		System.out.printf("  1)");test.addElt(null, "asd", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  2)");test.addElt("add", null, new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  3)");test.addElt("asdf", "asdf", null);
		System.out.printf("  4)");test.addElt("", "sdf", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  5)");test.addElt("sdf", "", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  7)");test.addElt("asdf", "asdf", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  8)");test.addElt("Freddy", "asdf", new Cellulare("Samsung", "Galaxy S1"));
		
		System.out.println("\nAGGIUNGO UN PO DI ELEMENTI al primo utente");
		System.out.printf("  1)");test.addElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  2)");test.addElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S2"));
		System.out.printf("  3)");test.addElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S2 Plus"));
		System.out.printf("  4)");test.addElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S3"));
		System.out.printf("  5)");test.addElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy Note 3"));
		System.out.printf("  6)");test.addElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S7"));
		System.out.printf("  7)");test.addElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S4"));
		System.out.printf("  8)");test.addElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S5"));
		System.out.printf("  9)");test.addElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy Note 9"));
		
		System.out.println("\nAGGIUNGO UN PO' DI ELEMENTI al secondo utente");
		System.out.printf("  1)");test.addElt("Lawrence", "5LYa8FCtk", new Cellulare("Apple", "iPhone 7"));
		System.out.printf("  2)");test.addElt("Lawrence", "5LYa8FCtk", new Cellulare("Apple", "iPhone 8"));
		System.out.printf("  3)");test.addElt("Lawrence", "5LYa8FCtk", new Cellulare("Apple", "iPhone XS"));
		System.out.printf("  4)");test.addElt("Lawrence", "5LYa8FCtk", new Cellulare("Apple", "iPhone XS"));
		System.out.printf("  5)");test.addElt("Lawrence", "5LYa8FCtk", new Cellulare("Apple", "iPhone 4"));
		System.out.printf("  6)");test.addElt("Lawrence", "5LYa8FCtk", new Cellulare("Apple", "iPhone 5s"));
		System.out.printf("  7)");test.addElt("Lawrence", "5LYa8FCtk", new Cellulare("Apple", "iPhone XR"));
		System.out.printf("  8)");test.addElt("Lawrence", "5LYa8FCtk", new Cellulare("Apple", "iPhone 3G"));
		
		System.out.println("\nTEST METODO getSize, requires non rispettata");
		System.out.printf("  1)");test.size(null, "asdf");
		System.out.printf("  2)");test.size("asdf", null);
		System.out.printf("  3)");test.size(null, null);
		System.out.printf("  4)");test.size("", "asdf");
		System.out.printf("  5)");test.size("asdf", "");
		System.out.printf("  6)");test.size("Freddy", "asdf");
		System.out.printf("  7)");test.size("asdf", "asdf");
		
		System.out.println("\nTEST METODO getSize, caso nessun elemento in condivisione, utente senza elementi");
		System.out.printf("  1)");test.size("Freddy", "dialUscr");
		System.out.printf("  2)");test.size("Lawrence", "5LYa8FCtk");
		System.out.printf("  3)");test.size("Eloisa", "n28Ye3XNu");
		
		System.out.println("\nTEST METODO put, riferimento già presente nella collezione");
		System.out.printf("  1)");test.addElt("Freddy", "dialUscr", test.getElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S1")));
		System.out.printf("  2)");test.addElt("Freddy", "dialUscr", test.getElt("Lawrence", "5LYa8FCtk", new Cellulare("Apple", "iPhone 7")));
		
		System.out.println("\nTEST METODO share, requires non rispettata");
		System.out.printf("  1)");test.shareElt(null, "asdf", "asdf", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  2)");test.shareElt("asdf", null, "asdf", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  3)");test.shareElt("asdf", "asdf", null, new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  4)");test.shareElt("asdf", "asdf", "asdf", null);
		System.out.printf("  5)");test.shareElt("", "asdf", "asdf", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  6)");test.shareElt("asdf", "", "asdf", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  7)");test.shareElt("asdf", "asdf", "", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  8)");test.shareElt("Freddy", "asdf", "asdf", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  9)");test.shareElt("asdf", "asdf", "Lawrence", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  10)");test.shareElt("Freddy", "qwer", "Lawrence", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  11)");test.shareElt("Freddy", "dialUscr", "Lawrence", new Cellulare("Samsung", "Galaxy S9"));
		System.out.printf("  12)");test.shareElt("Freddy", "dialUscr", "Freddy", new Cellulare("Samsung", "Galaxy S1"));
		
		System.out.println("\nCONDIVIDO UN PO' DI ELEMENTI, test caso condivisione non autorizzata");
		System.out.printf("  1)");test.shareElt("Freddy", "dialUscr", "Lawrence", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  2)");test.shareElt("Freddy", "dialUscr", "Lawrence", new Cellulare("Samsung", "Galaxy S2 Plus"));
		System.out.printf("  3)");test.shareElt("Freddy", "dialUscr", "Lawrence", new Cellulare("Samsung", "Galaxy S3"));
		System.out.printf("  4)");test.shareElt("Freddy", "dialUscr", "Lawrence", new Cellulare("Samsung", "Galaxy S4"));
		
		System.out.printf("  5)");test.shareElt("Lawrence", "5LYa8FCtk", "Hannah", new Cellulare("Samsung", "Galaxy S1"));
		
		System.out.printf("  6)");test.shareElt("Lawrence", "5LYa8FCtk", "Freddy", new Cellulare("Apple", "iPhone 7"));
		System.out.printf("  7)");test.shareElt("Lawrence", "5LYa8FCtk", "Freddy", new Cellulare("Apple", "iPhone 7"));
		System.out.printf("  8)");test.shareElt("Lawrence", "5LYa8FCtk", "Freddy", new Cellulare("Apple", "iPhone 8"));
		System.out.printf("  9)");test.shareElt("Lawrence", "5LYa8FCtk", "Freddy", new Cellulare("Apple", "iPhone XR"));
		System.out.printf("  10)");test.shareElt("Lawrence", "5LYa8FCtk", "Freddy", new Cellulare("Apple", "iPhone 4"));
		System.out.printf("  11)");test.shareElt("Lawrence", "5LYa8FCtk", "Freddy", new Cellulare("Apple", "iPhone XS"));
		System.out.printf("  12)");test.shareElt("Lawrence", "5LYa8FCtk", "Freddy", new Cellulare("Apple", "iPhone XS"));
		System.out.printf("  13)");test.shareElt("Lawrence", "5LYa8FCtk", "Freddy", new Cellulare("Apple", "iPhone XS"));
		System.out.printf("  14)");test.shareElt("Lawrence", "5LYa8FCtk", "Freddy", new Cellulare("Apple", "iPhone XR"));
		
		System.out.println("\nTEST METODO getSize, caso elementi in condivisione");
		System.out.printf("  1)");test.size("Freddy", "dialUscr");
		System.out.printf("  2)");test.size("Lawrence", "5LYa8FCtk");
		
		System.out.println("\nTEST METODO copy, requires non rispettata");
		System.out.printf("  1)");test.copyElt(null, "dialUscr", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  2)");test.copyElt("Freddy", null, new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  3)");test.copyElt("Freddy", "dialUscr", null);
		System.out.printf("  4)");test.copyElt("", "dialUscr", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  5)");test.copyElt("Freddy", "", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  6)");test.copyElt("Freddy", "asdf", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  7)");test.copyElt("asdf", "dialUscr", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  8)");test.copyElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S9"));
		
		System.out.println("\nTEST METODO copy, verifica se è deep");
		System.out.printf("  1)");test.copyElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy Note 3"));
		System.out.printf("  2)");test.copyElt("Lawrence", "5LYa8FCtk", new Cellulare("Samsung", "Galaxy S3"));
		System.out.printf("  3)");test.copyElt("Freddy", "dialUscr", new Cellulare("Apple", "iPhone 7"));
		System.out.printf("  4)");Cellulare a = test.remElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy Note 3"));
		System.out.printf("  5)");Cellulare b = test.getElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy Note 3"));
		System.out.printf("  6)");System.out.println(a == b);
		System.out.printf("  7)");test.copyElt("Freddy", "dialUscr", new Cellulare("Apple", "iPhone XS"));
		System.out.printf("  8)");test.copyElt("Freddy", "dialUscr", new Cellulare("Apple", "iPhone XR"));
		
		System.out.println("\nTEST METODO getIterator, requires non rispettata");
		System.out.printf("  1)");test.checkIt(null, "dialUscr");
		System.out.printf("  2)");test.checkIt("Freddy", null);
		System.out.printf("  3)");test.checkIt("", "dialUscr");
		System.out.printf("  4)");test.checkIt("Freddy", "");
		System.out.printf("  5)");test.checkIt("asdf", "dialUscr");
		System.out.printf("  6)");test.checkIt("Freddy", "asdf");
		
		System.out.println("\nTEST METODO getIterator, utenti con e senza elementi");
		System.out.printf("  1)");test.checkIt("Freddy", "dialUscr");
		System.out.printf("  2)");test.checkIt("Lawrence", "5LYa8FCtk");
		System.out.printf("  3)");test.checkIt("Hannah Foster", "A3rkYZcTQ");
		
		System.out.println("\nTEST METODO get, requires non rispettata");
		System.out.printf("  1)");test.getElt(null, "dialUscr", new Cellulare("Samsung", "Galaxy Note 3"));
		System.out.printf("  2)");test.getElt("Freddy", null, new Cellulare("Samsung", "Galaxy Note 3"));
		System.out.printf("  3)");test.getElt("Freddy", "dialUscr", null);
		System.out.printf("  4)");test.getElt("", "dialUscr", new Cellulare("Samsung", "Galaxy Note 3"));
		System.out.printf("  5)");test.getElt("Freddy", "", new Cellulare("Samsung", "Galaxy Note 3"));
		System.out.printf("  6)");test.getElt("asdf", "dialUscr", new Cellulare("Samsung", "Galaxy Note 3"));
		System.out.printf("  7)");test.getElt("Freddy", "asdf", new Cellulare("Samsung", "Galaxy Note 3"));
		System.out.printf("  8)");test.getElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S9"));
		
		System.out.println("\nTEST METODO get, verifica se le modifiche sono visibili all'autorizzato");
		System.out.printf("  1)");Cellulare s = test.getElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S4"));
		s.changeName("Galaxy S4 Plus");
		System.out.printf("  2)");test.getElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S4"));
		System.out.printf("  3)");test.getElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S4 Plus"));
		System.out.printf("  4)");test.getElt("Lawrence", "5LYa8FCtk", new Cellulare("Samsung", "Galaxy S4"));
		System.out.printf("  5)");test.getElt("Lawrence", "5LYa8FCtk", new Cellulare("Samsung", "Galaxy S4 Plus"));
		
		System.out.println("\nTEST METODO remove, requires non rispettata");
		System.out.printf("  1)");test.remElt(null, "dialUscr", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  2)");test.remElt("Freddy", null, new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  3)");test.remElt("Freddy", "dialUscr", null);
		System.out.printf("  4)");test.remElt("", "dialUscr", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  5)");test.remElt("Freddy", "", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  6)");test.remElt("asdf", "dialUscr", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  7)");test.remElt("Freddy", "asdf", new Cellulare("Samsung", "Galaxy S1"));
		System.out.printf("  8)");test.remElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S10"));
		
		System.out.println("\nTEST METODO remove, caso utente autorizzato e elemento in condivisione");
		System.out.printf("  1)");test.remElt("Lawrence", "5LYa8FCtk", new Cellulare("Samsung", "Galaxy S3"));
		System.out.printf("  2)");test.getElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S3"));
		System.out.printf("  3)");test.remElt("Freddy", "dialUscr", new Cellulare("Samsung", "Galaxy S2 Plus"));
		System.out.printf("  4)");test.getElt("Lawrence", "5LYa8FCtk", new Cellulare("Samsung", "Galaxy S2 Plus"));
		System.out.printf("  5)");test.checkIt("Freddy", "dialUscr");
		System.out.printf("  6)");test.checkIt("Lawrence", "5LYa8FCtk");
		
		System.out.println("\nTEST METODO removeUser, requires non rispettata");
		System.out.printf("  1)");test.remUser(null, "5LYa8FCtk");
		System.out.printf("  2)");test.remUser("Lawrence", null);
		System.out.printf("  3)");test.remUser("", "5LYa8FCtk");
		System.out.printf("  4)");test.remUser("Lawrence", "");
		System.out.printf("  5)");test.remUser("asdf", "5LYa8FCtk");
		System.out.printf("  6)");test.remUser("Lawrence", "asdf");
		
		System.out.println("\nTEST METODO removeUser, caso elementi in condivisione");
		System.out.printf("  1)");test.remUser("Lawrence", "5LYa8FCtk");
		System.out.printf("  2)");test.checkIt("Freddy", "dialUscr");
		
	}
}
