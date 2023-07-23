package instruments.window_messages.detailed_view_window;

import db_objects.UserRole;
import instruments.window_messages.permission_messages.LowPermissionNotAdminMessage;
import instruments.window_messages.WrongCommandMessage;
import instruments.window_messages.permission_messages.LowPermissionUnsignedMessage;

public class DetailedPictureWindowMessage {
    public void outputMenu(String detailedInfo,
                           UserRole role,
                           boolean isSaved) {
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
                If you want to open picture:
                    enter 'open'
                """;
        System.out.print(commonOptionsMessage);
        if (isSaved && role != UserRole.UNSIGNED) {
            String unSaveOption = """
                    If you want to remove this picture from saved list:
                        enter 'unsave'
                    """;
            System.out.print(unSaveOption);
        } else if (!isSaved && role != UserRole.UNSIGNED) {
            String saveOption = """
                    If you want to save this picture:
                        enter 'save'
                    """;
            System.out.print(saveOption);
        }
        String returnOption = """
                If you want to return back:
                    enter 'back'.
                """;
        System.out.print(returnOption);
    }

    public void outputWrongCommandEnteredMessage() {
        new WrongCommandMessage().outputMessage();
    }

    public void outputLowPermissionsAdmin() {
        new LowPermissionNotAdminMessage().outputMessage();
    }

    public void outputLowPermissionsUnsigned() {
        new LowPermissionUnsignedMessage().outputMessage();
    }

    public void outputWrongURL() {
        String message = """
                Unfortunately, url of this picture is wrong :(
                """;
        System.out.print(message);
    }
}
