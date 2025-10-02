# 📱 Mobile Recharge Simulator

## 📖 Overview

The **Mobile Recharge Simulator** is a Java Maven project that simulates the process of mobile recharge with OTP verification.  
It guides the user through entering their mobile number, selecting a recharge amount, providing card details, verifying an OTP sent via email, and finally generating a detailed transaction receipt.  
The receipt is shown in the console and also generated as a PDF. Additionally, the SIM operator is detected automatically based on the mobile number.  

---

## ✨ Features

-  **Robust user input validation** applied to all inputs (mobile number, amount selection, card fields, email, OTP).
-  **Automatic SIM operator detection** from the entered mobile number.  
-  **OTP sent and verified** via user-provided email for transaction confirmation  
-  **Detailed transaction receipt** shown on the console and **PDF receipt generation** for records.   
-  **Navigation control:** enter `b` at any input prompt to go back to the previous field.  
-  **Exit anytime:** enter `e` at any prompt to exit the current run.
-  **Console clarity:** the console is cleared automatically after each input to keep the interface clean and readable.  
-  **Transaction loop:** the user can perform multiple transactions in a single program run without restarting the
application.
  
---

## ⚙️ How It Works

1. User enters their **mobile number** → Operator detected automatically.  
2. User selects a **recharge amount** from available options.  
3. User provides **card details** for payment.  
4. User provides their **email address**.  
5. An **OTP is sent to the user’s email** for verification.  
6. User enters the **OTP** → after OTP validation, the recharge is confirmed.  
7. A **transaction receipt** is:  
   - Displayed in the console  
   - **Generated as a PDF file** with structured sections  

---

## 🏃 **How to Run**

Download the latest ZIP from the [GitHub Releases page](https://github.com/just-asim27/mobile-recharge-simulator/releases).

---

## 📂 Repository Structure

```

mobile-recharge-simulator/
├── src/
│ ├── main/java/projects/asim/recharge/ [Java source code]
│ └── test/... [Empty, reserved for unit testing]
├── pom.xml [Maven configuration & dependency management] 
├── README.md [Project documentation]
└── .gitignore [Ignored folders]

```

---

## 🔒 Reuse & Contribution

This project is provided publicly **for portfolio and demonstration purposes only**. You may **not copy, modify, redistribute, or use** this code **without my explicit permission**.

If you’d like to request permission, please contact me at: [hafizmuhammadasimofficial@gmail.com](mailto:hafizmuhammadasimofficial@gmail.com)

---

## 👤 Author

**Asim**

