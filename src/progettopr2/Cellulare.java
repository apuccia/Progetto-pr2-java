package progettopr2;

import java.io.Serializable;

public class Cellulare implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String marca;
	private String nome;
	
	public Cellulare(String marca, String nome) {
		this.marca = marca;
		this.nome = nome;
	}
	
	@Override
	public boolean equals(Object c) {
		Cellulare a = (Cellulare) c;
		
		return a.getMarca().equals(this.marca) && a.getNome().equals(this.nome);
	}
	
	public String getMarca() {
		return marca;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void changeName(String newname) {
		nome = newname;
	}
	
	@Override
	public String toString() {
		return "(" + this.marca.toString() + ", " + this.nome.toString() + ")";
	}
}
