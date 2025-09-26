package projects.asim.recharge.util;

import java.util.ArrayList;

public class ConsoleUtils {

    // ============================
    // Section 1: Title & Greetings
    // ============================

    public static void displayTitle(String title, int width) {

        int padding = (width - title.length()) / 2;

        System.out.println("=".repeat(width));
        System.out.println(" ".repeat(padding) + title);
        System.out.println("=".repeat(width));

    }

    public static void displayGreetings(String... lines) {
        
        int width = 0;

        for (String line : lines) {

            if (line.length() > width)
                width = line.length();

        }

        System.out.println();

        for (String line : lines) {

            System.out.println(centerText(line, width));

        }

    }

    private static String centerText(String text, int width) {

        int totalPadding = width - text.length();
        int leftPadding = totalPadding / 2;
        int rightPadding = totalPadding - leftPadding;

        return " ".repeat(Math.max(0, leftPadding)) + text + " ".repeat(Math.max(0, rightPadding));
        
    }

    // ================================
    // Section 2: Console Interactivity
    // ================================

    public static void displayProcessingText(String text, int dots, int delayMillis) {

        System.out.print("\n" + text);

        for (int i = 0; i < dots; i++) {

            System.out.print(".");

            try {
                Thread.sleep(delayMillis);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }

        System.out.println();

    }

    public static void clearConsole() {

        try {

            if (System.getProperty("os.name").contains("Windows")) {

                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            } else {

                System.out.print("\033[H\033[2J");
                System.out.flush();

            }

        } catch (Exception e) {

            for (int i = 0; i < 50; i++)
                System.out.println();

        }

    }

    public static void displayErrorMessage(String message) {

        System.out.println("\n[ERROR] " + message);

    }

    // ========================
    // Section 3: Boxed Layouts
    // ========================

    public static String buildPrompt(String title, String... lines) {

        ArrayList<String> expandedLines = new ArrayList<>();

        for (String line : lines) {

            for (String subLine : line.split("\n")) {

                expandedLines.add(subLine);

            }

        }

        int width = title.length();

        for (String line : expandedLines) {

            if (line.length() > width)
                width = line.length();

        }

        width += 2;

        StringBuilder box = new StringBuilder();

        box.append("\n┌").append("─".repeat(width)).append("┐\n");

        int padding = (width - title.length()) / 2;

        box.append("│")
                .append(" ".repeat(padding))
                .append(title)
                .append(" ".repeat(width - title.length() - padding))
                .append("│\n");

        box.append("│").append("─".repeat(width)).append("│\n");

        for (String line : expandedLines) {

            box.append("│ ").append(line);
            box.append(" ".repeat(width - line.length() - 1)).append("│\n");

        }

        box.append("└").append("─".repeat(width)).append("┘\n");
        box.append("> ");

        return box.toString();

    }

    public static void displayBoxTitle(String title, int width) {

        int padding = (width - 2 - title.length()) / 2;

        System.out.println("\n┌" + "─".repeat(width - 2) + "┐");
        System.out.println("│" + " ".repeat(padding) + title + " ".repeat(width - 2 - padding - title.length()) + "│");
        System.out.println("│" + "─".repeat(width - 2) + "│");

    }

    public static void displayBoxRow(String label, String value, int width) {

        int spaces = width - 2 - label.length() - value.length();

        System.out.println("│" + label + " ".repeat(spaces) + value + "│");

    }

    public static void displayBoxDivider(int width) {

        System.out.println("│" + "─".repeat(width - 2) + "│");

    }

    public static void displayBoxBottom(int width) {

        System.out.println("└" + "─".repeat(width - 2) + "┘");

    }

}
