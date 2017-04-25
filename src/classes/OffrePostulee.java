package classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OffrePostulee extends OffreStage {
	private StringProperty IDEtudiant;
	private StringProperty nomEtudiant;
	private StringProperty prenomEtudiant;
	private StringProperty statut;

	// CONSTRUCTEUR DAO
	public OffrePostulee(){
		
	}
	
	public OffrePostulee(String IDOffreStage, String nomEnt, String dmn, String libl, String dateDebut, Integer duree, 
	String chemin, String desc, String IDEntreprise_fk, String IDEtudiant, String nomEtudiant, String prenomEtudiant, String statut) {
		super(IDOffreStage, nomEnt, dmn, libl, dateDebut, duree, chemin, desc, IDEntreprise_fk);
		this.IDEtudiant = new SimpleStringProperty(IDEtudiant);
		this.nomEtudiant = new SimpleStringProperty(nomEtudiant);
		this.prenomEtudiant = new SimpleStringProperty(prenomEtudiant);
		this.statut = new SimpleStringProperty(statut);
	}

	// GETTER STRING
	public String getIDEtudiant() {
		return IDEtudiant.get();
	}

	public String getNomEtudiant() {
		return nomEtudiant.get();
	}

	public String getPrenomEtudiant() {
		return prenomEtudiant.get();
	}

	public String getStatut(){
		return statut.get();
	}
	// GETTER DES STRINGPROPERTY
	public StringProperty IDEtudiantProperty() {
		return IDEtudiant;
	}

	public StringProperty nomEtudiantProperty() {
		return nomEtudiant;
	}

	public StringProperty prenomEtudiantProperty() {
		return prenomEtudiant;
	}
	
	public StringProperty statut() {
		return statut;
	}
}