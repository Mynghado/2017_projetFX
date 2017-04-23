package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ConnexionControlleur {
	@FXML
	private TextField identifiant;
	
	@FXML
	private PasswordField motDePasse;
	@FXML
	
	private Button confirmer;
	@FXML
	private Button retourAccueil;

	public ConnexionControlleur() {
		// LE CONSTRUCTEUR DU CONTROLLEUR DOIT TOUJOURS ETRE VIDE
	}

	// PERMET D'INITIALISER DES DONNEES DANS L'INTERFACE GRAPHIQUE
	@FXML
	public void initialize() {
		
	}

	@FXML
	public void clicConfirmer(ActionEvent event) {
		String messageErreur = "", id, mdp;
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Attention !");
		alert.setHeaderText("Vous n'avez pas rempli tous les champs !");
		alert.setContentText("Veuillez entrer les champs suivants :\n" + messageErreur);

		// SI CHAMP(S) NON REMPLI(S)
		if (identifiant.getText().isEmpty() || motDePasse.getText().isEmpty()) {
			if (identifiant.getText().isEmpty()) {
				messageErreur += "- Votre identifiant\n";
			}
			if (motDePasse.getText().isEmpty()) {
				messageErreur += "- Votre mot de passe\n";
			}

			alert.showAndWait();
		} else {
			//((Node) (event.getSource())).getScene().getWindow().hide();
			id = identifiant.getText();
			mdp = motDePasse.getText();

			// SI IDENTIFIANTS INCORRECTS
			if (Main.gst.connexion(id, mdp) == false) {
				alert.setTitle("Attention !");
				alert.setHeaderText("Identifiant ou mot de passe incorrect");
				alert.setContentText("");
				alert.showAndWait();
			} else { // SI IDENTIFIANTS CORRECTS
				switch(Main.gst.getType()){
				case "etudiant" :
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
					break;
					
				case "entreprise" :
					((Node) (event.getSource())).getScene().getWindow().hide();
					try {
						Stage primaryStage = new Stage();

						// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
						FXMLLoader loader = new FXMLLoader();
						// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
						loader.setLocation(Main.class.getResource("TableauDeBordEntreprise.fxml"));

						// DONNE AU FICHIER RACINE LE FXML CREE PRECEDEMENT
						BorderPane rootLayout = (BorderPane) loader.load();

						// LA SCENE CONTIENDRA NOTRE PANE RACINE
						Scene scene = new Scene(rootLayout);

						primaryStage.setScene(scene);
						primaryStage.show();

					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
					
				case "administrateur" :
					((Node) (event.getSource())).getScene().getWindow().hide();
					try {
						Stage primaryStage = new Stage();

						// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
						FXMLLoader loader = new FXMLLoader();
						// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
						loader.setLocation(Main.class.getResource("TableauDeBordAdmin.fxml"));

						// DONNE AU FICHIER RACINE LE FXML CREE PRECEDEMENT
						BorderPane rootLayout = (BorderPane) loader.load();

						// LA SCENE CONTIENDRA NOTRE PANE RACINE
						Scene scene = new Scene(rootLayout);

						primaryStage.setScene(scene);
						primaryStage.show();

					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				}
			}
		}
	}

	@FXML
	public void clicRetour(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
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
