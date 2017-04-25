package classes;

import com.mysql.jdbc.Connection;

import javafx.collections.ObservableList;

public abstract class DAO<T> {
	protected java.sql.Connection connect = null;
	
	public DAO(java.sql.Connection connection){
		this.connect = connection;
	}
	
	// création d'un objet 
	public abstract boolean create(T obj);

	// suppression d'un objet
	public abstract boolean delete(T obj);
	
	// mise à jour d'un objet
	public abstract boolean update(T obj);

	// trouvation d'un objet
	public abstract T find(String id);
	
	// trouvation de tous les objets du type donné
	public abstract ObservableList<T> findAll();
}