package customer.management.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import customer.management.entity.Customer;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer>{

}