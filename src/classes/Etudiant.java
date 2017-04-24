package classes;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Etudiant {
	private StringProperty IDEtudiant;
	private StringProperty nom;
	private StringProperty prenom;
	private StringProperty rue;
	private StringProperty codePostal;
	private StringProperty ville;
	private StringProperty mail;
	private StringProperty numTel;
	private StringProperty ecole;
	private StringProperty IDUtilisateur_fk;

	public Etudiant(String IDEtudiant, String nom, String prenom, String rue, String codePostal, 
	String ville, String mail, String numTel, String ecole, String IDUtilisateur_fk){
		this.IDEtudiant = new SimpleStringProperty(IDEtudiant);
		this.nom = new SimpleStringProperty(nom);
		this.prenom = new SimpleStringProperty(prenom);
		this.rue = new SimpleStringProperty(rue);
		this.codePostal = new SimpleStringProperty(codePostal);
		this.ville = new SimpleStringProperty(ville);
		this.mail = new SimpleStringProperty(mail);
		this.numTel = new SimpleStringProperty(numTel);
		this.ecole = new SimpleStringProperty(ecole);
		this.IDUtilisateur_fk = new SimpleStringProperty(IDUtilisateur_fk);
	}
	
	public void exporter(){
	    Main.gst.ajoutEtudiant(this);
	}

	// GETTERS DES STRING
	public String getIDEtudiant() {
		return IDEtudiant.get();
	}
	
	public String getNom() {
		return nom.get();
	}	
	
	public String getPrenom() {
		return prenom.get();
	}

	public String getRue() {
		return rue.get();
	}

	public String getCodePostal() {
		return codePostal.get();
	}

	public String getVille() {
		return ville.get();
	}

	public String getMail() {
		return mail.get();
	}

	public String getNumTel() {
		return numTel.get();
	}
	
	public String getEcole() {
		return ecole.get();
	}
	
	public String getIDUtilisateur_fk() {
		return IDUtilisateur_fk.get();
	}

	public StringProperty IDetudiantProperty() {
		return IDEtudiant;
	}
	
	public StringProperty nomProperty() {
		return nom;
	}
	
	public StringProperty prenomProperty() {
		return prenom;
	}

	public StringProperty rueProperty() {
		return rue;
	}

	public StringProperty codePostalProperty() {
		return codePostal;
	}

	public StringProperty villeProperty() {
		return ville;
	}

	public StringProperty mailProperty() {
		return mail;
	}

	public StringProperty numTelProperty() {
		return numTel;
	}

	public StringProperty ecoleProperty() {
		return ecole;
	}
	
	public StringProperty IDUtilisateur_fkProperty() {
		return IDUtilisateur_fk;
	}
}
