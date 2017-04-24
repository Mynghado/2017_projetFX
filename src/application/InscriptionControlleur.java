package application;

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

public class InscriptionControlleur {
	// ************************** DEBUT DES ATTRIBUTS **************************
	@FXML
	private TextField champIdentifiant;
	@FXML
	private TextField champMotDePasse;

	@FXML
	private ChoiceBox<String> statut;

	@FXML
	private Button confirmer;
	@FXML
	private Button annuler;

	// ************************* FIN DES ATTRIBUTS *****************************

	public InscriptionControlleur() {
		// CONSTRUCTEUR DU CONTROLLEUR DOIT TOUJOURS ETRE VIDE
	}

	public void initialize() {
		statut.getItems().add("etudiant");
		statut.getItems().add("entreprise");
	}

	@FXML
	public void clicConfirmer(ActionEvent event) {
		Alert alert = new Alert(AlertType.ERROR);
		
		// SI TOUS LES CHAMPS NE SONT PAS REMPLI CONVENABLEMENT
		if (champIdentifiant.getText().isEmpty() || champMotDePasse.getText().isEmpty() || statut.getValue() == null) {
			// CREATION DE L'ALERTE
			alert.setTitle("Attention !");
			alert.setHeaderText("Vous n'avez pas rempli tous les champs !");
			alert.setContentText("Veuillez compléter la totalité du formulaire\n");
			alert.showAndWait();
		}
		
		// SI L'UTILISATEUR EST UNE ENTREPRISE
		else if (statut.getValue().equals("entreprise")) {
			// SI NOM D'UTILISATEUR DÉJÀ UTILISÉ
			if(!Main.gst.ajoutUtilisateur(champIdentifiant.getText(), champMotDePasse.getText(), statut.getValue().toString())){
				alert.setTitle("Attention !");
				alert.setHeaderText("Ce nom d'utilisateur est déjà pris");
				alert.setContentText("Veuillez en choisir un autre\n");
				alert.showAndWait();
			}
			else{
				System.out.println(champIdentifiant.getText());
				
				Main.utemp.setIdentifiant(champIdentifiant.getText());
				Main.utemp.setMotPasse(champMotDePasse.getText());
				Main.utemp.setType(statut.getValue().toString());
				Main.gst.supprUtilisateur(Main.utemp.getIdentifiant());
				
				// CHARGMEENT DE LA CRÉATION D'ENTREPRISE - BESOIN PAGE DE MODIFCATION D'ENTREPRISE
				((Node) (event.getSource())).getScene().getWindow().hide();
				try {
					Stage primaryStage = new Stage();

					// DONNE LE TITRE A LA FENETRE
					primaryStage.setTitle("Création d'une entreprise");

					// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
					FXMLLoader loader = new FXMLLoader();
					// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
					loader.setLocation(Main.class.getResource("CreationEntreprise.fxml"));

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

		// SI L'UTILISATEUR EST UN ÉTUDIANT
		else if (statut.getValue().equals("etudiant")) {
			// SI NOM D'UTILISATEUR DÉJÀ UTILISÉ
			if(!Main.gst.ajoutUtilisateur(champIdentifiant.getText(), champMotDePasse.getText(), statut.getValue().toString())){
				alert.setTitle("Attention !");
				alert.setHeaderText("Ce nom d'utilisateur est déjà pris");
				alert.setContentText("Veuillez en choisir un autre\n");
				alert.showAndWait();
			}
			else{
				System.out.println(champIdentifiant.getText());
				
				Main.utemp.setIdentifiant(champIdentifiant.getText());
				Main.utemp.setMotPasse(champMotDePasse.getText());
				Main.utemp.setType(statut.getValue().toString());
				Main.gst.supprUtilisateur(Main.utemp.getIdentifiant());

				((Node) (event.getSource())).getScene().getWindow().hide();
				try {
					Stage primaryStage = new Stage();

					// DONNE LE TITRE A LA FENETRE
					primaryStage.setTitle("Inscription d'un étudiant");

					// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
					FXMLLoader loader = new FXMLLoader();
					// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
					loader.setLocation(Main.class.getResource("CreationEtudiant.fxml"));

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
	}

	@FXML
	public void clicAnnuler(ActionEvent event) {
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
