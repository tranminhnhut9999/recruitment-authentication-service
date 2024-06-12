package project.springboot.template.service.impl;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageImpl;
import vn.grooo.vietdang.entity.tuple.ICustomerResponseTuple;

import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerServiceImpl;


    @Test
    void shouldFoundCustomer() {
        ICustomerResponseTuple iCustomerResponseTuple = mock(ICustomerResponseTuple.class);
        when(iCustomerResponseTuple.getIsActive()).thenReturn(true);
        when(iCustomerResponseTuple.getAccountId()).thenReturn(123L);
        when(iCustomerResponseTuple.getCustomerId()).thenReturn(123L);
        when(iCustomerResponseTuple.getCode()).thenReturn("SA1");
        when(iCustomerResponseTuple.getName()).thenReturn("Test");
        when(iCustomerResponseTuple.getType()).thenReturn(ECustomerType.CUSTOMERS.toString());
        when(iCustomerResponseTuple.getUserName()).thenReturn("janedoe");

        ArrayList<ICustomerResponseTuple> iCustomerResponseTupleList = new ArrayList<>();
        iCustomerResponseTupleList.add(iCustomerResponseTuple);
        PageImpl<ICustomerResponseTuple> pageImpl = new PageImpl<>(iCustomerResponseTupleList);
//        when(this.customerRepository.findAllCustomer((String) any(), (String) any(),
//                (org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
//        ApiPage<GetCustomerResponse> actualSearchCustomerResult = this.customerServiceImpl.searchAllCustomer("55", ECustomerType.CUSTOMERS,
//                null);
//
//        assertEquals(123L, actualSearchCustomerResult.getItems().get(0).getName());

    }
}

