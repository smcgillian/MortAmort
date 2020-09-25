//====================================================================================================================
//====================================================================================================================
// Copyright 20202 Shane McGillian, all rights reserved.
// The code is currently not open source, however, this is subject to change with a future upload.
//====================================================================================================================
//====================================================================================================================

package com.mcgillian.MortAmort;

// Imports section
import java.util.List;
import java.util.ArrayList;
//import java.io.Console;
import java.math.BigDecimal;
import java.math.RoundingMode;

// Mortgage calculations class for math and variables
class MortCalc {

    public MortCalc() {
        m_bPaymentsCalculated = false;
    }

    public void Calculate(){
        calculateMonthlyPayment();
        AmortizationSchedule();
    }

    //Public Methods
    public void setLoanAmount(int iLoadAmount){
        // Convert the input to the lowest denomination, id est cent/pounds etc.
        m_iLoanAmount = iLoadAmount * 100;
    }
    public void setTermYears(int iTermYears){
        m_iTermYears = iTermYears;
        m_iNumPayments = m_iTermYears * 12;
    }
    public void setYearlyInterestRate(float fYearlyInterestRate){
        m_dYearlyInterestRate = fYearlyInterestRate;
    }

    public void calculateMonthlyPayment(){
        // Currency variables are in lowest denominator, id est cents/pennies
        // Will require conversion back to Dollars/Pounds Sterling/Euros
        // at display time with multiply by 100.
        // This is done to avoid float point rounding errors. We could have
        // used BigDecimal in Java but then the code would have less portable.
        // BigDecimal is still going to be used for multiplication/division operations on fractions then
        // rounded up to the nearest penny.

        // First work out Monthly Payment
        // Get the monthly interest rate, yearly as: fraction and div by 12 months, == div by 100 / 12 == div by 1200.
        double dMonthlyInterestRate = m_dYearlyInterestRate / 1200;

        // Do power part
        double dPowerPart = Math.pow((1+dMonthlyInterestRate), m_iNumPayments);

        // Get the numerator
        double dNumerator = (m_iLoanAmount*dMonthlyInterestRate*dPowerPart);

        // Get the denominator
        double dDenominator=(dPowerPart-1);

        // Work out the payment and round up to nearest denomination.
        BigDecimal bdMonthlyPayment = new BigDecimal(dNumerator / dDenominator);

        // The monthly payment to nearest denomination (id est, cents/pennis etc)
        m_iMonthlyPayment = bdMonthlyPayment.intValue();

        // Mark that we've calculated the values.
        m_bPaymentsCalculated = true;
        return;
    }


    public void AmortizationSchedule(){
        // Make sure the payments are calculated
        if (!m_bPaymentsCalculated) {
            calculateMonthlyPayment();
        }

        AmortData adTemp = new AmortData();
        for (int i = 0; i < m_iNumPayments; i++){

            // If it's the first time, we need to setup initial balances.
            if (i == 0){
                adTemp.m_iBalance = m_iLoanAmount;
                adTemp.m_iStartingBalance = adTemp.m_iBalance;
            }

            // Set the payment number
            adTemp.m_iPeriod = i + 1;

            // Get the starting balance.
            adTemp.m_iStartingBalance = adTemp.m_iBalance;

            // If we have a balance greater or equal to the payment amount
            if (adTemp.m_iStartingBalance >= m_iMonthlyPayment){
                // Payment won't go into negative values, so let's compute:
                adTemp.m_iPeriodPayment = m_iMonthlyPayment;

                // First work out the interest for the period and running total: Opening Balance * Monthly Interest Rate
                adTemp.m_iPeriodInterest = (int) Math.round((adTemp.m_iStartingBalance * getMonthlyInterestRate()));

                adTemp.m_iRunningTotalInterest = adTemp.m_iRunningTotalInterest + adTemp.m_iPeriodInterest;

                // Next work out the principle portion of the payment and running totals
                adTemp.m_iPrinciplePayment = adTemp.m_iPeriodPayment - adTemp.m_iPeriodInterest;
                adTemp.m_iRunningTotalPrinciple = adTemp.m_iRunningTotalPrinciple + adTemp.m_iPrinciplePayment;

                // Work out running total payments
                adTemp.m_iRunningTotalPayment = adTemp.m_iRunningTotalPayment + adTemp.m_iPeriodPayment;

                //Set the closing balance
                adTemp.m_iBalance = adTemp.m_iBalance - adTemp.m_iPrinciplePayment;
            }
            else{ // Balance is less than full payment, so work out final payment.
                // First work out the interest for the period and running total.
                // This is the final payment so interest can't be carried over to the next period
                // and is added to this period.
                // Final payment may be different than usual.
                // Opening Balance * Monthly Interest Rate
                adTemp.m_iPeriodInterest = (int) Math.round((adTemp.m_iStartingBalance * getMonthlyInterestRate()));
                adTemp.m_iRunningTotalInterest = adTemp.m_iRunningTotalInterest + adTemp.m_iPeriodInterest;

                // Next work out the principle portion of the payment and running totals
                adTemp.m_iPeriodPayment = adTemp.m_iStartingBalance + adTemp.m_iPeriodInterest;
                adTemp.m_iPrinciplePayment = adTemp.m_iPeriodPayment - adTemp.m_iPeriodInterest;
                adTemp.m_iRunningTotalPrinciple = adTemp.m_iRunningTotalPrinciple + adTemp.m_iPrinciplePayment;

                // Work out running total payments
                adTemp.m_iRunningTotalPayment = adTemp.m_iRunningTotalPayment + adTemp.m_iPeriodPayment;

                //Set the closing balance
                adTemp.m_iBalance = adTemp.m_iBalance - adTemp.m_iPeriodPayment;

                // Next set the "actual" total cost of loan
                m_iTotalCostofLoan = adTemp.m_iRunningTotalPayment;

                // Next set the "actual" total of interest paid
                m_iTotalInterestPaid = adTemp.m_iRunningTotalInterest;
            }

            // Add to data list
            m_amortSchedule.add(new AmortData(adTemp));
        }
        return;
    }

    // Get functions
    public int getLoanAmount(){
        return m_iLoanAmount;
    }
    public int getTermYears(){
        return m_iTermYears;
    }
    public int GetNumberOfPayments(){
        return m_iNumPayments;
    }
    public double getYearlyInterestRate(){
        return m_dYearlyInterestRate;
    }
    public double getMonthlyInterestRate(){
        return m_dYearlyInterestRate / 1200;
    }

    public int getPaymentAmount(){
        return m_iMonthlyPayment;
    }
    public String getPaymentAmountStr(){
        return GetDollarFromCentAsString(m_iMonthlyPayment);
    }

    public int getTotalCostOfLoan(){
        return m_iTotalCostofLoan;
    }
    public String getTotalCostOfLoanStr(){
        return GetDollarFromCentAsString(m_iTotalCostofLoan);
    }

    public int getTotalInterestPaid(){
        return m_iTotalInterestPaid;
    }
    public String getTotalInterestPaidStr(){
        return GetDollarFromCentAsString(m_iTotalInterestPaid);
    }

    public String GetDollarFromCentAsString(int cents){
        String currencyDollar = Integer.toString(cents);
        if (cents > 99) {
            return new StringBuffer(currencyDollar).insert(currencyDollar.length()-2, ".").toString();
        }
        else if (cents < 100 && cents > 9) {
            return new StringBuffer(currencyDollar).insert(currencyDollar.length()-2, "0.").toString();
        }
        else if (cents < 10 && cents > 0) {
            return new StringBuffer(currencyDollar).insert(currencyDollar.length()-1, "0.0").toString();
        }
        else return "0.00";
    }

    public AmortData GetPeriod(int iPeriod){
        return m_amortSchedule.get(iPeriod);
    }

//Private Methods

    //Data variables
    // Mortgage values-
    private int m_iLoanAmount;
    private int m_iTermYears;
    private int m_iNumPayments;
    private double m_dYearlyInterestRate;
    private int m_iMonthlyPayment;
    private int m_iTotalCostofLoan;
    private int m_iTotalInterestPaid;

    // Worker values
    private boolean m_bPaymentsCalculated;

    List<AmortData> m_amortSchedule = new ArrayList<AmortData>();
}

