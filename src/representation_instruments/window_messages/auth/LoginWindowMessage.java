package representation_instruments.window_messages.auth;

public class LoginWindowMessage {
    public void outputEnterName() {
        String message = "Please, enter your username:";
        System.out.println(message);
    }

    public void outputForEnterPassword() {
        String message = "Please, enter your password:";
        System.out.println(message);
    }

    public void outputWrongLogin() {
        String message = """
                Unfortunately, you have entered wrong data.
                If you want to register new account:
                    enter 'register'
                If you want to continue be unsigned:
                    enter 'continue'
                                
                If you want to try sign in again:
                    press any other button
                """;
        System.out.println(message);
    }

    public void outputForUnsignedOption() {
        new AuthWindowMessage().outputNoteForUnsignedUsers();
    }

    public void outputForSuccessLogin() {
        String message = "You have been successfully log in!";
        System.out.println(message);
    }
}
