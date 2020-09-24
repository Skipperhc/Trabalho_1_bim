package controllers;

import java.net.URL;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import dao.Banco;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Faculdade;
import models.Pessoa;

public class PrincipalController {

	private static Stage principalStage;

	private void abrir() {
		principalStage.show();
	}

	private void fechar() {
		principalStage.hide();
		principalStage.setOnShown(e -> {
			atualizar();
		});
	}

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button btnIncluir;

	@FXML
	private Button btnPesquisar;

	@FXML
	private Button btnAlterar;

	@FXML
	private TextArea txtArea;

	@FXML
	private TextField txtIdGeral;

	@FXML
	private Button btnSair;
	
	@FXML
    void sair(ActionEvent event) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Até mais");
		alert.setHeaderText("Encerrando...");
		alert.setContentText("");
		alert.showAndWait();
		principalStage.close();
    }

	private void atualizar() {
		txtArea.setText("");
		int posicao = 0;
		for (Pessoa p : Banco.getListaPessoas()) {
			posicao++;
			txtArea.setText(txtArea.getText() + " Posição:" + posicao + " - " + p + "\n");
		}
	}

	@FXML
	void pesquisar(ActionEvent event) {
		try {
			if (txtIdGeral.getText().isEmpty()) {
				atualizar();
			} else {
				System.out.println(txtIdGeral.getText());
				int id = Integer.parseInt(txtIdGeral.getText());
				ArrayList<Pessoa> listaDeIds = new ArrayList<Pessoa>();
				for (Pessoa p : Banco.getListaPessoas()) {
					if (p.getCod() == id) {
						listaDeIds.add(p);
					}
				}
				txtArea.setText("");
				for (int i = 0; i < listaDeIds.size(); i++) {
					txtArea.setText(txtArea.getText() + " Posição: " + (i + 1) + " - " + listaDeIds.get(i) + "\n");
				}
			}
		} catch (Exception e) {
			atualizar();
			Alert alert = new Alert(AlertType.WARNING);
			alert.setContentText("Por favor insira um número válido");
			alert.setTitle("Aviso");
			alert.setHeaderText("Número inválido");
			alert.showAndWait();
		}
	}

	@FXML
	void incluir(ActionEvent event) {
		new InclusaoController().iniciarInclusao();
		fechar();
		atualizar();
	}

	@FXML
	void excluir(ActionEvent event) {
		try {
			int sizeList = Banco.getListaPessoas().size();
			int id = Integer.parseInt(txtIdGeral.getText());
			for (Pessoa p : Banco.getListaPessoas()) {
				if (p.getCod() == id) {
					Banco.getListaPessoas().remove(p);
					break;
				}
			}
			if (sizeList == Banco.getListaPessoas().size())
				throw new InvalidParameterSpecException("Deu ruim, vou usar essa exception mesmo");
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("");
			alert.setHeaderText("pessoa excluída");
			alert.setContentText("Pessoa excluída com sucesso!");
			alert.showAndWait();
		} catch (InvalidParameterSpecException e1) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("");
			alert.setHeaderText("id não encontrado");
			alert.setContentText("Nenhuma pessoa foi encontrada");
			alert.showAndWait();
		} catch (Exception e2) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Aviso");
			alert.setHeaderText("Número inválido");
			alert.setContentText("Por favor insira um número válido");
			alert.showAndWait();
		}
		atualizar();
	}

	@FXML
	void alterar(ActionEvent event) {
		try {
			new AlteracaoController().iniciarAlteracao();
			fechar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@FXML
	void initialize() {
		assert btnIncluir != null : "fx:id=\"btnIncluir\" was not injected: check your FXML file 'TelaPrincipal.fxml'.";
		assert btnAlterar != null : "fx:id=\"btnAlterar\" was not injected: check your FXML file 'TelaPrincipal.fxml'.";
		assert txtArea != null : "fx:id=\"txtArea\" was not injected: check your FXML file 'TelaPrincipal.fxml'.";
		assert txtIdGeral != null : "fx:id=\"txtIdGeral\" was not injected: check your FXML file 'TelaPrincipal.fxml'.";

	}

	public static Stage getPrincipalStage() {
		return principalStage;
	}

	public static void setPrincipalStage(Stage setPrincipalStage) {
		principalStage = setPrincipalStage;
	}
}
