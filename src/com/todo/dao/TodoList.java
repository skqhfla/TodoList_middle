package com.todo.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.todo.service.Dbconnect;
import com.todo.service.TodoSortByDate;
import com.todo.service.TodoSortByDateDesc;
import com.todo.service.TodoSortByName;

public class TodoList {
	Connection conn;

	public TodoList() {
		this.conn = Dbconnect.getConnection();
	}

	public int addItem(TodoItem t) {
		String sql = "insert into list (category, title, memo, current_date, due_date, priority, progress)" + " values (?,?,?,?,?,?,?);";
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getCategory());
			pstmt.setString(2, t.getTitle());
			pstmt.setString(3, t.getDesc());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDuedate());
			pstmt.setInt(6, t.getPriority());
			pstmt.setInt(7, t.getProgress());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int deleteItem(int index) {
		String sql = "delete from list where id=?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}

	public int updateItem(TodoItem t) {
		String sql = "update list set category=?, title=?, memo=?, current_date = ?, due_date=? , priority=?, progress=?" + " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getCategory());
			pstmt.setString(2, t.getTitle());
			pstmt.setString(3, t.getDesc());
			pstmt.setString(4, t.getCurrent_date());
			pstmt.setString(5, t.getDuedate());
			pstmt.setInt(6, t.getPriority());
			pstmt.setInt(7, t.getProgress());
			pstmt.setInt(8, t.getId());
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int completedItem(int index) {
		String sql = "update list set is_completed = ? " + " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 1);
			pstmt.setInt(2, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	
	public int progressItem(int index, int c_progress) {
		String sql = "update list set progress = ? " + " where id = ?;";
		PreparedStatement pstmt;
		int count = 0;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, c_progress);
			pstmt.setInt(2, index);
			count = pstmt.executeUpdate();
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ArrayList<String> getCategories(){
		ArrayList<String> list = new ArrayList<String>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT DISTINCT category FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				
				list.add(rs.getString("category"));				
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<TodoItem> getList(int completed){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		int com = completed;
		try {
			String sql = "SELECT * FROM list WHERE is_completed like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,completed);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int priority = rs.getInt("priority");
				int progress = rs.getInt("progress");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, priority, progress);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);				
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		keyword = "%" + keyword + "%";
		try {
			String sql = "SELECT * FROM list WHERE title like ? or memo like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,keyword);
			pstmt.setString(2, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int priority = rs.getInt("priority");
				int progress = rs.getInt("progress");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, priority, progress);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);				
			}
			pstmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getList(){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int priority = rs.getInt("priority");
				int progress = rs.getInt("progress");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, priority, priority);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);				
			}
			stmt.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public ArrayList<TodoItem> getListCategory(String keyword){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		PreparedStatement pstmt;
		try {
			String sql = "SELECT * FROM list WHERE category = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, keyword);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int priority = rs.getInt("priority");
				int progress = rs.getInt("progress");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, priority, progress);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);				
			}
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void listAll() {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		System.out.println("\n" + "inside list_All method\n");
		for (TodoItem myitem : list) {
			System.out.println(myitem.getTitle() + " " +myitem.getDesc());
		}
	}

	public Boolean isDuplicate(String title) {
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		for (TodoItem item : list) {
			if (title.equals(item.getTitle()))
				return true;
		}
		return false;
	}
	
	public int getCount() {
		Statement stmt;
		int count = 0;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT count(id) FROM list;";
			ResultSet rs = stmt.executeQuery(sql);
			rs.next();
			count = rs.getInt("count(id)");
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
//	public void importData(String filename) {
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(filename));
//			String line;
//			String sql = "insert into list (title, memo, category, current_date, due_date, is_completed)" + " values (?,?,?,?,?,?);";
//			int records = 0;
//			while((line = br.readLine()) != null) {
//				StringTokenizer st = new StringTokenizer(line, "##");
//				String category = st.nextToken();
//				String title = st.nextToken();
//				String description = st.nextToken();
//				String due_date = st.nextToken();
//				String current_date = st.nextToken();
//				String is_completed = st.nextToken();
//				int com = Integer.parseInt(is_completed);
//				PreparedStatement pstmt = conn.prepareStatement(sql);
//				pstmt.setString(1,title);
//				pstmt.setString(2,description);
//				pstmt.setString(3,category);
//				pstmt.setString(4,current_date);
//				pstmt.setString(5,due_date);
//				pstmt.setInt(6, com);
//				
//				int count = pstmt.executeUpdate();
//				if(count > 0)
//					records++;
//				pstmt.close();
//			}
//			System.out.println(records + " records read!!");
//			br.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public ArrayList<TodoItem> getOrderList(String orderby, int ordering){
		ArrayList<TodoItem> list = new ArrayList<TodoItem>();
		Statement stmt;
		try {
			stmt = conn.createStatement();
			String sql = "SELECT * FROM list ORDER BY " + orderby;
			if(ordering == 0)
				sql += " desc";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("id");
				String category = rs.getString("category");
				String title = rs.getString("title");
				String description = rs.getString("memo");
				String due_date = rs.getString("due_date");
				String current_date = rs.getString("current_date");
				int is_completed = rs.getInt("is_completed");
				int priority = rs.getInt("priority");
				int progress = rs.getInt("progress");
				TodoItem t = new TodoItem(category, title, description, due_date, is_completed, priority, progress);
				t.setId(id);
				t.setCurrent_date(current_date);
				list.add(t);				
			}
			stmt.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
