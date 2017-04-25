package classes;

import java.sql.SQLException;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class EtudiantDAO extends DAO<Etudiant> {
    private java.sql.PreparedStatement stmt = null;
    private String sql = null;
    private ResultSet rst = null;

	public EtudiantDAO(java.sql.Connection connection) {
		super(connection);
	}

	public boolean create(Etudiant etu) {		
		try {			
			stmt = this.connect.prepareStatement("INSERT INTO etudiant (`nom`, `prenom`, `adNumRue`, `adCodePostal`, `adVille`, `adMail`, `numTel`, `ecole`, `IDUtilisateur_fk`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			
			stmt.setString(1, etu.getNom());
			stmt.setString(2, etu.getPrenom());
			stmt.setString(3, etu.getRue());
			stmt.setString(4, etu.getCodePostal());
			stmt.setString(5, etu.getVille());
			stmt.setString(6, etu.getMail());
			stmt.setString(7, etu.getNumTel());
			stmt.setString(8, etu.getEcole());
			stmt.setString(9, etu.getIDUtilisateur_fk());
						
		    stmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public boolean delete(Etudiant etu) {
		try {  
			stmt = this.connect.prepareStatement("DELETE FROM etudiant WHERE IDEtudiant = ?");
		    stmt.setString(1, etu.getIDEtudiant());
		    
		    stmt.executeUpdate(); 
		}
		catch(Exception e) {
		    System.out.println(e);
		    return false;
		}
		
		return true;
	}
		
	public boolean update(Etudiant etu) {
		try{
		    stmt = this.connect.prepareStatement("UPDATE etudiant SET adNumRue = ?, adCodePostal = ?, adVille = ?, adNumTel = ?, adMail = ?"
		    		+ " WHERE IDEtudiant = ?");
		    stmt.setString(1, etu.getRue());
		    stmt.setString(2, etu.getCodePostal());
		    stmt.setString(3, etu.getVille());
		    stmt.setString(4, etu.getNumTel());
		    stmt.setString(5, etu.getMail());
		    stmt.setString(6, etu.getIDEtudiant());
		    
		    stmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	    return true;
	}
		   
	public Etudiant find(String id) {   
		String nomCol[] = {"IDEtudiant", "nom", "prenom", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "ecole", "IDUtilisateur_fk"};
		Etudiant etu = new Etudiant();      
		
		try{
			sql = "SELECT * FROM etudiant WHERE IDEtudiant = ?";

		    stmt = this.connect.prepareStatement(sql);
		    stmt.setString(1, id);
			rst = (ResultSet) stmt.executeQuery();
			
			while (rst.next()) {
				etu = new Etudiant(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
				rst.getString(nomCol[3]), rst.getString(nomCol[4]), rst.getString(nomCol[5]), rst.getString(nomCol[6]), 
				rst.getString(nomCol[7]), rst.getString(nomCol[8]), rst.getString(nomCol[9]));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				  
		return etu;
	}
	
	public ObservableList<Etudiant> findAll() {
		String nomCol[] = {"IDEtudiant", "nom", "prenom", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "ecole", "IDUtilisateur_fk"};
		ObservableList<Etudiant> listEtu = FXCollections.observableArrayList();
		
		try{
			sql = "SELECT * FROM etudiant";

		    stmt = this.connect.prepareStatement(sql);

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
}