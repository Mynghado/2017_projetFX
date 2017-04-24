package classes;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Utilisateur {

	private StringProperty IDUtilisateur;
	private StringProperty identifiant;
	private StringProperty motPasse;
	private StringProperty type;

	public Utilisateur(String identifiant, String motPasse, String type) {
		this.identifiant = new SimpleStringProperty(identifiant);
		this.motPasse = new SimpleStringProperty(motPasse);
		this.type = new SimpleStringProperty(type);
	}

	// GETTER DES STRING
	public String getIDUtilisateur() {
		return IDUtilisateur.get();
	}

	public String getIdentifiant() {
		return identifiant.get();
	}

	public String getMotPasse() {
		return motPasse.get();
	}

	public String getType() {
		return type.get();
	}

	// GETTERS DES STRINGPROPERTY
	public StringProperty IDUtilisateurProperty() {
		return IDUtilisateur;
	}

	public StringProperty identifiantProperty() {
		return identifiant;
	}

	public StringProperty motPasseProperty() {
		return motPasse;
	}

	public StringProperty typeProperty() {
		return type;
	}
	
	public void setIdentifiant(String id){
		this.identifiant = new SimpleStringProperty(id);
	}
	
	public void setMotPasse(String mdp){
		this.motPasse = new SimpleStringProperty(mdp);
	}
	
	public void setType(String type){
		this.type = new SimpleStringProperty(type);
	}
}
