package classes;

import com.mysql.jdbc.Connection;

import javafx.collections.ObservableList;

public abstract class DAO<T> {
	protected java.sql.Connection connect = null;
	
	public DAO(java.sql.Connection connection){
		this.connect = connection;
	}
		   
		  /**
		  * Méthode de création
		  * @param obj
		  * @return boolean 
		  */
	public abstract boolean create(T obj);

		  /**
		  * Méthode pour effacer
		  * @param obj
		  * @return boolean 
		  */
	public abstract boolean delete(T obj);

		  /**
		  * Méthode de mise à jour
		  * @param obj
		  * @return boolean
		  */
	public abstract boolean update(T obj);

		  /**
		  * Méthode de recherche des informations
		  * @param id
		  * @return T
		  */
	public abstract T find(String id);
	
	public abstract ObservableList<T> findAll();
}