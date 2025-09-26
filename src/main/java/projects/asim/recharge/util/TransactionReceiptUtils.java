package projects.asim.recharge.util;

import projects.asim.recharge.model.TransactionReceipt;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.io.IOException;
import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class TransactionReceiptUtils {

    // ===========================
    // Section 1: Receipt Creation 
    // ===========================

    public static TransactionReceipt createReceipt(
            String mobileNumber,
            int rechargeAmount,
            String cardNumber,
            String cardholderName,
            String email) {

        String simOperator = MobileNumberUtils.detectSimOperator(mobileNumber);
        String maskedCardNumber = CardUtils.maskCardNumber(cardNumber);
        String transactionId = generateTransactionId();
        String currentDateTime = getCurrentDateTime();

        return new TransactionReceipt(
                transactionId,
                currentDateTime,
                MobileNumberUtils.maskMobileNumber(mobileNumber),
                simOperator,
                rechargeAmount,
                maskedCardNumber,
                cardholderName,
                "SUCCESS");

    }

    // ==========================
    // Section 2: Console Display 
    // ==========================

    public static void displayTransactionReceipt(TransactionReceipt receipt) {

        final int WIDTH = 40;

        ConsoleUtils.displayBoxTitle("TRANSACTION RECEIPT", WIDTH);
        ConsoleUtils.displayBoxRow("Transaction ID", receipt.getTransactionId(), WIDTH);
        ConsoleUtils.displayBoxRow("Date & Time", receipt.getDateTime(), WIDTH);
        ConsoleUtils.displayBoxDivider(WIDTH);
        ConsoleUtils.displayBoxRow("Mobile Number", receipt.getMobileNumber(), WIDTH);
        ConsoleUtils.displayBoxRow("SIM Operator", receipt.getSimOperator(), WIDTH);
        ConsoleUtils.displayBoxRow("Recharge Amount", "PKR " + receipt.getRechargeAmount(), WIDTH);
        ConsoleUtils.displayBoxDivider(WIDTH);
        ConsoleUtils.displayBoxRow("Payment Method", "Credit Card", WIDTH);
        ConsoleUtils.displayBoxRow("Card Number", receipt.getCardNumber(), WIDTH);
        ConsoleUtils.displayBoxRow("Cardholder Name", receipt.getCardholderName(), WIDTH);
        ConsoleUtils.displayBoxDivider(WIDTH);
        ConsoleUtils.displayBoxRow("Payment Status", receipt.getPaymentStatus(), WIDTH);
        ConsoleUtils.displayBoxBottom(WIDTH);

    }

    private static String generateTransactionId() {

        return "TXN" + System.currentTimeMillis();

    }

    private static String getCurrentDateTime() {

        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));

    }

    // =========================
    // Section 1: PDF Generation 
    // =========================

    public static void generateTransactionReceiptPdf(TransactionReceipt receipt) {

        try {

            File folder = new File("receipts");

            if (!folder.exists())
                folder.mkdirs();

            String fileName = "receipt-" + receipt.getTransactionId() + ".pdf";

            File file = new File(folder, fileName);

            try (PDDocument document = new PDDocument()) {

                PDPage page = new PDPage(PDRectangle.A4);

                document.addPage(page);

                try (PDPageContentStream cs = new PDPageContentStream(document, page)) {

                    float pageWidth = page.getMediaBox().getWidth();
                    float pageHeight = page.getMediaBox().getHeight();

                    float boxWidth = pageWidth * 0.65f; 
                    float startX = (pageWidth - boxWidth) / 2;
                    float topMargin = 100;
                    float startY = pageHeight - topMargin;

                    
                    int totalRows = 2 + 2 + 3 + 3 + 1;
                    int totalDividers = 3; 
                    float lineHeight = 20;
                    float spacingAfterDivider = 5;
                    float paddingTopBottom = 20;

                    float boxHeight = totalRows * lineHeight + totalDividers * (lineHeight + spacingAfterDivider) + paddingTopBottom;

                    
                    cs.setLineWidth(2);
                    cs.addRect(startX, startY - boxHeight, boxWidth, boxHeight);
                    cs.stroke();

                    
                    float y = drawHeading(cs, startX, boxWidth, startY - 25, "TRANSACTION RECEIPT",
                            PDType1Font.HELVETICA_BOLD, 18, 1.5f);

                
                    y = drawLabelValueRow(cs, startX, boxWidth, y, "Transaction ID", receipt.getTransactionId(),
                            PDType1Font.HELVETICA, 12);
                    y = drawLabelValueRow(cs, startX, boxWidth, y, "Date & Time", receipt.getDateTime(),
                            PDType1Font.HELVETICA, 12);
                    y = drawDivider(cs, startX, boxWidth, y, 1.5f);

                    
                    y = drawLabelValueRow(cs, startX, boxWidth, y, "Mobile Number", receipt.getMobileNumber(),
                            PDType1Font.HELVETICA, 12);
                    y = drawLabelValueRow(cs, startX, boxWidth, y, "SIM Operator", receipt.getSimOperator(),
                            PDType1Font.HELVETICA, 12);
                    y = drawLabelValueRow(cs, startX, boxWidth, y, "Recharge Amount",
                            "PKR " + receipt.getRechargeAmount(), PDType1Font.HELVETICA, 12);
                    y = drawDivider(cs, startX, boxWidth, y, 1.5f);

                    
                    y = drawLabelValueRow(cs, startX, boxWidth, y, "Payment Method", "Credit Card",
                            PDType1Font.HELVETICA, 12);
                    y = drawLabelValueRow(cs, startX, boxWidth, y, "Card Number", receipt.getCardNumber(),
                            PDType1Font.HELVETICA, 12);
                    y = drawLabelValueRow(cs, startX, boxWidth, y, "Cardholder Name", receipt.getCardholderName(),
                            PDType1Font.HELVETICA, 12);
                    y = drawDivider(cs, startX, boxWidth, y, 1.5f);

                    
                    y = drawLabelValueRow(cs, startX, boxWidth, y, "Payment Status", receipt.getPaymentStatus(),
                            PDType1Font.HELVETICA, 12);
                            
                }

                document.save(file);

                System.out.println("\nReceipt saved at: " + file.getAbsolutePath());

            }

        } catch (IOException e) {

            ConsoleUtils.displayErrorMessage("Failed to generate receipt.");

        }

    }

    private static float drawLabelValueRow(PDPageContentStream cs, float startX, float boxWidth, float y,
            String label, String value, PDType1Font font, float fontSize) throws IOException {
        
        cs.beginText();
        cs.setFont(font, fontSize);
        cs.newLineAtOffset(startX + 10, y);
        cs.showText(label);
        cs.endText();

        cs.beginText();
        cs.setFont(font, fontSize);

        float valueWidth = font.getStringWidth(value) / 1000 * fontSize;

        cs.newLineAtOffset(startX + boxWidth - 10 - valueWidth, y);
        cs.showText(value);
        cs.endText();

        return y - 20;

    }

    
    private static float drawDivider(PDPageContentStream cs, float startX, float boxWidth, float y, float thickness)
            throws IOException {

        cs.setLineWidth(thickness);
        cs.moveTo(startX, y);
        cs.lineTo(startX + boxWidth, y);
        cs.stroke();

        return y - 25;

    }


    private static float drawHeading(PDPageContentStream cs, float startX, float boxWidth, float startY,
            String heading, PDType1Font font, float fontSize, float dividerThickness) throws IOException {

        float titleWidth = font.getStringWidth(heading) / 1000 * fontSize;

        cs.beginText();
        cs.setFont(font, fontSize);
        cs.newLineAtOffset(startX + (boxWidth - titleWidth) / 2, startY);
        cs.showText(heading);
        cs.endText();

        
        return drawDivider(cs, startX, boxWidth, startY - 15, dividerThickness);

    }

}
