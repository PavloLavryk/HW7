package org.example;

import java.sql.SQLException;

import static org.example.createDatabase.createTables;
import static org.example.databasePopulate.executePopulate;


public class Main {
    public static void main(String[] args) throws SQLException {
        createTables();
        executePopulate();
        DatabaseQueries databaseQueries = new DatabaseQueries();
            databaseQueries.executeQueries();


    }


}
