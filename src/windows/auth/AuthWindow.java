package windows.auth;

import com.google.cloud.firestore.Firestore;
import db_objects.User;
import db_objects.UserRole;
import representation_instruments.work_with_text.OutputMessage;
import windows.Window;

import java.io.IOException;
import java.util.Scanner;

public class AuthWindow implements Window {
    private final Scanner scanner;
    private final Firestore database;
    public User currentUser;

    public AuthWindow(Scanner scanner, Firestore database) {
        this.scanner = scanner;
        this.database = database;
    }

    @Override
    public void execute() {
        boolean running = true;
        OutputMessage authMessage =
                new OutputMessage("files/OutputForAuthPage");
        OutputMessage errorMessage =
                new OutputMessage("files/OutputForError");
        OutputMessage unsignedContinueMessage =
                new OutputMessage("files/OutputForUnsignedLogin");
        while (running) {
            try {
                authMessage.display();
                String input = scanner.next();
                switch (input) {
                    case "login" -> {
                        login();
                        running = false;
                    }
                    case "continue" -> {
                        continueUnsigned(unsignedContinueMessage);
                        running = false;
                    }
                    case "register" -> {
                        register();
                        running = false;
                    }
                    case "close" -> {
                        return;
                    }
                    default -> errorMessage.display();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void login() {
        LoginWindow loginWindow = new LoginWindow(
                scanner, database
        );
        loginWindow.execute();
        currentUser = loginWindow.currentUser;
    }

    private void register() {
        RegisterWindow registerWindow = new RegisterWindow(
                scanner,
                database
        );
        registerWindow.execute();
        currentUser = registerWindow.currentUser;
    }

    private void continueUnsigned(OutputMessage message) throws IOException {
        message.display();
        currentUser = new User("unsigned", "");
        currentUser.role = UserRole.UNSIGNED;
    }
}
