package representation_instruments.window_messages.auth;

public class RegisterWindowMessage {
    public void outputForEnterName() {
        new LoginWindowMessage().outputEnterName();
    }

    public void outputForEnterPassword() {
        new LoginWindowMessage().outputForEnterPassword();
    }

    public void outputForEnterPasswordAgain() {
        String message = "Please, enter your password again:";
        System.out.println(message);
    }

    public void outputForNotTheSameEnteredPasswords() {
        String message = "You entered two different passwords. Try again!";
        System.out.println(message);
    }

    public void outputForSuccessRegistration() {
        String message = "You have been successfully registered";
        System.out.println(message);
    }
}
