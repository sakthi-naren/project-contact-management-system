package models;

import java.math.BigDecimal;

public class DBQueries {
    
    private final String CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS contacts (name VARCHAR(50) NOT NULL, number integer UNIQUE NOT NULL PRIMARY KEY);";
    

    public String createTableQuery() {
        return CREATE_TABLE_QUERY;
    }

    public String addDataQuery (String name , BigDecimal number) {
        return "INSERT INTO contacts (name,number) VALUES ('" + name + "' , " + number + ");";
    }

    public String deleteDataQuery (BigDecimal number) {
        return "DELETE FROM contacts WHERE number = '" + number + "';";
    }

    public String modifyDataQuery (String name , BigDecimal number , BigDecimal existingNumber) {
        return "UPDATE contacts SET name = '"+ name + "' , number = " + number + " WHERE number = " + existingNumber + ";";
    }

    public String selectDataQuery (BigDecimal number) {
        return "SELECT * FROM contacts  WHERE number =  " + number + ";";
    }

    public String selectDataQuery (String name) {
        return "SELECT * FROM contacts  WHERE name = " + name + ";";
    }

    public String selectAllDataQuery () {
        return "SELECT * FROM contacts;";
    }


    

}
