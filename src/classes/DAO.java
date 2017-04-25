package classes;

import com.mysql.jdbc.Connection;

import javafx.collections.ObservableList;

public abstract class DAO<T> {
	protected java.sql.Connection connect = null;
	
	public DAO(java.sql.Connection connection){
		this.connect = connection;
	}
	
	// cr�ation d'un objet 
	public abstract boolean create(T obj);

	// suppression d'un objet
	public abstract boolean delete(T obj);
	
	// mise � jour d'un objet
	public abstract boolean update(T obj);

	// trouvation d'un objet
	public abstract T find(String id);
	
	// trouvation de tous les objets du type donn�
	public abstract ObservableList<T> findAll();
}