/*
 *
 *  * Copyright (c) 2017. Eric He (eriche39@gmail.com)
 *  *
 *  * This software is licensed under
 *  *
 *  * MIT license
 *  *
 *
 */

package com.github.ehe.simpleorchestrator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ehe.simpleorchestrator.meta.Meta;
import com.github.ehe.simpleorchestrator.sample.SampleApplication;
import com.github.ehe.simpleorchestrator.sample.entity.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = {SampleApplication.class})
public class SamplesApplicationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    private static CardApplication blackListCardApplication;
    private static CardApplication ssn4CardApplication;
    private static CardApplication debitCardApplication;
    private static LoanApplication loanApplication;
    private static LoanApplication invalidLoanApplication;

    @BeforeClass
    public static void setup(){
        Person backListedPerson = new Person("lawrence", "999999999", new Address(null,null, "92131"));
        Person ssn4Person = new Person("lawrence4", "424222444", new Address(null,null, "92131"));
        Person ssnInvalidPerson = new Person("lawrence", "abc", new Address(null,null, "92131"));

        blackListCardApplication =
                new CardApplication(backListedPerson,"engineer", true, CardApplication.CardType.Credit);
        ssn4CardApplication =
                new CardApplication(ssn4Person,"engineer", true, CardApplication.CardType.Credit);
        debitCardApplication =
                new CardApplication(ssn4Person,"engineer", true, CardApplication.CardType.Debit);
        loanApplication =
                new LoanApplication(ssn4Person, null, "engineer", 90000, 200000);
        invalidLoanApplication =
                new LoanApplication(ssnInvalidPerson, null, "engineer", 90000, 200000);

    }

    //test blacklisted ssn has checked and logged
    @Test
    public void blackListed() {
        ResponseEntity<ApplicationResult> entity = this.restTemplate.postForEntity("/application/card",
                blackListCardApplication, ApplicationResult.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody().getHistory().contains("riskCheck: blacklisted")).isTrue();
    }


    //test ssn is not blacklisted and application status is false
    @Test
    public void noblackListed() {
        ResponseEntity<ApplicationResult> entity = this.restTemplate.postForEntity("/application/card",
                ssn4CardApplication, ApplicationResult.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(!entity.getBody().getHistory().contains("riskCheck: blacklisted")).isTrue();
        assertThat(entity.getBody().isApproved()).isFalse();
    }

    //test debit application approved and debitTask is logged
    @Test
    public void debitCardApplication() {
        ResponseEntity<ApplicationResult> entity = this.restTemplate.postForEntity("/application/card",
                debitCardApplication, ApplicationResult.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody().getHistory().contains("com.github.ehe.simpleorchestrator.sample.task.DebitCardTask")).isTrue();
        assertThat(entity.getBody().isApproved()).isTrue();
    }

    //test loan application not approved
    @Test
    public void loanApplication() {
        ResponseEntity<ApplicationResult> entity = this.restTemplate.postForEntity("/application/loan",
                loanApplication, ApplicationResult.class);
        assertThat(entity.getBody().isApproved()).isFalse();
    }

    //test invalid application return errors
    @Test
    public void invalidApplication() {
        ResponseEntity<ApplicationResult> entity = this.restTemplate.postForEntity("/application/loan",
                invalidLoanApplication, ApplicationResult.class);
        assertThat(entity.getBody().getErrors().size()>0).isTrue();
    }

    //test dev admin to get meta info
    @Test
    public void metaApplication() throws JsonProcessingException {
        ResponseEntity<List> entity = this.restTemplate.getForEntity("/application/admin/orchestrators",
               List.class);
        List<Meta> list = entity.getBody();
        System.out.println(new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(list));
        assertNotNull(list);
        //two orchestrators: loan and card
        assertEquals(list.size(),2);
    }
}
