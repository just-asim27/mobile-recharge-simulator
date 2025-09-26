package projects.asim.recharge.util;

import projects.asim.recharge.model.OTPVerificationResult;
import projects.asim.recharge.Main;
import projects.asim.recharge.validation.InputValidationResult;

import java.security.SecureRandom;
import java.util.Scanner;

public class OTPUtils {

    // ======================================
    // Section: OTP Generation & Verification
    // ======================================

    private static String generateOTP() {

        SecureRandom random = new SecureRandom();

        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);

    }

    public static OTPVerificationResult handleOTPVerification(Scanner userInput, String email) {

        final int DOT_COUNT = 3;
        final int DOT_DELAY_MS = 500;

        ConsoleUtils.displayProcessingText("Generating OTP", DOT_COUNT, DOT_DELAY_MS);

        String otp = generateOTP();
        long otpGeneratedTime = System.currentTimeMillis();
        final long OTP_VALID_DURATION = 3 * 60 * 1000;

        ConsoleUtils.displayProcessingText("Sending OTP", DOT_COUNT, DOT_DELAY_MS);

        if (!EmailUtils.sendOTPEmail(email, otp))
            return OTPVerificationResult.RETRY_EMAIL;

        while (true) {

            String enteredOtp = Main.promptInput(userInput, "OTP VERIFICATION", "Enter OTP sent to your email",
                    input -> InputValidationResult.success());
                    
            if (enteredOtp == null) 
                return OTPVerificationResult.EXIT;
            else if ("__BACK__".equals(enteredOtp))
                return OTPVerificationResult.RETRY_EMAIL;

            long currentTime = System.currentTimeMillis();

            if (currentTime - otpGeneratedTime > OTP_VALID_DURATION) {

                ConsoleUtils.displayErrorMessage("OTP has expired. Please request a new one.");
                return OTPVerificationResult.RETRY_EMAIL;

            } else if (!enteredOtp.matches("\\d+")) {

                ConsoleUtils.displayErrorMessage("OTP must contain only digits.");

            } else if (enteredOtp.length() != 6) {

                ConsoleUtils.displayErrorMessage("OTP must be exactly 6 digits.");

            } else if (!enteredOtp.equals(otp)) {

                ConsoleUtils.displayErrorMessage("OTP is incorrect. Please try again.");

            } else {

                return OTPVerificationResult.VERIFIED;

            }

        }

    }

}
