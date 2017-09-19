package com.zzsim.taxi.admin.base;

import com.zzsim.taxi.admin.validate.annotation.IdCheck;
import com.zzsim.taxi.admin.validate.groups.Insert;
import com.zzsim.taxi.admin.validate.groups.Update;
import com.zzsim.taxi.core.common.annotations.OptionField;
import com.zzsim.taxi.core.common.annotations.OptionFieldLike;
import com.zzsim.taxi.core.common.annotations.OptionFieldLikeLeft;
import com.zzsim.taxi.core.common.annotations.OptionFieldLikeRight;
import com.zzsim.taxi.core.common.base.BaseEntity;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.base.Page;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.PagedList;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Validated
public abstract class BaseController<T extends BaseEntity> {

	private Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	@ApiOperation(value = "查询单个，不包括删除", notes = "查询单个，不包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParam(value = "查询的 id", required = true, name = "id", defaultValue = "1", paramType = "query", dataType = "long")
	@GetMapping("queryById")
	public Msg queryById(@IdCheck Long id) {
		return Msg.ok(Ebean.find(entityClass, id));
	}

	@ApiOperation(value = "查询单个，包括删除", notes = "查询单个，包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParam(value = "查询的 id", required = true, name = "id", defaultValue = "1", paramType = "query", dataType = "long")
	@GetMapping("queryByIded")
	public Msg queryByIded(@IdCheck Long id) {
		T unique = Ebean.find(entityClass).setIncludeSoftDeletes().setId(id).findUnique();
		return Msg.ok(unique);
	}

	@ApiOperation(value = "查询所有，不包括删除", notes = "查询单个，不包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@GetMapping("queryAll")
	public Msg queryAll() {
		return Msg.ok(Ebean.find(entityClass).findList());
	}

	@ApiOperation(value = "查询所有，包括删除", notes = "查询所有，包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@GetMapping("queryAlled")
	public Msg queryAlled() {
		return Msg.ok(Ebean.find(entityClass).setIncludeSoftDeletes().findList());
	}

	@ApiOperation(value = "条件查询分页，不包括删除", notes = "条件查询分页，不包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParams({
		@ApiImplicitParam(value = "条件", required = false, name = "t", paramType = "query"),
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
			@ApiImplicitParam(value = "条件", required = false, name = "t", paramType = "query"),
			@ApiImplicitParam(value = "当前页", name = "pageNo", defaultValue = "1", paramType = "query", dataType = "int"),
			@ApiImplicitParam(value = "每页页数", name = "pageSize", defaultValue = "1", paramType = "query", dataType = "int"),
	})
	@GetMapping("queryPaged")
	public Msg queryPaged(T t,
						  @RequestParam(required = false, defaultValue = "1") int pageNo,
						  @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(t).setIncludeSoftDeletes().where(), pageNo, pageSize));
	}

	@ApiOperation(value = "新增", notes = "新增", httpMethod = "POST", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "新增成功", response = Msg.class)
	@ApiImplicitParam(value = "实体", required = true, name = "obj", paramType = "body")
	@PostMapping("insert")
	public Msg insert(@Validated(value = Insert.class) T obj) {
		obj.insert();
		return Msg.ok("新增成功");
	}

	@ApiOperation(value = "新增", notes = "新增", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "新增成功", response = Msg.class)
	@ApiImplicitParam(value = "实体", required = true, name = "obj")
	@PutMapping("insert")
	public Msg insertJSON(@Validated(value = Insert.class) @RequestBody T obj) {
		obj.insert();
		return Msg.ok("新增成功");
	}

	@ApiOperation(value = "修改", notes = "修改", httpMethod = "POST", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "修改成功", response = Msg.class)
	@ApiImplicitParam(value = "实体", required = true, name = "obj", paramType = "body")
	@PostMapping("update")
	public Msg update(@Validated(value = Update.class) T obj) {
		obj.update();
		return Msg.ok("修改成功");
	}

	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "修改成功", response = Msg.class)
	@ApiImplicitParam(value = "实体", required = true, name = "obj")
	@PatchMapping("update")
	public Msg updateJSON(@Validated(value = Update.class) @RequestBody T obj) {
		obj.update();
		return Msg.ok("修改成功");
	}

	@ApiOperation(value = "删除", notes = "删除", httpMethod = "GET", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "删除成功", response = Msg.class)
	@ApiImplicitParam(value = "序号", required = true, name = "id", paramType = "query")
	@GetMapping("remoteById")
	public Msg remoteById(@IdCheck Long id) {
		Ebean.delete(entityClass, id);
		return Msg.ok("删除成功");
	}

	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "删除成功", response = Msg.class)
	@ApiImplicitParam(value = "序号", required = true, name = "id")
	@DeleteMapping("remoteById")
	public Msg remoteByIdForDeleteMapping(@IdCheck Long id) {
		Ebean.delete(entityClass, id);
		return Msg.ok("删除成功");
	}

	@ApiOperation(value = "物理删除", notes = "物理删除", httpMethod = "GET", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "物理删除成功", response = Msg.class)
	@ApiImplicitParam(value = "序号", required = true, name = "id", paramType = "query")
	@GetMapping("deleteByIded")
	public Msg deleteByIded(@IdCheck Long id) {
		Ebean.deletePermanent(entityClass, id);
		return Msg.ok("物理删除成功");
	}

	@ApiOperation(value = "物理删除", notes = "物理删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "物理删除成功", response = Msg.class)
	@ApiImplicitParam(value = "序号", required = true, name = "id")
	@DeleteMapping("deleteByIded")
	public Msg deleteByIdForDeleteMapping(@IdCheck Long id) {
		Ebean.deletePermanent(entityClass, id);
		return Msg.ok("物理删除成功");
	}

	@ApiOperation(value = "批量查询，不包括删除", notes = "批量查询，不包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParam(value = "id 集合", name = "ids", defaultValue = "1,2,3", paramType = "query", dataType = "string")
	@GetMapping("queryByIds")
	public Msg queryByIds(@NotBlank(message = "id 集合不能为空") String ids) {
		List<T> list = Ebean.find(entityClass)
				.where()
				.idIn(Arrays.asList(ids.split(",")))
				.findList();
		return Msg.ok(list);
	}

	@ApiOperation(value = "批量查询，包括删除", notes = "批量查询，包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParam(value = "id 集合", name = "ids", defaultValue = "1,2,3", paramType = "query", dataType = "string")
	@GetMapping("queryByIdsed")
	public Msg queryByIdsed(@NotBlank(message = "id 集合不能为空") String ids) {
		List<T> list = Ebean.find(entityClass)
				.setIncludeSoftDeletes()
				.where()
				.idIn(Arrays.asList(ids.split(",")))
				.findList();
		return Msg.ok(list);
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
