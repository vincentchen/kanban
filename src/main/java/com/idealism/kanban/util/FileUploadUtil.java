package com.idealism.kanban.util;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class FileUploadUtil {
	private File upfile; // 上传的文件
	private String upfileFileName; // 文件名称
	private String upfileContentType; // 文件类型

	private String storageFileName;// 文件修改后的名字
	private String storagePath;// 文件修改后的地址

	public String getStorageFileName() {
		return storageFileName;
	}

	public void setStorageFileName(String storageFileName) {
		this.storageFileName = storageFileName;
	}

	public String getStoragePath() {
		return storagePath;
	}

	public void setStoragePath(String storagePath) {
		this.storagePath = storagePath;
	}

	public static String getExtention(String fileName) {
		int pos = fileName.lastIndexOf(".");
		return fileName.substring(pos);
	}

	public String SaveFile() {
		if (upfile == null) {
			return "error.file.null";
		} else {
			setUpfileContentType(getExtention(upfileFileName));
			storageFileName = new Date().getTime() + upfileContentType;
			storagePath = "/upload/" + storageFileName;
			File savefile = new File(new GetSystemPath().GetWebRootPath() + storagePath);
			if (!savefile.getParentFile().exists()) {
				savefile.getParentFile().mkdirs();
			}
			try {
				FileUtils.copyFile(upfile, savefile);
			} catch (IOException e) {
				return "error.file.fail";
			}
		}

		return null;
	}

	public File getUpfile() {
		return upfile;
	}

	public void setUpfile(File upfile) {
		this.upfile = upfile;
	}

	public String getUpfileFileName() {
		return upfileFileName;
	}

	public void setUpfileFileName(String upfileFileName) {
		this.upfileFileName = upfileFileName;
	}

	public String getUpfileContentType() {
		return upfileContentType;
	}

	public void setUpfileContentType(String upfileContentType) {
		this.upfileContentType = upfileContentType;
	}

}