package com.paul.dealer.service

import com.paul.common.payload.CustomerDto
import com.paul.dealer.domain.Customer
import com.paul.dealer.persistence.CustomerRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class CustomerServiceImpl extends AbstractCommonService<CustomerDto, Customer, CustomerRepository> implements CustomerService {
  CustomerServiceImpl(CustomerRepository repository) {
    super(repository)
  }
}
