package classes;

import java.sql.SQLException;
import com.mysql.jdbc.ResultSet;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OffrePostuleeDAO  {
	protected java.sql.Connection connect = null;
    private java.sql.PreparedStatement stmt = null;
    private String sql = null;
    private ResultSet rst = null;

	public OffrePostuleeDAO(java.sql.Connection connection) {
		this.connect = connection;
	}

	public boolean create(Etudiant etu, OffrePostulee offre) {		
		try {			
		    stmt = this.connect.prepareStatement("INSERT INTO offrePostulee (`IDEtudiant_fk`, `IDOffreStage_fk`, `statut`) "
			+ "VALUES (?, ?, ?)");
		    stmt.setString(1, etu.getIDEtudiant());
		    stmt.setString(2, offre.getIDOffreStage());
		    stmt.setString(3, "0");
			
		    stmt.executeUpdate(); 		    
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public boolean delete(OffrePostulee offre) {
		try {  
			stmt = this.connect.prepareStatement("DELETE FROM offrepostulee WHERE IDEtudiant_fk = ? AND IDOffreStage_fk = ?");
		    stmt.setString(1, offre.getIDOffreStage());
		    stmt.setString(2, offre.getIDEtudiant());

		    stmt.executeUpdate(); 
		}
		catch(Exception e) {
		    System.out.println(e);
		    return false;
		}
		
		return true;
	}
		
	public boolean update(OffrePostulee offre) {
		try{
			stmt = this.connect.prepareStatement("UPDATE offrepostulee SET statut = 1 WHERE IDEtudiant_fk = ? AND IDOffreStage_fk = ?");
			stmt.setString(1, offre.getIDEtudiant());
			stmt.setString(2, offre.getIDOffreStage());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    return true;
	}
		   
	public OffrePostulee find(String id) {
		String nomCol[] = {"IDOffreStage", "nomEntreprise", "domOffre", "libelle", "dateDebut", "duree", "chemin", "description", "IDEntreprise_fk"};
		String nomCol2[] = {"IDEtudiant", "nom", "prenom", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "ecole", "IDUtilisateur_fk"};
		String nomCol3 = "statut";
		OffrePostulee offre = new OffrePostulee();      
		      
		try {			
			// Etape 3 : Création d'un statement
		    stmt = this.connect.prepareStatement("SELECT * FROM offreStage WHERE IDOffreStage = ?");
		    stmt.setString(1, id);
		    
			// Etape 4 : exécution requête
			rst = (ResultSet) stmt.executeQuery();
		    
			if(rst.first()){
				// Création de l'offre
				new OffrePostulee(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
				rst.getString(nomCol[3]), rst.getString(nomCol[4]), Integer.valueOf(rst.getInt(nomCol[5])), rst.getString(nomCol[6]), 
				rst.getString(nomCol[7]), rst.getString(nomCol[8]), rst.getString(nomCol2[0]), rst.getString(nomCol2[1]), rst.getString(nomCol2[2]),
				rst.getString(nomCol3));			
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  
		return offre;
	}
	
	public ObservableList<OffrePostulee> findAll(Utilisateur uti) {
		String nomCol[] = {"IDOffreStage", "nomEntreprise", "domOffre", "libelle", "dateDebut", "duree", "chemin", "description", "IDEntreprise_fk"};
		String nomCol2[] = {"IDEtudiant", "nom", "prenom", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "ecole", "IDUtilisateur_fk"};
		String nomCol3 = "statut";
		ObservableList<OffrePostulee> listOffre = FXCollections.observableArrayList();
		
		try{
			if(uti.getType().equals("entreprise")){
				sql = "SELECT o.*, e.*, o2.statut FROM offrestage o INNER JOIN offrepostulee o2 ON o.IDOffreStage = o2.IDOffreStage_fk "
				+ "INNER JOIN etudiant e ON e.IDEtudiant = o2.IDEtudiant_fk WHERE o.IDEntreprise_fk = ?";
				
			    stmt = this.connect.prepareStatement(sql);
			    stmt.setString(1, uti.getIDUtilisateur());
			}
			else if(uti.getType().equals("etudiant")){
				sql = "SELECT o.*, e.*, o2.statut FROM offrestage o INNER JOIN offrepostulee o2 ON o.IDOffreStage = o2.IDOffreStage_fk "
				+ "INNER JOIN etudiant e ON e.IDEtudiant = o2.IDEtudiant_fk AND o2.IDEtudiant_fk = ?";
				
			    stmt = this.connect.prepareStatement(sql);
			    stmt.setString(1, uti.getIDUtilisateur());
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
}
