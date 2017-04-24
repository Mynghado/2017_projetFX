package classes;

import com.mysql.jdbc.Connection;

import javafx.collections.ObservableList;

public abstract class DAO<T> {
	protected java.sql.Connection connect = null;
	
	public DAO(java.sql.Connection connection){
		this.connect = connection;
	}
		   
		  /**
		  * M�thode de cr�ation
		  * @param obj
		  * @return boolean 
		  */
	public abstract boolean create(T obj);

		  /**
		  * M�thode pour effacer
		  * @param obj
		  * @return boolean 
		  */
	public abstract boolean delete(T obj);

		  /**
		  * M�thode de mise � jour
		  * @param obj
		  * @return boolean
		  */
	public abstract boolean update(T obj);

		  /**
		  * M�thode de recherche des informations
		  * @param id
		  * @return T
		  */
	public abstract T find(String id);
	
	public abstract ObservableList<T> findAll();
}