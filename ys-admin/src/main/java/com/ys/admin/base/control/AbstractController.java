package com.ys.admin.base.control;

import com.ys.common.annotations.*;
import com.ys.common.base.Page;
import com.ys.common.base.entiry.IdAbstractEntity;
import com.zyf.result.Msg;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.PagedList;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

public abstract class AbstractController<T extends IdAbstractEntity> {

	protected Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	@ApiOperation(value = "条件查询分页，不包括删除", notes = "条件查询分页，不包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParams({
			@ApiImplicitParam(value = "条件", name = "t", paramType = "query"),
			@ApiImplicitParam(value = "当前页", name = "pageNo", defaultValue = "1", paramType = "query", dataType = "int"),
			@ApiImplicitParam(value = "每页页数", name = "pageSize", defaultValue = "1", paramType = "query", dataType = "int"),
	})
	@GetMapping("queryPage")
	public Msg queryPage(T t,
	                     @RequestParam(required = false, defaultValue = "1") int pageNo,
	                     @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(t), pageNo, pageSize));
	}

	@ApiOperation(value = "条件查询分页，包括删除", notes = "条件查询分页，包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParams({
			@ApiImplicitParam(value = "条件", name = "t", paramType = "query"),
			@ApiImplicitParam(value = "当前页", name = "pageNo", defaultValue = "1", paramType = "query", dataType = "int"),
			@ApiImplicitParam(value = "每页页数", name = "pageSize", defaultValue = "1", paramType = "query", dataType = "int"),
	})
	@GetMapping("queryPaged")
	public Msg queryPaged(T t,
	                      @RequestParam(required = false, defaultValue = "1") int pageNo,
	                      @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(t).setIncludeSoftDeletes().where(), pageNo, pageSize));
	}

	protected void setParams(ExpressionList where, T t) {
		for (Field field : t.getClass().getDeclaredFields()) {
			OptionField optionField = field.getAnnotation(OptionField.class);
			OptionFieldLike optionFieldLike = field.getAnnotation(OptionFieldLike.class);
			OptionFieldLikeLeft optionFieldLikeLeft = field.getAnnotation(OptionFieldLikeLeft.class);
			OptionFieldLikeRight optionFieldLikeRight = field.getAnnotation(OptionFieldLikeRight.class);
			boolean isOptionField = optionField != null;
			boolean isOptionFieldLike = optionFieldLike != null || optionFieldLikeLeft != null || optionFieldLikeRight != null;
			if (isOptionField || isOptionFieldLike) {
				String val = getValue(t, field);
				if (val != null) {
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

	protected void setParams(ExpressionList where, Object obj) {
		for (Field field : obj.getClass().getDeclaredFields()) {
			OptionField optionField = field.getAnnotation(OptionField.class);
			OptionFieldLike optionFieldLike = field.getAnnotation(OptionFieldLike.class);
			OptionFieldIn optionFieldIn = field.getAnnotation(OptionFieldIn.class);
			OptionFieldLikeLeft optionFieldLikeLeft = field.getAnnotation(OptionFieldLikeLeft.class);
			OptionFieldLikeRight optionFieldLikeRight = field.getAnnotation(OptionFieldLikeRight.class);
			boolean isOptionField = optionField != null;
			boolean isOptionFieldLike = optionFieldLike != null || optionFieldLikeLeft != null || optionFieldLikeRight != null;
			if (isOptionField || isOptionFieldLike) {
				String val = getValue(obj, field);
				if (val != null) {
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
			} else if (optionFieldIn != null) {
				String val = getValue(obj, field);
				if (val != null) {
					where.in(optionFieldIn.fieldName(), val);
				}
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
