package com.ys.admin.controller.device;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.AbstractCommunityController;
import com.ys.admin.util.DownloadFileUtil;
import com.ys.common.entitys.device.Card;
import io.ebean.Ebean;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Api(value = "物理卡", description = "物理卡管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("card")
@Update(value = Card.class, vo = Card.Vo.class)
@Insert(value = Card.class, vo = Card.Vo.class)
@RemoveById(Card.class)
@DeleteById(Card.class)
@QueryById(Card.class)
public class CardController extends AbstractCommunityController<Card, Card.Vo> {

	@GetMapping("export")
	public void export(HttpServletResponse resp) throws Exception {
		List<Card> list = Ebean.find(entityClass).findList();
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("物理卡","物理卡"),
				entityClass, list);
		DownloadFileUtil.download("物理卡", workbook, resp);
	}
}
