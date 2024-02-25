package org.example;

import java.sql.*;

class DatabaseQueries {
    private Connection connection;

    public DatabaseQueries() {
        this.connection = Database.getInstance().getConnection();
    }

    public void executeQueries() throws SQLException {
        findMaxSalaryWorker();
        System.out.println("=============");
        findMaxProjectsClient();
        System.out.println("=============");
        findLongestProject();
        System.out.println("=============");
        findYoungestEldestWorkers();
        System.out.println("=============");
        printProjectPrices();
    }

    private void findMaxSalaryWorker() throws SQLException {
        String query = "SELECT NAME, SALARY FROM worker WHERE SALARY = (SELECT MAX(SALARY) FROM worker)";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                int salary = resultSet.getInt("SALARY");
                System.out.println("Name: " + name + ", Salary: " + salary);
            }
        }
    }

    private void findMaxProjectsClient() throws SQLException {
        String query = "SELECT client.ID, client.NAME, COUNT(project.ID)" +
                " AS project_count FROM client JOIN project ON client.ID" +
                " = project.CLIENT_ID GROUP BY client.ID, client.NAME ORDER BY project_count DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                int projectCount = resultSet.getInt("project_count");
                System.out.println("Client ID: " + id + ", Name: " + name + ", Project Count: " + projectCount);
            }
        }
    }

    private void findLongestProject() throws SQLException {
        String query = "SELECT project.ID, project.START_DATE, project.FINISH_DATE," +
                " (project.FINISH_DATE - project.START_DATE)" +
                " AS project_duration FROM project ORDER BY project_duration DESC LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("ID");
                Date startDate = resultSet.getDate("START_DATE");
                Date finishDate = resultSet.getDate("FINISH_DATE");
                int duration = resultSet.getInt("project_duration");
                System.out.println("Project ID: " + id + ", Start Date: " + startDate +
                        ", Finish Date: " + finishDate + ", Duration: " + duration);
            }
        }
    }

    private void findYoungestEldestWorkers() throws SQLException {
        String query = "(SELECT 'YOUNGEST' AS TYPE, NAME, BIRTHDAY FROM worker WHERE BIRTHDAY = (SELECT MAX(BIRTHDAY)" +
                " FROM worker)) UNION ALL (SELECT 'OLDEST' AS TYPE, NAME, BIRTHDAY FROM worker WHERE BIRTHDAY = " +
                "(SELECT MIN(BIRTHDAY) FROM worker))";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String type = resultSet.getString("TYPE");
                String name = resultSet.getString("NAME");
                Date birthday = resultSet.getDate("BIRTHDAY");
                System.out.println(type + ": " + name + ", Birthday: " + birthday);
            }
        }
    }

    private void printProjectPrices() throws SQLException {
        String query = "SELECT project.ID AS NAME, SUM" +
                "(worker.SALARY * (EXTRACT(YEAR FROM AGE(project.FINISH_DATE, project.START_DATE))" +
                " * 12 + EXTRACT(MONTH FROM AGE(project.FINISH_DATE, project.START_DATE))))" +
                " AS PRICE FROM project JOIN project_worker ON project.ID" +
                " = project_worker.PROJECT_ID JOIN worker ON project_worker.WORKER_ID" +
                " = worker.ID GROUP BY project.ID ORDER BY PRICE DESC";
        try (PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                int price = resultSet.getInt("PRICE");
                System.out.println("Project Name: " + name + ", Price: " + price);
            }
        }
    }
}