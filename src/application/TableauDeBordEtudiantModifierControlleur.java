package application;

import classes.Entreprise;
import classes.Etudiant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TableauDeBordEtudiantModifierControlleur {
	@FXML
	private Button confirmer;
	@FXML
	private Button annuler;

	@FXML
	private TextField noEtRue;
	@FXML
	private TextField codePostal;
	@FXML
	private TextField ville;
	@FXML
	private TextField telephone;
	@FXML
	private TextField adresseMail;

	@FXML
	public void initialize() {
		for (Etudiant e : Main.gst.importEtudiant()) {
			if (e.getIDEtudiant().equals(Main.gst.getId())) {
				noEtRue.setText(e.getRue());
				codePostal.setText(e.getCodePostal());
				ville.setText(e.getVille());
				adresseMail.setText(e.getMail());
				telephone.setText(e.getNumTel());
			}
		}
	}

	// ACTION CLIC CONFIRMER
	@FXML
	public void clicConfirmer(ActionEvent event) {
		// ON RECUPERE LES NOUVELLES VALEURS
		Main.gst.modifEtudiant(noEtRue.getText(), codePostal.getText(), ville.getText(), telephone.getText(), adresseMail.getText());
		// ON FAIT LES MODIFICATION DANS LA BDD

		// ON RAFFRAICHI LE PANE EN HAUT A GAUCHE DU TABLEAU DE BORD ENTREPRISE

		// ET ON FERME LA PAGE
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			Stage primaryStage = new Stage();

			// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
			FXMLLoader loader = new FXMLLoader();
			// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
			loader.setLocation(Main.class.getResource("TableauDeBordEtudiant.fxml"));

			// DONNE AU FICHIER RACINE LE FXML CREE PRECEDEMENT
			BorderPane rootLayout = (BorderPane) loader.load();

			// LA SCENE CONTIENDRA NOTRE PANE RACINE
			Scene scene = new Scene(rootLayout);

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ACTION CLIC ANNULER
	@FXML
	public void clicAnnuler(ActionEvent event) {
		// IL NE SE PASSE RIEN
		// ON FERME JUSTE LA PAGE
		((Node) (event.getSource())).getScene().getWindow().hide();
		
		try {
			Stage primaryStage = new Stage();

			// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
			FXMLLoader loader = new FXMLLoader();
			// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
			loader.setLocation(Main.class.getResource("TableauDeBordEtudiant.fxml"));

			// DONNE AU FICHIER RACINE LE FXML CREE PRECEDEMENT
			BorderPane rootLayout = (BorderPane) loader.load();

			// LA SCENE CONTIENDRA NOTRE PANE RACINE
			Scene scene = new Scene(rootLayout);

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
