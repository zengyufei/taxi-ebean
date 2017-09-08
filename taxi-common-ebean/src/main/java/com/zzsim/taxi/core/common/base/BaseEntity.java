package com.zzsim.taxi.core.common.base;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.ebean.Model;
import io.ebean.annotation.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.persistence.*;

@MappedSuperclass
public class BaseEntity extends Model implements ExtensionFeatures {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @SoftDelete
    private boolean flag;

    /**特征对象JSON字段*/
    protected JSONObject features = new JSONObject();

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")//页面输出时格式化
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @WhenCreated
    @CreatedTimestamp
	@Temporal(TemporalType.TIME)
    protected LocalDateTime createTime;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @WhenModified
    @UpdatedTimestamp
    @Temporal(TemporalType.TIME)
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
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