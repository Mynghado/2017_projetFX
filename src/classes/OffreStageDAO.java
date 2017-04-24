package classes;

import java.sql.SQLException;
import com.mysql.jdbc.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OffreStageDAO extends DAO<OffreStage> {
    private java.sql.PreparedStatement stmt = null;
    private String sql = null;
    private ResultSet rst = null;

	public OffreStageDAO(java.sql.Connection connection) {
		super(connection);
	}

	public boolean create(OffreStage offre) {		
		try {			
			// Etape 3 : Création d'un statement
		    stmt = this.connect.prepareStatement("INSERT INTO `offrestage` (`nomEntreprise`, `domOffre`, `libelle`, `dateDebut`, `duree`, `chemin`, `description`, `IDEntreprise_fk`) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		    
		    stmt.setString(1, offre.getNomEnt());
		    stmt.setString(2, offre.getDmn());
		    stmt.setString(3, offre.getLibl());
		    stmt.setString(4, offre.getDateDebut());
		    stmt.setString(5, offre.getDuree().toString());
		    stmt.setString(6, offre.getChemin());
		    stmt.setString(7, offre.getDesc());
		    stmt.setString(8, offre.getIDEntreprise_fk());
		    
			// Etape 4 : exécution requête
		    stmt.executeUpdate(); 
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean delete(OffreStage offre) {
		try {  
			stmt = this.connect.prepareStatement("DELETE FROM offrestage WHERE IDOffreStage = ?");
		    stmt.setString(1, offre.getIDOffreStage());
		    
		    stmt.executeUpdate(); 
		}
		catch(Exception e) {
		    System.out.println(e);
		    return false;
		}
		
		return true;
	}
		
	public boolean update(OffreStage offre) {
//		try{
//		    stmt = this.connect.prepareStatement("UPDATE offreStage SET adNumRue = ?, adCodePostal = ?, adVille = ?, adNumTel = ?, adMail = ?"
//		    + " WHERE IDEntreprise = ?");
//		    stmt.setString(1, offre.getRue());
//		    stmt.setString(2, offre.getCodePostal());
//		    stmt.setString(3, offre.getVille());
//		    stmt.setString(4, offre.getNumTel());
//		    stmt.setString(5, offre.getMail());
//		    stmt.setString(6, offre.getIDEntreprise());
		    
//		    stmt.executeUpdate(); 
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
//		
	    return true;
	}
		   
	public OffreStage find(String id) {
		String nomCol[] = {"IDOffreStage", "nomEntreprise", "domOffre", "libelle", "dateDebut", "duree", "chemin", "description", "IDEntreprise_fk"};
		OffreStage offre = new OffreStage();      
		      
		try {			
			// Etape 3 : Création d'un statement
		    stmt = this.connect.prepareStatement("SELECT * FROM offreStage WHERE IDOffreStage = ?");
		    stmt.setString(1, id);
		    
			// Etape 4 : exécution requête
			rst = (ResultSet) stmt.executeQuery();
		    
			if(rst.first()){
				// Création de l'offre
				offre = new OffreStage(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
				rst.getString(nomCol[3]), rst.getString(nomCol[4]), Integer.valueOf(rst.getInt(nomCol[5])), rst.getString(nomCol[6]), rst.getString(nomCol[7]), 
				rst.getString(nomCol[8]));         
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		  
		return offre;
	}
	
	public ObservableList<OffreStage> findAll() {
		String nomCol[] = {"IDOffreStage", "nomEntreprise", "domOffre", "libelle", "dateDebut", "duree", "chemin", "description", "IDEntreprise_fk"};
		ObservableList<OffreStage> listOffre = FXCollections.observableArrayList();

		try{
			sql = "SELECT * FROM offreStage";
			
			// Etape 3 : Création d'un statement
		    stmt = this.connect.prepareStatement(sql);

			// Etape 4 : exécution requête
			rst = (ResultSet) stmt.executeQuery(sql);

			// Si récup données alors étapes 5 (parcours Resultset)
			while (rst.next()) {
				listOffre.add(new OffreStage(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
				rst.getString(nomCol[3]), rst.getString(nomCol[4]), Integer.valueOf(rst.getInt(nomCol[5])), rst.getString(nomCol[6]), rst.getString(nomCol[7]), 
				rst.getString(nomCol[8])));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return listOffre;
	}
}
