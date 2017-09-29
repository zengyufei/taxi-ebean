package com.ys.common.base.entiry;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ys.common.base.ExtensionFeatures;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.DbJson;
import lombok.*;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.MappedSuperclass;

@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public abstract class FeaturesAbstractEntity<T> extends DateTimeAbstractEntity<T> implements ExtensionFeatures {

    /**特征对象JSON字段*/
    @DbJson
    @DbComment("额外字段")
    @Setter(value = AccessLevel.NONE)
    @Getter(value = AccessLevel.NONE)
    protected JSONObject features = new JSONObject();

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

}