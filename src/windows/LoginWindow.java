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
    public User currentUser;

    public LoginWindow(Scanner scanner, Firestore db) {
        this.scanner = scanner;
        this.db = db;
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage enterNameMessage =
                new OutputMessage("files/OutputForEnterName");
        OutputMessage enterPasswordMessage =
                new OutputMessage("files/OutputForEnterPassword");
        OutputMessage successMessage =
                new OutputMessage("files/OutputForLogin");
        OutputMessage wrongDataMessage =
                new OutputMessage("files/OutputForWrongLogin");
        while (running) {
            try {
                enterNameMessage.display();
                String name = scanner.next();

                enterPasswordMessage.display();
                String password = scanner.next();

                currentUser = new User(name, password);

                boolean isLogged = new UserLogin(currentUser, db).login();
                if (isLogged) {
                    successMessage.display();
                    return;
                }

                wrongDataMessage.display();
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
