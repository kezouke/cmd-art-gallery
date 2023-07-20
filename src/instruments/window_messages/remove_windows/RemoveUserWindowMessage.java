package instruments.window_messages.remove_windows;

import exceptions.UserRemovedHisSelf;
import instruments.window_messages.WrongCommandMessage;

public class RemoveUserWindowMessage {

    public void outputQuestionIsUserSure() {
        String message = """
                You are going to remove some of the users.
                Are you sure? (yes/no)
                """;
        System.out.println(message);
    }

    public void outputEnterUsername() {
        String message = """
                Please, enter a username of user you want to delete:
                    ~ or enter 'return' to return back
                """;
        System.out.println(message);
    }

    public void outputNoSuchUserExists() {
        String message = """
                Unfortunately, you have entered username,
                which doesn't belong to anyone.
                                
                Please, try again.
                """;
        System.out.println(message);
    }

    public void outputForSuccess(String userRemover,
                                 String userRemoved) {
        String message;
        if (userRemoved.equals(userRemover)) {
            message = """
                    You successfully removed yourself!
                    """;
            System.out.println(message);
            throw new UserRemovedHisSelf();
        } else {
            message = """
                    User was deleted successfully!
                    """;
        }
        System.out.println(message);
    }

    public void outputCannotRemoveAnotherAdmin() {
        String message = "Stop! You cannot remove another admin user!";
        System.out.println(message);
    }


    public void outputWrongCommandEnteredMessage() {
        new WrongCommandMessage().outputMessage();
    }
}
