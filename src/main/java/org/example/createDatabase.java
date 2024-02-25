package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class createDatabase {
    public static void createTables() {
        Connection connection = Database.getInstance().getConnection();
        PreparedStatement pstmt = null;
        try {
            String createTypeLevel = "CREATE TYPE Level AS ENUM ('Trainee', 'Junior', 'Middle', 'Senior');";
            pstmt = connection.prepareStatement(createTypeLevel);
            pstmt.executeUpdate();

            String createTableWorker = "CREATE TABLE worker(" +
                    "ID SERIAL PRIMARY KEY," +
                    "NAME VARCHAR(1000) NOT NULL CHECK (LENGTH(NAME) >= 2)," +
                    "BIRTHDAY DATE CHECK (EXTRACT(YEAR FROM BIRTHDAY) > 1900)," +
                    "LEVEL Level NOT NULL," +
                    "SALARY INT CHECK (SALARY >= 100 AND SALARY <= 100000)" +
                    ");";
            pstmt = connection.prepareStatement(createTableWorker);
            pstmt.executeUpdate();

            String createTableClient = "CREATE TABLE client(" +
                    "ID SERIAL PRIMARY KEY," +
                    "NAME VARCHAR(1000) NOT NULL CHECK (LENGTH(NAME) >= 2)" +
                    ");";
            pstmt = connection.prepareStatement(createTableClient);
            pstmt.executeUpdate();

            String createTableProject = "CREATE TABLE project(" +
                    "ID SERIAL PRIMARY KEY," +
                    "CLIENT_ID INT REFERENCES client(ID)," +
                    "START_DATE DATE," +
                    "FINISH_DATE DATE CHECK (FINISH_DATE >= START_DATE + INTERVAL '1 month' AND FINISH_DATE <= START_DATE + INTERVAL '100 month')" +
                    ");";
            pstmt = connection.prepareStatement(createTableProject);
            pstmt.executeUpdate();

            String createTableProjectWorker = "create table project_worker(" +
                    "PROJECT_ID INT references project(ID)," +
                    "WORKER_ID INT references worker(ID)," +
                    "primary key (PROJECT_ID, WORKER_ID)" +
                    ");";
            pstmt = connection.prepareStatement(createTableProjectWorker);
            pstmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



