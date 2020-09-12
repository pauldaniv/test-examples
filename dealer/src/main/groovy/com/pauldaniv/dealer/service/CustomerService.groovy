package com.pauldaniv.dealer.service

import com.pauldaniv.common.payload.CustomerDto
import com.pauldaniv.dealer.domain.Customer

interface CustomerService extends CommonService<CustomerDto, Customer> {}
