package instruments.window_messages.detailed_view_window;

import db_objects.UserRole;
import instruments.window_messages.permission_messages.LowPermissionNotAdminMessage;
import instruments.window_messages.WrongCommandMessage;

public class DetailedAuthorWindowMessage {
    public void outputMenu(String detailedInfo,
                           UserRole role,
                           boolean isSaved) {
        System.out.println(detailedInfo);
        if (role == UserRole.ADMIN) {
            String removeAuthorMessage = """
                    ____________________________________
                    + as admin:
                    If you want to remove this author:
                        enter 'remove'
                    ____________________________________
                    """;
            System.out.println(removeAuthorMessage);
        }
        if (isSaved && role != UserRole.UNSIGNED) {
            String unSaveOption = """
                    If you want to remove this author from saved list:
                        enter 'unsave'
                    """;
            System.out.print(unSaveOption);
        } else if (!isSaved && role != UserRole.UNSIGNED) {
            String saveOption = """
                    If you want to save this author:
                        enter 'save'
                    """;
            System.out.print(saveOption);
        }

        String returnBackMessage = """
                If you want to return back:
                    enter 'back'.
                """;
        System.out.println(returnBackMessage);
    }

    public void outputWrongCommandWasEntered() {
        new WrongCommandMessage().outputMessage();
    }

    public void outputLowPermissionAdminMessage() {
        new LowPermissionNotAdminMessage().outputMessage();
    }

    public void outputLowPermissionUnsignedMessage() {
        new LowPermissionNotAdminMessage().outputMessage();
    }
}
