package net.pastegram.avsf.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import net.pastegram.avsf.service.RegistrationService;

public class RegistrationController extends Controller {
    private final RegistrationService service = RegistrationService.getInstance();

    @FXML
    private Button backButton;
    @FXML
    public TextField loginTextField;
    @FXML
    public PasswordField passwordPasswordField;
    @FXML
    public Button registrationButton;
    @FXML
    public Label errorLabel;

    @FXML
    void initialize() {
        backButton.setOnAction(actionEvent -> openOtherWindow("authorization", backButton));
        registrationButton.setOnMouseClicked(mouseEvent -> {
            String response;
            response = service.registration(loginTextField.getText(), passwordPasswordField.getText());

            if (!response.equals("success")) {
                errorLabel.setText(response);
                return;
            }

            openOtherWindow("authorization", registrationButton);
        });
    }
}
