package com.ll.springMvc.excel;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;

public class UserListExcelView extends AbstractExcelView {

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		response.setHeader("Content-Disposition", "inline; filename="
				+ new String("用户列表".getBytes(), "iso8859-1")); // iso8899-1：解决Excel文档名称出现中文乱码的问题
		List<User> userList = (List<User>) model.get("userList");

		HSSFSheet sheet = workbook.createSheet("users");
		HSSFRow header = sheet.createRow(0); // 第0行
		for (int i = 0; i < 3; i++) {
			header.createCell((short) i);
			sheet.getRow(0).getCell((short) i)
					.setEncoding(HSSFCell.ENCODING_UTF_16); // 解决Excel单元格出现中文乱码的问题
			sheet.getRow(0).getCell((short) i)
					.setCellValue((i == 0) ? "账号" : ((i == 1) ? "姓名" : "生日"));
		}

		int rowNum = 1;
		for (int i = 0; i < userList.size(); i++) {
			HSSFRow row = sheet.createRow(rowNum++); // 创建行
			User user = userList.get(i);

			row.createCell((short) 0).setCellValue(user.getUserName());

			row.createCell((short) 1);
			row.getCell((short) 1).setEncoding(HSSFCell.ENCODING_UTF_16); // 解决Excel单元格出现中文乱码的问题
			row.getCell((short) 1).setCellValue(user.getRealName());

		}
	}
}