package co.uk.anddigital.customer.telecom.repository;

import co.uk.anddigital.customer.telecom.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
