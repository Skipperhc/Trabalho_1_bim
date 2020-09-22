package models;

public class Telefone {
	
	private int cod;
	private String numero;

	public Telefone() {
		super();
	}

	public Telefone(String numero) {
		super();
		this.numero = numero;
	}

	@Override
	public String toString() {
		return "Telefone [numero=" + numero + "]";
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public int getCod() {
		return cod;
	}

	public void setCod(int cod) {
		this.cod = cod;
	}
	
	
}
