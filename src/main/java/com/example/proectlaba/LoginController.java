package com.example.proectlaba;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class LoginController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button LoginBTN;

    @FXML
    private Label LoginBtnLabel;

    @FXML
    private TextField LoginFiedlReg;

    @FXML
    private TextField LoginFieldLog;

    @FXML
    private Pane LoginPane;

    @FXML
    private PasswordField PasswordFiedlReg;

    @FXML
    private PasswordField PasswordFieldLog;

    @FXML
    private Pane RegistPane;

    @FXML
    private Button RegistrationBTN;

    @FXML
    private Label RegistrationBtnLabel;

    @FXML
    void initialize() {
        RegistrationBtnLabel.setOnMouseClicked(mouseEvent -> {
            LoginPane.setVisible(false);
            RegistPane.setVisible(true);
        });

        LoginBtnLabel.setOnMouseClicked(mouseEvent -> {
            LoginPane.setVisible(true);
            RegistPane.setVisible(false);
        });

        LoginBTN.setOnAction(actionEvent -> {
            String loginText = LoginFieldLog.getText().trim();
            String passText = PasswordFieldLog.getText().trim();

            if (!loginText.equals("") && !passText.equals("")) {
                if (loginText.equals("admin") && passText.equals("admin")) {
                    UserName.setUserName(loginText);
                    openNewWindow((Stage) LoginBTN.getScene().getWindow(), "AdminPage.fxml");
                } else {
                    try {
                        if (checkUser(loginText, passText)) {
                            loginUser(loginText, passText);
                        } else {
                            System.out.println("Неверный логин или пароль");
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                System.out.println("Логин или пароль пусты!");
            }
        });


        RegistrationBTN.setOnAction(actionEvent -> {
            DataBaseHandler dbHandler = new DataBaseHandler();;
            String LoginText = LoginFiedlReg.getText();
            String password = PasswordFiedlReg.getText();

            Users users = new Users(LoginText, password);
            try {
                dbHandler.insertUser(users);
                openNewWindow((Stage) LoginBTN.getScene().getWindow(), "UserPage.fxml");
                UserName.setUserName(LoginText);
            } catch (SQLException e) {
                throw new RuntimeException("Ошибка при добавлении данных в базу данных", e);
            }
        });

    }

    public void openNewWindow(Stage currentStage, String window) {
        try {
            // Загрузка файла FXML для нового окна
            FXMLLoader loader = new FXMLLoader(getClass().getResource(window));
            Parent root = loader.load();

            // Создание новой сцены и установка корневого узла
            Scene scene = new Scene(root);

            // Создание нового окна
            Stage stage = new Stage();
            stage.setTitle("Название нового окна");
            stage.setScene(scene);

            // Закрытие текущего окна
            currentStage.close();

            // Показать новое окно
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean checkUser(String loginText, String passText) throws SQLException {
        DataBaseHandler dbHandler = new DataBaseHandler();
        ResultSet resultSet = dbHandler.getUser(loginText, passText);
        return resultSet.next(); // Если результат содержит хотя бы одну строку, значит пользователь существует
    }

    private void loginUser(String loginText, String loginPassword) throws SQLException {
        boolean userExists = checkUser(loginText, loginPassword);
        Users users = new Users(loginText, loginPassword);
        users.setName(loginText);
        UserName.setUserName(loginText);

        if (userExists) {
            openNewWindow((Stage) LoginBTN.getScene().getWindow(), "UserPage.fxml");
        } else {
            System.out.println("Неверный логин или пароль");
        }
    }

    public class UserName {
        private static String userName;

        public static String getUserName() {
            return userName;
        }

        public static void setUserName(String name) {
            userName = name;
        }
    }

}
