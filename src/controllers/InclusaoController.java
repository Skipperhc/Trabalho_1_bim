package controllers;

import java.net.URL;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.Banco;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Faculdade;
import models.Pessoa;
import models.Telefone;

public class InclusaoController {

	private static Stage inclusaoStage;

	private static Pessoa pessoa;

	public void iniciarInclusao() {
		try {
			Stage inclusao = new Stage();
			AnchorPane root = FXMLLoader.load(getClass().getResource("../views/TelaIncluir.fxml"));
			Scene scene = new Scene(root);
			inclusao.setScene(scene);
			inclusao.setTitle("Criar Pessoa");
			InclusaoController.setInclusaoStage(inclusao);
			pessoa = new Pessoa();
			inclusaoStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField txtNome;

	@FXML
	private TextField txtNumero;

	@FXML
	private Button btnAddNumero;

	@FXML
	private Button btnDelNumero;

	@FXML
	private Button btnFinalizar;

	@FXML
	private Button btnCancelar;

	@FXML
	private Button btnEscolherFacul;

	@FXML
	private TextField txtSobrenome;

	@FXML
	private TextField txtIdade;

	@FXML
	private TextArea txtAreaNumeros;

	@FXML
	void addNumero(ActionEvent event) {
		try {
			String numero = txtNumero.getText();
			if (numero.length() <= 9 && numero.length() >= 8) {
				if (numeroExiste(numero))
					throw new InvalidParameterSpecException("Numero já existente");
				pessoa.getTelefones().add(new Telefone(numero));
			} else {
				throw new InvalidParameterSpecException("Insira um numero com 9 digitos");
			}
			atualizarTxtArea();
			txtNumero.setText("");
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText("Telefone não adicionado");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	private void atualizarTxtArea() {
		txtAreaNumeros.setText("");
		for (Telefone t : pessoa.getTelefones()) {
			txtAreaNumeros.setText(txtAreaNumeros.getText() + t + "\n");
		}
	}

	private boolean numeroExiste(String numero) {
		try {
			for (Telefone t : pessoa.getTelefones()) {
				if (t.getNumero().equals(numero))
					return true;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	private boolean pessoaExiste(int id) {
		for (Pessoa p : Banco.getListaPessoas()) {
			if (p.getCod() == id)
				return true;
		}
		return false;
	}

	@FXML
	void delNumero(ActionEvent event) {
		try {
			String numero = txtNumero.getText();
			if (numero.length() <= 9 && numero.length() >= 8) {
				if (!numeroExiste(numero))
					throw new InvalidParameterSpecException("Numero não encontrado");
				for (Telefone t : pessoa.getTelefones()) {
					if (t.getNumero().equals(numero)) {
						pessoa.getTelefones().remove(t);
						break;
					}
				}
			} else {
				throw new InvalidParameterSpecException("Insira um numero com 9 digitos");
			}
			atualizarTxtArea();
			txtNumero.setText("");
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText("Telefone não deletado");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		inclusaoStage.close();
		PrincipalController.getPrincipalStage().show();
	}

	@FXML
	void escolherFacul(ActionEvent event) {
		ChoiceDialog<String> cd = new ChoiceDialog<String>("", "Unibrasil", "Uniandrade", "UTFPR", "Americanas",
				"Universidade do seu zéca");
		cd.setContentText("Selecione uma faculdade");
		cd.setHeaderText("");
		cd.showAndWait();
		pessoa.setFacul(new Faculdade(cd.getResult()));
		btnEscolherFacul.setText(cd.getResult());
	}

	@FXML
	void finalizarInclusao(ActionEvent event) {
		try {
			if (txtNome.getText().isEmpty() || pessoa.getFacul() == null || pessoa.getTelefones().size() == 0
					|| txtSobrenome.getText().isEmpty() || txtIdade.getText().isEmpty()) {
				throw new InvalidParameterSpecException("Preencha todas as informações");
			} else if(pessoa.getFacul().getNome().isEmpty()) {
				throw new InvalidParameterSpecException("Preencha todas as informações");
			}
			pessoa.setNome(txtNome.getText());
			pessoa.setSobrenome(txtSobrenome.getText());
			pessoa.setIdade(Integer.parseInt(txtIdade.getText()));
			Banco.getListaPessoas().add(pessoa);
			inclusaoStage.close();
			PrincipalController.getPrincipalStage().show();
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText("Insira um número no campo idade");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText("Não foi possível finalizar inclusão");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	@FXML
	void initialize() {
		assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'TelaIncluir.fxml'.";
		assert txtNumero != null : "fx:id=\"txtNumero\" was not injected: check your FXML file 'TelaIncluir.fxml'.";
		assert btnAddNumero != null : "fx:id=\"btnAddNumero\" was not injected: check your FXML file 'TelaIncluir.fxml'.";
		assert btnDelNumero != null : "fx:id=\"btnDelNumero\" was not injected: check your FXML file 'TelaIncluir.fxml'.";
		assert btnFinalizar != null : "fx:id=\"btnFinalizar\" was not injected: check your FXML file 'TelaIncluir.fxml'.";
		assert btnCancelar != null : "fx:id=\"btnCancelar\" was not injected: check your FXML file 'TelaIncluir.fxml'.";
		assert txtAreaNumeros != null : "fx:id=\"txtAreaNumeros\" was not injected: check your FXML file 'TelaIncluir.fxml'.";
		assert btnEscolherFacul != null : "fx:id=\"btnEscolherFacul\" was not injected: check your FXML file 'TelaIncluir.fxml'.";
	}

	public static Stage getInclusaoStage() {
		return inclusaoStage;
	}

	private static void setInclusaoStage(Stage inclusaoStage) {
		InclusaoController.inclusaoStage = inclusaoStage;
	}
}
