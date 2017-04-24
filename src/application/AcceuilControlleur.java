package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class AcceuilControlleur {
	// ****************** DEBUT DES ATTRIBUTS ****************************
	@FXML
	private Button consulterStage;

	@FXML
	private Button saisirOffreStage;
	@FXML
	private Button connexion;
	@FXML
	private Button monProfil;
	@FXML
	private Button inscription;

	// **************** FIN DES ATTRIBUTS ********************************

	// CONSTRUCTEUR PAR DEFAUT
	public AcceuilControlleur() {
		// LE CONSTRUCTEUR DU CONTROLLEUR DOIT TOUJOURS ETRE VIDE !

	}

	@FXML
	public void initialize() {
		// SI NOUS SOMMES CONNECTÉS -> BOUTON DÉCONNEXION
		if (Main.gst.getValCo() == true) {
			connexion.setText("DÉCONNEXION");
			inscription.setVisible(false);
		} else {
			monProfil.setVisible(false);
		}
	}

	@FXML
	public void clicConsulterOffreStage(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
		try {
			Stage primaryStage = new Stage();

			// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
			FXMLLoader loader = new FXMLLoader();
			// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
			loader.setLocation(Main.class.getResource("ConsulterOffresStages.fxml"));

			// DONNE AU FICHIER RACINE LE FXML CREE PRECEDEMENT
			BorderPane rootLayout = (BorderPane) loader.load();

			// LA SCENE CONTIENDRA NOTRE PANE RACINE
			Scene scene = new Scene(rootLayout);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void clicSaisirOffreStage(ActionEvent event) {
		Alert alert = new Alert(AlertType.ERROR);

		// SI L'UTILISATEUR EST UNE ENTREPRISE
		if (Main.gst.getValCo() == true) {
			if (Main.gst.getType().equals("entreprise")) {
				System.out.println(Main.gst.getType());
				((Node) (event.getSource())).getScene().getWindow().hide();
				try {
					Stage primaryStage = new Stage();
					// DONNE LE TITRE A LA FENETRE
					primaryStage.setTitle("Saisie d'une offre de stage étape 1/2");
					// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
					FXMLLoader loader = new FXMLLoader();
					// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
					loader.setLocation(Main.class.getResource("SaisieOffreDeStage.fxml"));

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
			// SI L'UTILISATEUR N'EST PAS UNE ENTREPRISE
			else {
				System.out.println("erreur" + Main.gst.getType());
				alert.setTitle("Attention !");
				alert.setHeaderText("Vous n'êtes pas connecté en tant qu'entreprise");
				alert.setContentText("Seules les entreprises peuvent saisir des offres de stage");
				alert.showAndWait();
			}
		}
		// SI L'UTILISATEUR N'EST PAS CONNECTÉ
		else {
			alert.setTitle("Attention !");
			alert.setHeaderText("Vous n'êtes pas connecté");
			alert.setContentText("Vous devez vous connecter en tant qu'entreprise pour pouvoir accéder à cette page");
			alert.showAndWait();
		}
	}

	@FXML
	public void clicConnexion(ActionEvent event) {
		if (Main.gst.getValCo() == false) {
			((Node) (event.getSource())).getScene().getWindow().hide();
			try {
				Stage primaryStage = new Stage();

				// DONNE LE TITRE A LA FENETRE
				primaryStage.setTitle("Connexion");

				// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
				FXMLLoader loader = new FXMLLoader();
				// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
				loader.setLocation(Main.class.getResource("Connexion.fxml"));

				// DONNE AU FICHIER RACINE LE FXML CREE PRECEDEMENT
				BorderPane rootLayout = (BorderPane) loader.load();

				// LA SCENE CONTIENDRA NOTRE PANE RACINE
				Scene scene = new Scene(rootLayout);
				primaryStage.setResizable(false);
				primaryStage.setScene(scene);
				primaryStage.show();

			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			Main.gst.deconnexion();
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

	// CETTE FONCTION EST SEULEUMENT UTILISABLE SI L'UTILISATEUR EST CONNECTE
	// IL Y A DEUX PROFILS POSSIBLES : ETUDIANT & ENTREPRISE
	// L'ADMIN EST UN CAS PARTICULIER
	@FXML
	public void clicMonProfil(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();

		// SI L'UTILISATEUR EST UNE ENTREPRISE
		switch (Main.gst.getType()) {
		case "etudiant":
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

		case "entreprise":
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

		case "administrateur":
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

	@FXML
	public void clicInscription(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();

		// SI L'UTILISATEUR EST UNE ENTREPRISE
		try {
			Stage primaryStage = new Stage();

			// DONNE LE TITRE A LA FENETRE
			primaryStage.setTitle("Inscription");

			// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
			FXMLLoader loader = new FXMLLoader();
			// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
			loader.setLocation(Main.class.getResource("Inscription.fxml"));

			// DONNE AU FICHIER RACINE LE FXML CREE PRECEDEMENT
			BorderPane rootLayout = (BorderPane) loader.load();

			// LA SCENE CONTIENDRA NOTRE PANE RACINE
			Scene scene = new Scene(rootLayout);
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
