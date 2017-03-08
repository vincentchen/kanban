package com.idealism.kanban.action;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.idealism.kanban.service.SystemInstall;
import com.idealism.kanban.service.impl.SystemInstallImpl;
import com.idealism.kanban.util.AjaxActionBase;
import com.idealism.kanban.vo.SystemInstallInfo;
import com.opensymphony.xwork2.ModelDriven;

@Component("install")
@Scope("prototype")
public class SystemInstallAction extends AjaxActionBase implements ModelDriven<SystemInstallInfo> {

	private SystemInstallInfo sii = new SystemInstallInfo();
	private SystemInstall install = new SystemInstallImpl();

	public SystemInstallInfo getSii() {
		return sii;
	}

	public void Install() throws Exception {
		String error = install.Setup(sii);
		if (error != "") {
			ErrorText(error);
			return;
		} else {
			Success();
		}
	}

	public void setSii(SystemInstallInfo sii) {
		this.sii = sii;
	}

	@Override
	public SystemInstallInfo getModel() {
		return sii;
	}

}