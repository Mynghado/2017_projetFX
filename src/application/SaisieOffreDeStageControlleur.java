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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SaisieOffreDeStageControlleur {

	// ***************** DEBUT DES ATTRIBUTS
	@FXML
	private Button confirmer;
	@FXML
	private Button annuler;

	@FXML
	private Label nomDeL_Entreprise;
	@FXML
	private ChoiceBox<Integer> duree;

	@FXML
	private TextField domaineDeL_Offre;
	@FXML
	private TextField libelleDeL_Offre;
	@FXML
	private TextField cheminDeL_Offre;

	@FXML
	private DatePicker dateDeDebut;

	static String nomEnt, dmn, libl, dateDebut, chemin;
	static Integer temps;
	// **************** FIN DES ATTRIBUTS
	
	// CONSTRUCTEUR VIDE
	public SaisieOffreDeStageControlleur() {
		// LE CONSTRUCTEUR DU CONTROLLEUR DOIT TOUJOURS ETRE VIDE !

	}

	// PERMET D'INITIALISER DES DONNEES DANS L'INTERFACE GRAPHIQUE
	@FXML
	public void initialize() {
		// UN STAGE VA DE 1 À 6 MOIS
		for (int i = 1; i < 7; i++) {
			duree.getItems().add(i);
		}
		
		for (Entreprise e : Main.gst.importEnt()) {
			if (e.getIDEntreprise().equals(Main.gst.getId())) {
				nomDeL_Entreprise.setText(e.getNomEnt());
			}
		}
	}

	@FXML
	public void confirmerAction(ActionEvent event) {
		// SI L'UTILISATEUR NE MET PAS DE DATE DE DEBUT
		// ON NE PEUT PAS AVANCER
		if (domaineDeL_Offre.getText().isEmpty()
		|| libelleDeL_Offre.getText().isEmpty() || dateDeDebut.getValue() == null || duree.getValue() == null
		|| cheminDeL_Offre.getText().isEmpty()) {
			// CREATION DE L'ALERTE
			Alert alert = new Alert(AlertType.ERROR);
			
			alert.setTitle("Attention !");
			alert.setHeaderText("Vous n'avez pas rempli tous les champs !");
			alert.setContentText("Veuillez compléter la totalité du formulaire : \n");
			alert.showAndWait();
		}
		
		// SI TOUS LES CHAMPS SONT REMPLIS CONVENABLEMENT
		// ON PASSE A LA FENETRE SUIVANTE
		else {
			((Node) (event.getSource())).getScene().getWindow().hide();
			System.out.println("Vous avez rempli tous les champs !");

			nomEnt = nomDeL_Entreprise.getText();
			dmn = domaineDeL_Offre.getText();
			libl = libelleDeL_Offre.getText();
			dateDebut = dateDeDebut.getValue().toString();
			temps = duree.getValue();
			chemin = cheminDeL_Offre.getText();

			try {
				Stage primaryStage = new Stage();

				// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
				FXMLLoader loader = new FXMLLoader();
				
				// DONNE LE TITRE A LA FENETRE
				primaryStage.setTitle("Saisie d'une offre de stage étape 2/2");
				
				// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
				loader.setLocation(Main.class.getResource("SaisieOffreDeStage2.fxml"));

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
		// SI L'UTILISATEUR A DEJA ENTRE DES VALEURS
		// ON LUI DEMANDE SI IL VEUT BIEN ANNULER SA SAISIE
		if (dateDeDebut.getValue() != null || duree.getValue() != null) {
			System.out.println("Voulez vous annuler votre saisie ?");
		}
		// SI IL N'Y A AUCUNE DONNEE SAISIE
		// ON REVIENT A L'ACCEUIL
		else {
			try {
				((Node) (event.getSource())).getScene().getWindow().hide();

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
}
