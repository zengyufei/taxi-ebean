package com.ys.common.base.entiry;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.SoftDelete;
import io.ebean.annotation.WhoCreated;
import io.ebean.annotation.WhoModified;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain=true)
@FieldDefaults(level = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AbstractDeletedEntity<T> extends AbstractFeaturesEntity<T> {

    @SoftDelete
    @DbComment("数据状态")
    @Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
    boolean deleted = false;

    @DbComment("创建者")
    @WhoCreated
    @JSONField(serialize = false)
    String whoCreated;

    @DbComment("修改者")
    @WhoModified
    @JSONField(serialize = false)
    String whoModified;

    @Override
    public String toString() {
        JSONSerializer serializer = new JSONSerializer();
        serializer.config(SerializerFeature.QuoteFieldNames, false);
        serializer.config(SerializerFeature.UseISO8601DateFormat, true);
        serializer.write(this);
        return serializer.getWriter().toString();
    }

}