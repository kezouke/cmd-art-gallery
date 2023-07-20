package representation_instruments.window_messages.show_windows;

import db_objects.UserRole;
import representation_instruments.window_messages.permission_messages.LowPermissionUnsignedMessage;
import representation_instruments.window_messages.WrongCommandMessage;

public class ShowPicturesWindowMessage {
    public void outputMenu(boolean isHasNext,
                           boolean isHasPrev,
                           UserRole role) {
        if (isHasNext) {
            String nextPicturesMessage = """
                    if you want to see next pictures
                        enter "next"
                    """;
            System.out.println(nextPicturesMessage);
        }
        if (isHasPrev) {
            String prevPicturesMessage = """
                    if you want to see previous pictures
                        enter "back"
                    """;
            System.out.println(prevPicturesMessage);
        }
        String menu = """
                Menu:
                1) If you want to see detailed info about picture
                    enter a text with following format: picture#
                    where # is Unique Number of picture
                2) If you want to return back to main menu:
                    enter 'return'
                                
                """;
        System.out.println(menu);
        if (role != UserRole.UNSIGNED) {
            String addPicturesOptionMessage = """
                   ____________________________________
                    + for signed in users:
                   If you want to add new picture
                       enter 'add'
                   ____________________________________
                    """;
            System.out.println(addPicturesOptionMessage);
        }
    }

    public void outputNoNextPictures() {
        String noNextPicturesMessage = """
                You have rich end of the list with pictures.
                There is no any pictures more :(
                """;
        System.out.println(noNextPicturesMessage);
    }

    public void outputNoPreviousPictures() {
        String noPrevPicturesMessage = """
                Unfortunately there is no previous pictures.
                You already at the beginning of the list.
                """;
        System.out.println(noPrevPicturesMessage);
    }

    public void outputLowPermissionMessage() {
        new LowPermissionUnsignedMessage().outputMessage();
    }

    public void outputWrongCommandEntered() {
        new WrongCommandMessage().outputMessage();
    }
}
