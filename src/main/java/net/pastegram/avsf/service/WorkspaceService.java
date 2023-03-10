package net.pastegram.avsf.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.pastegram.avsf.entity.Staff;
import net.pastegram.avsf.entity.Vacation;
import net.pastegram.avsf.entity.VacationSchedule;
import net.pastegram.avsf.repository.DatabaseHandler;

import java.util.List;

public class WorkspaceService {
    private static final WorkspaceService WORKSPACE_SERVICE = new WorkspaceService();

    private WorkspaceService() {
    }

    public static WorkspaceService getInstance() {
        return WORKSPACE_SERVICE;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();


    public ObservableList<VacationSchedule> getRequestForTable(String year) {
        ObservableList<VacationSchedule> vacationSchedules = FXCollections.observableArrayList();
        List<Vacation> vacations = databaseHandler.getVacationList(year);

        for (Vacation vacation : vacations) {
            VacationSchedule vacationSchedule = new VacationSchedule();

            vacationSchedule.setStaff(vacation.getStaff().getFullName());

            boolean[] vacationMonth = new boolean[]
                    {false, false, false, false, false, false, false, false, false, false, false, false};

            for (int i = vacation.getStartMonth(); i < vacation.getStaff().getVacation() + vacation.getStartMonth(); i++) {
                int id = i > 11 ? i - 12 : i;
                vacationMonth[id] = true;
            }

            vacationSchedule.setJanuary(vacationMonth[0] ? "+" : "");
            vacationSchedule.setFebruary(vacationMonth[1] ? "+" : "");
            vacationSchedule.setMarch(vacationMonth[2] ? "+" : "");
            vacationSchedule.setApril(vacationMonth[3] ? "+" : "");
            vacationSchedule.setMay(vacationMonth[4] ? "+" : "");
            vacationSchedule.setJune(vacationMonth[5] ? "+" : "");
            vacationSchedule.setJuly(vacationMonth[6] ? "+" : "");
            vacationSchedule.setAugust(vacationMonth[7] ? "+" : "");
            vacationSchedule.setSeptember(vacationMonth[8] ? "+" : "");
            vacationSchedule.setOctober(vacationMonth[9] ? "+" : "");
            vacationSchedule.setNovember(vacationMonth[10] ? "+" : "");
            vacationSchedule.setDecember(vacationMonth[11] ? "+" : "");


            vacationSchedules.add(vacationSchedule);
        }


        return vacationSchedules;
    }

    public void generateVacations(String year) {
        try {
            if (!databaseHandler.getVacationList(year).isEmpty()) {
                return;
            }

            int month = 0;

            try {
                List<Vacation> vacations = databaseHandler.getVacationList(
                        String.valueOf(Integer.parseInt(year) - 1));
                month = vacations.get(0).getStartMonth() + vacations.get(0).getStaff().getVacation();
                if (month > 11) {
                    month -= 12;
                }
            } catch (Exception ignored){}


            long id = databaseHandler.generateNewId("vacation", "vacation_id");

            ObservableList<Vacation> vacationObservableList = FXCollections.observableArrayList();
            ObservableList<Staff> staffObservableList = databaseHandler.getStaffList();
            for (Staff staff : staffObservableList) {
                Vacation vacation = new Vacation();
                vacation.setId(++id);
                vacation.setStaff(staff);
                vacation.setStartMonth(month);
                month += staff.getVacation();
                if (month > 11) {
                    month -= 12;
                }
                vacation.setYear(year);
                vacationObservableList.add(vacation);
            }

            databaseHandler.insertVacation(vacationObservableList);
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
