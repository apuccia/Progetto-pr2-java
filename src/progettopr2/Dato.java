package progettopr2;

import java.io.Serializable;
import java.util.TreeSet;

public class Dato<E extends Serializable>{
	/*
	 * OVERVIEW: struttura che rappresenta un elemento di tipo E, il suo proprietario e un insieme di persone autorizzate ad accedervi.
	 * TYPICAL DATA:
	 * 		<proprietario, dato, {aut_0, ... , aut_n-1}>.
	 * 		dove:
	 * 			1) proprietario � una stringa che rappresenta il proprietario effettivo dell'elemento.
	 * 			1) dato � un elemento di tipo E.
	 * 			2) {aut_0, ... , aut_n-1} � un insieme di stringhe che rappresentano le persone autorizzate.
	 */
	
	private String proprietario = new String();
	private E dato;
	private TreeSet<String> autorizzati;
	
	/*
	 * AF(proprietario, dato, autorizzati): 
	 * 		<proprietario, dato, {autorizzati.get(0), ... , autorizzati.get(autorizzati.size()-1)}>.
	 * IR(dato, autorizzati):
	 * 		proprietario != null && dato != null && autorizzati != null && !(exists 0<=i<autorizzati.size(). (autorizzati.get(i) == proprietario))
	 * 		(forall 0<=i<(autorizzati.size()). (autorizzati.get(i) != null && !(exists 0<=j<autorizzati.size(), i!=j. (autorizzati.get(i) == autorizzati.get(j))))).
	 */
	
	public Dato(String owner, E data) throws NullPointerException {
		/* 
		 * REQUIRES: data != null && owner != null.
		 * THROWS: se data == null || owner == null lancia NullPointerException unchecked disponibile in java.
		 * MODIFIES: this.
		 * EFFECTS: crea una nuova coppia <owner, data, {}>.
		 */
		
		if(data == null || owner == null) {
			throw new NullPointerException();
		}
		
		dato = data;
		proprietario = owner;
		autorizzati = new TreeSet<String>();
	}
	
	public boolean insertAuthorized(String aut) throws NullPointerException {
		/*
		 * REQUIRES: aut != null && (forall 0<=i<n. (aut_i != aut)).
		 * THROWS: se aut == null lancia NullPointerException unchecked.
		 * MODIFIES: autorizzati.
		 * EFFECTS: aggiunge aut ad autorizzati se non è già presente, altrimenti non fa nulla.
		 */
		
		if(aut == null) {
			throw new NullPointerException();
		}
		return autorizzati.add(aut);
	}
	
	public void removeAuthorized(String aut) {
		/*
		 * REQUIRES: aut != null && (exists 0<=i<n. (aut_i == aut)).
		 * THROWS: se aut == null lancia NullPointerException unchecked.
		 * MODIFIES: autorizzati.
		 * EFFECTS: rimuove aut da autorizzati se è presente, altrimenti non fa nulla.
		 */
		
		if(aut == null) {
			throw new NullPointerException();
		}
		
		autorizzati.remove(aut);
	}
	
	public boolean checkAuthorized(String aut) throws NullPointerException {
		/*
		 * REQUIRES: aut != null.
		 * THROWS: se aut == null lancia NullPointerException unchecked.
		 * EFFECTS: se aut è presente in autorizzati restituisce true altrimenti restituisce false.
		 */
		
		if(aut == null) {
			throw new NullPointerException();
		}
		
		return autorizzati.contains(aut);
	}
	
	public String getOwner() {
		/*
		 * EFFECTS: ritorna il nome del proprietario effettivo.
		 */
		
		return proprietario;
	}
	
	public E getData() {
		/*
		 * EFFECTS: ritorna il dato E.
		 */
		
		return dato;
	}
	
	public TreeSet<String> getAuthorized(){
		/*
		 * EFFECTS: restituisce l'insieme delle persone autorizzate.
		 */
		
		return autorizzati;
	}
	
	@Override
	public boolean equals(Object o) {
		/*
		 * REQUIRES: o != null.
		 * THROWS: se o == null lancia NullPointerException unchecked disponibile in Java.
		 * EFFECTS: se o.getData() == this.getData() restituisce true altrimenti false.
		 */
		@SuppressWarnings("unchecked")
		Dato<E> d = (Dato<E>) o;
		
		return this.getData().equals(d.getData());
	}
	
	@Override 
	public String toString() {
		return "<" + this.proprietario + ", " + this.dato + ", " + this.autorizzati + ">";
	}
}
