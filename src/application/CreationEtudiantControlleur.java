package application;

import classes.Entreprise;
import classes.Etudiant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CreationEtudiantControlleur {

	// ************************ DEBUT DES ATTRIBUTS ***************
	@FXML
	private TextField nom;

	@FXML
	private TextField prenom;

	@FXML
	private TextField numeroEtRue;

	@FXML
	private TextField codePostal;

	@FXML
	private TextField ville;

	@FXML
	private TextField addresseMail;

	@FXML
	private TextField numeroTel;

	@FXML
	private TextField ecole;

	@FXML
	private Button confirmer;

	@FXML
	private Button annuler;

	// *********************** FIN DES ATTRIBUTS *******************

	public CreationEtudiantControlleur() {
		// LE CONSTRUCTEUR DU CONTROLEUR DOIT TOUJOURS ETRE VIDE
	}

	public void initialize() {

	}

	// EVENEMENT CLIC SUR CONFIRMER
	@FXML
	public void confirmerAction(ActionEvent event) {
		if (prenom.getText().isEmpty() || nom.getText().isEmpty()
				|| numeroEtRue.getText().isEmpty() || codePostal.getText().isEmpty()
				|| ville.getText().isEmpty() || addresseMail.getText().isEmpty()
				|| numeroTel.getText().isEmpty() || ecole.getText().isEmpty()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Attention !");
			alert.setHeaderText("Vous n'avez pas rempli tous les champs !");
			alert.showAndWait();
		}
		// SI TOUS LES CHAMPS SONT REMPLIS CONVENABLEMENT
		// ON PASSE A LA FENETRE SUIVANTE
		else {
			((Node) (event.getSource())).getScene().getWindow().hide();
			// CREATION D'UNE ENTREPRISE
			Main.gst.ajoutUtilisateur(Main.utemp.getIdentifiant(), Main.utemp.getMotPasse(), Main.utemp.getType());
			Main.gst.connexion(Main.utemp.getIdentifiant(), Main.utemp.getMotPasse());
			
			Etudiant etu = new Etudiant("0", nom.getText(), prenom.getText(),
					numeroEtRue.getText(), codePostal.getText(), ville.getText(),
					addresseMail.getText(), numeroTel.getText(), ecole.getText(), "0");

			// EXPORTE L'ENTREPRISE VERS LA BDD VIA GESTIONBDD
			etu.exporter();
			
			Main.gst.deconnexion();
			// PAGE SUIVANTE
			try {
				Stage primaryStage = new Stage();

				// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
				FXMLLoader loader = new FXMLLoader();
				// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
				loader.setLocation(Main.class.getResource("Acceuil.fxml"));

				// DONNE AU FICHIER RACINE LE FXML CREE PRECEDEMENT
				BorderPane rootLayout = (BorderPane) loader.load();

				// LA SCENE CONTIENDRA NOTRE PANE RACINE
				Scene scene = new Scene(rootLayout);

				primaryStage.setScene(scene);

				primaryStage.setResizable(false);
				primaryStage.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// EVENEMENT CLIC SUR ANNULER
	@FXML
	public void annulerAction(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
		try {
			Stage primaryStage = new Stage();
			// DONNE LE TITRE A LA FENETRE
			primaryStage.setTitle("Acceuil");
			// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
			FXMLLoader loader = new FXMLLoader();
			// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
			loader.setLocation(Main.class.getResource("Acceuil.fxml"));

			// DONNE AU FICHIER RACINE LE FXML CREE PRECEDEMENT
			BorderPane rootLayout = (BorderPane) loader.load();

			// LA SCENE CONTIENDRA NOTRE PANE RACINE
			Scene scene = new Scene(rootLayout);

			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
