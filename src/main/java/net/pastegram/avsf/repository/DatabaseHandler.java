package net.pastegram.avsf.repository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.pastegram.avsf.entity.Staff;
import net.pastegram.avsf.entity.User;
import net.pastegram.avsf.entity.Vacation;

import java.sql.*;

public class DatabaseHandler {
    private static final DatabaseHandler databaseHandler = new DatabaseHandler();

    private DatabaseHandler() {
        String connectionString = "jdbc:postgresql://localhost:5432/avsf";
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            dbConnection = DriverManager.getConnection(connectionString, "postgres", "890123890123");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DatabaseHandler getInstance() {
        return databaseHandler;
    }

    final Connection dbConnection;

    public User authorization(String login, String password) throws Exception {
        String request = "select * from avsf_user where login = ? and password = ?";
        PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (!resultSet.next()) {
            throw new Exception();
        }
        User user = new User();
        user.setId(resultSet.getLong("user_id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));

        return user;
    }

    public String registration(String login, String password) {
        try {
            String request = "insert into avsf_user (user_id, login, password) values(?,?,?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setLong(1, generateNewId("avsf_user", "user_id"));
            preparedStatement.setString(2, login);
            preparedStatement.setString(3, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            return "error";
        }
        return "success";
    }

    public ObservableList<Vacation> getVacationList(String year) {
        ObservableList<Vacation> vacationList = FXCollections.observableArrayList();

        try {
            String request = "select * from vacation where year = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setString(1, year);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vacation vacation = new Vacation();
                vacation.setId(resultSet.getLong("vacation_id"));
                vacation.setStartMonth(resultSet.getInt("start_month"));
                vacation.setStaff(getStaff(resultSet.getLong("staff_id")));
                vacationList.add(vacation);
            }
            return vacationList;
        } catch (SQLException e) {
            return vacationList;
        }
    }

    private Staff getStaff(long id) {
        Staff staff = new Staff();
        try {
            String request = "select * from staff where staff_id = ?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            staff.setId(id);
            staff.setFullName(resultSet.getString("full_name"));
            staff.setVacation(resultSet.getInt("vacation"));

            return staff;
        } catch (SQLException e) {
            return staff;
        }
    }

    public ObservableList<Staff> getStaffList() {
        ObservableList<Staff> staffObservableList = FXCollections.observableArrayList();
        try {
            String request = "select * from staff";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Staff staff = new Staff();
                staff.setId(resultSet.getLong("staff_id"));
                staff.setFullName(resultSet.getString("full_name"));
                staff.setVacation(resultSet.getInt("vacation"));
                staffObservableList.add(staff);
            }
            return staffObservableList;
        } catch (SQLException e) {
            return staffObservableList;
        }
    }

    public long generateNewId(String table, String id) throws SQLException {
        String request = "select " + id + " from " + table;
        PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
        ResultSet resultSet = preparedStatement.executeQuery();
        long newId = 0;
        try {
            while (resultSet.next()) {
                long thisId = Long.parseLong(resultSet.getString(id));
                if (newId < thisId) {
                    newId = thisId;
                }
            }
        } catch (Exception ignored) {
        }
        return ++newId;
    }

    public void insertVacation(ObservableList<Vacation> vacationObservableList) {
        try {
            for (Vacation vacation : vacationObservableList) {
                String request = "insert into vacation" +
                        "(vacation_id, staff_id, start_month, year)" +
                        "values(?,?,?,?)";
                PreparedStatement preparedStatement = dbConnection.prepareStatement(request);
                preparedStatement.setLong(1, vacation.getId());
                preparedStatement.setLong(2, vacation.getStaff().getId());
                preparedStatement.setInt(3, vacation.getStartMonth());
                preparedStatement.setString(4, vacation.getYear());
                preparedStatement.executeUpdate();
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
