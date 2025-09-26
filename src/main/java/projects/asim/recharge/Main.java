package projects.asim.recharge;

import projects.asim.recharge.util.ConsoleUtils;
import projects.asim.recharge.util.CardUtils;
import projects.asim.recharge.util.EmailUtils;
import projects.asim.recharge.util.OTPUtils;
import projects.asim.recharge.util.TransactionReceiptUtils;

import projects.asim.recharge.model.*;
import projects.asim.recharge.validation.*;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        final int WIDTH = 40;
        ConsoleUtils.displayTitle("MOBILE RECHARGE SIMULATOR", WIDTH);

        ConsoleUtils.displayGreetings("Welcome to Mobile Recharge Simulator!", "Instant & Secure Top-up Platform");

        Scanner userInput = new Scanner(System.in);

        final int[] amounts = { 100, 200, 300, 500, 750, 1000 };

        // ===========================
        // Section 1: Transaction Loop
        // ===========================

        outerLoop: while (true) {

            String mobileNumber = null;
            String amountChoice = null;
            String cardholderName = null;
            String cardNumber = null;
            String expiryDate = null;
            String cvv = null;
            String email = null;

            int step = 1;

            // ========================
            // Section 2: Step Handling
            // ========================

            while (true) {

                switch (step) {

                    case 1:
                        mobileNumber = promptInput(userInput, "MOBILE NUMBER", "Enter mobile number (03X-XXXXXXXX)",
                                input -> {

                                    String sanitized = input.replaceAll("[\\s-]", "");

                                    if (!sanitized.matches("\\d+"))
                                        return InputValidationResult.fail("Mobile Number must contain only digits.");
                                    else if (sanitized.length() != 11)
                                        return InputValidationResult.fail("Mobile Number must be exactly 11 digits.");
                                    else if (!sanitized.startsWith("03"))
                                        return InputValidationResult.fail("Mobile Number must start with 03.");

                                    return InputValidationResult.success();

                                });

                        if (mobileNumber == null) {

                            userInput.close();
                            return;

                        } else if ("__BACK__".equals(mobileNumber)) {

                            step = Math.max(1, step - 1);
                            break;

                        }

                        step++;
                        break;
                        
                    case 2:
                        amountChoice = promptInput(userInput, "RECHARGE AMOUNT", "Select recharge amount:\n1) PKR 100  2) PKR 200  3) PKR 300\n4) PKR 500  5) PKR 750  6) PKR 1000",
                                input -> {

                                    try {

                                        int choice = Integer.parseInt(input);

                                        if (choice < 1 || choice > 6)
                                            return InputValidationResult.fail("Amount choice must be between 1 and 6.");

                                        return InputValidationResult.success();

                                    } catch (NumberFormatException e) {

                                        return InputValidationResult.fail("Amount choice must be a number.");

                                    }

                                });

                        if (amountChoice == null) {

                            userInput.close();
                            return;

                        } else if ("__BACK__".equals(amountChoice)) {

                            step = Math.max(1, step - 1);
                            break;

                        } 
                            
                        step++;
                        break;
                         
                    case 3:
                        cardholderName = promptInput(userInput, "CARDHOLDER NAME", "Enter cardholder name",
                                input -> {

                                    if (!input.matches("[A-Za-zÀ-ÿ'\\- ]+"))
                                        return InputValidationResult.fail("Name must contain only letters, spaces, hyphens, or apostrophes.");

                                    return InputValidationResult.success();

                                });

                        if (cardholderName == null) {

                            userInput.close();
                            return;

                        } else if ("__BACK__".equals(cardholderName)) {

                            step = Math.max(1, step - 1);
                            break;

                        } 

                        step++;
                        break;

                    case 4:
                        cardNumber = promptInput(userInput, "CARD NUMBER", "Enter card number (16 digits)",
                                input -> {

                                    String sanitized = input.replaceAll("[\\s-]", "");

                                    if (!sanitized.matches("\\d+"))
                                        return InputValidationResult.fail("Card Number must contain only digits.");
                                    else if (sanitized.length() != 16)
                                        return InputValidationResult.fail("Card Number must be exactly 16 digits.");
                                    else if (CardUtils.allDigitsSame(sanitized))
                                        return InputValidationResult.fail("Card Number cannot have all identical digits.");
                                    else if (!CardUtils.passesLuhn(sanitized))
                                        return InputValidationResult.fail("Card Number is invalid. Please check the digits and try again.");

                                    return InputValidationResult.success();

                                });

                        if (cardNumber == null) {

                            userInput.close();
                            return;

                        } else if ("__BACK__".equals(cardNumber)) {

                            step = Math.max(1, step - 1);
                            break;

                        }

                        step++;
                        break;

                    case 5:
                        expiryDate = promptInput(userInput, "EXPIRY DATE", "Enter expiry date (MM/YY)",
                                input -> {

                                    if (input.length() != 5 || input.charAt(2) != '/')
                                        return InputValidationResult.fail("Expiry Date must be in MM/YY format.");

                                    String monthStr = input.substring(0, 2);
                                    String yearStr = input.substring(3, 5);

                                    if (!monthStr.matches("\\d+") || !yearStr.matches("\\d+"))
                                        return InputValidationResult.fail("Month and Year must be numeric.");

                                    int month = Integer.parseInt(monthStr);

                                    if (month < 1 || month > 12)
                                        return InputValidationResult.fail("Month must be between 01 and 12.");

                                    return InputValidationResult.success();

                                });

                        if (expiryDate == null) {

                            userInput.close();
                            return;

                        } else if ("__BACK__".equals(expiryDate)) {

                            step = Math.max(1, step - 1);
                            break;

                        }

                        step++;
                        break;

                    case 6:
                        cvv = promptInput(userInput, "CVV", "Enter CVV (3 digits)",
                                input -> {

                                    if (!input.matches("\\d+"))
                                        return InputValidationResult.fail("CVV must contain only digits.");
                                    else if (input.length() != 3)
                                        return InputValidationResult.fail("CVV must be exactly 3 digits.");

                                    return InputValidationResult.success();

                                });

                        if (cvv == null) {

                            userInput.close();
                            return;

                        } else if ("__BACK__".equals(cvv)) {

                            step = Math.max(1, step - 1);
                            break;

                        }

                        step++;
                        break;

                    case 7:
                        email = promptInput(userInput, "EMAIL", "Enter your email address (example@gmail.com)",
                                input -> EmailUtils.isValidEmail(input) ? InputValidationResult.success()
                                        : InputValidationResult.fail("Email must be in valid format."));

                        if (email == null) {

                            userInput.close();
                            return;

                        } else if ("__BACK__".equals(email)) {

                            step = Math.max(1, step - 1);
                            break;

                        }

                        // ===========================
                        // Section 3: OTP Verification
                        // ===========================

                        OTPVerificationResult result = OTPUtils.handleOTPVerification(userInput, email);

                        switch (result) {

                            case EXIT:
                                userInput.close();
                                return;

                            case RETRY_EMAIL:
                                step = 7;
                                break;
                            
                            case VERIFIED:
                                step = 8;
                                break;

                        }

                        break;

                }

                if (step == 8)
                    break;

            }

            int rechargeAmount = amounts[Integer.parseInt(amountChoice) - 1];

            // =================================
            // Section 4: Transaction Processing
            // =================================

            TransactionReceipt receipt = TransactionReceiptUtils.createReceipt(
                    mobileNumber,
                    rechargeAmount,
                    cardNumber,
                    cardholderName,
                    email);

            TransactionReceiptUtils.displayTransactionReceipt(receipt);
            TransactionReceiptUtils.generateTransactionReceiptPdf(receipt);

            String again = promptInput(userInput, "ANOTHER TRANSACTION", "Do you want to make another transaction? Enter (y/n)",
                    input -> {

                        if (input.equalsIgnoreCase("y") || input.equalsIgnoreCase("n")) 
                            return InputValidationResult.success();
                        
                        return InputValidationResult.fail("Please enter 'y' for yes or 'n' for no.");
                        
                    });

            if (again == null || "__BACK__".equals(again) || again.equalsIgnoreCase("n")) 
                break outerLoop;

        }

        ConsoleUtils.displayGreetings("Thank you for using Mobile Recharge Simulator!", "Created by Asim");

        userInput.close();

    }

    // ===============================
    // Section 5: Input Prompt Utility
    // ===============================

    public static String promptInput(Scanner scanner, String title, String promptMsg, InputValidator validator) {

        final int DOT_COUNT = 3;
        final int DOT_DELAY_MS = 500;

        while (true) {

            String prompt = ConsoleUtils.buildPrompt(title, promptMsg, "Or 'e' to exit, 'b' to go back");

            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            ConsoleUtils.clearConsole();

            if (input.equalsIgnoreCase("e")) {

                ConsoleUtils.displayProcessingText("Exiting", DOT_COUNT, DOT_DELAY_MS);
                return null; 

            } else if (input.equalsIgnoreCase("b")) {

                return "__BACK__"; 

            } else if (input.isEmpty()) {

                ConsoleUtils.displayErrorMessage("Please type something before pressing Enter.");

            } else {

                InputValidationResult result = validator.validate(input);

                if (result.isValid())
                    return input;
                else
                    System.out.println(result.getErrorMessage());

            }

        }

    }

}