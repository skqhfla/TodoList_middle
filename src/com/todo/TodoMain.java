package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {

	public static void start() {

		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		//l.importData("todolist.txt");
		TodoUtil u = new TodoUtil();
		boolean isList = false;
		boolean quit = false;
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
				
			case "comp":
				u.completeItem(l);
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
			
			case "ls_date_desc" :
				System.out.println("날짜역순으로 정렬하였습니다.");
				u.listAll(l, "due_date", 0);
				break;
				
			case "ls_comp":
				System.out.println("완료된 항목입니다.");
				u.listAll(l, 1);
				break;

			case "exit":
				quit = true;
				break;
				
			case "help" :
				Menu.displaymenu();
				break;
			
			case "find" :
				String keyword = sc.nextLine().trim();
				u.findList(l,keyword);
				break;
			
			case "find_cate" : 
				String cate = sc.nextLine().trim();
				u.findCateList(l,cate);
				break;
				
			case "ls_cate" : 
				u.listCateAll(l);
				break;

			default:
				System.out.println("명령어를 다시 입력해 주세요 - 명령어 다시보기(help)");
				break;
			}

			if (isList)
				l.listAll();
		} while (!quit);
		
		u.saveList(l,"todolist.txt");
	}
}
