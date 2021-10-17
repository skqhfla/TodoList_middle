package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	private List<TodoItem> list;

	public TodoUtil() {
		this.list = new ArrayList<TodoItem>();
	}
	
	public void savegson(TodoList l, String filename) {
		Gson gson = new Gson();
		String jsonstr = "";
		
		for (TodoItem item : l.getList()) {
			jsonstr += gson.toJson(item) + "\n";
		}
		
		try {
			FileWriter writer = new FileWriter(filename);
			writer.write(jsonstr);
			writer.close();
			System.out.println("Gson 파일에 저장되었습니다.");
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadgson(TodoList l, String filename) {
		Gson gson = new Gson();
		String jsonstr = "";
		FileReader fr;
		try {
			fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);	
		
			try {
				for(int i = 1; (jsonstr = br.readLine()) != null; i++) {
					System.out.println(i + " " + jsonstr);
					TodoItem I = gson.fromJson(jsonstr, TodoItem.class);
					l.addItem(I);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch(IOException e) {
				e.printStackTrace();
			} 
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		String is_completed, is_priority, is_progress;
		int completed, priority, progress;
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
					is_completed = st.nextToken();
					is_priority = st.nextToken();
					is_progress = st.nextToken();
					completed = Integer.parseInt(is_completed);
					priority = Integer.parseInt(is_priority);
					progress = Integer.parseInt(is_progress);
					TodoItem t = new TodoItem(category,title, desc, due_date, time, completed, priority, progress);
					l.addItem(t);
				}
			} catch (IOException e) {
				System.out.println("에러가 발생했습니다.");
			}

		} catch (FileNotFoundException e) {
			System.out.println("에러가 발생했습니다.");
		}
	}

	public void createItem(TodoList l) {

		String category, title, desc, due_date;
		int priority, progress;
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
		
		System.out.println("우선순위 > ");
		priority = sc.nextInt();
		
		TodoItem t = new TodoItem(category, title, desc, due_date, 0, priority, 0);
		
		if(l.addItem(t)>0)
			System.out.println("추가되었습니다.");
	}

	public void deleteItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("[항목 삭제]\n" + "삭제할 항목의 개수를 입력하세요 > ");

		int count = sc.nextInt();
		int num[] = new int[count];		
		
		// 숫자 입력받기 
		System.out.print("삭제할 " + count + " 개의 숫자를 입력하시오. ");
		for (int i = 0 ; i < count ; i++) {
			num[i] = sc.nextInt();
			if(l.deleteItem(num[i]) <= 0)
				break;
		}
		System.out.println("항목이 삭제되었습니다.\n");
	}
	
	public void completeItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("[완료여부]\n" + "완료할 항목의 개수를 입력하시오 > ");
		
		int count = sc.nextInt();
		int num[] = new int[count];		
		
		// 숫자 입력받기 
		System.out.print("완료할 " + count + " 개의 숫자를 입력하시오. ");
		for (int i = 0 ; i < count ; i++) {
			num[i] = sc.nextInt();
			if(l.completedItem(num[i]) <= 0)
				break;
		}
		System.out.println("완료되었습니다.\n");
	}
	
	public void progressItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		System.out.print("[진행]\n" + "진행률을 변경할 항목의 번호를 입력하시오 > ");
		
		int index = sc.nextInt();
		
		System.out.print("진행률을 입력하시오.");
		int c_progress = sc.nextInt();
		
		if(l.progressItem(index, c_progress) > 0)
			System.out.println("완료되었습니다.\n");
	}
	
	public void updateItem(TodoList l) {
		
		String new_category, new_title, new_desc, new_due_date;
		int new_is_completed, new_progress, new_priority;
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
		
		System.out.println("완료 여부 > ");
		new_is_completed = sc.nextInt();
		
		System.out.println("우선순위 > ");
		new_priority = sc.nextInt();
		
		System.out.println("진행률 > ");
		new_progress = sc.nextInt();
		
		TodoItem t = new TodoItem(new_category, new_title, new_desc, new_due_date, new_is_completed, new_priority, new_progress);
		t.setId(index);
		if(l.updateItem(t) > 0)
				System.out.println("수정되었습니다.");
	}

	public void listAll(TodoList l, String orderby, int ordering) {
		System.out.println("[전체 목록 총 " + l.getCount() + "개]");
		
		for (TodoItem item : l.getOrderList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	
	public void listAll(TodoList l) {
		System.out.println("[전체 목록 총 " + l.getCount() + "개]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.getId() + " " + item.toString());
		}
	}
	
	public void listAll(TodoList l, int completed) {
		int count = 0;
		for (TodoItem item : l.getList(completed)) {
			System.out.println(item.getId() + " " + item.toString());
		}
		System.out.println("총 " + count + "개의 항목을 찾았습니다.");
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
			System.out.println(item + " ");
			count++;
		}
		System.out.printf("\n총 %d개의 카테고리가 등록되어 있습니다.\n", count);
	}

	
}
