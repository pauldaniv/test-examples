package com.paul.dealer.domain.base

import groovy.transform.ToString
import groovy.transform.builder.Builder
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate

import javax.persistence.MappedSuperclass
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import java.time.LocalDateTime
import java.time.ZoneOffset

@Builder
@MappedSuperclass
@ToString
class WithDate<T extends WithDate> extends WithId<T> {

  @CreatedDate
  private LocalDateTime createdTime

  @LastModifiedDate
  private LocalDateTime modifiedTime

  LocalDateTime getCreatedTime() {
    return createdTime
  }

  LocalDateTime getModifiedTime() {
    return modifiedTime
  }

  @PrePersist
  void prePersist() {
    LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC)
    this.createdTime = now
    this.modifiedTime = now
  }

  @PreUpdate
  void preUpdate() {
    this.modifiedTime = LocalDateTime.now(ZoneOffset.UTC)
  }
}
