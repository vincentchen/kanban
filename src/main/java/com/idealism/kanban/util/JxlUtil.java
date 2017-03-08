package com.idealism.kanban.util;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.idealism.kanban.model.ProjectHistory;
import com.idealism.kanban.vo.UserHistoryInfo;

public class JxlUtil {
	private String path = new GetSystemPath().GetWebRootPath() + "temp/";

	public void DelOldFiles() {
		// 设置日期转换格式
		SimpleDateFormat smp = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		int now_time = Integer.parseInt(smp.format(date));
		File file = new File(path);
		int modify_time = 0;
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				modify_time = Integer.parseInt(smp.format(new Date(files[i].lastModified())));
				if (now_time - modify_time == 1) {
					files[i].delete();
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public String CreateUserHistoryExcel(List historyList, OutputStream os) throws IOException, RowsExceededException, WriteException {
		// DelOldFiles();
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet sheet1 = wwb.createSheet("sheet0", 0);
		int i;
		for (i = 0; i < historyList.size() - 1; i++) {
			UserHistoryInfo info = (UserHistoryInfo) historyList.get(i);
			sheet1.addCell(new Label(0, i, info.getHistoryTypeName()));
			sheet1.addCell(new Label(1, i, info.getHistoryContent()));
			sheet1.addCell(new Label(2, i, info.getHistoryLogTime()));
		}
		sheet1.addCell(new Label(0, i + 1, (String) historyList.get(historyList.size() - 1)));
		sheet1.mergeCells(0, i + 1, 3, i + 1);
		wwb.write();
		wwb.close();
		return path;
	}

	public String writeExcel() throws IOException, RowsExceededException, WriteException {

		// 创建Excel
		WritableWorkbook workbook = Workbook.createWorkbook(new File("write.xls"));
		// 创建sheet
		WritableSheet sheet1 = workbook.createSheet("sheet0", 0);
		for (int i = 0; i < 10; i++) {
			sheet1.addCell(new jxl.write.Label(1, i, "书目ID"));
		}
		workbook.write();
		workbook.close();
		return null;
	}

	/*
	暂未使用
	public String readExcel() {
		try {
			// 选取指定的excel
			Workbook workbook = Workbook.getWorkbook(new File("text.xls"));
			// 选取制定的sheet
			Sheet sheet = workbook.getSheet(0);
			// 选取指定的cell
			// 遍历循环得到所要的cell值
			for (int j = 0; j < sheet.getRows(); j++)
				for (int i = 0; i < sheet.getColumns(); i++) {
					Cell cell = sheet.getCell(i, j);
					// 获取该cell的值
					String var1 = cell.getContents();
					// 打印输出该值
					System.out.println(var1);
				}
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	*/

	@SuppressWarnings("rawtypes")
	public String CreateProjectHistoryExcel(List proHistory, ServletOutputStream os) throws RowsExceededException, WriteException, IOException {
		WritableWorkbook wwb = Workbook.createWorkbook(os);
		WritableSheet sheet1 = wwb.createSheet("sheet0", 0);
		int i;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DefineConfig defineConfig = new DefineConfig();
		for (i = 0; i < proHistory.size() - 1; i++) {
			ProjectHistory projectHistory = (ProjectHistory) proHistory.get(i);
			sheet1.addCell(new Label(0, i, defineConfig.GetStatusName(projectHistory.getHistory_status())));
			sheet1.addCell(new Label(1, i, projectHistory.getHistory_content()));
			sheet1.addCell(new Label(2, i, sdf.format(projectHistory.getCreate_time())));
		}
		sheet1.mergeCells(0, i + 1, 3, i + 1);
		wwb.write();
		wwb.close();
		return path;
	}

}