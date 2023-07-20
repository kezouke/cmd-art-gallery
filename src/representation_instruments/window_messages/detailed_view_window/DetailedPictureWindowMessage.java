package representation_instruments.window_messages.detailed_view_window;

import db_objects.UserRole;
import representation_instruments.window_messages.permission_messages.LowPermissionNotAdminMessage;
import representation_instruments.window_messages.WrongCommandMessage;

public class DetailedPictureWindowMessage {
    public void outputMenu(String detailedInfo,
                           UserRole role) {
        System.out.println(detailedInfo);
        if (role == UserRole.ADMIN) {
            String adminOptionsMessage = """
                    ____________________________________
                    + as admin:
                    If you want to remove this picture:
                        enter 'remove'
                    ____________________________________
                    """;
            System.out.println(adminOptionsMessage);
        }
        String commonOptionsMessage = """
                If you want to see detailed info about picture's author
                    enter 'author'
                If you want to see picture's commentaries
                    enter 'comments'
                If you want to return back:
                    enter 'back'.
                """;
        System.out.println(commonOptionsMessage);
    }

    public void outputWrongCommandEnteredMessage() {
        new WrongCommandMessage().outputMessage();
    }

    public void outputLowPermissions() {
        new LowPermissionNotAdminMessage().outputMessage();
    }
}
