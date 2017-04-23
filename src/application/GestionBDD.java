package application;
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

public class GestionBDD {
	private static String url = "jdbc:mysql://localhost/bdd_stage";
	private static String login = "root";
	private static String passwd = "";
	private static String sql = null;
	private static java.sql.Connection cn = null;
	private static java.sql.Statement st = null;
    private static java.sql.PreparedStatement stmt = null;
    private static ResultSet rst = null;
	private static ResultSet rs, rs2 = null;
	private static String type, ide, idu;
	
	GestionBDD(java.sql.Connection connection){
		this.cn = connection;
	}
	
	public boolean connexion(String id, String mdp){
		try{
			// Etape 3 : Création d'un statement
			st = cn.createStatement();
		
			String sql = "SELECT IDUtilisateur, identifiant, motPasse, type FROM utilisateur";
		
			// Etape 4 : exécution requête
			rs = (ResultSet) st.executeQuery(sql);
			
			// Si récup données alors étapes 5 (parcours Resultset)
			while (rs.next() && Main.valCo == false) {
				if(id.equals(rs.getString("identifiant")) == true && mdp.equals(rs.getString("motPasse")) == true){
					type = rs.getString("type");
					System.out.println(type);
					idu = rs.getString("IDUtilisateur");
					Main.valCo = true;
					getTableId(rs.getString("IDUtilisateur"));
					return Main.valCo;
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(Main.valCo == false){
				Main.valCo = false;
				type = null;
				id = null;
			}
			return Main.valCo;
		}
	}
	
	public void getTableId(String IDUti){
		try{
			if(type.equals("entreprise")){
				sql = "SELECT e.IDEntreprise FROM entreprise e INNER JOIN utilisateur ON IDUtilisateur_fk = ?";
			    stmt = cn.prepareStatement(sql);
			    stmt.setString(1, IDUti);

				rst = (ResultSet) stmt.executeQuery();
			}
			else{ 
				sql ="SELECT IDEtudiant FROM etudiant INNER JOIN utilisateur ON IDUtilisateur_fk = ?";
			    java.sql.PreparedStatement stmt = cn.prepareStatement(sql);
			    stmt.setString(1, IDUti);
			    
				rst = (ResultSet) stmt.executeQuery();
			}

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
	
	public void deconnexion(){
		Main.valCo = false;
		type = null;
		ide = null;
	}
			
	public void importEnt(){
		String nomCol[] = {"IDEntreprise", "nomEntreprise", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "sectActv", "IDUtilisateur_fk"};
		String sql = null;
	    ResultSet rst = null;

		Main.listeEntreprise.remove(0, Main.listeEntreprise.size());

		try{
			sql = "SELECT * FROM entreprise";
			
			// Etape 3 : Création d'un statement
		    stmt = cn.prepareStatement(sql);

			// Etape 4 : exécution requête
			rst = (ResultSet) stmt.executeQuery(sql);

			// Si récup données alors étapes 5 (parcours Resultset)
			while (rst.next()) {
				Main.listeEntreprise.add(new Entreprise(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
				rst.getString(nomCol[3]), rst.getString(nomCol[4]), rst.getString(nomCol[5]), rst.getString(nomCol[6]), rst.getString(nomCol[7]), 
				rst.getString(nomCol[8])));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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

	public void importOffre(){
		String nomCol[] = {"IDOffreStage", "nomEntreprise", "domOffre", "libelle", "dateDebut", "duree", "chemin", "description", "IDEntreprise_fk", "adVille", "adMail"};

	    Main.listeOffre.remove(0, Main.listeOffre.size());
		
		try{
			sql = "SELECT o.*, e.adVille, e.adMail FROM entreprise e INNER JOIN offrestage o ON e.IDEntreprise = o.IDEntreprise_fk";
			
			// Etape 3 : Création d'un statement
		    stmt = cn.prepareStatement(sql);

			// Etape 4 : exécution requête
			rst = (ResultSet) stmt.executeQuery();

			while (rst.next()) {
				Main.listeOffre.add(new OffreStage(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
				rst.getString(nomCol[3]), rst.getString(nomCol[4]), Integer.valueOf(rst.getInt(nomCol[5])), rst.getString(nomCol[6]), 
				rst.getString(nomCol[7]), rst.getString(nomCol[8]), rst.getString(nomCol[9]), rst.getString(nomCol[10])));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void importOffresPostulees(){
		String nomCol[] = {"IDOffreStage", "nomEntreprise", "domOffre", "libelle", "dateDebut", "duree", "chemin", "description", "IDEntreprise_fk"};
		String nomCol2[] = {"IDEtudiant", "nom", "prenom", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "ecole", "IDUtilisateur_fk"};
		String nomCol3 = "statut";
	    
		Main.listeOffrePostulee.remove(0, Main.listeOffrePostulee.size());
		
		try{
			if(type.equals("entreprise")){
				sql = "SELECT o.*, e.*, o2.statut FROM offrestage o INNER JOIN offrepostulee o2 ON o.IDOffreStage = o2.IDOffreStage_fk "
				+ "INNER JOIN etudiant e ON e.IDEtudiant = o2.IDEtudiant_fk WHERE o.IDEntreprise_fk = ?";
				
			    stmt = cn.prepareStatement(sql);
			    stmt.setString(1, ide);
			}
			else if(type.equals("etudiant")){
				sql = "SELECT o.*, e.*, o2.statut FROM offrestage o INNER JOIN offrepostulee o2 ON o.IDOffreStage = o2.IDOffreStage_fk "
				+ "INNER JOIN etudiant e ON e.IDEtudiant = o2.IDEtudiant_fk AND o2.IDEtudiant_fk = ?";
				
			    stmt = cn.prepareStatement(sql);
			    stmt.setString(1, ide);
			}
			
			// Etape 4 : exécution requête
			rst = (ResultSet) stmt.executeQuery();

			while (rst.next()) {
				Main.listeOffrePostulee.add(new OffrePostulee(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
				rst.getString(nomCol[3]), rst.getString(nomCol[4]), Integer.valueOf(rst.getInt(nomCol[5])), rst.getString(nomCol[6]), 
				rst.getString(nomCol[7]), rst.getString(nomCol[8]), rst.getString(nomCol2[0]), rst.getString(nomCol2[1]), rst.getString(nomCol2[2]),
				rst.getString(nomCol3)));
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public void importEtudiant(){		
		String nomCol[] = {"IDEtudiant", "nom", "prenom", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "ecole", "IDUtilisateur_fk"};

		Main.listeEtudiant.remove(0, Main.listeEtudiant.size());
		
		try{
			// Etape 3 : Création d'un statement
			sql = "SELECT * FROM etudiant";

			// Etape 3 : Création d'un statement
		    stmt = cn.prepareStatement(sql);

			// Etape 4 : exécution requête
			rst = (ResultSet) stmt.executeQuery();
			
			// Si récup données alors étapes 5 (parcours Resultset)
			while (rst.next()) {
				Main.listeEtudiant.add(new Etudiant(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
				rst.getString(nomCol[3]), rst.getString(nomCol[4]), rst.getString(nomCol[5]), rst.getString(nomCol[6]), 
				rst.getString(nomCol[7]), rst.getString(nomCol[8]), rst.getString(nomCol[9])));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
	
	public void modifEtudiant(String nom, String prenom, String rue, String codePostal, String ville, String tel, String mail){
		try{
			stmt = cn.prepareStatement("UPDATE etudiant SET nom = ?, prenom = ?, adNumRue = ?, adCodePostal = ?, adVille = ?, adMail = ?, numTel = ? WHERE IDUtilisateur_fk = ?");
			stmt.setString(1, nom);
			stmt.setString(2, prenom);
			stmt.setString(3, rue);
			stmt.setString(4, codePostal);
			stmt.setString(5, ville);
			stmt.setString(6, mail);
			stmt.setString(7, tel);
			stmt.setString(8, idu);
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

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

	public LinkedList<String> importDom(){
		LinkedList<String> listDom = new LinkedList();
		
		try{
			sql = "SELECT DISTINCT sectActv FROM entreprise";

			// Etape 3 : Création d'un statement
		    stmt = cn.prepareStatement(sql);

			// Etape 4 : exécution requête
			rst = (ResultSet) stmt.executeQuery();

			// Si récup données alors étapes 5 (parcours Resultset)

			while (rst.next()) {
				listDom.add(rst.getString("sectActv"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return listDom;		
	}
	
	public void exporterEnt(String nomEnt, String rue, String codePostal, String ville, String mail, String numTel, String sectActv){		
		try {
			//String sql = "INSERT INTO `entreprise` (`nomEntreprise`, `adNumRue`, `adCodePostal`, `adVille`, `adMail`, `numTel`, `sectActv`) VALUES ('" + "tuc" + "','" + "tuc" + "','" + "tuc" + "','" + "tuc" + "','" + "tuc" + "','" + "tuc" + "','" + "tuc" + "')";
			sql = "INSERT INTO `entreprise` (`nomEntreprise`, `adNumRue`, `adCodePostal`, `adVille`, `adMail`, `numTel`, `sectActv`, `IDUtilisateur_fk`) "
						 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
			// Etape 3 : Création d'un statement
		    stmt = cn.prepareStatement(sql);
		    
		    stmt.setString(1, nomEnt);
		    stmt.setString(2, rue);
		    stmt.setString(3, codePostal);
		    stmt.setString(4, ville);
		    stmt.setString(5, mail);
		    stmt.setString(6, numTel);
		    stmt.setString(7, sectActv);
		    stmt.setString(8, idu);
		    
			// Etape 4 : exécution requête
		    stmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
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
	public static void exporterOffre(String nomEnt, String dmn, String libl, String dateDebut, Integer duree, String chemin, String desc){				
		try {
			// Etape 3 : Création d'un statement
			st = (Statement) cn.createStatement();

			//String sql = "INSERT INTO `entreprise` (`nomEntreprise`, `adNumRue`, `adCodePostal`, `adVille`, `adMail`, `numTel`, `sectActv`) VALUES ('" + "tuc" + "','" + "tuc" + "','" + "tuc" + "','" + "tuc" + "','" + "tuc" + "','" + "tuc" + "','" + "tuc" + "')";
			String sql = "INSERT INTO `offreStage` (`nomEntreprise`, `domOffre`, `libelle`, `dateDebut`, `duree`, `chemin`, `description`) "
						 + "VALUES ('" + nomEnt + "', '" + dmn + "', '" + libl + "', '" + dateDebut + "', '" + duree + "', '" + chemin + "', + '" + desc + "')";

			// Etape 4 : exécution requête
			st.executeUpdate(sql);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public boolean ajoutUtilisateur(String identifiant, String motPasse, String type){		
		try {
			sql = "INSERT INTO `utilisateur` (`identifiant`, `motPasse`, `type`) "
						 + "VALUES (?, ?, ?)";
			
			// Etape 3 : Création d'un statement
		    stmt = cn.prepareStatement(sql);
		    
		    stmt.setString(1, identifiant);
		    stmt.setString(2, motPasse);
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
	
	public boolean supprUtilisateur(String identifiant){		
		try {
			sql = "DELETE FROM `utilisateur` WHERE identifiant = ?";
			
			// Etape 3 : Création d'un statement
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
	
	public String getType(){
		return type;
	}
	
	public String getId(){
		return ide;
	}
}
