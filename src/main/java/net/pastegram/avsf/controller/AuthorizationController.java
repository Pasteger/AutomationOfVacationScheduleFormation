package net.pastegram.avsf.controller;

import javafx.scene.control.PasswordField;
import net.pastegram.avsf.service.AuthorizationService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AuthorizationController extends Controller {
    AuthorizationService service = AuthorizationService.getInstance();
    @FXML
    private Label errorLabel;
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private Button registrationButton;

    @FXML
    void initialize() {
        registrationButton.setOnAction(actionEvent -> openOtherWindow(
                "registration", registrationButton));
        passwordPasswordField.setOnAction(actionEvent -> authorization());
    }

    private void authorization() {
        try {
            String response = service.authorization(loginTextField.getText(), passwordPasswordField.getText());
            switch (response) {
                case "login is empty" -> errorLabel.setText("Поле логина пустое");
                case "short login" -> errorLabel.setText("Короткий логин");
                case "password is empty" -> errorLabel.setText("Поле пароля пустое");
                case "short password" -> errorLabel.setText("Короткий пароль");
                case "user not found" -> errorLabel.setText("Пользователь не найден");
                case "incorrect password" -> errorLabel.setText("Неверный пароль");
                case "success" -> openOtherWindow("workspace", passwordPasswordField);
                default -> errorLabel.setText("Ошибка");
            }
        } catch (Exception exception) {
            errorLabel.setText("Ошибка");
        }
    }
}
