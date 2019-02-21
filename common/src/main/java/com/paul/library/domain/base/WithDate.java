package com.paul.library.domain.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static lombok.AccessLevel.NONE;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@MappedSuperclass
public abstract class WithDate<T extends WithDate> extends WithId<T> {

  @CreatedDate
  @Setter(NONE)
  private LocalDateTime createdTime;

  @LastModifiedDate
  @Setter(NONE)
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
