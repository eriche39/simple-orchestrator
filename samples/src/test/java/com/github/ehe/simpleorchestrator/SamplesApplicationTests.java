package com.github.ehe.simpleorchestrator;

import com.github.ehe.simpleorchestrator.sample.SampleApplication;
import com.github.ehe.simpleorchestrator.sample.entity.*;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

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
                new CardApplication(backListedPerson,"eignieer", true, CardApplication.CardType.Credit);
        ssn4CardApplication =
                new CardApplication(ssn4Person,"eignieer", true, CardApplication.CardType.Credit);
        debitCardApplication =
                new CardApplication(ssn4Person,"eignieer", true, CardApplication.CardType.Debit);
        loanApplication =
                new LoanApplication(ssn4Person, null, "eignieer", 90000, 200000);
        invalidLoanApplication =
                new LoanApplication(ssnInvalidPerson, null, "eignieer", 90000, 200000);

    }

    //test blacklisted ssn has checked and logged
    @Test
    public void blackListed() {
        ResponseEntity<AppliationResult> entity = this.restTemplate.postForEntity("/application/card",
                blackListCardApplication, AppliationResult.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody().getHistory().contains("riskCheck: blacklisted")).isTrue();
    }


    //test ssn is not blacklisted and application status is false
    @Test
    public void noblackListed() {
        ResponseEntity<AppliationResult> entity = this.restTemplate.postForEntity("/application/card",
                ssn4CardApplication, AppliationResult.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(!entity.getBody().getHistory().contains("riskCheck: blacklisted")).isTrue();
        assertThat(entity.getBody().isApproved()).isFalse();
    }

    //test debit application approved and debitTask is logged
    @Test
    public void debitCardApplication() {
        ResponseEntity<AppliationResult> entity = this.restTemplate.postForEntity("/application/card",
                debitCardApplication, AppliationResult.class);
        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody().getHistory().contains("com.github.ehe.simpleorchestrator.sample.task.DebitCardTask")).isTrue();
        assertThat(entity.getBody().isApproved()).isTrue();
    }

    //test loan application not approved
    @Test
    public void loanApplication() {
        ResponseEntity<AppliationResult> entity = this.restTemplate.postForEntity("/application/loan",
                loanApplication, AppliationResult.class);
        assertThat(entity.getBody().isApproved()).isFalse();
    }

    //test invalid application return errors
    @Test
    public void invalidApplication() {
        ResponseEntity<AppliationResult> entity = this.restTemplate.postForEntity("/application/loan",
                invalidLoanApplication, AppliationResult.class);
        assertThat(entity.getBody().getErrors().size()>0).isTrue();
    }
}
