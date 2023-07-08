package windows;

import com.google.cloud.firestore.Firestore;
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
        OutputMessage enterNameMessage =
                new OutputMessage("files/OutputForEnterName");
        OutputMessage enterPasswordMessage =
                new OutputMessage("files/OutputForEnterPassword");
        OutputMessage wrongPasswordMessage =
                new OutputMessage("files/OutputForWrongPasswords");
        OutputMessage successMessage =
                new OutputMessage("files/OutputForRegistration");
        while (running) {
            try {
                enterNameMessage.display();
                String name = scanner.next();

                enterPasswordMessage.display();
                String password1 = scanner.next();

                enterPasswordMessage.display();
                String password2 = scanner.next();

                if (!password1.equals(password2)) {
                    wrongPasswordMessage.display();
                } else {
                    new UserRegister(new User(name, password1), db);
                    successMessage.display();
                    running = false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
