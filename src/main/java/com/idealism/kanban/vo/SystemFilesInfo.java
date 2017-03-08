package com.idealism.kanban.vo;

import java.io.File;

public class SystemFilesInfo {
	private int id;
	private String tblName;
	private int tblId;
	private int files_id;
	private String files_name;
	private String files_ext;
	private String files_url;
	private String files_des;
	private int is_delete;

	private File file;
	private String fileFileName;

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTblName() {
		return tblName;
	}

	public void setTblName(String tblName) {
		this.tblName = tblName;
	}

	public int getTblId() {
		return tblId;
	}

	public void setTblId(int tblId) {
		this.tblId = tblId;
	}

	public int getFiles_id() {
		return files_id;
	}

	public void setFiles_id(int files_id) {
		this.files_id = files_id;
	}

	public String getFiles_name() {
		return files_name;
	}

	public void setFiles_name(String files_name) {
		this.files_name = files_name;
	}

	public String getFiles_ext() {
		return files_ext;
	}

	public void setFiles_ext(String files_ext) {
		this.files_ext = files_ext;
	}

	public String getFiles_url() {
		return files_url;
	}

	public void setFiles_url(String files_url) {
		this.files_url = files_url;
	}

	public String getFiles_des() {
		return files_des;
	}

	public void setFiles_des(String files_des) {
		this.files_des = files_des;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}

}