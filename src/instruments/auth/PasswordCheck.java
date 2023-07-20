package instruments.auth;

public class PasswordCheck {
    public boolean isPasswordStrong(String password) {
        // Password strength criteria (you can modify these as needed)
        // Minimum password length
        int minLength = 8;
        // Password should contain uppercase letters
        boolean hasUppercase = false;
        // Password should contain lowercase letters
        boolean hasLowercase = false;
        // Password should contain at least one digit
        boolean hasDigit = false;
        // Password should contain at least one special character
        boolean hasSpecialCharacter = false;
        // Check the length of the password

        if (password.length() < minLength) {
            return false;
        }

        // Check for other criteria using character-by-character analysis
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowercase = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (!Character.isLetterOrDigit(c)) {
                hasSpecialCharacter = true;
            }
        }

        // Return true if all criteria are met
        return hasUppercase
                && hasLowercase
                && hasDigit
                && hasSpecialCharacter;
    }
}
