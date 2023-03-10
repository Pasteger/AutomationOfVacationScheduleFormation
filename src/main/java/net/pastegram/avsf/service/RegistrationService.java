package net.pastegram.avsf.service;

import net.pastegram.avsf.repository.DatabaseHandler;

public class RegistrationService {
    private static final RegistrationService REGISTRATION_SERVICE = new RegistrationService();

    private RegistrationService() {
    }

    public static RegistrationService getInstance() {
        return REGISTRATION_SERVICE;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    public String registration(String login, String password) {
        if (login.equals(""))
            return "login is empty";
        if (login.length() < 3)
            return "short login";
        if (password.equals(""))
            return "password is empty";
        if (password.length() < 3)
            return "short password";

        return databaseHandler.registration(login, password);
    }
}
