package windows;

import com.google.cloud.firestore.Firestore;
import db_connectors.UserLogin;
import db_connectors.UserRegister;
import db_objects.User;
import representation_instruments.OutputMessage;

import java.io.IOException;
import java.util.Scanner;

public class RegisterWindow implements Window {
    private final Scanner scanner;
    private final Firestore db;

    public RegisterWindow(Scanner scanner, Firestore db) {
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
                String password1 = scanner.next();

                new OutputMessage("files/OutputForEnterPassword").display();
                String password2 = scanner.next();

                if (!password1.equals(password2)) {
                    new OutputMessage("files/OutputForWrongPasswords").display();
                } else {
                    new UserRegister(new User(name, password1), db);
                    new OutputMessage("files/OutputForRegistration").display();
                    running = false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
