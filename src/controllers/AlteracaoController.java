package controllers;

import java.net.URL;
import java.security.spec.InvalidParameterSpecException;
import java.util.ResourceBundle;

import dao.Banco;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Faculdade;
import models.Pessoa;
import models.Telefone;

public class AlteracaoController {

	private static Stage alteracaoStage;

	private Pessoa pessoa;

	public void iniciarAlteracao() {
		try {
			Stage alteracao = new Stage();
			AnchorPane root = FXMLLoader.load(getClass().getResource("../views/TelaAlterar.fxml"));
			Scene scene = new Scene(root);
			alteracao.setScene(scene);
			alteracao.setTitle("Alterar Pessoa");
			alteracaoStage = alteracao;
			alteracao.show();
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
	private TextArea txtAreaNumeros;

	@FXML
	private Button btnEscolherFacul;

	@FXML
	private TextField txtIdGeral;

	@FXML
	private TextField txtSobrenome;

	@FXML
	private TextField txtIdade;

	@FXML
	private Button btnBuscarPessoa;

	@FXML
	void buscarPessoa(ActionEvent event) {
		try {
			int id = Integer.parseInt(txtIdGeral.getText());
			for (Pessoa p : Banco.getListaPessoas()) {
				if (p.getCod() == id) {
					pessoa = p;
					atualizar();
					break;
				}
			}
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Aviso");
			alert.setHeaderText("Número inválido");
			alert.setContentText("Por favor insira um número válido");
			alert.showAndWait();
		}
	}

	@FXML
	void cancelar(ActionEvent event) {
		alteracaoStage.close();
		PrincipalController.getPrincipalStage().show();
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
	void escolherFacul(ActionEvent event) {
		try {
			ChoiceDialog<String> cd = new ChoiceDialog<String>("", "Unibrasil", "Uniandrade", "UTFPR", "Americanas",
					"Universidade do seu zéca");
			cd.setContentText("Selecione uma faculdade");
			cd.setHeaderText("");
			cd.showAndWait();
			if (cd.getResult().equals("")) {
				System.out.println(cd.getResult() + "saasdasd");
				throw new InvalidParameterSpecException("Nenhuma faculdade escolhida");
			}
			pessoa.setFacul(new Faculdade(cd.getResult()));
			btnEscolherFacul.setText(cd.getResult());
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Aviso");
			alert.setHeaderText("Escolha uma das faculdades");
			alert.setContentText("");
			alert.showAndWait();
		}
	}

	@FXML
	void finalizarAlteracao(ActionEvent event) {
		try {
			if (txtNome.getText().isEmpty() || txtSobrenome.getText().isEmpty() || txtIdade.getText().isEmpty() || pessoa.getFacul() == null
					|| pessoa.getTelefones().size() == 0) {
				throw new InvalidParameterSpecException("Preencha todas as informações");
			} else if (pessoa.getFacul().getNome().equals("")) {
				throw new InvalidParameterSpecException("Preencha todas as informações");
			}
			for (int i = 0; i < Banco.getListaPessoas().size(); i++) {
				if (Banco.getListaPessoas().get(i).getCod() == pessoa.getCod()) {
					pessoa = Banco.getListaPessoas().get(i);
					pessoa.setNome(txtNome.getText());
					pessoa.setSobrenome(txtSobrenome.getText());
					pessoa.setIdade(Integer.parseInt(txtIdade.getText()));
					Banco.getListaPessoas().set(i, pessoa);
					break;
				}
			}
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Alteração");
			alert.setHeaderText("Alteração da pessoa com cod: " + pessoa.getCod());
			alert.setContentText("Alteração concluida");
			alert.showAndWait();
			alteracaoStage.close();
			PrincipalController.getPrincipalStage().show();
		} catch (NumberFormatException e) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Aviso");
			alert.setHeaderText("Insira um número no campo idade");
			alert.setContentText("Falha na alteração");
			alert.showAndWait();
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erro na alteração");
			alert.setHeaderText("");
			alert.setContentText(e.getMessage());
			alert.showAndWait();
		}
	}

	private void atualizar() {
		txtNome.setText(pessoa.getNome());
		txtIdade.setText(pessoa.getIdade() + "");
		txtSobrenome.setText(pessoa.getSobrenome());
		atualizarTxtArea();
		btnEscolherFacul.setText(pessoa.getFacul().getNome());
	}

	private void atualizarTxtArea() {
		txtAreaNumeros.setText("");
		pessoa.getTelefones().forEach(item -> {
			txtAreaNumeros.setText(txtAreaNumeros.getText() + item + "\n");
		});
	}

	@FXML
	void initialize() {
		assert txtNome != null : "fx:id=\"txtNome\" was not injected: check your FXML file 'TelaAlterar.fxml'.";
		assert txtNumero != null : "fx:id=\"txtNumero\" was not injected: check your FXML file 'TelaAlterar.fxml'.";
		assert btnAddNumero != null : "fx:id=\"btnAddNumero\" was not injected: check your FXML file 'TelaAlterar.fxml'.";
		assert btnDelNumero != null : "fx:id=\"btnDelNumero\" was not injected: check your FXML file 'TelaAlterar.fxml'.";
		assert btnFinalizar != null : "fx:id=\"btnFinalizar\" was not injected: check your FXML file 'TelaAlterar.fxml'.";
		assert btnCancelar != null : "fx:id=\"btnCancelar\" was not injected: check your FXML file 'TelaAlterar.fxml'.";
		assert txtAreaNumeros != null : "fx:id=\"txtAreaNumeros\" was not injected: check your FXML file 'TelaAlterar.fxml'.";
		assert btnEscolherFacul != null : "fx:id=\"btnEscolherFacul\" was not injected: check your FXML file 'TelaAlterar.fxml'.";
		assert txtIdGeral != null : "fx:id=\"txtIdGeral\" was not injected: check your FXML file 'TelaAlterar.fxml'.";
		assert btnBuscarPessoa != null : "fx:id=\"btnBuscarPessoa\" was not injected: check your FXML file 'TelaAlterar.fxml'.";

	}
}
