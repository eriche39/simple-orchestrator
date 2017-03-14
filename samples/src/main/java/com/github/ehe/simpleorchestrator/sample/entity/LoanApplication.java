/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.entity;

/**
 * Created by eric on 3/12/17.
 */
public class LoanApplication {
    private Person person;
    private Address address;
    private String occupation;
    private int salary;
    private int loanAmount;

    public LoanApplication(Person person, Address address, String occupation, int salary, int loadAmount) {
        this.person = person;
        this.address = address;
        this.occupation = occupation;
        this.salary = salary;
        this.loanAmount = loadAmount;
    }

    public LoanApplication() {
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(int loanAmount) {
        this.loanAmount = loanAmount;
    }

}
