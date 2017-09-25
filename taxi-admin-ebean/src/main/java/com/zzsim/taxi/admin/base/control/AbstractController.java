package com.zzsim.taxi.admin.base.control;

import com.zzsim.taxi.core.common.annotations.OptionField;
import com.zzsim.taxi.core.common.annotations.OptionFieldLike;
import com.zzsim.taxi.core.common.annotations.OptionFieldLikeLeft;
import com.zzsim.taxi.core.common.annotations.OptionFieldLikeRight;
import com.zzsim.taxi.core.common.base.Page;
import com.zzsim.taxi.core.common.base.entiry.IdAbstractEntity;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.PagedList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

public abstract class AbstractController<T extends IdAbstractEntity> {

	protected Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	protected void setParams(ExpressionList where, T t) {
		for (Field field : t.getClass().getDeclaredFields()) {
			OptionField optionField = field.getAnnotation(OptionField.class);
			OptionFieldLike optionFieldLike = field.getAnnotation(OptionFieldLike.class);
			OptionFieldLikeLeft optionFieldLikeLeft = field.getAnnotation(OptionFieldLikeLeft.class);
			OptionFieldLikeRight optionFieldLikeRight = field.getAnnotation(OptionFieldLikeRight.class);
			boolean isOptionField = optionField != null;
			boolean isOptionFieldLike = optionFieldLike != null || optionFieldLikeLeft != null || optionFieldLikeRight != null;
			if (isOptionField || isOptionFieldLike) {
				Method method = null;
				try {
					method = t.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase().concat(field.getName().substring(1)));
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				}
				Object value = null;
				try {
					value = method.invoke(t);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
				Optional<Object> optional = Optional.ofNullable(value);
				if (optional.isPresent()) {
					String val = String.valueOf(value).trim();
					if (isOptionFieldLike) {
						if (optionFieldLike != null)
							val = "%" + val + "%";
						else if (optionFieldLikeLeft != null)
							val = val + "%";
						else if (optionFieldLikeRight != null)
							val = "%" + val;
						where.ilike(field.getName(), val);
					} else {
						where.eq(field.getName(), val);
					}
				}
			}
		}
	}

	protected ExpressionList<?> setParams(T t) {
		ExpressionList<T> where = Ebean.find(entityClass).where();
		setParams(where, t);
		return where;
	}

	protected Page setPage(ExpressionList where, int pageNo, int pageSize) {
		PagedList<?> pagedList = where
				.setFirstRow((pageNo - 1) * pageSize)
				.setMaxRows(pageNo * pageSize)
				.findPagedList();
		Page page = new Page();
		page.setDataList(pagedList.getList());
		page.setPageNo(pagedList.getPageIndex() + 1);
		page.setPageSize(pagedList.getPageSize());
		page.setTotalCount(pagedList.getTotalCount());
		page.setTotalPageCount(pagedList.getTotalPageCount());
		return page;
	}

}
