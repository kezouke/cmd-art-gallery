package windows.auth;

import com.google.cloud.firestore.Firestore;
import db_objects.User;
import db_objects.UserRole;
import representation_instruments.window_messages.auth.AuthWindowMessage;
import windows.Window;

import java.util.Scanner;

public class AuthWindow implements Window {
    private final Scanner scanner;
    private final Firestore database;
    private final AuthWindowMessage messageEngine;
    public User currentUser;

    public AuthWindow(Scanner scanner, Firestore database) {
        this.scanner = scanner;
        this.database = database;
        this.messageEngine = new AuthWindowMessage();
    }

    @Override
    public void execute() {
        boolean running = true;
        while (running) {
            messageEngine.outputGreeting();
            String input = scanner.next();
            switch (input) {
                case "login" -> {
                    login();
                    running = false;
                }
                case "continue" -> {
                    continueUnsigned();
                    running = false;
                }
                case "register" -> {
                    register();
                    running = false;
                }
                case "close" -> {
                    return;
                }
                default -> messageEngine
                        .outputWrongCommandEntered();
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

    private void continueUnsigned() {
        messageEngine.outputNoteForUnsignedUsers();
        currentUser = new User("unsigned", "");
        currentUser.role = UserRole.UNSIGNED;
    }
}
