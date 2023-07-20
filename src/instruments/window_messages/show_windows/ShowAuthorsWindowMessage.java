package instruments.window_messages.show_windows;

import db_objects.UserRole;
import instruments.window_messages.permission_messages.LowPermissionUnsignedMessage;
import instruments.window_messages.WrongCommandMessage;

public class ShowAuthorsWindowMessage {
    public void outputMenu(boolean hasNext, boolean hasPrev, UserRole role) {
        if (hasNext) {
            String showNextAuthorsMessage = """
                    if you want to see next authors
                        enter "next"
                    """;
            System.out.println(showNextAuthorsMessage);
        }
        if (hasPrev) {
            String showPreviousAuthorsMessage = """
                    if you want to see previous authors
                        enter "back"
                    """;
            System.out.println(showPreviousAuthorsMessage);
        }
        if (role != UserRole.UNSIGNED) {
            String addAuthorsMessage = """
                    ____________________________________
                    + as signed in user:
                    If you want to add new author:
                        enter 'add'
                    ____________________________________
                    """;
            System.out.println(addAuthorsMessage);
        }
        String returnOptionMessage = """
                If you want to return to previous page:
                    enter 'return'
                """;
        System.out.println(returnOptionMessage);
    }

    public void outputNoMoreAuthors() {
        String allAuthorsShowedMessage = """
                You have rich end of the list with authors.
                There is no any pictures more :(
                """;
        System.out.println(allAuthorsShowedMessage);
    }

    public void outputNoPrevAuthors() {
        String noPreviousAuthorsMessage = """
                Unfortunately there is no previous authors.
                You already at the beginning of the list.
                """;
        System.out.println(noPreviousAuthorsMessage);
    }

    public void outputSuccessAddedAuthor() {
        String successAddingMessage = """
                You have been successfully add new author!
                """;
        System.out.println(successAddingMessage);
    }

    public void outputLowPermissionMessage() {
        new LowPermissionUnsignedMessage().outputMessage();
    }

    public void outputWrongCommandEntered() {
        new WrongCommandMessage().outputMessage();
    }
}
