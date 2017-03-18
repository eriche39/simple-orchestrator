/*
 * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *
 * This software is licensed under
 *
 * MIT license
 *
 */

package com.github.ehe.simpleorchestrator.sample.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class Person {
    @NotNull
    private String name;

    @NotNull
    @Pattern(regexp = "^\\d{9}$")
    private String ssn;

    private Address address;

    public Person(String name, String ssn, Address address) {
        this.name = name;
        this.ssn = ssn;
        this.address = address;
    }

    public Person() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

}
