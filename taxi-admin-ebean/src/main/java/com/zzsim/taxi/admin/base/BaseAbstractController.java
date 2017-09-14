package com.zzsim.taxi.admin.base;

import com.zzsim.taxi.admin.validate.annotation.IdCheck;
import com.zzsim.taxi.admin.validate.groups.Insert;
import com.zzsim.taxi.admin.validate.groups.Update;
import com.zzsim.taxi.core.common.base.BaseEntity;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.base.Page;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.PagedList;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Optional;

@Validated
public abstract class BaseAbstractController<T extends BaseEntity, S> {

	private Class <T> entityClass = (Class <T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	@GetMapping("queryById")
	public Msg queryById(@IdCheck Long id) {
		return Msg.ok(Ebean.find(entityClass, id));
	}

	@GetMapping("queryByIded")
	public Msg queryByIded(@IdCheck Long id) {
		T unique = Ebean.find(entityClass).setIncludeSoftDeletes().setId(id).findUnique();
		return Msg.ok(unique);
	}

	@GetMapping("queryAll")
	public Msg queryAll() {
		return Msg.ok(Ebean.find(entityClass).findList());
	}

	@GetMapping("queryAlled")
	public Msg queryAlled() {
		return Msg.ok(Ebean.find(entityClass).setIncludeSoftDeletes().findList());
	}

	@GetMapping("queryPage")
	public Msg queryPage(S option,
						 @RequestParam(required = false, defaultValue = "1") int pageNo,
						 @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(entityClass, option), pageNo, pageSize));
	}

	@GetMapping("queryPaged")
	public Msg queryPaged(S option,
						  @RequestParam(required = false, defaultValue = "1") int pageNo,
						  @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(entityClass, option).setIncludeSoftDeletes().where(), pageNo, pageSize));
	}

	@PostMapping("insert")
	public Msg insert(@Validated(value = Insert.class) T obj) {
		obj.insert();
		return Msg.ok("新增成功");
	}

	@PostMapping("update")
	public Msg<String> update(@Validated(value = Update.class) T obj) {
		obj.update();
		return Msg.ok("修改成功");
	}

	@GetMapping("deleteById")
	public Msg deleteById(@IdCheck Long id) {
		Ebean.delete(entityClass, id);
		return Msg.ok("删除成功");
	}

	@GetMapping("deleteByIded")
	public Msg deleteByIded(@IdCheck Long id) {
		Ebean.deletePermanent(entityClass, id);
		return Msg.ok("物理删除成功");
	}

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
			Optional<Object> optional = Optional.ofNullable(value);
			if(optional.isPresent()){
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
