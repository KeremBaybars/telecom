package co.uk.anddigital.customer.telecom.controller;

import co.uk.anddigital.customer.telecom.model.Customer;
import co.uk.anddigital.customer.telecom.model.PhoneNumber;
import co.uk.anddigital.customer.telecom.service.PhoneNumberService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneNumberService phoneNumberService;

    @Before
    public void setUp() {
    }

    @Test
    public void getAllPhoneNumbers() throws Exception {
        Customer customerA = new Customer("customerA");
        customerA.setCustomerId(1L);
        Customer customerB = new Customer("customerB");
        customerB.setCustomerId(2L);

        PhoneNumber phoneNumberOne = new PhoneNumber(customerA, "12849506922", true);
        phoneNumberOne.setPhoneNumberId(1L);
        customerA.setPhoneNumbers(Collections.singletonList(phoneNumberOne));

        PhoneNumber phoneNumberTwo = new PhoneNumber(customerB, "93847291099", true);
        phoneNumberTwo.setPhoneNumberId(2L);
        PhoneNumber phoneNumberThree = new PhoneNumber(customerB, "74633920192", false);
        phoneNumberThree.setPhoneNumberId(3L);
        customerB.setPhoneNumbers(Arrays.asList(phoneNumberTwo, phoneNumberThree));

        given(phoneNumberService.getAllPhoneNumbers()).willReturn(Arrays.asList(phoneNumberOne, phoneNumberTwo, phoneNumberThree));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/phone-numbers/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].phoneNumberId").value(phoneNumberOne.getPhoneNumberId()))
                .andExpect(jsonPath("$[1].phoneNumberId").value(phoneNumberTwo.getPhoneNumberId()))
                .andExpect(jsonPath("$[2].phoneNumberId").value(phoneNumberThree.getPhoneNumberId()))
                .andExpect(jsonPath("$[0].number").value(phoneNumberOne.getNumber()))
                .andExpect(jsonPath("$[1].number").value(phoneNumberTwo.getNumber()))
                .andExpect(jsonPath("$[2].number").value(phoneNumberThree.getNumber()))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[1].active").value(true))
                .andExpect(jsonPath("$[2].active").value(false));
    }

    @Test
    public void getAllPhoneNumbersByCustomer() throws Exception {
        Customer customer = new Customer("customerA");
        customer.setCustomerId(1L);

        PhoneNumber phoneNumberOne = new PhoneNumber(customer, "93847291099", true);
        phoneNumberOne.setPhoneNumberId(1L);
        PhoneNumber phoneNumberTwo = new PhoneNumber(customer, "74633920192", false);
        phoneNumberTwo.setPhoneNumberId(2L);
        customer.setPhoneNumbers(Arrays.asList(phoneNumberOne, phoneNumberTwo));

        given(phoneNumberService.getAllPhoneNumbersByCustomer(1L)).willReturn(Arrays.asList(phoneNumberOne, phoneNumberTwo));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/customer/phone-numbers/{id}", 1L).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].phoneNumberId").value(phoneNumberOne.getPhoneNumberId()))
                .andExpect(jsonPath("$[1].phoneNumberId").value(phoneNumberTwo.getPhoneNumberId()))
                .andExpect(jsonPath("$[0].number").value(phoneNumberOne.getNumber()))
                .andExpect(jsonPath("$[1].number").value(phoneNumberTwo.getNumber()))
                .andExpect(jsonPath("$[0].active").value(true))
                .andExpect(jsonPath("$[1].active").value(false));
    }

    @Test
    public void updateActiveStateForPhoneNumber() throws Exception {
        Customer customerA = new Customer("customerA");
        customerA.setCustomerId(1L);

        PhoneNumber phoneNumber = new PhoneNumber(customerA, "12849506922", true);
        phoneNumber.setPhoneNumberId(1L);
        customerA.setPhoneNumbers(Collections.singletonList(phoneNumber));

        given(phoneNumberService.changeActiveStateForPhoneNumberById(1L, true)).willReturn(phoneNumber);

        assertNotNull(phoneNumber);
        assertEquals(1L, phoneNumber.getPhoneNumberId().longValue());
        assertTrue(phoneNumber.isActive());

        this.mockMvc.perform(MockMvcRequestBuilders.put("/customer/phone-numbers/{phoneNumberId}/set/state/{state}", phoneNumber.getPhoneNumberId(), true)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("phoneNumberId").value(phoneNumber.getPhoneNumberId()))
                .andExpect(jsonPath("number").value(phoneNumber.getNumber()))
                .andExpect(jsonPath("active").value(true));

    }
}