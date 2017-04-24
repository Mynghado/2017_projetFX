package application;

import classes.OffrePostulee;
import classes.OffreStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ConsulterOffresStagesControlleur {
	// LE TABLE VIEW
	@FXML
	private TableView<OffreStage> offreStage;

	// LES COLONNES DU TABLEAU <Objet TableView - Objet TableColumn>
	@FXML
	private TableColumn<OffreStage, String> nom;
	@FXML
	private TableColumn<OffreStage, String> libelle;
	@FXML
	private TableColumn<OffreStage, String> date;
	@FXML
	private TableColumn<OffreStage, Integer> temps;

	// LES LABELS
	@FXML
	private Text texte;
	@FXML
	private Label description;

	@FXML
	private Label nomDeL_Entreprise;
	@FXML
	private Label libelle_details;
	@FXML
	private Label domaine;
	@FXML
	private Label dateDeDebut;
	@FXML
	private Label duree;

	@FXML
	private StackPane test;

	@FXML
	private Button postuler;
	@FXML
	private Button retourAccueil;

	// ******************* FIN DES ATTRIBUTS ************************

	public ConsulterOffresStagesControlleur() {
		// CONSTRUCTEUR DU CONTROLLEUR DOIT TOUJOURS ETRE VIDE
	}

	@FXML
	public void initialize() {
		// LE TEXTE DE LA DESCRIPTION SERA DISTRIBUé!
		description.setWrapText(true);
		
		
		//test = new StackPane(libelle_details);
		test.setPadding(new Insets(10));

		Main.gst.importOffre();

		// paramètre -> le nom de l'attribut - il doit y avoir un
		// "nomVariableProperty"
		nom.setCellValueFactory(new PropertyValueFactory<>("nomEnt"));
		libelle.setCellValueFactory(new PropertyValueFactory<>("libl"));
		date.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
		temps.setCellValueFactory(new PropertyValueFactory<>("duree"));

		offreStage.setItems(null);
		offreStage.setItems(Main.gst.importOffre());

		// APELLE LA METHODE D'ECOUTE
		offreStage.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> detailsDuStage(newValue));
	}

	
	// FONCTION DE DETAILS DU STAGE
	@FXML
	private void detailsDuStage(OffreStage offre) {
		if (offre != null) {
			texte.setText("");
			nomDeL_Entreprise.setText(offre.getNomEnt());
			libelle_details.setText(offre.getLibl());
			domaine.setText(offre.getDmn());
			description.setText(offre.getDesc());
			dateDeDebut.setText("Le stage commence le " + offre.getDateDebut());
			duree.setText("La durée est de " + offre.getDuree().toString() + " mois");
		} else {
			nomDeL_Entreprise.setText("");
			libelle_details.setText("");
			domaine.setText("");
			description.setText("");
			dateDeDebut.setText("");
			duree.setText("");
		}
	}

	@FXML
	public void clicRetourAccueil(ActionEvent event) {
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

	@FXML
	public void clicPostuler(ActionEvent event) {
		Alert alert = new Alert(AlertType.ERROR);
		// 1 - RAJOUTER UNE LIGNE DANS LA TABLE OFFREPOSTULEE AVEC L'ID DE
		// L'ETUDIANT ET DE L'OFFRE
		int i = offreStage.getSelectionModel().getSelectedIndex();

		// SI UNE OFFRE DE STAGE EST SÉLECTIONNÉE
		if (i >= 0) {
			// SI L'UTILISATEUR EST BIEN CONNECTÉ
			if (Main.gst.getValCo()) {
				// S'IL SAGIT D'UN ETUDIANT ET QU'IL N'A PAS DÉJÀ POSTULÉ À
				// CETTE OFFRE DE STAGE
				if (isEtudiant() && isPostulee(i)) {
					Main.gst.postulerOffre(offreStage.getItems().get(i).getIDOffreStage());
				}
			} else {
				alert.setTitle("Attention !");
				alert.setHeaderText("Vous n'êtes pas connecté");
				alert.setContentText("Veuillez vous connecter en tant qu'étudiant pour pouvoir postuler à cette offre");
				alert.showAndWait();
			}
		}
		// MESSAGE D'ERREUR SI L'UTILISATEUR N'A PAS SELECTIONNE D'OFFRE DE
		// STAGE
		else {
			alert.setTitle("Attention !");
			alert.setHeaderText("Vous n'avez pas selectionné d'offre de stage");
			alert.setContentText("Cliquez sur une offre de stage pour pouvoir y postuler");
			alert.showAndWait();
		}
	}

	public boolean isPostulee(int i) {
		Alert alert = new Alert(AlertType.ERROR);
		Main.gst.importOffresPostulees();

		for (OffrePostulee o : Main.gst.importOffresPostulees()) {
			if (o.getIDEtudiant().equals(Main.gst.getId())
					&& o.getIDOffreStage().equals(offreStage.getItems().get(i).getIDOffreStage())) {
				alert.setTitle("Attention !");
				alert.setHeaderText("Vous avez déjà postulé à cette offre de stage");
				alert.setContentText(
						"Veuillez sélectionner une offre de stage pour laquelle vous n'avez pas déjà postulé");
				alert.showAndWait();

				return false;
			}
		}

		return true;
	}

	public boolean isEtudiant() {
		Alert alert = new Alert(AlertType.ERROR);

		if (!Main.gst.getType().equals("etudiant")) {
			alert.setTitle("Attention !");
			alert.setHeaderText("Vous n'êtes pas identifié en tant qu'étudiant");
			alert.setContentText("Veuillez vous connecter en tant qu'étudiant pour pouvoir postuler à cette offre");
			alert.showAndWait();

			return false;
		}

		return true;
	}
}
