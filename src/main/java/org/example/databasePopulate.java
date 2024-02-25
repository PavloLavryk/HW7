package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class databasePopulate {



    public static void executePopulate() throws SQLException {
        seedWorkerTable();
        seedClientTable();
        seedProjectTable();
        seedProjectWorkerTable();
    }
    public static void seedWorkerTable() {
        Connection connection = Database.getInstance().getConnection();
        String insertWorkerData = "INSERT INTO worker (NAME, BIRTHDAY, LEVEL, SALARY) VALUES (?, ?, ?::Level, ?);";

        try (PreparedStatement pstmt = connection.prepareStatement(insertWorkerData)) {
            Object[][] workers = {
                    {"Worker1", "1980-01-01", "Trainee", 800},
                    {"Worker2", "1985-02-02", "Junior", 1200},
                    {"Worker3", "1990-03-03", "Middle", 3000},
                    {"Worker4", "1996-05-02", "Senior", 9000},
                    {"Worker5", "2000-01-14", "Trainee", 100},
                    {"Worker6", "1997-07-11", "Senior", 7500},
                    {"Worker7", "1994-11-11", "Middle", 4000},
                    {"Worker8", "1991-10-04", "Junior", 750},
                    {"Worker9", "1991-10-04", "Junior", 750},
                    {"Worker10", "1993-02-24", "Middle", 2450}
            };
            for (Object[] worker : workers) {
                pstmt.setString(1, (String) worker[0]);
                pstmt.setDate(2, java.sql.Date.valueOf((String) worker[1]));
                pstmt.setString(3, (String) worker[2]);
                pstmt.setInt(4, (Integer) worker[3]);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void seedClientTable() {
        Connection connection = Database.getInstance().getConnection();
        String insertClientData = "INSERT INTO client (NAME) VALUES (?);";

        try (PreparedStatement pstmt = connection.prepareStatement(insertClientData)) {
            String[] clients = {"Client1","Client2","Client3","Client4","Client5","Client6",
                    "Client7","Client8","Client9","Client10"};

            for (String client : clients) {
                pstmt.setString(1, client);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void seedProjectTable() {
        Connection connection = Database.getInstance().getConnection();
        String insertProjectData = "INSERT INTO project (CLIENT_ID, START_DATE, FINISH_DATE) VALUES (?, ?, ?);";

        try (PreparedStatement pstmt = connection.prepareStatement(insertProjectData)) {
            // Додавання даних для кожного проекту
            Object[][] projects = {
                    {1, "2022-01-01", "2023-01-01"},
                    {2, "2023-02-02", "2024-02-02"},
                    {3, "2024-02-02", "2025-02-02"},
                    {4, "2025-02-02", "2026-02-02"},
                    {5, "2026-02-02", "2027-02-02"},
                    {6, "2027-02-02", "2028-02-02"},
                    {7, "2028-02-02", "2029-02-02"},
                    {8, "2029-02-02", "2030-02-02"},
                    {9, "2030-02-02", "2031-02-02"},
                    {10, "2031-02-02", "2032-02-02"}
            };

            for (Object[] project : projects) {
                pstmt.setInt(1, (Integer) project[0]);
                pstmt.setDate(2, java.sql.Date.valueOf((String) project[1]));
                pstmt.setDate(3, java.sql.Date.valueOf((String) project[2]));
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void seedProjectWorkerTable() {
        Connection connection = Database.getInstance().getConnection();
        String insertProjectWorkerData = "INSERT INTO project_worker (PROJECT_ID, WORKER_ID) VALUES (?, ?);";

        try (PreparedStatement pstmt = connection.prepareStatement(insertProjectWorkerData)) {

            int[][] projectWorkers = {
                    {1, 1},
                    {1, 3},
                    {1, 5},
                    {2, 4},
                    {2, 7},
                    {2, 2},
                    {3, 1},
                    {3, 10},
                    {3, 3},
                    {3, 5},
                    {4, 8},
                    {4, 5},
                    {5, 2},
                    {6, 6},
                    {6, 2},
                    {7, 4},
                    {7, 3},
                    {7, 9},
                    {8, 7},
                    {8, 6},
                    {8, 1},
                    {8, 8},
                    {9, 3},
                    {10, 10}

            };

            for (int[] projectWorker : projectWorkers) {
                pstmt.setInt(1, projectWorker[0]);
                pstmt.setInt(2, projectWorker[1]);
                pstmt.addBatch();
            }

            pstmt.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}

