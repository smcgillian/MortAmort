//====================================================================================================================
//====================================================================================================================
// Copyright 20202 Shane McGillian, all rights reserved.
// The code is currently not open source, however, this is subject to change with a future upload.
//====================================================================================================================
//====================================================================================================================

package com.mcgillian.MortAmort;

// Data for Amortization Schedule
public class AmortData{

    // Constructor
    public AmortData(){
        m_iPeriod = 0;
        m_iStartingBalance = 0;
        m_iPeriodInterest = 0;
        m_iRunningTotalInterest = 0;
        m_iPrinciplePayment = 0;
        m_iRunningTotalPrinciple = 0;
        m_iRunningTotalPayment = 0;
        m_iBalance = 0;
        m_iAdditionalPrinciplePayment = 0;
    }

    //Copy constructor
    public AmortData(AmortData ad)
    {
        // Copy the date.
        this.m_iPeriod = ad.m_iPeriod;
        this.m_iStartingBalance = ad.m_iStartingBalance;
        this.m_iPeriodInterest = ad.m_iPeriodInterest;
        this.m_iRunningTotalInterest = ad.m_iRunningTotalInterest;
        this.m_iPrinciplePayment = ad.m_iPrinciplePayment;
        this.m_iRunningTotalPrinciple = ad.m_iRunningTotalPrinciple;
        this.m_iRunningTotalPayment = ad.m_iRunningTotalPayment;
        this.m_iBalance = ad.m_iBalance;
        this.m_iPeriodPayment = ad.m_iPeriodPayment;
        this.m_iAdditionalPrinciplePayment = ad.m_iAdditionalPrinciplePayment;
    }

    // Currency variables are in lowest denominator, id est cents/pennies
    // Will require conversion back to Dollars/Pounds Sterling/Euros etc
    // at display time with multiply by 100.
    public int m_iPeriod;
    public int m_iStartingBalance;
    public int m_iPeriodInterest;
    public int m_iRunningTotalInterest;
    public int m_iPrinciplePayment;
    public int m_iRunningTotalPrinciple;
    public int m_iRunningTotalPayment;
    public int m_iBalance;
    public int m_iPeriodPayment;
    public int m_iAdditionalPrinciplePayment;
}

