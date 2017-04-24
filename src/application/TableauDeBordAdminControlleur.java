package application;

import classes.Entreprise;
import classes.Etudiant;
import classes.OffreStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TableauDeBordAdminControlleur {
	// ************************** DEBUT DES ATTRIBUTS **************************

	// ----------- TABLE VIEW A GAUCHE -------------------
	@FXML
	private TableView<Entreprise> lesEntreprises;

	@FXML
	private TableColumn<Entreprise, String> lesEntreprises_Nom;

	@FXML
	private TableColumn<Entreprise, String> lesEntreprises_Rue;

	// ----------- FIN TABLE VIEW A GAUCHE -------------------

	// ----------- TABLE VIEW AU MILIEU -------------------
	@FXML
	private TableView<OffreStage> lesOffresDeStages;

	@FXML
	private TableColumn<OffreStage, String> lesOffresDeStages_Libelle;
	// ----------- FIN TABLE VIEW AU MILIEU -------------------
	@FXML
	private TableColumn<OffreStage, String> lesOffresDeStages_Entreprises;

	// ----------- TABLE VIEW A DROITE -------------------
	@FXML
	private TableView<Etudiant> lesEtudiants;

	@FXML
	private TableColumn<Etudiant, String> lesEtudiants_Nom;

	@FXML
	private TableColumn<Etudiant, String> lesEtudiants_Prenom;
	// ----------- FIN TABLE VIEW A DROITE -------------------

	@FXML
	private Button supprimerEntreprise;

	@FXML
	private Button supprimerOffreDeStage;

	@FXML
	private Button supprimerEtudiant;

	@FXML
	private Button retour_deconnexion;

	// *************************** FIN DES ATTRIBUTS ****************

	public TableauDeBordAdminControlleur() {
		// LE CONSTRUCTEUR DU CONTROLLEUR DOIT ETRE VIDE
	}

	@FXML
	public void initialize() {
		Main.gst.importEnt();
		Main.gst.importOffre();
		Main.gst.importEtudiant();

		// ETAPE 3 : SPECIFIER CE QUE DOIT AFFICHER LA COLONNE
		lesEntreprises_Nom.setCellValueFactory(new PropertyValueFactory<>("nomEnt"));
		lesEntreprises_Rue.setCellValueFactory(new PropertyValueFactory<>("rue"));

		lesOffresDeStages_Libelle.setCellValueFactory(new PropertyValueFactory<>("libl"));
		lesOffresDeStages_Entreprises.setCellValueFactory(new PropertyValueFactory<>("nomEnt"));

		lesEtudiants_Nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
		lesEtudiants_Prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

		// ETAPE 4 : APPLIQUER L'OBSERVALE LISTE AU TABLE VIEW
		lesEntreprises.setItems(null);
		lesEntreprises.setItems(Main.gst.importEnt());

		lesOffresDeStages.setItems(null);
		lesOffresDeStages.setItems(Main.gst.importOffre());

		lesEtudiants.setItems(null);
		lesEtudiants.setItems(Main.gst.importEtudiant());
	}

	@FXML
	public void clicRetour_deconnexion(ActionEvent event) {
		Main.gst.deconnexion();
		// Main.gst.deconnexion();
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

	@FXML
	public void clicSupprimerEntreprise(ActionEvent event) {
		Alert alert = new Alert(AlertType.ERROR);
		// ON CREE UNE VARIABLE POUR L'INDEX
		int i = lesEntreprises.getSelectionModel().getSelectedIndex();

		// SI L'ENTREPRISE EST DANS LA LISTE
		if (i >= 0) {
			// 1 - RECUPERATION DE L'ID DE L'ENTREPRISE ET SUPPRESSION DANS LA BDD
			Main.gst.supprEnt(lesEntreprises.getItems().get(i).getIDUtilisateur_fk());

			// 2 - REIMPORTATION DE LA LISTE DEPUIS LA BDD
			Main.gst.importEnt();
			Main.gst.importOffre();

			// 3 - APPLIQUER LA "NOUVELLE" LISTE OBSERVABLE AU TABLE VIEW
			lesEntreprises.setItems(Main.gst.importEnt());
			lesOffresDeStages.setItems(Main.gst.importOffre());
		}
		// MESSAGE D'ERREUR SI L'UTILISATEUR N'A PAS SELECTIONNE D'ENTREPRISE
		else {
			alert.setTitle("Attention !");
			alert.setHeaderText("Vous n'avez pas selectionné d'entreprise");
			alert.setContentText("Cliquez sur une entreprise pour la supprimer");
			alert.showAndWait();
		}
	}

	@FXML
	public void clicSupprimerOffreDeStage(ActionEvent event) {
		// ON CREE UNE VARIABLE POUR L'INDEX
		int i = lesOffresDeStages.getSelectionModel().getSelectedIndex();

		// SI L'ENTREPRISE EST DANS LA LISTE
		if (i >= 0) {
			// 1 - RECUPERATION DE L'ID DE L'ENTREPRISE ET SUPPRESSION DANS LA BDD
			Main.gst.supprOffre(lesOffresDeStages.getItems().get(i).getIDOffreStage());

			// 2 - REIMPORTATION DE LA LISTE DEPUIS LA BDD
			Main.gst.importOffre();

			// 3 - APPLIQUER LA "NOUVELLE" LISTE OBSERVABLE AU TABLE VIEW
			lesOffresDeStages.setItems(Main.gst.importOffre());
		}
		// MESSAGE D'ERREUR SI L'UTILISATEUR N'A PAS SELECTIONNE D'ENTREPRISE
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Attention !");
			alert.setHeaderText("Vous n'avez pas selectionné d'offre de stage");
			alert.setContentText("Cliquez sur une offre de stage pour la supprimer");
			alert.showAndWait();
		}
	}

	@FXML
	public void clicSupprimerEtudiant(ActionEvent event) {
		// ON CREE UNE VARIABLE POUR L'INDEX
		int i = lesEtudiants.getSelectionModel().getSelectedIndex();

		// SI L'ENTREPRISE EST DANS LA LISTE
		if (i >= 0) {
			// 1 - RECUPERATION DE L'ID DE L'ENTREPRISE ET SUPPRESSION DANS LA BDD
			Main.gst.supprEtudiant(lesEtudiants.getItems().get(i).getIDUtilisateur_fk());

			// 2 - REIMPORTATION DE LA LISTE DEPUIS LA BDD
			Main.gst.importEtudiant();

			// 3 - APPLIQUER LA "NOUVELLE" LISTE OBSERVABLE AU TABLE VIEW
			lesEtudiants.setItems(Main.gst.importEtudiant());
		}
		// MESSAGE D'ERREUR SI L'UTILISATEUR N'A PAS SELECTIONNE D'ENTREPRISE
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Attention !");
			alert.setHeaderText("Vous n'avez pas selectionné d'étudiant");
			alert.setContentText("Cliquez sur un étudiant pour la supprimer");
			alert.showAndWait();
		}
	}
}
