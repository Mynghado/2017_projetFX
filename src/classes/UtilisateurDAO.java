package classes;

import java.sql.SQLException;

import com.mysql.jdbc.ResultSet;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import application.Main;

public class UtilisateurDAO {
	protected java.sql.Connection connect = null;
    private java.sql.PreparedStatement stmt = null;
	private String sql, type, ide = null;
    private ResultSet rst = null;
    private boolean valCo = false;

	public UtilisateurDAO(java.sql.Connection connection) {
		this.connect = connection;
		valCo = false;
	}
	
	public boolean create(Utilisateur uti) {		
		try {
			// Etape 3 : Création d'un statement
		    stmt = this.connect.prepareStatement("INSERT INTO `utilisateur` (`identifiant`, `motPasse`, `type`) "
			+ "VALUES (?, ?, ?)");
		    
		    stmt.setString(1, uti.getIdentifiant());
		    stmt.setString(2, uti.getMotPasse());
		    stmt.setString(3, uti.getType());
		    
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
	
	public boolean authentification(String identifiant, String motPasse){
		try{
			// Etape 3 : Création d'un statement
		    stmt = this.connect.prepareStatement("SELECT * FROM utilisateur WHERE identifiant = ?");
		    System.out.println("auth : id : " + identifiant + " mdp : " + motPasse);
			stmt.setString(1, identifiant);
			//stmt.setString(2, motPasse);
			
			// Etape 4 : exécution requête
			rst = (ResultSet) stmt.executeQuery();
			
			// Si récup données alors étapes 5 (parcours Resultset)
			if(rst.first()) {
				if(rst.getString("motPasse").equals(motPasse)){
					System.out.println("oui");
//					ConnectionBDD.setTypeCo(rst.getString("type"));
					getTableID(rst.getString("IDUtilisateur"));
					valCo = true;
					
					return valCo;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(valCo == false){
				return valCo;
			}
		}
		
		return valCo;
	}
	
	public void deconnexion(){
		valCo = false;
//		ConnectionBDD.setTypeCo(null);
		ide = null;
	}
	
	public String getTableID(String IDUti){
		try{
			if(type.equals("entreprise")){
			    stmt = this.connect.prepareStatement("SELECT e.IDEntreprise FROM entreprise e INNER JOIN utilisateur ON IDUtilisateur_fk = ?");
			    stmt.setString(1, IDUti);

				rst = (ResultSet) stmt.executeQuery();
			}
			else if(type.equals("etudiant")){ 
				stmt = this.connect.prepareStatement("SELECT IDEtudiant FROM etudiant INNER JOIN utilisateur ON IDUtilisateur_fk = ?");
			    stmt.setString(1, IDUti);
			    
				rst = (ResultSet) stmt.executeQuery();
			}

			if(rst.first()) {
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
		
		return ide;
	}
}
