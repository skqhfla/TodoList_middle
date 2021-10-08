package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {

	public static void start() {

		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		l.importData("todolist.txt");
		TodoUtil u = new TodoUtil();
		boolean isList = false;
		boolean quit = false;
		u.loadList(l, "D:/����/�ѵ�/�ѵ� 4�б�/����������Ʈ/todolist.txt");
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				u.createItem(l);
				break;

			case "del":
				u.deleteItem(l);
				break;

			case "edit":
				u.updateItem(l);
				break;

			case "ls":
				u.listAll(l);
				break;

			case "ls_name_asc":
				System.out.println("제목순으로 정렬하였습니다.");
				u.listAll(l, "title", 1);
				break;

			case "ls_name_desc":
				System.out.println("제목역순으로 정렬하였습니다.");
				u.listAll(l, "title", 0);
				break;

			case "ls_date":
				System.out.println("날짜순으로 정렬하였습니다.");
				u.listAll(l, "due_date", 1);
				break;

			case "exit":
				quit = true;
				break;
				
			case "help" :
				Menu.displaymenu();
				break;
			
			case "find" :
				String keyword = sc.nextLine().trim();
				u.findList(1,keyword);
				break;
			
			case "ls_date_desc" :
				System.out.println("날짜역순으로 정렬하였습니다.");
				u.listAll(l, "due_date", 0);
				break;
			
			case "find_cate" : 
				String cate = sc.nextLine().trim();
				u.findCateList(l,cate);
				break;
				
			case "ls_cate" : 
				u.ls_cate(l);
				break;

			default:
				System.out.println("��ɾ �ٽ� �Է��� �ּ��� - ��ɾ� �ٽú���(help)");
				break;
			}

			if (isList)
				l.listAll();
		} while (!quit);
		
		u.saveList(l,"D:/����/�ѵ�/�ѵ� 4�б�/����������Ʈ/todolist.txt");
	}
}
