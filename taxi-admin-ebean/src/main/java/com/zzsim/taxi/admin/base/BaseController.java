package com.zzsim.taxi.admin.base;

import com.zzsim.taxi.core.common.base.Page;
import com.zzsim.taxi.core.common.entitys.rbac.SysOrg;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.PagedList;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public abstract class BaseController {

	protected void setParams(ExpressionList where, Object obj){
		for (Field field : obj.getClass().getDeclaredFields()) {
			Method method = null;
			try {
				method = obj.getClass().getMethod("get" + field.getName().substring(0, 1).toUpperCase().concat(field.getName().substring(1)));
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			Object value = null;
			try {
				value = method.invoke(obj);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			if(value != null){
				where.eq(field.getName(), value);
			}
		}
	}

	protected ExpressionList<?> setParams(Class<?> t, Object obj) {
		ExpressionList<?> where = Ebean.find(t).where();
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

	protected Page setPage(PagedList pagedList) {
		Page page = new Page();
		page.setDataList(pagedList.getList());
		page.setPageNo(pagedList.getPageIndex() + 1);
		page.setPageSize(pagedList.getPageSize());
		page.setTotalCount(pagedList.getTotalCount());
		page.setTotalPageCount(pagedList.getTotalPageCount());
		return page;
	}
}
