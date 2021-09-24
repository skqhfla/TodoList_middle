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
		      System.out.println("에러가 발생했습니다.");
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
			System.out.println("파일이 없습니다.");
		}


		
	}

	public void createItem(TodoList list) {

		String category, title, desc, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("\n" + "========== 항목 추가\n" + "카데고리를 입력하세요.\n");
		
		category = sc.next();

		System.out.println("제목을 입력하세요.\n");

		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.println("제목 중복");
			return;
		}

		sc.nextLine();
		System.out.println("할일을 입력해 주세요.");
		desc = sc.nextLine().trim();
		
		System.out.println("마감날짜를 입력해 주세요.");
		due_date = sc.next();

		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
		System.out.println("추가되었습니다.");
	}

	public void deleteItem(TodoList l) {
		int num;
		Scanner sc = new Scanner(System.in);

		System.out.println("\n" + "========== 항목 삭제\n" + "삭제할 항목의 번호를 입력해주세요\n" + "\n");
		num = sc.nextInt();
		num  = num-1;
		for (TodoItem item : l.getList()) {
			if(num == l.indexOf(item)) {
				l.deleteItem(item);
				System.out.println("삭제되었습니다.");
				break;
			}
		}
	}

	public void updateItem(TodoList l) {
		int num;

		Scanner sc = new Scanner(System.in);

		System.out.println(
				"\n" + "========== 항목 수정\n" + "수정하고 싶은 항목의 번호를 입력해주세요\n" + "\n");
		num = sc.nextInt();
		if (num>l.getSize()) {
			System.out.println("항목이 존재하지 않습니다");
			return;
		}
		
		System.out.println("새로운 카데고리를 입력해주세요");
		String new_category = sc.next();
		
		System.out.println("항목을 입력해주세요");
		String new_title = sc.next();
		if (l.isDuplicate(new_title)) {
			System.out.println("이미 존재하는 항목입니다.");
			return;
		}
		
		sc.nextLine();
		System.out.println("할일을 입력해주세요");
		String new_description = sc.nextLine().trim();
		
		System.out.println("마감 날짜를 입력해주세요");
		String new_due_date = sc.next();
		num = num-1;
		for (TodoItem item : l.getList()) {
			if (num == l.indexOf(item)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date);
				l.addItem(t);
				System.out.println("변경되었습니다.");
			}
		}

	}

	public void listAll(TodoList l) {
		System.out.println("[전체 목록, 총 " + l.getSize() + "개]");
		int i = 1;
		
		for (TodoItem item : l.getList()) {
			System.out.println(i + ". " + item.toString());
			i++;
		}
	}
	
	public void fine(TodoList l) {
		System.out.println("찾고자 하는 키워드를 입력하세요.");
		Scanner sc = new Scanner(System.in);
		String k = sc.next();
		int count = 0;
		
		for (TodoItem item : l.getList()) {
			if(l.contains(k,item) != -1) {
				count++;
				System.out.println(count + ". " + item.toString());
			}
		}
		
		if(count != 0) {
			System.out.println("총 " + count + "개의 항목을 찾았습니다.");
			return;
		}
		
		System.out.println("일치하는 항목을 찾지 못하였습니다.");
		return;
	}
	
	public void fine_cate(TodoList l) {
		System.out.println("찾고자 하는 카테고리를 입력하세요.");
		Scanner sc = new Scanner(System.in);
		String k = sc.next();
		int count = 0;
		
		for (TodoItem item : l.getList()) {
			if(l.contains_cate(k,item) != -1) {
				count++;
				System.out.println(count + ". " + item.toString());
			}
		}
		
		if(count != 0) {
			System.out.println("총 " + count + "개의 항목을 찾았습니다.");
			return;
		}
		
		System.out.println("일치하는 항목을 찾지 못하였습니다.");
		return;
	}
	
	public void ls_cate(TodoList l) {
		Set<String> clist = new HashSet<String>();
		
		for(TodoItem c : l.getList()) {
			clist.add(c.getCategory());
		}
		
		Iterator it = clist.iterator();
		while(it.hasNext()) {
			String s = (String)it.next();
			System.out.print(s);
			if(it.hasNext())
				System.out.print(" / ");
		}
		
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", clist.size());
	}
}
