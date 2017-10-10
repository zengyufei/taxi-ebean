package com.ys.admin.base.control;

import com.ys.common.base.entiry.IdAbstractEntity;
import com.zyf.result.Msg;
import io.ebean.Ebean;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class CommunityBaseController<T extends IdAbstractEntity> extends AbstractController<T> {

	@GetMapping("queryByCommunityId")
	public Msg queryByCommunityId(@Min(value = 1, message = "查询的 id 不能为空")
	                              @NotNull(message = "id 不能为空")
			                              Long id) {
		List<T> result = Ebean.find(entityClass)
				.where()
				.eq("communityId", id)
				.findList();
		return Msg.ok(result);
	}

}
