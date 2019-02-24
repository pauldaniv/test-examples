package com.paul.dealer.service

import com.paul.dealer.domain.Customer
import com.paul.dealer.persintence.CustomerRepository
import com.paul.library.payload.CustomerDto
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class CustomerService extends AbstractCommonService<CustomerDto, Customer, CustomerRepository> {
  CustomerService(CustomerRepository repository) {
    super(repository)
  }
}
