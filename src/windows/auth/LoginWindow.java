package windows.auth;

import com.google.cloud.firestore.Firestore;
import db_connectors.auth.UserLogin;
import db_objects.User;
import db_objects.UserRole;
import representation_instruments.work_with_text.OutputMessage;
import windows.Window;

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
        OutputMessage unsignedContinue =
                new OutputMessage("files/OutputForUnsignedLogin");
        while (running) {
            try {
                enterNameMessage.display();
                String name = scanner.next();

                enterPasswordMessage.display();
                String password = scanner.next();

                currentUser = new User(name, password);

                UserLogin loginEngine = new UserLogin(currentUser, db);
                currentUser.role = loginEngine.obtainUserRole();
                if (currentUser.role != UserRole.UNSIGNED) {
                    successMessage.display();
                    return;
                }

                wrongDataMessage.display();
                String input = scanner.next();
                if (input.equals("register")) {
                    new RegisterWindow(scanner, db).execute();
                    running = false;
                } else if (input.equals("continue")) {
                    unsignedContinue.display();
                    running = false;
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
