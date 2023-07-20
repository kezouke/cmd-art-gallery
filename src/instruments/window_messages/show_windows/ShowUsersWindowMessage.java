package instruments.window_messages.show_windows;

import instruments.window_messages.WrongCommandMessage;

public class ShowUsersWindowMessage {
    public void outputMenu(boolean isHasNext,
                           boolean isHasPrev) {
        if (isHasNext) {
            String nextCommentsMessage = """
                    if you want to see next users
                        enter "next"
                    """;
            System.out.println(nextCommentsMessage);
        }
        if (isHasPrev) {
            String prevCommentsMessage = """
                    if you want to see previous users
                        enter "back"
                    """;
            System.out.println(prevCommentsMessage);
        }

        String returnOptionMessage = """
                If you want to change user's role:
                    enter 'change-role'
                If you want to remove some user:
                    enter 'remove-user'
                If you want to return back:
                    enter 'return'
                """;
        System.out.println(returnOptionMessage);
    }

    public void outputNoNextUsers() {
        String noNextCommentsMessage = """
                You have rich end of the list with comments.
                 There is no any comments more :(
                """;
        System.out.println(noNextCommentsMessage);
    }

    public void outputNoPrevUsers() {
        String noPrevCommentsMessage = """
                Unfortunately there is no previous comments.
                You already at the beginning of the list.
                """;
        System.out.println(noPrevCommentsMessage);
    }

    public void outputWrongCommandEntered() {
        new WrongCommandMessage().outputMessage();
    }
}
