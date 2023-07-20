package instruments.window_messages.show_windows;

import db_objects.UserRole;
import instruments.window_messages.permission_messages.LowPermissionUnsignedMessage;
import instruments.window_messages.WrongCommandMessage;

public class ShowCommentsWindowMessage {
    public void outputMenu(boolean isCommentsEmpty,
                           boolean isHasNext,
                           boolean isHasPrev,
                           UserRole role) {
        if (isCommentsEmpty) {
            String noCommentsMessage = """
                    Unfortunately, there are no comments yet for this picture
                    """;
            System.out.println(noCommentsMessage);
        } else {
            if (isHasNext) {
                String nextCommentsMessage = """
                        if you want to see next comments
                            enter "next"
                        """;
                System.out.println(nextCommentsMessage);
            }
            if (isHasPrev) {
                String prevCommentsMessage = """
                        if you want to see previous comments
                            enter "back"
                        """;
                System.out.println(prevCommentsMessage);
            }
        }
        if (role != UserRole.UNSIGNED) {
            String signedInOption = """
                    ____________________________________
                    + as signed in user:
                    If you want to add new comment to current picture:
                        enter 'add'
                    ____________________________________
                    """;
            System.out.println(signedInOption);
        }
        String returnOptionMessage = """
                If you want to return back:
                    enter 'return'
                """;
        System.out.println(returnOptionMessage);
    }

    public void outputNoNextComments() {
        String noNextCommentsMessage = """
                You have rich end of the list with comments.
                 There is no any comments more :(
                """;
        System.out.println(noNextCommentsMessage);
    }

    public void outputNoPrevComments() {
        String noPrevCommentsMessage = """
                Unfortunately there is no previous comments.
                You already at the beginning of the list.
                """;
        System.out.println(noPrevCommentsMessage);
    }

    public void outputWrongCommandEntered() {
        new WrongCommandMessage().outputMessage();
    }

    public void outputLowPermission() {
        new LowPermissionUnsignedMessage().outputMessage();
    }
}
