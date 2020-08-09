package com.paul.dbservice.domain.base;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class WithId<T extends WithId> {
    @Id
    private String nothing;

    private String id;

    @SuppressWarnings("unchecked")
    public T setNothing(String nothing) {
        this.nothing = nothing;
        return (T) this;
    }
}
