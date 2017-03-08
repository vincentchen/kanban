package com.idealism.kanban.action;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsStatics;

import com.idealism.kanban.model.SystemFiles;
import com.idealism.kanban.service.SystemFilesService;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.util.FileUploadUtil;
import com.idealism.kanban.util.GetSystemPath;
import com.idealism.kanban.util.Global;
import com.idealism.kanban.vo.SystemFilesInfo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

public class SystemFilesAction extends AjaxActionBase implements ModelDriven<SystemFilesInfo> {
	private SystemFilesInfo systemFilesInfo = new SystemFilesInfo();
	private SystemFilesService systemFilesService;

	public SystemFilesService getSystemFilesService() {
		return systemFilesService;
	}

	@Resource
	public void setSystemFilesService(SystemFilesService systemFilesService) {
		this.systemFilesService = systemFilesService;
	}

	@Override
	public SystemFilesInfo getModel() {
		return systemFilesInfo;
	}

	public void DownLoad() throws Exception {
		if (systemFilesInfo.getId() > 0) {
			Global global = new Global();
			int id = global.GetRegID("SystemFiles", systemFilesInfo.getId());
			if (id > 0) {
				SystemFiles systemFiles = systemFilesService.LoadSystemFilesById(id);
				String urlString = new GetSystemPath().GetWebRootPath() + systemFiles.getFiles_url();
				InputStream inStream = new FileInputStream(urlString);
				ActionContext context1 = ActionContext.getContext();
				HttpServletResponse response = (HttpServletResponse) context1.get(StrutsStatics.HTTP_RESPONSE);
				response.reset();
				response.setContentType("application/octet-stream;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment; filename=" + new String(systemFiles.getFiles_name().getBytes("gbk"), "iso8859-1") + "." + systemFiles.getFiles_ext());
				byte[] b = new byte[100];
				int len;
				while ((len = inStream.read(b)) > 0) {
					response.getOutputStream().write(b, 0, len);
				}
				inStream.close();
			}
		}
	}
	
	public void Upload() throws Exception{
		if(systemFilesInfo.getTblId()>0 && systemFilesInfo.getTblName() != null){
			FileUploadUtil fileUploadUtil = new FileUploadUtil();
			fileUploadUtil.setUpfile(systemFilesInfo.getFile());
			fileUploadUtil.setUpfileFileName(systemFilesInfo.getFileFileName());
			
			String error = fileUploadUtil.SaveFile();
			if (error != null) {
				ErrorText(error);
			} else {
				SystemFiles systemFiles = new SystemFiles();
				systemFiles.setFiles_name(fileUploadUtil.getUpfileFileName());
				systemFiles.setFiles_url(fileUploadUtil.getStoragePath());
				systemFiles.setFiles_des(systemFilesInfo.getFiles_des());
				systemFiles.setFiles_ext(fileUploadUtil.getUpfileContentType());
				systemFiles.setIs_delete(0);
				systemFilesService.SaveSystemFiles(systemFiles);
				if (systemFiles.getFiles_id() > 0) {
					if(systemFilesInfo.getTblName().equals("Project")){
						String tabName = "rel_pro_file";
						String tabIdRowName = "pro_id";
						Global global = new Global();
						int tabId = global.GetRegID("Project", systemFilesInfo.getTblId());
						int row = systemFilesService.SaveRelTable(tabName,tabIdRowName,tabId,systemFiles.getFiles_id());
						if (row > 0) {
							CallBackJSON();
						} else {
							SetJsonBaseErrorText("error.file.fail");
							CallBackJSON();
						}
						return;
					}
					if(systemFilesInfo.getTblName().equals("ProTarget")){
						String tabName = "rel_target_file";
						String tabIdRowName = "target_id";
						Global global = new Global();
						int tabId = global.GetRegID("ProTarget", systemFilesInfo.getTblId());
						int row = systemFilesService.SaveRelTable(tabName,tabIdRowName,tabId,systemFiles.getFiles_id());
						if (row > 0) {
							CallBackJSON();
						} else {
							SetJsonBaseErrorText("error.file.fail");
							CallBackJSON();
						}
						return;
					}
					if(systemFilesInfo.getTblName().equals("Idea")){
						String tabName = "rel_idea_file";
						String tabIdRowName = "idea_id";
						Global global = new Global();
						int tabId = global.GetRegID("Idea", systemFilesInfo.getTblId());
						int row = systemFilesService.SaveRelTable(tabName,tabIdRowName,tabId,systemFiles.getFiles_id());
						if (row > 0) {
							CallBackJSON();
						} else {
							SetJsonBaseErrorText("error.file.fail");
							CallBackJSON();
						}
						return;
					}
					if(systemFilesInfo.getTblName().equals("Help")){
						String tabName = "rel_help_file";
						String tabIdRowName = "request_id";
						Global global = new Global();
						int tabId = global.GetRegID("Help", systemFilesInfo.getTblId());
						int row = systemFilesService.SaveRelTable(tabName,tabIdRowName,tabId,systemFiles.getFiles_id());
						if (row > 0) {
							CallBackJSON();
						} else {
							SetJsonBaseErrorText("error.file.fail");
							CallBackJSON();
						}
						return;
					}
					if(systemFilesInfo.getTblName().equals("ProTask")){
						String tabName = "rel_task_file";
						String tabIdRowName = "task_id";
						Global global = new Global();
						int tabId = global.GetRegID("ProTask", systemFilesInfo.getTblId());
						int row = systemFilesService.SaveRelTable(tabName,tabIdRowName,tabId,systemFiles.getFiles_id());
						if (row > 0) {
							CallBackJSON();
						} else {
							SetJsonBaseErrorText("error.file.fail");
							CallBackJSON();
						}
						return;
					}
					SetJsonBaseErrorText("error.file.fail");
					CallBackJSON();
				} else {
					SetJsonBaseErrorText("error.file.fail");
					CallBackJSON();
				}
			}
		}else {
			SetJsonBaseErrorText("error.file.fail");
			CallBackJSON();
		}
	}
}