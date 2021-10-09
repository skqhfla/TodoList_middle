package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;


import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	private List<TodoItem> list;

	public TodoUtil() {
		this.list = new ArrayList<TodoItem>();
	}
		
	public void saveList(TodoList l, String filename) {
		int count = 0;
		StringBuffer text = new StringBuffer("");
		 try {
		      FileWriter myWriter = new FileWriter(filename);
		      for (TodoItem item : l.getList()) {
		    	  myWriter.write(item.toSaveString());
				}
		      myWriter.flush();
		      myWriter.close();
		      
		    } 
		    catch (IOException e) {
		      System.out.println("에러가 발생했습니.");
		      e.printStackTrace();
		    }
	}
	
	public void loadList(TodoList l, String filename) {
		int index = 0;
		String category, title, desc, due_date, time;
		try {
			FileReader fr = new FileReader(filename);
			BufferedReader in = new BufferedReader(fr);	
			String line = "";
			try {
				for(int i = 1; (line = in.readLine()) != null; i++) {
					System.out.println(line);
					StringTokenizer st = new StringTokenizer(line, "##");
					category = st.nextToken();
					title = st.nextToken();
					desc = st.nextToken();
					due_date = st.nextToken();
					time = st.nextToken();
					TodoItem t = new TodoItem(category,title, desc, due_date,time);
					l.addItem(t);
				}
			} catch (IOException e) {
				System.out.println("에러가 발생했습니다.");
			}

		} catch (FileNotFoundException e) {
			System.out.println("������ �����ϴ�.");
		}


		
	}

	public void createItem(TodoList l) {

		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 추가]\n" + "제목 > ");
		title = sc.next();
		
		if(l.isDuplicate(title)) {
			System.out.println("제목이 중복됩니다!");
			return;
		}
		
		System.out.println("카테고리 > ");
		category = sc.next();
		
		sc.nextLine();
		
		System.out.println("내용 > ");
		desc = sc.nextLine().trim();
		
		System.out.println("마감일자 > ");
		due_date = sc.nextLine().trim();

		TodoItem t = new TodoItem(category, title, desc, due_date);
		if(l.addItem(t)>0)
			System.out.println("추가되었습니다.");
	}

	public void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("[항목 삭]\n" + "삭제할 항목의 번호를 입력하시오 > ");
		int index = sc.nextInt();
		if(l.deleteItem(index) > 0)
			System.out.println("삭제되었습니다.");
	}

	public void updateItem(TodoList l) {
		
		String new_category, new_title, new_desc, new_due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 추가]\n" + "수정할 항목의 번호를 입력하시오 > ");
		int index = sc.nextInt();
		
		System.out.println("새 제목 > ");
		new_title = sc.next();
		
		System.out.println("새 카테고리 > ");
		new_category = sc.next();
		
		sc.nextLine();
		
		System.out.println("새 내용 > ");
		new_desc = sc.nextLine().trim();
		
		System.out.println("새 마감일자 > ");
		new_due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(new_category, new_title, new_desc, new_due_date);
		t.setId(index);
		if(l.updateItem(t) > 0)
				System.out.println("수정되었습니다.");
	}

	public void listAll(TodoList l, String orderby, int ordering) {
		System.out.println("[전체 목록 총 " + l.getCount() + "]");
		
		for (TodoItem item : l.getOrderList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public void listAll(TodoList l) {
		System.out.println("[전체 목록 총 " + l.getCount() + "]");
		
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}

	public void findCateList(TodoList l, String cate) {
		int count = 0;
		for(TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\n총 %d개의 항목을 찾았습니다.\n", count);
	}

	public void findList(TodoList l, String keyword) {
		int count = 0;
		for(TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("총 %d개의 항목을 찾았습니다.\n", count);
	}

	public void listCateAll(TodoList l) {
		int count = 0;
		for(String item : l.getCategories()) {
			System.out.print(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}
}
