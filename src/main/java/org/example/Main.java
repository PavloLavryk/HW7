package org.example;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        DatabaseQueries databaseQueries = new DatabaseQueries();
        try {

            databaseQueries.executeQueries();

        } catch (SQLException e) {
            e.printStackTrace();

        }
    }
}
