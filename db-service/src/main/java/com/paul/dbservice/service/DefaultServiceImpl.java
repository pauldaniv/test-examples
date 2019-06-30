package com.paul.dbservice.service;

import com.paul.common.component.Mapper;
import com.paul.common.payload.Resp;
import com.paul.common.payload.TestEntityDto;
import com.paul.dbservice.domain.TestEntity;
import com.paul.dbservice.persistence.DefaultRepository;
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
        TestEntity one = repository.findById(id).orElseThrow();
        one.setFirstName(one.getFirstName().toUpperCase());
        return Resp.ok(map.map(one, getDtoType()));
    }
}
