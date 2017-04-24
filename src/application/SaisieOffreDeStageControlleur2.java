package application;

import classes.OffreStage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SaisieOffreDeStageControlleur2 {
	@FXML
	private TextArea descOffre;
	
	@FXML
	private Button confirmer;
	@FXML
	private Button annuler;
	
	
	static String desc;

	public SaisieOffreDeStageControlleur2 () {
		
	}
	
	@FXML
	public void clicConfirmer (ActionEvent event) {
		if(descOffre.getText().isEmpty()){
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Attention !");
			alert.setHeaderText("Vous n'avez pas saisi de description pour votre offre !");
			alert.showAndWait();		
		}
		else{
			((Node) (event.getSource())).getScene().getWindow().hide();
			desc = descOffre.getText();

			try {
				OffreStage offre = new OffreStage(SaisieOffreDeStageControlleur.nomEnt, SaisieOffreDeStageControlleur.dmn, SaisieOffreDeStageControlleur.libl, SaisieOffreDeStageControlleur.dateDebut, SaisieOffreDeStageControlleur.temps, SaisieOffreDeStageControlleur.chemin, desc);
				Stage primaryStage = new Stage();
				
				// EXPORTE L'OFFRE VERS LA BDD VIA GESTIONBDD
				offre.exporter();

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

	
	public void clicAnnuler () {
		
	}
}
