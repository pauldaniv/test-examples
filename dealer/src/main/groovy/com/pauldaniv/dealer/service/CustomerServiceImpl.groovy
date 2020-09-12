package com.pauldaniv.dealer.service

import com.pauldaniv.common.payload.CustomerDto
import com.pauldaniv.dealer.domain.Customer
import com.pauldaniv.dealer.persistence.CustomerRepository
import lombok.RequiredArgsConstructor
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class CustomerServiceImpl extends AbstractCommonService<CustomerDto, Customer, CustomerRepository> implements CustomerService {
  CustomerServiceImpl(CustomerRepository repository) {
    super(repository)
  }
}
