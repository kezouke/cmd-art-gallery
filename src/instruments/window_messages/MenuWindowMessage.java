package instruments.window_messages;

import db_objects.UserRole;
import instruments.window_messages.permission_messages.LowPermissionNotAdminMessage;
import instruments.window_messages.permission_messages.LowPermissionUnsignedMessage;

public class MenuWindowMessage {
    private final String greetingMessage;
    private final String logoutSuccessMessage;
    private final WrongCommandMessage wrongCommandMessage;

    public MenuWindowMessage() {
        this.greetingMessage = """
                _____________________________________________________
                ////////////////////// MAIN MENU
                _____________________________________________________
                1) If you want to take a look at all pictures:
                    enter 'pictures'
                _____________________________________________________
                2) If you want to take a look at all authors:
                    enter 'authors'
                _____________________________________________________
                3) If you want to search concrete pictures/authors:
                    enter 'search'
                _____________________________________________________
                4) If you want to change your current account:
                    enter 'change'
                _____________________________________________________
                5) If you want to open your profile:
                    enter 'profile'
                _____________________________________________________
                6) If you want to close application:
                    enter 'close'
                _____________________________________________________
                """;
        this.logoutSuccessMessage = """
                You have been successfully log out!
                """;
        this.wrongCommandMessage = new WrongCommandMessage();
    }

    public void outputGreeting(UserRole role) {
        System.out.print(greetingMessage);
        if (role != UserRole.UNSIGNED) {
            String signedInOption = """
                    + as signed you can do following:
                    _____________________________________________________
                    7) If you want to open saved list:
                        enter 'saved'
                    _____________________________________________________
                    """;
            System.out.println(signedInOption);
            if (role == UserRole.ADMIN) {
                String adminOptions = """
                        + as admin you can do following:
                        _____________________________________________________
                        8) if you want to take a look at all users:
                            enter 'users'
                        _____________________________________________________
                        """;
                System.out.print(adminOptions);
            }

        }
    }

    public void outputWrongCommandEnteredMessage() {
        wrongCommandMessage.outputMessage();
    }

    public void outputSuccessLogout() {
        System.out.println(logoutSuccessMessage);
    }

    public void outputForLowPermissionsAdmin() {
        new LowPermissionNotAdminMessage().outputMessage();
    }

    public void outputForLowPermissionsUnsigned() {
        new LowPermissionUnsignedMessage()
                .outputMessage();
    }
}
