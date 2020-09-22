package dao;

import java.util.ArrayList;
import java.util.List;

import models.Pessoa;

public class Banco {
	private static List<Pessoa> listaPessoas = new ArrayList<Pessoa>();

	public static List<Pessoa> getListaPessoas() {
		return listaPessoas;
	}

	public static void setListaPessoas(List<Pessoa> listaPessoas) {
		Banco.listaPessoas = listaPessoas;
	}
}
