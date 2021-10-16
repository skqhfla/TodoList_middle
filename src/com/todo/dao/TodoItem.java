package com.todo.dao;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TodoItem {
	private String category;
	private String title;
	private String desc;
	private String due_date;
	private String current_date;
	private int is_completed;
	private int priority, progress;
	private int id;

	public TodoItem(String category, String title, String desc, String due_date, int is_completed, int priority, int progress) {
		this.category = category;
		this.title = title;
		this.desc = desc;
		this.due_date = due_date;
		this.is_completed = is_completed;
		SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd kk.mm.ss");
		this.current_date = f.format(new Date());
		this.priority = priority;
		this.progress = progress;
	}
	
	public TodoItem(String category, String title, String desc, String due_date, String time, int is_completed, int priority, int progress) {
		this.category = category;
		this.title = title;
		this.desc = desc;
		this.due_date = due_date;
		this.current_date = time;
		this.is_completed = is_completed;
		this.priority = priority;
		this.progress = progress;
	}
	
	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getIs_completed() {
		return is_completed;
	}

	public void setIs_completed(int is_completed) {
		this.is_completed = is_completed;
	}

	public String getCategory() {
		return category;
	}

	public void setgetCategory(String category) {
		this.category = category;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getDuedate() {
		return due_date;
	}

	public void setDuedate(String due_date) {
		this.due_date = due_date;
	}
	
	public String getCurrent_date() {
		return current_date;
	}

	public void setCurrent_date(String current_date) {
		this.current_date = current_date;
	}

	@Override
	public String toString() {
		return  "[" + category + "] " + title + " - " + desc + " - " + due_date + " - " + current_date + " - " + is_completed + " - " + priority + " - " + progress + "\n";
	}
	
	public String toSaveString() {
		return category + "##" + title + "##" + desc + "##" + due_date + "##" + current_date + "##" + is_completed + "##" + priority + "##" + progress + "\n";
	}


}
