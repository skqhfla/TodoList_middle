package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {

	public static void start() {

		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		TodoUtil u = new TodoUtil();
		boolean isList = false;
		boolean quit = false;
		u.loadList(l, "D:/공부/한동/한동 4학기/실전프로젝트/todolist.txt");
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
				l.sortByName();
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				isList = true;
				break;

			case "ls_date":
				l.sortByDate();
				isList = true;
				break;

			case "exit":
				quit = true;
				break;
				
			case "help" :
				Menu.displaymenu();
				break;
			
			case "find" :
				u.fine(l);
				break;
			
			case "ls_date_desc" :
				l.sortByDatedesc();
				isList = true;
				break;
			
			case "find_cate" : 
				u.fine_cate(l);
				break;
				
			case "ls_cate" : 
				u.ls_cate(l);
				break;

			default:
				System.out.println("명령어를 다시 입력해 주세요 - 명령어 다시보기(help)");
				break;
			}

			if (isList)
				l.listAll();
		} while (!quit);
		
		u.saveList(l,"D:/공부/한동/한동 4학기/실전프로젝트/todolist.txt");
	}
}
