package com.paul.dealer.service

import com.paul.dealer.persintence.CustomerRepository
import com.paul.library.component.Mapper
import com.paul.library.payload.CustomerDto
import lombok.RequiredArgsConstructor
import org.hibernate.ObjectNotFoundException
import org.springframework.stereotype.Service

@Service
@RequiredArgsConstructor
class CustomerService {
  final CustomerRepository customerRepository
  final Mapper mapper

  CustomerDto findOne(Long id) {
    mapper.map(customerRepository.findById(id).orElseThrow({new ObjectNotFoundException(id, "customer")}), CustomerDto)
  }

}
