// Copyright 20202 Shane McGillian, all rights reserved.
// The code is currently not open source, however, this is subject to change with a future upload.
package com.mcgillian.MortAmort;

// Imports Section
import java.io.Console;
import java.util.Scanner;

// Main Class with main entry point
public class MortAmort {

    public static void main(String[] args) {
        // Program Logic
        GetInput();
        Calculate();
        PrintOutput();
        return;
    }

    public static void GetInput(){
        String consoleInput = "";
        Console c = System.console();
        Scanner inputReader = new Scanner(System.in);

        // Beging main logic
        System.out.println("");
        System.out.println("Mortguage Calculator");
        System.out.println("--------------------");
        System.out.println("");

        //Input the Loan details
        System.out.println("Enter the loan amount:");
        consoleInput = "";
        consoleInput = inputReader.nextLine();

        //Set the value
        mainMort.setLoanAmount(Integer.parseInt(consoleInput));

        consoleInput = "";
        System.out.println("Enter the term in years:");
        consoleInput = inputReader.nextLine();

        //Set the value
        mainMort.setTermYears(Integer.parseInt(consoleInput));

        consoleInput = "";
        System.out.println("Enter the interest rate:");
        consoleInput = inputReader.nextLine();
        mainMort.setYearlyInterestRate(Float.parseFloat(consoleInput));
    }

    public static void Calculate(){
        mainMort.Calculate();
    }

    public static void PrintOutput(){
        // Locals
        AmortData adData;

        // First print a summary
        System.out.println("");
        System.out.println("Summary");
        System.out.println("-------");
        System.out.println("");

        // All is ok so let's print out the values.
        System.out.println("Loan Amount: " + mainMort.getLoanAmount() / 100);
        System.out.println("Term: " + mainMort.getTermYears());
        System.out.println("Number of payments: " + mainMort.GetNumberOfPayments());
        System.out.println("Interest rate: " + mainMort.getYearlyInterestRate());
        System.out.println("Montly interest Rate: " + mainMort.getMonthlyInterestRate() * 100);
        System.out.println("Monthly repayment: " + mainMort.getPaymentAmountStr());
        System.out.println("Total cost of loan: " + mainMort.getTotalCostOfLoanStr());
        System.out.println("Total interest paid: " + mainMort.getTotalInterestPaidStr());

        //Print teh Amortization Schedule
        System.out.println("");
        System.out.println("Amortization Schedule");
        System.out.println("---------------------");
        System.out.println("");

        //Output the table header
        System.out.format("%12s%12s%12s%12s%12s%12s%12s%12s%12s%n", "------------", "------------", "------------", "------------", "------------", "------------", "------------", "------------", "------------");
        System.out.format("%12s%12s%12s%12s%12s%12s%12s%12s%12s%n", "Period", "StartBal","Payment", "Interest", "Principle", "Total int", "Total Prin", "Total Pay", "End Balance");
        System.out.format("%12s%12s%12s%12s%12s%12s%12s%12s%12s%n", "-----------", "-----------", "-----------", "-----------", "-----------", "-----------", "-----------", "-----------", "-----------");

        //Print out the table contents
        // For the number of payments that need to be made....
        for (int i = 0; i < mainMort.GetNumberOfPayments(); i++){
            // Get the data for the period.
            adData = mainMort.GetPeriod(i);
            //System.out.println(i);
            System.out.format("%12s%12s%12s%12s%12s%12s%12s%12s%12s%n",
                    adData.m_iPeriod,
                    mainMort.GetDollarFromCentAsString(adData.m_iStartingBalance),
                    mainMort.GetDollarFromCentAsString(adData.m_iPeriodPayment),
                    mainMort.GetDollarFromCentAsString(adData.m_iPeriodInterest),
                    mainMort.GetDollarFromCentAsString(adData.m_iPrinciplePayment),
                    mainMort.GetDollarFromCentAsString(adData.m_iRunningTotalInterest),
                    mainMort.GetDollarFromCentAsString(adData.m_iRunningTotalPrinciple),
                    mainMort.GetDollarFromCentAsString(adData.m_iRunningTotalPayment),
                    mainMort.GetDollarFromCentAsString(adData.m_iBalance));

        }
    }

    //Data members
    public static MortCalc mainMort = new MortCalc();
}


