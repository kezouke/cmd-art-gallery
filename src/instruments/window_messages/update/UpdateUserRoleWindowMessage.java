package instruments.window_messages.update;

import db_objects.UserRole;
import instruments.window_messages.WrongCommandMessage;
import instruments.window_messages.remove_windows.RemoveUserWindowMessage;

public class UpdateUserRoleWindowMessage {
    public void outputIsUserSure() {
        String message = """
                You are going to change user's role.
                Are you sure? (yes/no)
                """;
        System.out.println(message);
    }

    public void outputNoSuchUserExists() {
        new RemoveUserWindowMessage().outputNoSuchUserExists();
    }

    public void outputEnterUsername() {
        String message = """
                Please, enter a username of user you want to update:
                    ~ or enter 'return' to return back
                """;
        System.out.println(message);
    }

    public void outputForSuccess() {
        String message = "You successfully updated role of user!";
        System.out.println(message);
    }
    public void outputNewRole() {
        String message = """
                Please, enter a new role of user
                    It can be
                        - 'admin'
                        - 'signed'
                        - unsigned'
                New role:\s""";
        System.out.print(message);
    }

    public void outputCurrentRole(UserRole role) {
        String message = "Current role of user is: ";
        switch (role) {
            case ADMIN -> System.out.println(message + "admin");
            case SIGNED -> System.out.println(message + "signed");
            default -> System.out.println(message + "unsigned");
        }
    }

    public void outputCannotUpdateAnotherAdmin() {
        String message = """
                Sorry, but you can update role of another admin!
                """;
        System.out.println(message);
    }

    public void outputForWrongCommand() {
        new WrongCommandMessage().outputMessage();
    }
}
