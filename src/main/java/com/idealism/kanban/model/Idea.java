package com.idealism.kanban.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionType;

@Entity
@Table(name = "idea")
public class Idea {
	private int idea_id;
	private String idea_title;
	private String idea_content;
	private Date create_time;
	private int create_user_id;
	private int is_anonymous;
	private int by_audit;
	private int is_delete;
	private int good;
	private int bad;

	public int getIs_anonymous() {
		return is_anonymous;
	}

	public void setIs_anonymous(int is_anonymous) {
		this.is_anonymous = is_anonymous;
	}

	@Id
	@GeneratedValue
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

	@Column(columnDefinition="TEXT")
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

	public int getGood() {
		return good;
	}

	public void setGood(int good) {
		this.good = good;
	}

	public int getBad() {
		return bad;
	}

	public void setBad(int bad) {
		this.bad = bad;
	}

}