package Repository;

import MyModel.Customer;
import org.springframework.data.repository.CrudRepository;

public interface Customer_Repository extends CrudRepository<Customer,Integer> {
}
