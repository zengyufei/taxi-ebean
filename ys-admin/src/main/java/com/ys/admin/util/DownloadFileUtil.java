package com.ys.admin.util;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class DownloadFileUtil {

	public static HttpServletResponse download(String path, HttpServletResponse resp) {
		try {
			// path是指欲下载的文件的路径。
			File file = new File(path);
			// 取得文件名。
			String filename = file.getName();
			// 取得文件的后缀名。
			String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空resp
			resp.reset();
			// 设置resp的Header
			resp.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
			resp.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(resp.getOutputStream());
			resp.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return resp;
	}

	public static void download(String path, Workbook workbook, HttpServletResponse resp) {
		try {
			String fileName = URLEncoder.encode(path, "UTF-8");
			if (fileName.length() > 150) {
				fileName = new String(path.getBytes("gb2312"), "ISO8859-1");
			}

			resp.setCharacterEncoding("UTF-8");
			resp.setHeader("content-Type", "application/vnd.ms-excel");
			resp.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xls");
			workbook.write(resp.getOutputStream());
			workbook.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
