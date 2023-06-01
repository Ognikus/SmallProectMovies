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

public class AdminPage {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button AddMoviesBTN;

    @FXML
    private Button AddPageBTN;

    @FXML
    private Button DeletMoviesBTN;

    @FXML
    private TextField IdFieldMovies;


    @FXML
    private TextField JanrMoviesField;

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
    private TableColumn<Movies, Integer> MoviesYearColumn;

    @FXML
    private TextField NameMoviesField;


    @FXML
    private Pane PaneAddMovies;

    @FXML
    private TextField RatingMoviesField;

    @FXML
    private Button RedactMoviesBTN;
    @FXML
    private Button ExtBTN;

    @FXML
    private Button StartPageBTN;

    @FXML
    private Pane StartPane;

    @FXML
    private Label UserNameLabel;

    @FXML
    private TextField YearMoviesField;

    DataBaseHandler dbHandler = new DataBaseHandler();

    private final ObservableList<Movies> dtmovies = FXCollections.observableArrayList();

    @FXML
    void initialize() {

        UpdateMoviesTableColumn();
        UserNameLabel.setText(LoginController.UserName.getUserName());

        //Таблицы события
        MoviesAllTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 1) {
                Movies selectedMovies = MoviesAllTable.getSelectionModel().getSelectedItem();
                if (selectedMovies != null) {
                    // Здесь вы можете установить значения выбранного фильма в текстовые поля
                    IdFieldMovies.setText(String.valueOf((selectedMovies.getId())));
                    NameMoviesField.setText(selectedMovies.getName());
                    YearMoviesField.setText(String.valueOf(selectedMovies.getYear()));
                    JanrMoviesField.setText(selectedMovies.getGenre());
                    RatingMoviesField.setText(String.valueOf(selectedMovies.getScore()));
                }
            }
        });

        //Кнопки смены панелей
        StartPageBTN.setOnAction(actionEvent -> {
            StartPane.setVisible(true);
            PaneAddMovies.setVisible(false);
        });

        AddPageBTN.setOnAction(actionEvent -> {
            StartPane.setVisible(false);
            PaneAddMovies.setVisible(true);
        });

        ExtBTN.setOnAction(actionEvent -> {
            LoginController lg = new LoginController();
            lg.openNewWindow((Stage) ExtBTN.getScene().getWindow(), "LoginPage.fxml");;
        });
        //------------------------------

        //Кнопки с фильмами-----------------------
        AddMoviesBTN.setOnAction(actionEvent -> {
            Movies movies = new Movies("",NameMoviesField.getText(), YearMoviesField.getText(), JanrMoviesField.getText(), RatingMoviesField.getText());
            dtmovies.clear();
            try {
                dbHandler.insertMovies(movies);
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при добавлении данных в базу данных", e);
            } catch (Exception e) {
                throw new RuntimeException("Ошибка при добавлении данных в список", e);
            }
            UpdateMoviesTableColumn();
        });

        RedactMoviesBTN.setOnAction(actionEvent -> {
            Movies selectedMovies = MoviesAllTable.getSelectionModel().getSelectedItem();
            if (selectedMovies != null) {
                // Получите обновленные значения из текстовых полей
                String id = IdFieldMovies.getText();
                String updatedName = NameMoviesField.getText();
                String updatedYear = YearMoviesField.getText();
                String updatedJanr = JanrMoviesField.getText();
                String updatedRating = RatingMoviesField.getText();

                // Обновите выбранную игру
                selectedMovies.setId(Integer.valueOf(id));
                selectedMovies.setName(updatedName);
                selectedMovies.setYear(Integer.valueOf(updatedYear));
                selectedMovies.setGenre(updatedJanr);
                selectedMovies.setScore(Integer.valueOf(updatedRating));


                // Здесь вызовите метод для обновления игры в базе данных
                try {
                    dbHandler.updateMovies(selectedMovies);
                } catch (SQLException e) {
                    throw new RuntimeException("Ошибка при обновлении данных в базе данных", e);
                }

                // Очистите текстовые поля после обновления
                dtmovies.clear();
                IdFieldMovies.clear();
                NameMoviesField.clear();
                YearMoviesField.clear();
                JanrMoviesField.clear();
                RatingMoviesField.clear();
                UpdateMoviesTableColumn();
            } else {
                System.out.println("Фильм не найден");
            }
        });

        DeletMoviesBTN.setOnAction(actionEvent -> {
            try {
                int moviesIdToDelete = Integer.parseInt(IdFieldMovies.getText()); // Идентификатор игры, которую нужно удалить
                dbHandler.deleteMovies(moviesIdToDelete);
                System.out.println("Запись успешно удалена");
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при удалении записи из базы данных", e);
            }
            dtmovies.clear();
            IdFieldMovies.clear();
            NameMoviesField.clear();
            YearMoviesField.clear();
            JanrMoviesField.clear();
            RatingMoviesField.clear();
            UpdateMoviesTableColumn();
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

    private void SetUserNameLabel(){
        UserNameLabel.setText(LoginController.UserName.getUserName());
    }

}
