package models;

import controllers.DataBaseCustomException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBContactSystem { 

	private static DBContactSystem instanceDB ;
	private final String DB_URL = "jdbc:postgresql://localhost:5432/example";
	private final String DB_USER_NAME = "postgres";
	private final String DB_PASSWORD = "postgres@123";
	private Connection connect;
	private Statement statement;
	private final DBQueries QUERY = new DBQueries();
	

	private DBContactSystem () throws DataBaseCustomException {
		initialize();
	};

	private void initialize () throws DataBaseCustomException {
		try {
			connect = DriverManager.getConnection(DB_URL, DB_USER_NAME, DB_PASSWORD);
			statement = connect.createStatement();
		} catch (SQLException exception) {
			throw new DataBaseCustomException("INITIALIZATION OF DATABASE!!!");
		}
		
	}

	public static final DBContactSystem getDeclaredInstance() throws DataBaseCustomException {
		return instanceDB == null ? instanceDB = new DBContactSystem() : instanceDB;
	}

	public boolean addData(Contacts contact) throws DataBaseCustomException {
		try {
			return statement.executeUpdate(QUERY.addDataQuery(contact.getName(), contact.getNumber())) != 0;
		} catch (SQLException exception) {
			throw new DataBaseCustomException("CONTACT ALREADY EXISTS!!!");
		}
	}

	public boolean modifyData(String name , BigDecimal number , BigDecimal existingNumber) throws DataBaseCustomException {
		try {
			return statement.executeUpdate(QUERY.modifyDataQuery(name, number, existingNumber)) != 0;
		} catch (SQLException exception) {
			throw new DataBaseCustomException("CONTACT NOT FOUND!!!");
		}
	}

	public boolean deleteData(BigDecimal number) throws DataBaseCustomException {
		try {
			return statement.executeUpdate(QUERY.deleteDataQuery(number)) != 0;
			 
		} catch (SQLException exception) {
			throw new DataBaseCustomException("CONTACT NOT FOUND!!!");

		}
	}

	public Contacts selectData(BigDecimal number) throws DataBaseCustomException {
		try {
			ResultSet data = statement.executeQuery(QUERY.selectDataQuery(number));
			data.next();
			return createNewContact(data.getString("name") , data.getBigDecimal("number"));
		} catch (SQLException exception) {
			throw new DataBaseCustomException("RETRIEVING DATA IN DATABASE!!!" + exception.getLocalizedMessage());
		}
	}

	public List<Contacts> selectAllData() throws DataBaseCustomException {
		try {
			ResultSet data = statement.executeQuery(QUERY.selectAllDataQuery());
			List<Contacts> contactsList = new ArrayList<>();
			while(data.next()) {
				contactsList.add(createNewContact(data.getString("name") , data.getBigDecimal("number")));
			}
			return contactsList;
		} catch (SQLException exception) {
			throw new DataBaseCustomException("RETRIEVING DATA IN DATABASE!!!");

		}
	}

    public Contacts createNewContact(String name, BigDecimal number) {
        return new Contacts(number , name);
    }
	
}
