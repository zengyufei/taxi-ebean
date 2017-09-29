package com.ys.common.base.entiry;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import javax.persistence.MappedSuperclass;

@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public abstract class AbstractEntity<T> extends FeaturesAbstractEntity<T> {

}