package co.uk.anddigital.customer.telecom.controller;

import co.uk.anddigital.customer.telecom.model.PhoneNumber;
import co.uk.anddigital.customer.telecom.service.PhoneNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private PhoneNumberService phoneNumberService;

    @Autowired
    public CustomerController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    @RequestMapping(value = "/phone-numbers/all", method = RequestMethod.GET)
    public List<PhoneNumber> getAllPhoneNumbers() {
        return phoneNumberService.getAllPhoneNumbers();
    }

    @RequestMapping(value = "/phone-numbers/{customerId}", method = RequestMethod.GET)
    public List<PhoneNumber> getAllPhoneNumbersByCustomer(@PathVariable Long customerId) {
        return phoneNumberService.getAllPhoneNumbersByCustomer(customerId);
    }

    @RequestMapping(value = "/phone-numbers/{phoneNumberId}/set/state/{state}", method = { RequestMethod.GET, RequestMethod.PUT })
    public PhoneNumber updateActiveStateForPhoneNumber(@PathVariable Long phoneNumberId, @PathVariable boolean state) {
        return phoneNumberService.changeActiveStateForPhoneNumberById(phoneNumberId, state);
    }

}
