package com.ys.common.base.entiry;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.annotation.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain=true)
@FieldDefaults(level = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AbstractDateTimeEntity<T> extends AbstractIdEntity<T> {

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面输出时格式化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @WhenCreated
    @CreatedTimestamp
	@Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP")
    @DbComment("创建时间")
    LocalDateTime createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @WhenModified
    @UpdatedTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @DbComment("修改时间")
    LocalDateTime updateTime;

    @Override
    public String toString() {
        JSONSerializer serializer = new JSONSerializer();
        serializer.config(SerializerFeature.QuoteFieldNames, false);
        serializer.config(SerializerFeature.UseISO8601DateFormat, true);
        serializer.write(this);
        return serializer.getWriter().toString();
    }

}