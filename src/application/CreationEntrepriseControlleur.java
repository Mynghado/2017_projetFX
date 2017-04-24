package application;

import classes.Entreprise;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class CreationEntrepriseControlleur {
	// -------- DEBUT DES ATTRIBUTS------------------------------
	// TEXTFIELDS
	@FXML
	private TextField nomEntreprise;
	@FXML
	private TextField adresseEntreprise;
	@FXML
	private TextField CPEntreprise;
	@FXML
	private TextField villeEntreprise;
	@FXML
	private TextField mailEntreprise;
	@FXML
	private TextField telEntreprise;

	@FXML
	private Button confirmer;
	@FXML
	private Button annuler;

	@FXML
	private ChoiceBox<String> domaine;

	// --------------------FIN DES ATTRIBUTS---------------------------

	public CreationEntrepriseControlleur() {
		// CONSTRUCTEUR DU CONTROLLEUR DOIT TOUJOURS ETRE VIDE
	}

	public void initialize() {
		domaine.getItems().add("Telecom");
		domaine.getItems().add("Sécurité");
		domaine.getItems().add("Transport");
		domaine.getItems().add("Finance");
	}

	@FXML
	public void confirmerAction(ActionEvent event) {
		if (nomEntreprise.getText().isEmpty() || adresseEntreprise.getText().isEmpty()
		|| CPEntreprise.getText().isEmpty() || villeEntreprise.getText().isEmpty()
		|| mailEntreprise.getText().isEmpty() || telEntreprise.getText().isEmpty()
		|| domaine.getValue() == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Attention !");
			alert.setHeaderText("Vous n'avez pas rempli tous les champs !");
			alert.showAndWait();
		}
		// SI TOUS LES CHAMPS SONT REMPLIS CONVENABLEMENT
		// ON PASSE A LA FENETRE SUIVANTE
		else {
			((Node) (event.getSource())).getScene().getWindow().hide();
			// VÉRIFICATION SI IDENTIFIANTS PAS DÉJÀ PRIS
			Main.gst.ajoutUtilisateur(Main.utemp.getIdentifiant(), Main.utemp.getMotPasse(), Main.utemp.getType());
			
			// CONNEXION (POUR L'EXPORTATION - FERA LA LIAISON ENTRE L'UTILISATEUR ET SON ENTREPRISE)
			Main.gst.connexion(Main.utemp.getIdentifiant(), Main.utemp.getMotPasse());
			
			// CRÉATION DE L'ENTREPRISE
			Entreprise ent = new Entreprise(nomEntreprise.getText(), adresseEntreprise.getText(),
			CPEntreprise.getText(), villeEntreprise.getText(), mailEntreprise.getText(),
			telEntreprise.getText(), domaine.getValue().toString());

			// EXPORTATION DE L'ENTREPRISE
			ent.exporter();
			
			// DÉCONNEXION
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

	// EVENEMENT LORSQUE L'ON CLIQUE SUR ANNULER
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
