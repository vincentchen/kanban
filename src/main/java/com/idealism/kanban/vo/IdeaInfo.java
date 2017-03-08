package com.idealism.kanban.vo;

import java.util.Date;

import com.idealism.kanban.util.Page;

public class IdeaInfo extends Page {
	private int id;
	private int idea_id;
	private String idea_title;
	private String idea_content;
	private Date create_time;
	private int create_user_id;
	private String create_user_name;
	private int is_anonymous;
	private int choose;
	private int cntGood;
	private int cntBad;
	private Boolean hasAttach;
	private Boolean hasChoose;
	private int user_id;
	private int by_audit;
	private int is_delete;
	private int is_read;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public IdeaInfo() {
		this.cntGood = 0;
		this.cntBad = 0;
	}

	public int getChoose() {
		return choose;
	}

	public void setChoose(int choose) {
		this.choose = choose;
	}

	public int getCntGood() {
		return cntGood;
	}

	public void setCntGood(int cntGood) {
		this.cntGood = cntGood;
	}

	public int getCntBad() {
		return cntBad;
	}

	public void setCntBad(int cntBad) {
		this.cntBad = cntBad;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public Boolean getHasChoose() {
		return hasChoose;
	}

	public void setHasChoose(Boolean hasChoose) {
		this.hasChoose = hasChoose;
	}

	public Boolean getHasAttach() {
		return hasAttach;
	}

	public void setHasAttach(Boolean hasAttach) {
		this.hasAttach = hasAttach;
	}

	public String getCreate_user_name() {
		return create_user_name;
	}

	public void setCreate_user_name(String create_user_name) {
		this.create_user_name = create_user_name;
	}

	public int getIdea_id() {
		return idea_id;
	}

	public void setIdea_id(int idea_id) {
		this.idea_id = idea_id;
	}

	public String getIdea_title() {
		return idea_title;
	}

	public void setIdea_title(String idea_title) {
		this.idea_title = idea_title;
	}

	public String getIdea_content() {
		return idea_content;
	}

	public void setIdea_content(String idea_content) {
		this.idea_content = idea_content;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getCreate_user_id() {
		return create_user_id;
	}

	public void setCreate_user_id(int create_user_id) {
		this.create_user_id = create_user_id;
	}

	public int getIs_anonymous() {
		return is_anonymous;
	}

	public void setIs_anonymous(int is_anonymous) {
		this.is_anonymous = is_anonymous;
	}

	public int getBy_audit() {
		return by_audit;
	}

	public void setBy_audit(int by_audit) {
		this.by_audit = by_audit;
	}

	public int getIs_delete() {
		return is_delete;
	}

	public void setIs_delete(int is_delete) {
		this.is_delete = is_delete;
	}

	public int getIs_read() {
		return is_read;
	}

	public void setIs_read(int is_read) {
		this.is_read = is_read;
	}

}
