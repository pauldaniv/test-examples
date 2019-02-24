package com.paul.dealer.service
        ;

import com.paul.dealer.domain.TestEntity;
import com.paul.dealer.persintence.DefaultRepository;
import com.paul.library.payload.TestEntityDto;
import org.springframework.stereotype.Service;

@Service
public class DefaultService extends AbstractCommonService<TestEntityDto, TestEntity, DefaultRepository> {
    public DefaultService(DefaultRepository repository) {
        super(repository);
    }
}
