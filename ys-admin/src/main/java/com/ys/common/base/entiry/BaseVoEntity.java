package com.ys.common.base.entiry;

import com.google.common.collect.Lists;
import com.ys.common.annotations.VoFieldIn;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.MappedSuperclass;
import java.util.List;

@Data
@Accessors(chain=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@MappedSuperclass
public class BaseVoEntity extends AbstractVoEntity{

	Long unitId;
	Long buildingId;
	Long communityId;

	@VoFieldIn(fieldName = "buildingId")
	List<Long> buildingIds = Lists.newArrayList();
	@VoFieldIn(fieldName = "unitId")
	List<Long> unitIds = Lists.newArrayList();

}