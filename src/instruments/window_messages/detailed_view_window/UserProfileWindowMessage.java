package instruments.window_messages.detailed_view_window;

import instruments.window_messages.WrongCommandMessage;

public class UserProfileWindowMessage {
    public void outputProfile(String detailedUserInfo,
                              boolean isItCurrentUser) {
        System.out.println(detailedUserInfo);
        String menuMessage = """
                If you want to open profile picture:
                    enter 'open'
                """;
        System.out.print(menuMessage);
        if (isItCurrentUser) {
            String updateProfileOptionsMessage = """
                    If you want to update your current profile data:
                        enter 'update'
                    """;
            System.out.println(updateProfileOptionsMessage);
        }
        String returnOption = """
                If you want to return to the previous window:
                    enter 'return'
                """;
        System.out.print(returnOption);
    }

    public void outputWrongCommandEntered() {
        new WrongCommandMessage().outputMessage();
    }

    public void outputNotAURL() {
        String message = """
                At this moment you have entered wrong url as your profile picture link.
                Try to change it please!
                """;
        System.out.print(message);
    }

    public void outputCannotUpdateAnotherAccount() {
        String message = """
                Stop! You cannot update another account!
                """;
        System.out.println(message);
    }
}
