package com.todo.dao;

import java.util.*;

import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByDateDesc;
import com.todo.service.TodoSortByName;

public class TodoList {
	private List<TodoItem> list;

	public TodoList() {
		this.list = new ArrayList<TodoItem>();
	}

	public void addItem(TodoItem t) {
		list.add(t);
	}

	public void deleteItem(TodoItem t) {
		list.remove(t);
	}

	void editItem(TodoItem t, TodoItem updated) {
		int index = list.indexOf(t);
		list.remove(index);
		list.add(updated);
	}

	public ArrayList<TodoItem> getList() {
		return new ArrayList<TodoItem>(list);
	}

	public void sortByName() {
		Collections.sort(list, new TodoSortByName());

	}

	public void listAll() {
		System.out.println("\n" + "inside list_All method\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.getTitle() + " " +myitem.getDesc());
		}
	}

	public void reverseList() {
		Collections.reverse(list);
	}

	public void sortByDate() {
		Collections.sort(list, new TodoSortByDate());
	}

	public void sortByDatedesc() {
		Collections.sort(list, new TodoSortByDateDesc());
	}
	
	public int indexOf(TodoItem t) {
		return list.indexOf(t);
	}

	public Boolean isDuplicate(String title) {
		for (TodoItem item : list) {
			if (title.equals(item.getTitle()))
				return true;
		}
		return false;
	}
	
	public int getSize() {
		return list.size();
	}
	
	public int contains(String k, TodoItem item) {
		if (item.getCategory().contains(k))
			return list.indexOf(item);
		else if(item.getTitle().contains(k))
			return list.indexOf(item);
		else if(item.getDesc().contains(k))
			return list.indexOf(item);
		else if(item.getDuedate().contains(k))
			return list.indexOf(item);
		else if(item.getCurrent_date().contains(k))
			return list.indexOf(item);
		return -1;
	}
	
	public int contains_cate(String k, TodoItem item) {
		if (item.getCategory().contains(k))
			return list.indexOf(item);
		
		return -1;
	}
}
