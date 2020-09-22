package models;

import java.util.ArrayList;
import java.util.List;

public class Faculdade {

	private int cod;
	private String nome;
	
	public Faculdade() {
		super();
	}

	public Faculdade(String nome) {
		super();
		this.nome = nome;
	}
	
	@Override
	public String toString() {
		return "Faculdade [nome=" + nome + "]";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}
}
