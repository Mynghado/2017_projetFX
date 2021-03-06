package classes;

import java.sql.SQLException;
import com.mysql.jdbc.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EntrepriseDAO extends DAO<Entreprise> {
    private java.sql.PreparedStatement stmt = null;
    private String sql = null;
    private ResultSet rst = null;

	public EntrepriseDAO(java.sql.Connection connection) {
		super(connection);
	}

	public boolean create(Entreprise ent) {		
		try {
			sql = "INSERT INTO `entreprise` (`nomEntreprise`, `adNumRue`, `adCodePostal`, `adVille`, `adMail`, `numTel`, `sectActv`, `IDUtilisateur_fk`) "
						 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
			
			// Etape 3 : Cr�ation d'un statement
		    stmt = this.connect.prepareStatement(sql);
		    
		    stmt.setString(1, ent.getNomEnt());
		    stmt.setString(2, ent.getRue());
		    stmt.setString(3, ent.getCodePostal());
		    stmt.setString(4, ent.getVille());
		    stmt.setString(5, ent.getMail());
		    stmt.setString(6, ent.getNumTel());
		    stmt.setString(7, ent.getSectActv());
		    stmt.setString(8, ent.getIDUtilisateur_fk());
		    
			// Etape 4 : ex�cution requ�te
		    stmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean delete(Entreprise ent) {
		try {  
			stmt = this.connect.prepareStatement("DELETE FROM utilisateur WHERE IDUtilisateur = ?");
		    stmt.setString(1, ent.getIDUtilisateur_fk());
		    
		    stmt.executeUpdate(); 
		}
		catch(Exception e) {
		    System.out.println(e);
		    return false;
		}
		
		return true;
	}
		
	public boolean update(Entreprise ent) {
		try{
		    stmt = this.connect.prepareStatement("UPDATE entreprise SET adNumRue = ?, adCodePostal = ?, adVille = ?, adNumTel = ?, adMail = ?"
		    		+ " WHERE IDEntreprise = ?");
		    stmt.setString(1, ent.getRue());
		    stmt.setString(2, ent.getCodePostal());
		    stmt.setString(3, ent.getVille());
		    stmt.setString(4, ent.getNumTel());
		    stmt.setString(5, ent.getMail());
		    stmt.setString(6, ent.getIDEntreprise());
		    
		    stmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	    return true;
	}
		   
	public Entreprise find(String id) {
		String nomCol[] = {"IDEntreprise", "nomEntreprise", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "sectActv", "IDUtilisateur_fk"};
		Entreprise ent = new Entreprise();      
		      
		try {			
			// Etape 3 : Cr�ation d'un statement
		    stmt = this.connect.prepareStatement("SELECT * FROM entreprise WHERE IDUtilisateur_fk = ?");
		    stmt.setString(1, id);
		    
			// Etape 4 : ex�cution requ�te
			rst = (ResultSet) stmt.executeQuery();
		    
			if(rst.first()){
				// Cr�ation de l'entreprise
				ent = new Entreprise(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
				rst.getString(nomCol[3]), rst.getString(nomCol[4]), rst.getString(nomCol[5]), rst.getString(nomCol[6]), rst.getString(nomCol[7]), 
				rst.getString(nomCol[8]));         
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  
		return ent;
	}
	
	public ObservableList<Entreprise> findAll() {
		String nomCol[] = {"IDEntreprise", "nomEntreprise", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "sectActv", "IDUtilisateur_fk"};
		ObservableList<Entreprise> listEnt = FXCollections.observableArrayList();

		try{
			sql = "SELECT * FROM entreprise";
			
			// Etape 3 : Cr�ation d'un statement
		    stmt = this.connect.prepareStatement(sql);

			// Etape 4 : ex�cution requ�te
			rst = (ResultSet) stmt.executeQuery(sql);

			// Si r�cup donn�es alors �tapes 5 (parcours Resultset)
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
}
