package projects.asim.recharge.validation;

@FunctionalInterface
public interface InputValidator {

    InputValidationResult validate(String input);
    
}