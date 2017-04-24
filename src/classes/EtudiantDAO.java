//package classes;
//
//import java.sql.SQLException;
//
//import com.mysql.jdbc.ResultSet;
//import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;
//
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//
//public class EtudiantDAO extends DAO<Etudiant> {
//    private java.sql.PreparedStatement stmt = null;
//    private String sql = null;
//    private ResultSet rst = null;
//
//	public EtudiantDAO(java.sql.Connection connection) {
//		super(connection);
//	}
//
//	public boolean create(Etudiant uti) {		
////		
////		
////		try {
////			sql = "INSERT INTO `utilisateur` (`identifiant`, `motPasse`, `type`) "
////						 + "VALUES (?, ?, ?)";
////			
////			// Etape 3 : Création d'un statement
////		    stmt = this.connect.prepareStatement(sql);
////		    
////		    stmt.setString(1, etu.get);
////		    stmt.setString(2, motPasse);
////		    stmt.setString(3, type);
////		    
////		    try{
////		    	stmt.executeUpdate(); 
////		    } catch(MySQLIntegrityConstraintViolationException e){
////		    	return false;
////		    }
////		} catch (SQLException e) {
////			e.printStackTrace();
////		}
////		
////		return true;
//	}
//
//	public boolean delete(Entreprise ent) {
//		try {  
//			stmt = this.connect.prepareStatement("DELETE FROM utilisateur WHERE IDUtilisateur = ?");
//		    stmt.setString(1, ent.getIDUtilisateur_fk());
//		    
//		    stmt.executeUpdate(); 
//		}
//		catch(Exception e) {
//		    System.out.println(e);
//		    return false;
//		}
//		
//		return true;
//	}
//		
//	public boolean update(Entreprise ent) {
//		try{
//		    stmt = this.connect.prepareStatement("UPDATE entreprise SET adNumRue = ?, adCodePostal = ?, adVille = ?, adNumTel = ?, adMail = ?"
//		    		+ " WHERE IDEntreprise = ?");
//		    stmt.setString(1, ent.getRue());
//		    stmt.setString(2, ent.getCodePostal());
//		    stmt.setString(3, ent.getVille());
//		    stmt.setString(4, ent.getNumTel());
//		    stmt.setString(5, ent.getMail());
//		    stmt.setString(6, ent.getIDEntreprise());
//		    
//		    stmt.executeUpdate(); 
//		} catch (SQLException e) {
//			e.printStackTrace();
//			return false;
//		}
//		
//	    return true;
//	}
//		   
//	public EtudiantDAO find(String id) {
//		String nomCol[] = {"IDEntreprise", "nomEntreprise", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "sectActv", "IDUtilisateur_fk"};
//		Entreprise ent = new Entreprise();      
//		      
//		try {			
//			// Etape 3 : Création d'un statement
//		    stmt = this.connect.prepareStatement("SELECT * FROM entreprise WHERE IDUtilisateur_fk = ?");
//		    stmt.setString(1, id);
//		    
//			// Etape 4 : exécution requête
//			rst = (ResultSet) stmt.executeQuery();
//		    
//			if(rst.first()){
//				// Création de l'entreprise
//				ent = new Entreprise(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
//				rst.getString(nomCol[3]), rst.getString(nomCol[4]), rst.getString(nomCol[5]), rst.getString(nomCol[6]), rst.getString(nomCol[7]), 
//				rst.getString(nomCol[8]));         
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		  
//		return ent;
//	}
//	
//	public ObservableList<EtudiantDAO> findAll() {
//		String nomCol[] = {"IDEntreprise", "nomEntreprise", "adNumRue", "adCodePostal", "adVille", "adMail", "numTel", "sectActv", "IDUtilisateur_fk"};
//		ObservableList<EtudiantDAO> listEnt = FXCollections.observableArrayList();
//
//		try{
//			sql = "SELECT * FROM entreprise";
//			
//			// Etape 3 : Création d'un statement
//		    stmt = this.connect.prepareStatement(sql);
//
//			// Etape 4 : exécution requête
//			rst = (ResultSet) stmt.executeQuery(sql);
//
//			// Si récup données alors étapes 5 (parcours Resultset)
//			while (rst.next()) {
//				listEnt.add(new EtudiantDAO(rst.getString(nomCol[0]), rst.getString(nomCol[1]), rst.getString(nomCol[2]), 
//				rst.getString(nomCol[3]), rst.getString(nomCol[4]), rst.getString(nomCol[5]), rst.getString(nomCol[6]), rst.getString(nomCol[7]), 
//				rst.getString(nomCol[8])));
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		return listEnt;
//	}
//}
