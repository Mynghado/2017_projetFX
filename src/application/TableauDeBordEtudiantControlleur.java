package application;

import classes.Etudiant;
import classes.OffrePostulee;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TableauDeBordEtudiantControlleur {

	// ************************** DEBUT DES ATTRIBUTS **************************

	// ----------------------- TABLE VIEW EN HAUT A GAUCHE ----------------
	@FXML
	private Text nom;
	@FXML
	private Text prenom;
	@FXML
	private Text CP;
	@FXML
	private Text ville;
	@FXML
	private Text mail;
	@FXML
	private Text noTel;
	@FXML
	private Button modifierMonProfil;

	// ---------------------- TABLE VIEW EN HAUT A DROITE ----------------

	// ----------- TABLE VIEW EN BAS A GAUCHE-------------------
	@FXML
	private TableView<OffrePostulee> lesOffresPostulees;

	@FXML
	private TableColumn<OffrePostulee, String> lesOffresPostulees_Entreprises;
	@FXML
	private TableColumn<OffrePostulee, String> lesOffresPostulees_Libelle;
	@FXML
	private TableColumn<OffrePostulee, String> lesOffresPostulees_DateDebut;
	// -------------- FIN TABLEVIEW EN BAS A GAUCHE
	// --------------------------------

	// ----------- TABLE VIEW EN HAUT A DROITE-------------------
	@FXML
	private TableView<OffrePostulee> lesOffresAcceptees;

	@FXML
	private TableColumn<OffrePostulee, String> lesOffresAcceptees_Entreprises;
	@FXML
	private TableColumn<OffrePostulee, String> lesOffresAcceptees_Libelle;
	@FXML
	private TableColumn<OffrePostulee, String> lesOffresAcceptees_DateDebut;
	// -------------- FIN TABLEVIEW EN EN HAUT A DROITE
	// --------------------------------

	@FXML
	private Button retourAccueil;
	@FXML
	private Button deconnexion;

	private ObservableList<OffrePostulee> offrePostuleeEtudiant;
	private ObservableList<OffrePostulee> offreAcceptee;

	@FXML
	private Button annulerCandidature;

	// ************************** FIN DES ATTRIBUTS **************************

	public TableauDeBordEtudiantControlleur() {
		// LE CONSTRUCTEUR DU CONTROLLEUR DOIT ETRE VIDE
	}

	@FXML
	public void initialize() {
		offreAcceptee = FXCollections.observableArrayList(); // définition des
																// données
		offrePostuleeEtudiant = FXCollections.observableArrayList(); // définition
																		// des
																		// données

		Main.gst.importEtudiant();
		Main.gst.importOffresPostulees();
		triOffres();

		lesOffresPostulees_Entreprises.setCellValueFactory(new PropertyValueFactory<>("nomEnt"));
		lesOffresPostulees_Libelle.setCellValueFactory(new PropertyValueFactory<>("libl"));
		lesOffresPostulees_DateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));

		lesOffresAcceptees_Entreprises.setCellValueFactory(new PropertyValueFactory<>("nomEnt"));
		lesOffresAcceptees_Libelle.setCellValueFactory(new PropertyValueFactory<>("libl"));
		lesOffresAcceptees_DateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));

		lesOffresPostulees.setItems(null);
		lesOffresPostulees.setItems(offrePostuleeEtudiant);

		lesOffresAcceptees.setItems(null);
		lesOffresAcceptees.setItems(offreAcceptee);

		for (Etudiant e : Main.listeEtudiant) {
			if (e.getIDEtudiant().equals(Main.gst.getId())) {
				System.out.println("e.IDEtudiant : " + e.getIDEtudiant() + " Main.gst.getID : " + Main.gst.getId());
				nom.setText(e.getNom());
				prenom.setText(e.getPrenom());
				CP.setText(e.getCodePostal());
				ville.setText(e.getVille());
				mail.setText(e.getMail());
				noTel.setText(e.getNumTel());
			}
		}
	}

	// RETOUR A L'ACCUEIL
	@FXML
	public void clicRetourAcceuil(ActionEvent event) {
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
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// RETOUR A L'ACCEUIL ET DECONNEXION
	@FXML
	public void clicDeconnexion(ActionEvent event) {
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

	@FXML
	public void clicAnnulerCandidature(ActionEvent event) {
		// ON CREE UNE VARIABLE POUR L'INDEX
		int i = lesOffresPostulees.getSelectionModel().getSelectedIndex();

		// SI L'OFFRE EST DANS LA LISTE
		if (i >= 0) {
			Main.gst.depostulerOffre(offrePostuleeEtudiant.get(i).getIDOffreStage());
			Main.gst.importOffresPostulees();
			triOffres();

			// 4 - APPLIQUER LA "NOUVELLE" LISTE OBSERVABLE AU TABLE VIEW
			lesOffresPostulees.setItems(offrePostuleeEtudiant);
			lesOffresAcceptees.setItems(offreAcceptee);
		}
		// SI L'OFFRE N'EST PAS SELECTIONNÉE
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Attention !");
			alert.setHeaderText("Vous n'avez pas selectionné d'offre");
			alert.setContentText("Cliquez sur une offre pour annuler votre candidature");
			alert.showAndWait();
		}
	}

	@FXML
	public void clicModifierMonProfil(ActionEvent event) {
		((Node) (event.getSource())).getScene().getWindow().hide();
		try {
			Stage primaryStage = new Stage();
			// DONNE LE TITRE A LA FENETRE
			primaryStage.setTitle("Saisie d'une offre de stage étape 1/2");
			// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
			FXMLLoader loader = new FXMLLoader();
			// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
			loader.setLocation(Main.class.getResource("TableauDeBordEtudiantModifier.fxml"));

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

	public void triOffres() {
		offrePostuleeEtudiant.remove(0, offrePostuleeEtudiant.size());
		offreAcceptee.remove(0, offreAcceptee.size());

		for (OffrePostulee o : Main.listeOffrePostulee) {
			if (o.getIDEtudiant().equals(Main.gst.getId())) {
				System.out.println(o.getLibl() + " " + o.getIDEtudiant() + " " + Main.gst.getId());
				offrePostuleeEtudiant.add(o);
			}
		}

		for (OffrePostulee o : offrePostuleeEtudiant) {
			if (o.getStatut().equals("1")) {
				offreAcceptee.add(o);
			}
		}

		System.out.println(
				"offrePostulee : " + offrePostuleeEtudiant.size() + " offreAcceptee : " + offreAcceptee.size());
	}
}
