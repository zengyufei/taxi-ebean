package com.ys.admin.base.control;

import com.ys.common.annotations.VoFieldIn;
import com.ys.common.annotations.VoFieldLike;
import com.ys.common.annotations.VoFieldLikeLeft;
import com.ys.common.annotations.VoFieldLikeRight;
import com.ys.common.base.Page;
import com.ys.common.base.entiry.AbstractIdEntity;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.PagedList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public abstract class AbstractController<T extends AbstractIdEntity> {

	protected Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	protected void setParams(ExpressionList where, T t) {
		for (Field field : t.getClass().getDeclaredFields()) {
			String regex = "ebean|^_|serialVersionUID";
			Pattern compile = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			if (compile.matcher(field.getName()).find()) {
				continue;
			}
			String val = getValue(t, field);
			if (val == null) {
				continue;
			}
			appendSql(where, field, val);
		}
	}

	protected void setParams(ExpressionList where, Object obj) {
		for (Field field : obj.getClass().getDeclaredFields()) {
			String regex = "ebean|^_|serialVersionUID";
			Pattern compile = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
			if (compile.matcher(field.getName()).find()) {
				continue;
			}
			String val = getValue(obj, field);
			if (val == null) {
				continue;
			}
			appendSql(where, field, val);
		}
	}

	private void appendSql(ExpressionList where, Field field, String val) {
		VoFieldLike optionFieldLike = field.getAnnotation(VoFieldLike.class);
		VoFieldIn optionFieldIn = field.getAnnotation(VoFieldIn.class);
		VoFieldLikeLeft optionFieldLikeLeft = field.getAnnotation(VoFieldLikeLeft.class);
		VoFieldLikeRight optionFieldLikeRight = field.getAnnotation(VoFieldLikeRight.class);
		boolean isOptionFieldLike = optionFieldLike != null || optionFieldLikeLeft != null || optionFieldLikeRight != null;
		String regex = "[]";
		if (isOptionFieldLike) {
			if (optionFieldLike != null) {
				val = "%" + val + "%";
			} else if (optionFieldLikeLeft != null) {
				val = val + "%";
			} else if (optionFieldLikeRight != null) {
				val = "%" + val;
			}
			where.ilike(field.getName(), val);
		} else if (optionFieldIn != null) {
			if(!regex.equals(val)){
				if(field.getType().equals(List.class)){
					String substring = val.substring(1, val.length() - 1);
					where.in(optionFieldIn.fieldName(), substring.split(","));
				}else{
					where.in(optionFieldIn.fieldName(), val.split(","));
				}
			}
		} else {
			if(!regex.equals(val)){
				where.eq(field.getName(), val);
			}
		}
	}

	private String getValue(Object obj, Field field) {
		Method method = null;
		try {
			method = obj.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase().concat(field.getName().substring(1)));
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		Object value = null;
		try {
			value = method.invoke(obj);
		} catch (IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		Optional<Object> optional = Optional.ofNullable(value);
		String val = null;
		if (optional.isPresent()) {
			val = String.valueOf(value).trim();
		}
		return val;
	}

	protected ExpressionList<?> setParams(T t) {
		ExpressionList<T> where = Ebean.find(entityClass).where();
		setParams(where, t);
		return where;
	}

	protected ExpressionList<?> setParams(Object obj) {
		ExpressionList<T> where = Ebean.find(entityClass).where();
		setParams(where, obj);
		return where;
	}

	protected Page setPage(ExpressionList where, int pageNo, int pageSize) {
		PagedList<?> pagedList = where
				.setFirstRow((pageNo - 1) * pageSize)
				.setMaxRows(pageSize)
				.findPagedList();
		Page page = new Page();
		page.setDataList(pagedList.getList());
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotalCount(pagedList.getTotalCount());
		page.setTotalPageCount(pagedList.getTotalPageCount());
		return page;
	}

}
