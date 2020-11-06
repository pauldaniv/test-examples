package com.pauldaniv.kafka.handling.tx.repository

import com.pauldaniv.kafka.common.model.Foo
import org.springframework.data.repository.CrudRepository

interface FooRepository : CrudRepository<Foo, Long>
