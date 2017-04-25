package application;
import java.security.MessageDigest;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

import com.mysql.jdbc.CommunicationsException;
import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.Statement;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import classes.Entreprise;
import classes.Etudiant;
import classes.OffrePostulee;
import classes.OffreStage;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class GestionBDD {
	private static String sql = null; // REQUÊTES
	private static java.sql.Connection cn = null; // CONNEXION
    private static java.sql.PreparedStatement stmt = null; // REQUÊTES PRÉPARÉES
    private static ResultSet rst = null; // LE RÉSULTAT DE LA SÉLECTION
	private static String type, ide, idu; // TYPE DE COMPTE - IDENTREPRISE - IDUTILISATEUR
	private static boolean valCo = false; // SI CONNECTÉ OU NON
	
	GestionBDD(java.sql.Connection connection){
		this.cn = connection;
	}
	
	// AUTHENTIFICATION
	public boolean connexion(String id, String mdp){
		try{
			sql = "SELECT IDUtilisateur, identifiant, motPasse, type FROM utilisateur";
		
		    stmt = cn.prepareStatement(sql);

			rst = (ResultSet) stmt.executeQuery(sql);
			
			while (rst.next() && valCo == false) {
				if(id.equals(rst.getString("identifiant")) == true && md5(mdp).equals(rst.getString("motPasse")) == true){
					type = rst.getString("type");
					System.out.println(type);
					idu = rst.getString("IDUtilisateur");
					valCo = true;
					getTableId(rst.getString("IDUtilisateur"));
					
					return valCo;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(valCo == false){
				valCo = false;
				type = null;
			}
			return valCo;
		}
	}
	
	// CRYPTAGE DU MOT DE PASSE
	public static String md5( String source ) {
        try {
            MessageDigest md = MessageDigest.getInstance( "MD5" );
            byte[] bytes = md.digest( source.getBytes("UTF-8") );
            return getString(bytes);
        } catch( Exception e )	{
            e.printStackTrace();
            return null;
        }
    }
	
	// GETSTRING AUTHENTIFICATION
	private static String getString( byte[] bytes )
    {
        StringBuffer sb = new StringBuffer();
        for( int i=0; i<bytes.length; i++ )
        {
            byte b = bytes[ i ];
            String hex = Integer.toHexString((int) 0x00FF & b);
            if (hex.length() == 1)
            {
                sb.append("0");
            }
            sb.append( hex );
        }
        return sb.toString();
    }
	
	// RÉCUPÉRATION DE L'ID DE L'UTILISATEUR DANS "SA" TABLE
	public void getTableId(String IDUti){
		try{
			// SI C'EST UNE ENTREPRISE
			if(type.equals("entreprise")){
				sql = "SELECT e.IDEntreprise FROM entreprise e INNER JOIN utilisateur ON IDUtilisateur_fk = ?";
			    stmt = cn.prepareStatement(sql);
			    stmt.setString(1, IDUti);

				rst = (ResultSet) stmt.executeQuery();
			}
			// SI C'EST UN ÉTUDIANT - NE FAIT RIEN SI ADMINISTRATEUR
			else{ 
				sql = "SELECT IDEtudiant FROM etudiant INNER JOIN utilisateur ON IDUtilisateur_fk = ?";
			    stmt = cn.prepareStatement(sql);
			    stmt.setString(1, IDUti);
			    
				rst = (ResultSet) stmt.executeQuery();
			}
			
			// RÉCUPÉRATION DE L'ID
			while (rst.next()) {
				if(type.equals("entreprise")){
					ide = rst.getString("IDEntreprise");	
				}
				else{
					ide = rst.getString("IDEtudiant");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// DÉCONNEXION - PASSE NOS DONNÉES À NULL ET NOTRE VARIABLE DE CONNEXION À FALSE
	public void deconnexion(){
		valCo = false;
		type = null;
		ide = null;
	}
			
	// RÉCUPÉRATION DE LA LISTE DES ENTREPRISES - NOUS SERT À SET LES TABLEVIEW
	public ObservableList<Entreprise> importEnt(){
		String nomCol[] = {"IDEntreprise", "nomEntreprise", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "sectActv", "IDUtilisateur_fk"};
		ObservableList<Entreprise> listEnt = FXCollections.observableArrayList();

		try{
			sql = "SELECT * FROM entreprise";
			
		    stmt = cn.prepareStatement(sql);

			rst = (ResultSet) stmt.executeQuery(sql);

			// POUR CHAQUE ENTREPRISE - L'AJOUTER À LA LISTE
			while (rst.next()) {
				listEnt.add(new Entreprise(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
				rst.getString(nomCol[3]), rst.getString(nomCol[4]), rst.getString(nomCol[5]), rst.getString(nomCol[6]), rst.getString(nomCol[7]), 
				rst.getString(nomCol[8])));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listEnt;
	}
	
	// SUPPRESSION D'UNE ENTREPRISE EN FONCTION DE L'ID DE SON UTILISATEUR - LES DEUX SONT LIÉS
	public void supprEnt(String id){
		try {  
		    stmt = cn.prepareStatement("DELETE FROM utilisateur WHERE IDUtilisateur = ?");
		    stmt.setString(1, id);
		    
		    stmt.executeUpdate(); 
		 }
		 catch(Exception e) {
		     System.out.println(e);
		 }
	}

	// RÉCUPÉRATION DE LA LISTE DES OFFRES DE STAGE - NOUS SERT À SET LES TABLEVIEW
	public ObservableList<OffreStage> importOffre(){
		String nomCol[] = {"IDOffreStage", "nomEntreprise", "domOffre", "libelle", "dateDebut", "duree", "chemin", "description", "IDEntreprise_fk", "adVille", "adMail"};
		ObservableList<OffreStage> listOffre = FXCollections.observableArrayList();
		
		try{
			sql = "SELECT o.*, e.adVille, e.adMail FROM entreprise e INNER JOIN offrestage o ON e.IDEntreprise = o.IDEntreprise_fk";
			
		    stmt = cn.prepareStatement(sql);

			rst = (ResultSet) stmt.executeQuery();

			while (rst.next()) {
				listOffre.add(new OffreStage(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
				rst.getString(nomCol[3]), rst.getString(nomCol[4]), Integer.valueOf(rst.getInt(nomCol[5])), rst.getString(nomCol[6]), 
				rst.getString(nomCol[7]), rst.getString(nomCol[8]), rst.getString(nomCol[9]), rst.getString(nomCol[10])));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listOffre;
	}
	
	// RÉCUPÉRATION DE LA LISTE DES OFFRES POSTULÉES - NOUS SERT À SET LES TABLEVIEW
	public ObservableList<OffrePostulee> importOffresPostulees(){
		String nomCol[] = {"IDOffreStage", "nomEntreprise", "domOffre", "libelle", "dateDebut", "duree", "chemin", "description", "IDEntreprise_fk"};
		String nomCol2[] = {"IDEtudiant", "nom", "prenom", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "ecole", "IDUtilisateur_fk"};
		String nomCol3 = "statut";
		ObservableList<OffrePostulee> listOffre = FXCollections.observableArrayList();
		
		try{
			// SI C'EST UNE ENTREPRISE - ON RÉCUPÈRE SES OFFRES OÙ DES ÉTUDIANTS ONT POSTULÉ
			if(type.equals("entreprise")){
				sql = "SELECT o.*, e.*, o2.statut FROM offrestage o INNER JOIN offrepostulee o2 ON o.IDOffreStage = o2.IDOffreStage_fk "
				+ "INNER JOIN etudiant e ON e.IDEtudiant = o2.IDEtudiant_fk WHERE o.IDEntreprise_fk = ?";
				
			    stmt = cn.prepareStatement(sql);
			    stmt.setString(1, ide);
			}
			// SINON, C'EST UN ÉTUDIANT - ON RÉCUPÈRE LES OFFRES OÙ IL A POSTULÉ LUI
			else if(type.equals("etudiant")){
				sql = "SELECT o.*, e.*, o2.statut FROM offrestage o INNER JOIN offrepostulee o2 ON o.IDOffreStage = o2.IDOffreStage_fk "
				+ "INNER JOIN etudiant e ON e.IDEtudiant = o2.IDEtudiant_fk AND o2.IDEtudiant_fk = ?";
				
			    stmt = cn.prepareStatement(sql);
			    stmt.setString(1, ide);
			}
			
			rst = (ResultSet) stmt.executeQuery();

			while (rst.next()) {
				listOffre.add(new OffrePostulee(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
				rst.getString(nomCol[3]), rst.getString(nomCol[4]), Integer.valueOf(rst.getInt(nomCol[5])), rst.getString(nomCol[6]), 
				rst.getString(nomCol[7]), rst.getString(nomCol[8]), rst.getString(nomCol2[0]), rst.getString(nomCol2[1]), rst.getString(nomCol2[2]),
				rst.getString(nomCol3)));
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listOffre;
	}
	
	// SUPPRESSION D'UNE OFFRE EN FONCTION DE SON ID
	public void supprOffre(String id){
		try {  
		    java.sql.PreparedStatement st = cn.prepareStatement("DELETE FROM offrestage WHERE IDOffreStage = ?");
		    st.setString(1, id);
		    
		    st.executeUpdate(); 
		 }
		 catch(Exception e) {
		     System.out.println(e);
		 }
	}
	
	// ACCEPTATION PAR L'ENTREPRISE D'UN ÉTUDIANT
	public void accepterOffre(String IDOffreStage, String IDEtudiant){
		try{
			java.sql.PreparedStatement st = cn.prepareStatement("UPDATE offrepostulee SET statut = 1 WHERE IDEtudiant_fk = ? AND IDOffreStage_fk = ?");
			st.setString(1, IDEtudiant);
			st.setString(2, IDOffreStage);
			
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// POSTULATION À UNE OFFRE D'UNE ENTREPRISE PAR UN ÉTUDIANT
	public void postulerOffre(String IDOffreStage){
		try {			
			java.sql.PreparedStatement st = cn.prepareStatement("INSERT INTO offrePostulee (`IDEtudiant_fk`, `IDOffreStage_fk`, `statut`) "
			+ "VALUES (?, ?, ?)");
			st.setString(1, ide);
			st.setString(2, IDOffreStage);
			st.setString(3, "0");
			
		    st.executeUpdate(); 
		    
		    importOffresPostulees();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// ANNULATION DE LA POSTULATION À UNE OFFRE PAR UN ÉTUDIANT
	public void depostulerOffre(String IDOffreStage){
		try {  
		    java.sql.PreparedStatement st = cn.prepareStatement("DELETE FROM offrepostulee WHERE IDEtudiant_fk = ? AND IDOffreStage_fk = ?");
		    st.setString(1, ide);
		    st.setString(2, IDOffreStage);
		    
		    st.executeUpdate(); 
		 }
		 catch(Exception e) {
		     System.out.println(e);
		 }
	}
	
	// RÉCUPÉRATION DE LA LISTE DES ÉTUDIANTS - NOUS SERT À SET LES TABLEVIEW
	public ObservableList<Etudiant> importEtudiant(){		
		String nomCol[] = {"IDEtudiant", "nom", "prenom", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "ecole", "IDUtilisateur_fk"};
		ObservableList<Etudiant> listEtu = FXCollections.observableArrayList();
		
		try{
			sql = "SELECT * FROM etudiant";

		    stmt = cn.prepareStatement(sql);

			rst = (ResultSet) stmt.executeQuery();
			
			while (rst.next()) {
				listEtu.add(new Etudiant(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
				rst.getString(nomCol[3]), rst.getString(nomCol[4]), rst.getString(nomCol[5]), rst.getString(nomCol[6]), 
				rst.getString(nomCol[7]), rst.getString(nomCol[8]), rst.getString(nomCol[9])));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listEtu;
	}
	
	// AJOUT D'UN ÉTUDIANT 
	public void ajoutEtudiant(Etudiant etu){
		try {			
			stmt = cn.prepareStatement("INSERT INTO etudiant (`nom`, `prenom`, `adNumRue`, `adCodePostal`, `adVille`, `adMail`, `numTel`, `ecole`, `IDUtilisateur_fk`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			stmt.setString(1, etu.getNom());
			stmt.setString(2, etu.getPrenom());
			stmt.setString(3, etu.getRue());
			stmt.setString(4, etu.getCodePostal());
			stmt.setString(5, etu.getVille());
			stmt.setString(6, etu.getMail());
			stmt.setString(7, etu.getNumTel());
			stmt.setString(8, etu.getEcole());
			stmt.setString(9, idu);
			
			System.out.println(idu);
			
		    stmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// MODIFICATION D'UN ÉTUDIANT
	public void modifEtudiant(String rue, String codePostal, String ville, String tel, String mail){
		try{
			stmt = cn.prepareStatement("UPDATE etudiant SET adNumRue = ?, adCodePostal = ?, adVille = ?, adMail = ?, numTel = ? WHERE IDUtilisateur_fk = ?");
			stmt.setString(1, rue);
			stmt.setString(2, codePostal);
			stmt.setString(3, ville);
			stmt.setString(4, mail);
			stmt.setString(5, tel);
			stmt.setString(6, idu);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// SUPPRESSION D'UN ÉTUDIANT EN FONCTION DE SON ID UTILISATEUR
	public void supprEtudiant(String id){
		try {  
		    java.sql.PreparedStatement st = cn.prepareStatement("DELETE FROM utilisateur WHERE IDUtilisateur = ?");
		    st.setString(1, id);
		    st.executeUpdate(); 
		 }
		 catch(Exception e) {
		     System.out.println(e);
		 }
	}
	
	// AJOUT D'UNE ENTREPRISE 
	public void exporterEnt(Entreprise ent){		
		try {
			sql = "INSERT INTO `entreprise` (`nomEntreprise`, `adNumRue`, `adCodePostal`, `adVille`, `adMail`, `numTel`, `sectActv`, `IDUtilisateur_fk`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
		    stmt = cn.prepareStatement(sql);
		    
		    stmt.setString(1, ent.getNomEnt());
		    stmt.setString(2, ent.getRue());
		    stmt.setString(3, ent.getCodePostal());
		    stmt.setString(4, ent.getVille());
		    stmt.setString(5, ent.getMail());
		    stmt.setString(6, ent.getNumTel());
		    stmt.setString(7, ent.getSectActv());
		    stmt.setString(8, idu);
		    
		    stmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// MODIFICATION D'UNE ENTREPRISE
	public void modifEntreprise(String rue, String codePostal, String ville, String telephone, String mail){
		try{
			System.out.println(telephone);
			
			stmt = cn.prepareStatement("UPDATE entreprise SET adNumRue = ?, adCodePostal = ?, adVille = ?, adMail = ?, numTel = ? WHERE IDEntreprise = ?");
			stmt.setString(1, rue);
			stmt.setString(2, codePostal);
			stmt.setString(3, ville);
			stmt.setString(4, mail);
			stmt.setString(5, telephone);
			stmt.setString(6, ide);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// AJOUT D'UNE OFFRE
	public static void exporterOffre(OffreStage offre){				
		try {
			sql = "INSERT INTO `offrestage` (`nomEntreprise`, `domOffre`, `libelle`, `dateDebut`, `duree`, `chemin`, `description`, `IDEntreprise_fk`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

			stmt = cn.prepareStatement(sql);
			
			stmt.setString(1, offre.getNomEnt());
			stmt.setString(2, offre.getDmn());
			stmt.setString(3, offre.getLibl());
			stmt.setString(4, offre.getDateDebut());
			stmt.setInt(5, offre.getDuree());
			stmt.setString(6, offre.getChemin());
			stmt.setString(7, offre.getDesc());
			stmt.setString(8, ide);

			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// AJOUT D'UN UTILISATEUR
	public boolean ajoutUtilisateur(String identifiant, String motPasse, String type){		
		try {
			sql = "INSERT INTO `utilisateur` (`identifiant`, `motPasse`, `type`) "
			+ "VALUES (?, ?, ?)";
			
		    stmt = cn.prepareStatement(sql);
		    
		    stmt.setString(1, identifiant);
		    stmt.setString(2, md5(motPasse));
		    stmt.setString(3, type);
		    
		    try{
		    	stmt.executeUpdate(); 
		    } catch(MySQLIntegrityConstraintViolationException e){
		    	return false;
		    }
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	// SUPPRESSION D'UN UTILISATEUR
	public boolean supprUtilisateur(String identifiant){		
		try {
			sql = "DELETE FROM `utilisateur` WHERE identifiant = ?";
			
		    stmt = cn.prepareStatement(sql);
		   
		    stmt.setString(1, identifiant);
		    
		    try{
		    	stmt.executeUpdate(); 
		    } catch(MySQLIntegrityConstraintViolationException e){
		    	return false;
		    }
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	// RETOURNE LE TYPE DE COMPTE (ETUDIANT - ADMINISTRATEUR - ENTREPRISE)
	public String getType(){
		return type;
	}
	
	// RETOURNE L'ID DE L'ENTREPRISE OU DE L'ETUDIANT
	public String getId(){
		return ide;
	}
	
	// RETOURNE L'ID UTILISATEUR
	public String getIdu(){
		return idu;
	}
	
	// RETOURNE LA VALEUR DE LA CONNEXION (SI UN UTILISATEUR EST CONNECTÉ OU NON)
	public boolean getValCo(){
		return valCo;
	}	
}
