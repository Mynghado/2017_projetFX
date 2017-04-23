package classes;

import java.util.LinkedList;

import application.Main;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Entreprise {
	// CONVENTION JAVAFX, TOUT LES ATTRIBUTS DOIVENT ETRE DECLAREES 1 PAR 1
	private StringProperty IDEntreprise;
	private StringProperty nomEnt;
	private StringProperty rue;
	private StringProperty codePostal;
	private StringProperty ville;
	private StringProperty mail;
	private StringProperty numTel;
	private StringProperty sectActv;
	private StringProperty IDUtilisateur_fk;

	// CONSTRUCTEUR INSCRIPTION ENTREPRISE
	public Entreprise(String nomEnt, String rue, String codePostal, String ville, String mail, String numTel, String sectActv) {
		this.nomEnt = new SimpleStringProperty(nomEnt);
		this.rue = new SimpleStringProperty(rue);
		this.codePostal = new SimpleStringProperty(codePostal);
		this.ville = new SimpleStringProperty(ville);
		this.mail = new SimpleStringProperty(mail);
		this.numTel = new SimpleStringProperty(numTel);
		this.sectActv = new SimpleStringProperty(sectActv);
	}

	// CONSTRUCTEUR IMPORTATION ENTREPRISE
	public Entreprise(String IDEntreprise, String nomEnt, String rue, String codePostal, String ville, String mail, String numTel, String sectActv,
	String IDUtilisateur_fk) {
		this.IDEntreprise = new SimpleStringProperty(IDEntreprise);
		this.nomEnt = new SimpleStringProperty(nomEnt);
		this.rue = new SimpleStringProperty(rue);
		this.codePostal = new SimpleStringProperty(codePostal);
		this.ville = new SimpleStringProperty(ville);
		this.mail = new SimpleStringProperty(mail);
		this.numTel = new SimpleStringProperty(numTel);
		this.sectActv = new SimpleStringProperty(sectActv);
		this.IDUtilisateur_fk = new SimpleStringProperty(IDUtilisateur_fk);
	}

	public void exporter(){
	    Main.gst.exporterEnt(nomEnt.get(), rue.get(), codePostal.get(), ville.get(), mail.get(), numTel.get(), sectActv.get());
	}

//	public void importer() {
//		 GestionBDD gstbdd = new GestionBDD();
//		
//		 LinkedList<String> listEnt = gstbdd.importEnt();
//		 nomEnt = listEnt.get(0);
//		 rue = listEnt.get(1);
//		 codePostal = listEnt.get(2);
//		 ville = listEnt.get(3);
//		 mail = listEnt.get(4);
//		 numTel = listEnt.get(5);
//		 sectActv = listEnt.get(6);
//
//		// Voir pour retourner un objet entreprise / une liste d'entreprises
//		// dans la méthode importer de GestionBDD
//	}

//	public void importer() {
//		LinkedList<Entreprise> listEnt = Main.gst.importEnt();
//		for(Entreprise e : listEnt){
//			Main.listeEntreprise.add(e);
//		}
//	}
	
	// GETTER DES STRING
	public String getIDEntreprise() {
		return IDEntreprise.get();
	}
	
	public String getNomEnt() {
		return nomEnt.get();
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

	public String getSectActv() {
		return sectActv.get();
	}
	
	public String getIDUtilisateur_fk() {
		return IDUtilisateur_fk.get();
	}

	// GETTERS DES SYTRINGPROPERTY
	public StringProperty IDEntrepriseProperty() {
		return IDEntreprise;
	}
	
	public StringProperty nomEntProperty() {
		return nomEnt;
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

	public StringProperty sectActvProperty() {
		return sectActv;
	}
	
	public StringProperty IDUtilisateur_fkProperty() {
		return IDUtilisateur_fk;
	}
}
