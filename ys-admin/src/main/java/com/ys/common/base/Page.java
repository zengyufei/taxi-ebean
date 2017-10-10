package com.ys.common.base;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

@Data
@Accessors(chain=true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Page  {

	int totalCount;
	int totalPageCount;
	int pageNo;
	int pageSize;
	Object dataList;

}
