package windows.auth;

import com.google.cloud.firestore.Firestore;
import db_connectors.auth.UserLogin;
import db_objects.User;
import db_objects.UserRole;
import instruments.window_messages.auth.LoginWindowMessage;
import windows.Window;

import java.util.Scanner;

public class LoginWindow implements Window {
    private final Scanner scanner;
    private final Firestore db;
    public User currentUser;

    public LoginWindow(Scanner scanner, Firestore db) {
        this.scanner = scanner;
        this.db = db;
    }

    @Override
    public void execute() {
        boolean running = true;
        LoginWindowMessage messageEngine = new LoginWindowMessage();
        while (running) {
            // enter username
            messageEngine.outputEnterName();
            String name = scanner.next();
            // enter password
            messageEngine.outputForEnterPassword();
            String password = scanner.next();
            // create current user using entered data
            currentUser = new User(name, password);
            // check is such user exists or not
            UserLogin loginEngine = new UserLogin(currentUser, db);
            currentUser.role = loginEngine.obtainUserRole();
            if (currentUser.role != UserRole.UNSIGNED) {
                messageEngine.outputForSuccessLogin();
                return;
            }
            // if such user doesn't exist => wrong data was entered
            // show options
            messageEngine.outputWrongLogin();
            String input = scanner.next();
            if (input.equals("register")) {
                new RegisterWindow(scanner, db).execute();
                running = false;
            } else if (input.equals("continue")) {
                messageEngine.outputForUnsignedOption();
                running = false;
            }
        }
    }
}
