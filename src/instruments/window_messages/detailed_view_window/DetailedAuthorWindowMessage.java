package instruments.window_messages.detailed_view_window;

import db_objects.UserRole;
import instruments.window_messages.permission_messages.LowPermissionNotAdminMessage;
import instruments.window_messages.WrongCommandMessage;

public class DetailedAuthorWindowMessage {
    public void outputMenu(String detailedInfo,
                         UserRole role) {
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
        String returnBackMessage = """
                If you want to return back:
                    enter 'back'.
                """;
        System.out.println(returnBackMessage);
    }

    public void outputWrongCommandWasEntered() {
        new WrongCommandMessage().outputMessage();
    }

    public void outputLowPermissionMessage() {
        new LowPermissionNotAdminMessage().outputMessage();
    }
}
