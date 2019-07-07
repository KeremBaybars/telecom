package co.uk.anddigital.customer.telecom.service;

import co.uk.anddigital.customer.telecom.model.Customer;
import co.uk.anddigital.customer.telecom.model.PhoneNumber;
import co.uk.anddigital.customer.telecom.repository.CustomerRepository;
import co.uk.anddigital.customer.telecom.repository.PhoneNumberRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class PhoneNumberServiceTest {

    @Mock
    private PhoneNumberRepository phoneNumberRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private PhoneNumberService phoneNumberService;

    @Before
    public void setUp() {
    }

    @Test
    public void getAllPhoneNumbers() {
        //Given
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

        List<PhoneNumber> expectedPhoneNumbers = Arrays.asList(phoneNumberOne, phoneNumberTwo, phoneNumberThree);

        doReturn(expectedPhoneNumbers).when(phoneNumberRepository).findAll();

        //When
        List<PhoneNumber> actualPhoneNumbers = phoneNumberService.getAllPhoneNumbers();

        //Then
        assertThat(actualPhoneNumbers).isEqualTo(expectedPhoneNumbers);
    }

    @Test
    public void getAllPhoneNumbersByCustomer() {
        //Given
        Customer customer = new Customer("customerA");
        customer.setCustomerId(1L);

        PhoneNumber phoneNumberOne = new PhoneNumber(customer, "93847291099", true);
        phoneNumberOne.setPhoneNumberId(1L);
        PhoneNumber phoneNumberTwo = new PhoneNumber(customer, "74633920192", false);
        phoneNumberTwo.setPhoneNumberId(2L);
        customer.setPhoneNumbers(Arrays.asList(phoneNumberOne, phoneNumberTwo));

        List<PhoneNumber> expectedPhoneNumbers = Arrays.asList(phoneNumberOne, phoneNumberTwo);

        Customer expectedCustomer = customerRepository.getOne(1L);
        doReturn(expectedPhoneNumbers).when(phoneNumberRepository).findAllByCustomer(expectedCustomer);

        //When
        List<PhoneNumber> actualPhoneNumbers = phoneNumberService.getAllPhoneNumbersByCustomer(1L);

        //Then
        assertThat(actualPhoneNumbers).isEqualTo(expectedPhoneNumbers);
    }

}