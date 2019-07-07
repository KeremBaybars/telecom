package co.uk.anddigital.customer.telecom.service;

import co.uk.anddigital.customer.telecom.model.Customer;
import co.uk.anddigital.customer.telecom.model.PhoneNumber;
import co.uk.anddigital.customer.telecom.repository.CustomerRepository;
import co.uk.anddigital.customer.telecom.repository.PhoneNumberRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class PhoneNumberService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhoneNumberService.class);

    private PhoneNumberRepository phoneNumberRepository;
    private CustomerRepository customerRepository;

    @Autowired
    public PhoneNumberService(PhoneNumberRepository phoneNumberRepository, CustomerRepository customerRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
        this.customerRepository = customerRepository;

        populateCustomers();
    }

    private void populateCustomers() {
        Customer customerA = new Customer("customerA");
        Customer customerB = new Customer("customerB");

        PhoneNumber phoneNumberOne = new PhoneNumber(customerA, "12849506922", true);
        customerA.setPhoneNumbers(Collections.singletonList(phoneNumberOne));

        PhoneNumber phoneNumberTwo = new PhoneNumber(customerB, "93847291099", true);
        PhoneNumber phoneNumberThree = new PhoneNumber(customerB, "74633920192", false);
        customerB.setPhoneNumbers(Arrays.asList(phoneNumberTwo, phoneNumberThree));

        customerRepository.saveAll(Arrays.asList(customerA, customerB));
    }

    public List<PhoneNumber> getAllPhoneNumbers() {
        try {
            return phoneNumberRepository.findAll();
        } catch (Exception e) {
            LOGGER.error("Could not fetch any phone numbers.", e);

            return Collections.emptyList();
        }
    }

    public List<PhoneNumber> getAllPhoneNumbersByCustomer(Long id) {
        try {
            return phoneNumberRepository.findAllByCustomer(customerRepository.getOne(id));
        } catch (Exception e) {
            LOGGER.error("Could not fetch any phone numbers for customer with id: {}", id, e);

            return Collections.emptyList();
        }
    }

    public PhoneNumber changeActiveStateForPhoneNumberById(Long id, boolean state) {
        try {
            PhoneNumber phoneNumber = phoneNumberRepository.getOne(id);

            phoneNumber.setActive(state);

            return phoneNumberRepository.save(phoneNumber);
        } catch (Exception e) {
            LOGGER.error("Could not change active state for phone number with the following id: {}", id, e);

            return null;
        }
    }

}
