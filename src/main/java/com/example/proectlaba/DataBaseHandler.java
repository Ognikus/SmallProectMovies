package com.example.proectlaba;
import java.sql.*;

public class DataBaseHandler {
    private Connection connection;
    ResultSet resSet = null;

    public DataBaseHandler() {
        try {
            connection = getDBConnection();
        } catch (SQLException e) {
            // Обработка ошибки подключения к базе данных
            e.printStackTrace();
        }
    }

    public Connection getDBConnection() throws SQLException {
        String connectionStrings = "jdbc:postgresql://localhost:5432/Proect";
        connection = DriverManager.getConnection(connectionStrings, "postgres", "admin");
        return connection;
    }

    //Запросы для пользователей
    public ResultSet getUser(String loginText, String passText) throws SQLException {
        String getUserQuery = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement prSt = getDBConnection().prepareStatement(getUserQuery);
        prSt.setString(1, loginText);
        prSt.setString(2, passText);
        return prSt.executeQuery();
    }


    public boolean insertUser(Users users) throws SQLException {
        String insertUser = "INSERT INTO users(username, password) VALUES (?, ?)";

        try (PreparedStatement prSt = getDBConnection().prepareStatement(insertUser)) {
            prSt.setString(1, users.getName());
            prSt.setString(2, users.getPassword());
            int rowsAffected = prSt.executeUpdate();
            return rowsAffected > 0; // Возвращаем true, если были затронуты строки
        }
    }

    //Запросы для фильмов
    public ResultSet getMovies() throws SQLException {
        String getGames = "SELECT * FROM movies";
        PreparedStatement prST = getDBConnection().prepareStatement(getGames);
        resSet = prST.executeQuery();
        return resSet;
    }

    public void insertMovies(Movies movies) throws SQLException {
        String insertGames = "INSERT INTO movies(title, release_year, genre, rating) VALUES (?, ?, ?, ?)";
        PreparedStatement prSt = connection.prepareStatement(insertGames);
        prSt.setString(1, movies.getName());
        prSt.setInt(2, movies.getYear());
        prSt.setString(3, movies.getGenre());
        prSt.setInt(4, movies.getScore());
        prSt.executeUpdate();
    }

    public void updateMovies(Movies movies) throws SQLException {
        String updateQuery = "UPDATE movies SET title = ?, release_year = ?, genre = ?, rating = ? WHERE id = ?";
        PreparedStatement prSt = getDBConnection().prepareStatement(updateQuery);
        prSt.setString(1, movies.getName());
        prSt.setInt(2, movies.getYear());
        prSt.setString(3, movies.getGenre());
        prSt.setInt(4, movies.getScore());
        prSt.setInt(5, movies.getId());
        prSt.executeUpdate();
    }

    public void deleteMovies(int moviesId) throws SQLException {
        String deleteQuery = "DELETE FROM movies WHERE id = ?";
        PreparedStatement prSt = getDBConnection().prepareStatement(deleteQuery);
        prSt.setInt(1, moviesId);
        prSt.executeUpdate();
    }

    public ResultSet getMoviesSearch(String moviesName) throws SQLException{
        String searchCategory = "SELECT * FROM movies WHERE title = ?";
        PreparedStatement prSt = getDBConnection().prepareStatement(searchCategory);
        prSt.setString(1, moviesName);

        resSet = prSt.executeQuery();
        return resSet;
    }
}
