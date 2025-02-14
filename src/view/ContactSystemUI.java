package view;

import java.math.BigDecimal;
import java.util.Scanner;

public class ContactSystemUI {

    Scanner scan = new Scanner(System.in);

    public void print(String str) {
        System.out.print(str);
    }

    public void printLine(String str) {
        System.out.println(str);
    }

    public void printNewLine() {
        System.out.println();
    }

    public String getString () {
        return scan.next();
    }

    public int getInteger () {
        return scan.nextInt();
    }

    public BigDecimal getBigDecimal () {
        return scan.nextBigDecimal();
    }

    public void printMenu (String[] arrayStrings , String headerText) {
        printNewLine();
        printLine(headerText);
        printLine(underLineString(headerText));
        for(int arrayIndex = 0 ; arrayIndex < arrayStrings.length ; arrayIndex++) {
            printLine((arrayIndex+1) + ". "+ arrayStrings[arrayIndex] + "");
        }
        printLine(arrayStrings.length+1 + ". Exit");
        print("Enter your Choice:");
    }

    private String underLineString(String text) {
        return "=".repeat(text.length());
    }

    public void printWarningMessage(String message) {
        printLine( "\033[0;31m" + message + "\033[0m" );
    }

    public void printInformativeMessage (String message) {
        printLine( "\033[0;33m" + message + "\033[0m" );
    }

    public void printOutputMessage (String message) {
        printLine( "\033[0;32m"+ message + "\033[0m" );
    }


}
