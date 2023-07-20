package windows.auth;

import com.google.cloud.firestore.Firestore;
import db_connectors.auth.UserExistenceCheck;
import db_connectors.auth.UserRegister;
import db_objects.User;
import db_objects.UserRole;
import instruments.auth.PasswordCheck;
import instruments.window_messages.auth.RegisterWindowMessage;
import windows.Window;

import java.util.Scanner;

public class RegisterWindow implements Window {
    private final Scanner scanner;
    private final Firestore db;
    public User currentUser;

    public RegisterWindow(Scanner scanner, Firestore db) {
        this.scanner = scanner;
        this.db = db;
    }

    @Override
    public void execute() {
        boolean running = true;
        RegisterWindowMessage messageEngine =
                new RegisterWindowMessage();
        while (running) {
            // ask to enter name
            messageEngine.outputForEnterName();
            String name = scanner.next();
            // ask to enter password
            messageEngine.outputForEnterPassword();
            String password1 = scanner.next();
            // ask to enter password again
            messageEngine.outputForEnterPasswordAgain();
            String password2 = scanner.next();
            // check are entered passwords the same
            if (new UserExistenceCheck(db)
                    .isExist(name)) {
                messageEngine.outputSuchUserNameExists();
            } else if (!password1.equals(password2)) {
                messageEngine
                        .outputForNotTheSameEnteredPasswords();
            } else if (!new PasswordCheck()
                    .isPasswordStrong(password1)) {
                // check requirements on password complexity
                messageEngine.outputForWeekPassword();
            } else {
                currentUser = new User(name, password1);
                currentUser.role = UserRole.SIGNED;
                new UserRegister(currentUser, db);
                messageEngine.outputForSuccessRegistration();
                running = false;
            }
        }
    }
}
