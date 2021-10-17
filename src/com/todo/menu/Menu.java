package com.todo.menu;

public class Menu {

	public static void displaymenu() {
		System.out.println();
		System.out.println("1. add - 항목 추가");
		System.out.println("2. del - 항목 삭제");
		System.out.println("3. edit - 항목 수정");
		System.out.println("4. comp - 완료");
		System.out.println("5. progress - 진행");
		System.out.println("6. ls - 전체 목록");
		System.out.println("7. ls_name_asc - 재목순 정렬");
		System.out.println("8. ls_name_desc - 제목역순 정렬");
		System.out.println("9. ls_date - 날짜순 정렬");
		System.out.println("10. ls_date_desc - 날짜역순 정렬");
		System.out.println("11. ls_comp - 완료 목록");
		System.out.println("12. ls_cate - 카테고리 목록");
		System.out.println("13. find - 항목 찾기");
		System.out.println("14. find_cate - 카테고리 찾기");
		System.out.println("15. exit - 종료");
	}
	
	public static void prompt() {
		System.out.println("\n명령어를 입력하세요 >");
	}
	
}
