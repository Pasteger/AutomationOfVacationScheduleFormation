package net.pastegram.avsf.service;

import net.pastegram.avsf.entity.User;
import net.pastegram.avsf.repository.DatabaseHandler;

public class AuthorizationService {
    private static final AuthorizationService AUTHORIZATION_SERVICE = new AuthorizationService();

    private AuthorizationService() {
    }

    public static AuthorizationService getInstance() {
        return AUTHORIZATION_SERVICE;
    }

    private final DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    public String authorization(String login, String password) {
        if (login.equals(""))
            return "login is empty";
        if (login.length() < 3)
            return "short login";
        if (password.equals(""))
            return "password is empty";
        if (password.length() < 3)
            return "short password";

        try {
            databaseHandler.authorization(login, password);
            return "success";
        } catch (Exception exception) {
            return exception.getMessage();
        }
    }
}
