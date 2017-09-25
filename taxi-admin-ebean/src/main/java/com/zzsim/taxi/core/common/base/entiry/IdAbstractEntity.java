package com.zzsim.taxi.core.common.base.entiry;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.zzsim.taxi.admin.validate.groups.DeleteById;
import com.zzsim.taxi.admin.validate.groups.QueryById;
import com.zzsim.taxi.admin.validate.groups.Update;
import io.ebean.Model;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public abstract class IdAbstractEntity<T> extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "id 不能为空", groups = { QueryById.class, Update.class, DeleteById.class })
    @NotNull(message = "id 不能为空", groups = { QueryById.class, Update.class, DeleteById.class })
    private Long id;

    @Override
    public String toString() {
        JSONSerializer serializer = new JSONSerializer();
        serializer.config(SerializerFeature.QuoteFieldNames, false);
        serializer.config(SerializerFeature.UseISO8601DateFormat, true);
        serializer.write(this);
        return serializer.getWriter().toString();
    }

}