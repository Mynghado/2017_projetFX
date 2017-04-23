package application;


import classes.ConnectionBDD;
import classes.Entreprise;
import classes.Etudiant;
import classes.OffrePostulee;
import classes.OffreStage;
import classes.Utilisateur;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

	// CREATION DES ATTRIBUTS
	private Stage primaryStage;
	//private Scene scene;
	private BorderPane rootLayout;
	
	public static GestionBDD gst = new GestionBDD(ConnectionBDD.getCo());
	static boolean valCo = false;
	static Utilisateur utemp = new Utilisateur("", "", "");

	static ObservableList<Entreprise> listeEntreprise;
	static ObservableList<OffreStage> listeOffre;
	static ObservableList<Etudiant> listeEtudiant;
	static ObservableList<Etudiant> listeEtudiantPostulant;
	static ObservableList<OffrePostulee> listeOffrePostulee;

	@Override
	public void start(Stage primaryStage) {
		listeEntreprise = FXCollections.observableArrayList();
		listeOffre = FXCollections.observableArrayList();
		listeEtudiant = FXCollections.observableArrayList();
		listeOffrePostulee = FXCollections.observableArrayList();
		listeEtudiantPostulant = FXCollections.observableArrayList();
		
		this.primaryStage = primaryStage;
		// DONNE LE TITRE A LA FENETRE
		this.primaryStage.setTitle("Offres de Stages");

		try {
			// CREE UN FICHIER FXML (VIDE POUR L'INSTANT)
			FXMLLoader loader = new FXMLLoader();
			// DONNE LE CHEMIN AU FICHIER FXML CREE AU-DESSUS
			loader.setLocation(Main.class.getResource("Acceuil.fxml"));
			
			// DONNE AU FICHIER RACINE LE FXML CREE PRECEDEMENT
			rootLayout =  (BorderPane) loader.load();
			
			// LA SCENE CONTIENDRA NOTRE PANE RACINE
			Scene scene = new Scene(rootLayout);
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public void setPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}
	
	
}
