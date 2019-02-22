package com.paul.library.domain.base

import lombok.Setter
import lombok.experimental.Accessors
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import java.time.LocalDateTime
import java.time.ZoneOffset

import static lombok.AccessLevel.NONE

@Accessors(chain = true)
@MappedSuperclass
abstract class WithDate<T extends WithDate> extends WithId<T> {

  @CreatedDate
  @Setter(NONE)
  LocalDateTime createdTime

  @LastModifiedDate
  @Setter(NONE)
  LocalDateTime modifiedTime

  @PrePersist
  void prePersist() {
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC)
    this.createdTime = now;
    this.modifiedTime = now;
  }

  @PreUpdate
  void preUpdate() {
    this.modifiedTime = LocalDateTime.now(ZoneOffset.UTC)
  }
}
