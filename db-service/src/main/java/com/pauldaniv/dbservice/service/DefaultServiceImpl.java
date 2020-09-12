package com.pauldaniv.dbservice.service;

import com.pauldaniv.common.component.Mapper;
import com.pauldaniv.common.payload.Resp;
import com.pauldaniv.dbservice.domain.TestEntity;
import com.pauldaniv.dbservice.domain.dto.TestEntityDto;
import com.pauldaniv.dbservice.persistence.DefaultRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class DefaultServiceImpl extends AbstractCommonService<TestEntityDto, TestEntity, DefaultRepository> implements DefaultService {

    private DefaultRepository repository;
    private Mapper map;

    DefaultServiceImpl(DefaultRepository repository, Mapper map) {
        super(repository);
        this.repository = repository;
        this.map = map;
    }

    @Override
    public ResponseEntity<Resp<TestEntityDto>> getOne(final String id) {
        TestEntity one = repository.findById(id).orElseThrow(RuntimeException::new);
        one.setFirstName(one.getFirstName().toUpperCase());
        return Resp.ok(map.map(one, getDtoType()));
    }
}
