package com.zzsim.taxi.core.common.base.entiry;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.persistence.MappedSuperclass;

@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public abstract class BaseEntity<T> extends AbstractEntity<T> {

}