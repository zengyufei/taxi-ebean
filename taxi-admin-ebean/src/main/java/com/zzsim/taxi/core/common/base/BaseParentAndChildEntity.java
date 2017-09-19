package com.zzsim.taxi.core.common.base;

import com.zzsim.taxi.admin.validate.groups.DeleteById;
import com.zzsim.taxi.admin.validate.groups.QueryById;
import com.zzsim.taxi.admin.validate.groups.Update;
import io.ebean.annotation.DbComment;
import lombok.Data;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;

@Data
@MappedSuperclass
public class BaseParentAndChildEntity<T> extends BaseEntity<T> {

    @DbComment("父id")
    @Min(value = 1, message = "id 不能为空", groups = { QueryById.class, Update.class, DeleteById.class })
    private Long parentId;
}