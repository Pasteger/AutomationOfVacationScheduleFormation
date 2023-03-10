package net.pastegram.avsf.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import net.pastegram.avsf.entity.VacationSchedule;
import net.pastegram.avsf.service.WorkspaceService;

public class WorkspaceController extends Controller {
    WorkspaceService service = WorkspaceService.getInstance();

    @FXML
    private TableColumn<VacationSchedule, String> staffTC;

    @FXML
    private TableColumn<VacationSchedule, String> aprilTC;

    @FXML
    private TableColumn<VacationSchedule, String> augustTC;

    @FXML
    private TableColumn<VacationSchedule, String> decemberTC;

    @FXML
    private TableColumn<VacationSchedule, String> februaryTC;

    @FXML
    private TableColumn<VacationSchedule, String> julyTC;

    @FXML
    private TableColumn<VacationSchedule, String> juneTC;

    @FXML
    private TableColumn<VacationSchedule, String> marchTC;

    @FXML
    private TableColumn<VacationSchedule, String> mayTC;

    @FXML
    private TableColumn<VacationSchedule, String> novemberTC;

    @FXML
    private TableColumn<VacationSchedule, String> octoberTC;

    @FXML
    private TableColumn<VacationSchedule, String> septemberTC;

    @FXML
    private TableColumn<VacationSchedule, String> januaryTC;

    @FXML
    private TableView<VacationSchedule> vacationScheduleTableView;

    @FXML
    private Button exitButton;

    @FXML
    private Button newButton;

    @FXML
    private Button previousYearButton;

    @FXML
    private Button nextYearButton;

    @FXML
    private Label yearLabel;

    private int year = 2023;

    @FXML
    void initialize() {
        staffTC.setCellValueFactory(new PropertyValueFactory<>("staff"));
        januaryTC.setCellValueFactory(new PropertyValueFactory<>("january"));
        februaryTC.setCellValueFactory(new PropertyValueFactory<>("february"));
        marchTC.setCellValueFactory(new PropertyValueFactory<>("march"));
        aprilTC.setCellValueFactory(new PropertyValueFactory<>("april"));
        mayTC.setCellValueFactory(new PropertyValueFactory<>("may"));
        juneTC.setCellValueFactory(new PropertyValueFactory<>("june"));
        julyTC.setCellValueFactory(new PropertyValueFactory<>("july"));
        augustTC.setCellValueFactory(new PropertyValueFactory<>("august"));
        septemberTC.setCellValueFactory(new PropertyValueFactory<>("september"));
        octoberTC.setCellValueFactory(new PropertyValueFactory<>("october"));
        novemberTC.setCellValueFactory(new PropertyValueFactory<>("november"));
        decemberTC.setCellValueFactory(new PropertyValueFactory<>("december"));

        yearLabel.setText(String.valueOf(year));

        vacationScheduleTableView.setItems(service.getRequestForTable(String.valueOf(year)));

        previousYearButton.setOnMouseClicked(mouseEvent -> {
            year--;
            yearLabel.setText(String.valueOf(year));
            vacationScheduleTableView.setItems(service.getRequestForTable(String.valueOf(year)));
        });
        nextYearButton.setOnMouseClicked(mouseEvent -> {
            year++;
            yearLabel.setText(String.valueOf(year));
            vacationScheduleTableView.setItems(service.getRequestForTable(String.valueOf(year)));
        });

        newButton.setOnMouseClicked(mouseEvent -> {
            service.generateVacations(String.valueOf(year));
            vacationScheduleTableView.setItems(service.getRequestForTable(String.valueOf(year)));
        });

        exitButton.setOnMouseClicked(mouseEvent -> openOtherWindow("authorization", exitButton));
    }
}
