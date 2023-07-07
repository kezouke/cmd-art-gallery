package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.UserLogin;
import db_objects.User;
import representation_instruments.OutputMessage;

import java.io.IOException;
import java.util.Scanner;

public class LoginWindow implements Window {
    private final Scanner scanner;
    private final Firestore db;

    public LoginWindow(Scanner scanner, Firestore db) {
        this.scanner = scanner;
        this.db = db;
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            try {
                new OutputMessage("files/OutputForEnterName").display();
                String name = scanner.next();

                new OutputMessage("files/OutputForEnterPassword").display();
                String password = scanner.next();

                boolean isLogged = new UserLogin(new User(name, password), db).login();
                if (isLogged) {
                    running = false;
                    new OutputMessage("files/OutputForLogin").display();
                    return;
                }

                new OutputMessage("files/OutputForWrongLogin").display();
                String input = scanner.next();
                if (input.equals("register")) {
                    new RegisterWindow(scanner, db).execute();
                    running = false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
