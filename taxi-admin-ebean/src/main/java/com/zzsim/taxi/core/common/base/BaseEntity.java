package com.zzsim.taxi.core.common.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.zzsim.taxi.admin.validate.groups.DeleteById;
import com.zzsim.taxi.admin.validate.groups.QueryById;
import com.zzsim.taxi.admin.validate.groups.Update;
import io.ebean.Model;
import io.ebean.annotation.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity<T> extends Model implements ExtensionFeatures {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Min(value = 1, message = "id 不能为空", groups = { QueryById.class, Update.class, DeleteById.class })
    @NotNull(message = "id 不能为空", groups = { QueryById.class, Update.class, DeleteById.class })
    private Long id;

    @SoftDelete
    @DbComment("数据状态")
    @Column(columnDefinition = "tinyint(1)")
    private Boolean flag = false;

    /**特征对象JSON字段*/
    @DbJson
    @DbComment("额外字段")
    protected JSONObject features = new JSONObject();

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面输出时格式化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @WhenCreated
    @CreatedTimestamp
	@Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime not null")
    @DbComment("创建时间")
    protected LocalDateTime createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @WhenModified
    @UpdatedTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(columnDefinition = "datetime not null")
    @DbComment("修改时间")
    protected LocalDateTime updateTime;

    @Override
    public String getFeatures() {
        return features == null || features.size() == 0 ? null : JSON.toJSONString(features, SerializerFeature.UseISO8601DateFormat);
    }

    @Override
    public void setupFeature(String columnName, String value) {
        features.put(columnName, value);
    }

    @Override
    public void setupFeature(String columnName, Object value) {
        features.put(columnName, value);
    }

    @Override
    public void removeFeature(String columnName) {
        features.remove(columnName);
    }

    @Override
    public String getFeature(String columnName) {
        return features.getString(columnName);
    }

    @Override
    public <T> T getFeature(String columnName, Class<T> clz) {
        return features.getObject(columnName, clz);
    }

    @Override
    public void setFeatures(String features) {
        this.features = StringUtils.isBlank(features) ? new JSONObject() : JSONObject.parseObject(features, Feature.AllowISO8601DateFormat);
    }

    @Override
    public String toString() {
        JSONSerializer serializer = new JSONSerializer();
        serializer.config(SerializerFeature.QuoteFieldNames, false);
        serializer.config(SerializerFeature.UseISO8601DateFormat, true);
        serializer.write(this);
        return serializer.getWriter().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getFlag() {
        return flag;
    }

    public void setFlag(Boolean flag) {
        this.flag = flag;
    }

    public void setFeatures(JSONObject features) {
        this.features = features;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }
}