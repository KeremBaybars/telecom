package co.uk.anddigital.customer.telecom.repository;

import co.uk.anddigital.customer.telecom.model.Customer;
import co.uk.anddigital.customer.telecom.model.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {

    List<PhoneNumber> findAllByCustomer(Customer customer);

}
