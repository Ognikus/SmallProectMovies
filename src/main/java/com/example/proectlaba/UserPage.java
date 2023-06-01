package com.example.proectlaba;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class UserPage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Pane AllMoviesPane;

    @FXML
    private TableView<Movies> MoviesAllTable;

    @FXML
    private TableColumn<Movies, Integer> MoviesIdColumn;

    @FXML
    private TableColumn<Movies, String> MoviesJanrColumn;

    @FXML
    private TableColumn<Movies, String> MoviesNameColumn;

    @FXML
    private TableColumn<Movies, Integer> MoviesRatingColumn;

    @FXML
    private Button MoviesSearchPageBTN;

    @FXML
    private TableColumn<Movies, Integer> MoviesYearColumn;

    @FXML
    private Button SearchMoviesBTN;

    @FXML
    private TextField SearchMoviesField;

    @FXML
    private Button StartPageBTN;

    @FXML
    private Pane StartPane;

    @FXML
    private Label UserNameLabel;

    @FXML
    private Button ExtBTN;

    DataBaseHandler dbHandler = new DataBaseHandler();
    private final ObservableList<Movies> dtmovies = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        UserNameLabel.setText(LoginController.UserName.getUserName());

        //Кнопки смены панелей
        StartPageBTN.setOnAction(actionEvent -> {
            StartPane.setVisible(true);
            AllMoviesPane.setVisible(false);
        });

        MoviesSearchPageBTN.setOnAction(actionEvent -> {
            StartPane.setVisible(false);
            AllMoviesPane.setVisible(true);
        });

        ExtBTN.setOnAction(actionEvent -> {
            LoginController lg = new LoginController();
            lg.openNewWindow((Stage) ExtBTN.getScene().getWindow(), "LoginPage.fxml");;
        });
        //------------------------------

        SearchMoviesBTN.setOnAction(actionEvent -> {
            if(SearchMoviesField.getText().isEmpty()){
                dtmovies.clear();
                UpdateMoviesTableColumn();
            }
            else {
                String moviesName = SearchMoviesField.getText();
                searchMoviesByName(moviesName);
            }
        });
    }

    private void UpdateMoviesTableColumn(){
        try {
            addInfAboutMovies();
            MoviesIdColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
            MoviesNameColumn.setCellValueFactory(new PropertyValueFactory<>("Name"));
            MoviesYearColumn.setCellValueFactory(new PropertyValueFactory<>("Year"));
            MoviesJanrColumn.setCellValueFactory(new PropertyValueFactory<>("Genre"));
            MoviesRatingColumn.setCellValueFactory(new PropertyValueFactory<>("Score"));
            MoviesAllTable.setItems(dtmovies);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void addInfAboutMovies() throws  SQLException{
        ResultSet movie = dbHandler.getMovies();
        while (movie.next()){
            Movies movies = new Movies(
                    movie.getString(1),
                    movie.getString(2),
                    movie.getString(3),
                    movie.getString(4),
                    movie.getString(5));
            dtmovies.add(movies);
        }
    }

    private void searchMoviesByName(String moviesName) {
        try {
            // Очищаем список игр перед выполнением поиска
            dtmovies.clear();
            ResultSet movie = dbHandler.getMoviesSearch(moviesName);

            // Получаем результаты запроса и добавляем их в список игр
            while (movie.next()) {
                Movies movies = new Movies(
                        movie.getString(1),
                        movie.getString(2),
                        movie.getString(3),
                        movie.getString(4),
                        movie.getString(5)
                );
                dtmovies.add(movies);
            }

            // Обновляем таблицу с найденными играми
            MoviesAllTable.setItems(dtmovies);
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка при выполнении поиска в базе данных", e);
        }
    }

}
