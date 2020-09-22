package models;

import java.util.ArrayList;
import java.util.List;

public class Pessoa {

	private static int qtdPessoa = 0;

	private int cod = codPessoa();
	private String nome;
	private String sobrenome;
	private int idade;
	private Faculdade facul;
	private List<Telefone> telefones = new ArrayList<Telefone>();

	

	@Override
	public String toString() {
		return "Pessoa [cod=" + cod + ", nome=" + nome + ", sobrenome=" + sobrenome + ", idade=" + idade + ", facul="
				+ facul + ", telefones= [" + telefones + "]]\n";
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Faculdade getFacul() {
		return facul;
	}

	public void setFacul(Faculdade facul) {
		this.facul = facul;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public int getCod() {
		return cod;
	}

	private int codPessoa() {
		qtdPessoa++;
		return qtdPessoa;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}
}
