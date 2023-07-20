package instruments.window_messages.auth;

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

    public void outputForWeekPassword() {
        String message = """
                You have entered week password.
                It must be:
                    ~ Minimum password length is 8 characters
                      (you can adjust this value as needed).
                    ~ Password should contain at least
                      one uppercase letter,
                      one lowercase letter,
                      one digit, and
                      one special character.
                   
                Please, try again!
                """;
        System.out.println(message);
    }

    public void outputSuchUserNameExists() {
        String message = """
                Unfortunately, such username already exists!
                Please, try again :(
                """;
        System.out.println(message);
    }
}
