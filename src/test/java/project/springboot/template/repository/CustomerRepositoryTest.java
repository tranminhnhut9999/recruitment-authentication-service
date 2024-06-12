package project.springboot.template.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;
    Customer customer;

    @BeforeEach
    public void setUp() {
        Account account = new Account();
        account.setStatus(EAccountStatus.ALIVE);
        account.setUsername("huythanh");
        account.setPassword("123456789");
        account.setType(EAccountType.CUSTOMER);
        account.setIsActive(true);
        accountRepository.save(account);

        customer = new Customer();
        customer.setName("nameCustomer");
        customer.setCode("123");
        customer.setType(ECustomerType.CUSTOMERS);
        customer.setPhoneNumber1("123456");
        customer.setPhoneNumber2("654987");
        customer.setTaxCode("123456789");
        customer.setAccountStatus(EAccountStatus.ALIVE);
        customer.setAccount(account);
        customer = customerRepository.save(customer);


    }

    @Test
    void shouldFoundCustomer() {
        String keyword = "name";
//        Page<ICustomerResponseTuple> iCustomerResponseTuples = customerRepository.findAllPageable(keyword, ECustomerType.CUSTOMERS.toString(), Pageable.unpaged());
//        Assertions.assertThat(iCustomerResponseTuples.getContent().get(0).getCustomerId()).isEqualTo(1L);
    }

    @Test
    void shouldFoundNotFoundCustomerByKeyWord() {
        String keyword = "nothing";
//        Page<ICustomerResponseTuple> iCustomerResponseTuples = customerRepository.findAllPageable(keyword, ECustomerType.CUSTOMERS.toString(), Pageable.unpaged());
//        Assertions.assertThat(iCustomerResponseTuples.getContent().size()).isEqualTo(0);
    }
}

