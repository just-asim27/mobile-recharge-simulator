package projects.asim.recharge.model;

public class TransactionReceipt {

    private final String transactionId;
    private final String dateTime;
    private final String mobileNumber;
    private final String simOperator;
    private final int rechargeAmount;
    private final String cardNumber;
    private final String cardholderName;
    private final String paymentStatus;

    public TransactionReceipt(
            String transactionId,
            String dateTime,
            String mobileNumber,
            String simOperator,
            int rechargeAmount,
            String cardNumber,
            String cardholderName,
            String paymentStatus) {

        this.transactionId = transactionId;
        this.dateTime = dateTime;
        this.mobileNumber = mobileNumber;
        this.simOperator = simOperator;
        this.rechargeAmount = rechargeAmount;
        this.cardNumber = cardNumber;
        this.cardholderName = cardholderName;
        this.paymentStatus = paymentStatus;
        
    }

    public String getTransactionId() { return transactionId; }
    public String getDateTime() { return dateTime; }
    public String getMobileNumber() { return mobileNumber; }
    public String getSimOperator() { return simOperator; }
    public int getRechargeAmount() { return rechargeAmount; }
    public String getCardNumber() { return cardNumber; }
    public String getCardholderName() { return cardholderName; }
    public String getPaymentStatus() { return paymentStatus; }

}
