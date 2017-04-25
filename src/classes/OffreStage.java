package classes;

import application.Main;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class OffreStage {
	private StringProperty IDOffreStage;
	private StringProperty nomEnt;
	private StringProperty dmn;
	private StringProperty libl;
	private StringProperty dateDebut;
	private StringProperty chemin;
	private StringProperty desc;
	private StringProperty IDEntreprise_fk;
	private StringProperty adVille;
	private StringProperty adMail;
	private IntegerProperty duree;
	
	// CONSTRUCTEUR DAO
	public OffreStage(){
		
	}
	
	// CONSTRUCTEUR INSCRIPTION OFFRESTAGE
	public OffreStage(String nomEnt, String dmn, String libl, String dateDebut, Integer duree, String chemin, String desc){
        this.nomEnt = new SimpleStringProperty(nomEnt);
        this.dmn = new SimpleStringProperty(dmn);
        this.libl = new SimpleStringProperty(libl);
        this.dateDebut = new SimpleStringProperty(dateDebut);
        this.duree = new SimpleIntegerProperty(duree);
        this.chemin = new SimpleStringProperty(chemin);
        this.desc = new SimpleStringProperty(desc);        
	}
	
	// CONSTRUCTEUR IMPORTATION OFFRESTAGE
	OffreStage(String IDOffreStage, String nomEnt, String dmn, String libl, String dateDebut, Integer duree, String chemin, String desc, String IDEntreprise_fk){
        this.IDOffreStage = new SimpleStringProperty(IDOffreStage);
		this.nomEnt = new SimpleStringProperty(nomEnt);
        this.dmn = new SimpleStringProperty(dmn);
        this.libl = new SimpleStringProperty(libl);
        this.dateDebut = new SimpleStringProperty(dateDebut);
        this.duree = new SimpleIntegerProperty(duree);
        this.chemin = new SimpleStringProperty(chemin);
        this.desc = new SimpleStringProperty(desc);        
        this.IDEntreprise_fk = new SimpleStringProperty(IDEntreprise_fk);
	}

	// CONSTRUCTEUR CONSULTATION OFFRESTAGE
	public OffreStage(String IDOffreStage, String nomEnt, String dmn, String libl, String dateDebut, Integer duree, String chemin, String desc, String IDEntreprise_fk, String adVille, String adMail){
        this.IDOffreStage = new SimpleStringProperty(IDOffreStage);
		this.nomEnt = new SimpleStringProperty(nomEnt);
        this.dmn = new SimpleStringProperty(dmn);
        this.libl = new SimpleStringProperty(libl);
        this.dateDebut = new SimpleStringProperty(dateDebut);
        this.duree = new SimpleIntegerProperty(duree);
        this.chemin = new SimpleStringProperty(chemin);
        this.desc = new SimpleStringProperty(desc);        
        this.IDEntreprise_fk = new SimpleStringProperty(IDEntreprise_fk);
        this.adVille = new SimpleStringProperty(adVille);
        this.adMail = new SimpleStringProperty(adMail);
	}
	
	public void exporter(){
		System.out.println(this.nomEnt + "" + this.dmn + this.libl + this.dateDebut + this.duree + this.chemin + this.desc);
		Main.gst.exporterOffre(this);
	}
	
	public void importer(){
		Main.gst.importOffre();
	}
	
	public String getIDOffreStage(){
		return IDOffreStage.get();
	}
	
	public String getNomEnt(){
		return nomEnt.get();
	}
	
	public String getDmn(){
		return dmn.get();
	}
	
	public String getLibl(){
		return libl.get();
	}
	
	public String getDateDebut(){
		return dateDebut.get();
	}
	
	public String getChemin(){
		return chemin.get();
	}
	
	public String getDesc(){
		return desc.get();
	}
	
	public String getIDEntreprise_fk(){
		return IDEntreprise_fk.get();
	}

	public String getAdVille(){
		return adVille.get();
	}
	
	public String getAdMail(){
		return adMail.get();
	}
	
	public Integer getDuree(){
		return duree.get();
	}
	
	// GETTERS DES SYTRINGPROPERTY
	public StringProperty IDOffreStageProperty(){
		return IDOffreStage;
	}

	public StringProperty nomEntProperty(){
		return nomEnt;
	}
	
	public StringProperty dmnProperty(){
		return dmn;
	}
	
	public StringProperty liblProperty(){
		return libl;
	}
	
	public StringProperty dateDebutProperty(){
		return dateDebut;
	}
	
	public StringProperty cheminProperty(){
		return chemin;
	}
	
	public StringProperty descProperty(){
		return desc;
	}
	
	public StringProperty IDEntreprise_fkProperty(){
		return IDEntreprise_fk;
	}
	
	public StringProperty adVilleProperty(){
		return adVille;
	}
	
	public StringProperty adMailProperty(){
		return adMail;
	}

	public IntegerProperty dureeProperty(){
		return duree;
	}
}
