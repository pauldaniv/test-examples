package com.paul.dealer.domain.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@EqualsAndHashCode(callSuper = true)
@Data
@MappedSuperclass
public class WithDate<T extends WithDate> extends WithId<T> {


    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime modifiedTime;

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        this.createdTime = now;
        this.modifiedTime = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.modifiedTime = LocalDateTime.now(ZoneOffset.UTC);
    }
}
