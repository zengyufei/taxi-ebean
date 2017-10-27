package com.ys.common.base.entiry;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.SoftDelete;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Data
@Accessors(chain=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public class AbstractVoEntity extends AbstractIdEntity{

	@SoftDelete
	@DbComment("数据状态")
	@Column(columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean deleted = false;

	@Override
	public String toString() {
		JSONSerializer serializer = new JSONSerializer();
		serializer.config(SerializerFeature.QuoteFieldNames, false);
		serializer.config(SerializerFeature.UseISO8601DateFormat, true);
		serializer.write(this);
		return serializer.getWriter().toString();
	}

}