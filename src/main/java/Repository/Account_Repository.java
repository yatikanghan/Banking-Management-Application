package Repository;

import MyModel.Account;
import org.springframework.data.repository.CrudRepository;

public interface Account_Repository extends CrudRepository<Account,Integer> {
}
