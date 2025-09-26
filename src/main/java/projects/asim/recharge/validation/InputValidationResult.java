package projects.asim.recharge.validation;

public class InputValidationResult {

    private final boolean valid;
    private final String errorMessage;

    public InputValidationResult(boolean valid, String errorMessage) {

        this.valid = valid;
        this.errorMessage = errorMessage;

    }

    public boolean isValid() {

        return valid;

    }

    public String getErrorMessage() {

        return  "\n[ERROR] " + errorMessage;

    }

    public static InputValidationResult success() {

        return new InputValidationResult(true, "");

    }

    public static InputValidationResult fail(String message) {

        return new InputValidationResult(false, message);

    }

}
