package com.ys.common.base.entiry;

import com.zyf.valid.DeleteById;
import com.zyf.valid.QueryById;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public abstract class BaseParentIdEntity<T> extends BaseEntity<T> {

    @DbComment("父id")
    @Min(value = 1, message = "id 不能为空", groups = { QueryById.class, Update.class, DeleteById.class })
    private Long parentId;
}