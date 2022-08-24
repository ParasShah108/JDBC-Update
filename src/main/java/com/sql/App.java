package com.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class App {
	Scanner sc = new Scanner(System.in);
	List<String> sid = new ArrayList<String>();
	List<String> sidd = new ArrayList<String>();
	String studentid;
	String cont;
	String choice;
	String str1;
	String str2;
	String cid;
	String vcid;
	String cEname;
	int time;
	int updateTime;
	float comp;
	int updateComp;
	Date edate = new Date();
	SimpleDateFormat f = new SimpleDateFormat("dd/MM/yy");
	String date = f.format(edate);

	public static void main(String args[]) throws ClassNotFoundException, SQLException {

		App ap = new App();
		ap.Login();

	}

	private void Login() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/coursedetails", "root", "root");

		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		// ResultSet rs = stmt.executeQuery("select * from student_info");
		System.out.println("Enter the login details!!");
//		Scanner input = new Scanner(System.in);
//		System.out.println("Enter the Username  ");
//		//while (rs.next());
//		String studentName = input.nextLine();
//		for (int i = 1; i < 100; i++) {
//				if(rs.next()) 
//				{
//				
//				if (studentName.equals(rs.getString(3))) {
//					System.out.println("Enter Password");
//					String password = input.nextLine();
//					if (password.equals(rs.getString(4))) {
//						System.out.println("Login Successfull!!");
//						break;
//					} else {
//						System.out.println("Wrong  Password");
//						Login();
//
//					}
//				}
//				}
//				else
//				{
//					System.out.println("Wrong Username!!");
//					Login();
//				}
//
//			
//				
//
//			
//		}

		System.out.print("Enter the username : ");
		str1 = sc.next();
		System.out.print("Enter the password : ");
		str2 = sc.next();
		ResultSet rs = stmt
				.executeQuery("select * from student_info where Name='" + str1 + "' and Login_Password='" + str2 + "'");
		if (rs.next()) {
			System.out.println("Welcome::: " + str1);
			menu();
		}

		else {
			System.out.println("Invalid user name and password");
			Login();
		}
		con.close();
	}

	public void menu() throws ClassNotFoundException, SQLException, IllegalArgumentException, NullPointerException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/coursedetails", "root", "root");

		Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery("select * from course_info_table");

		Statement student = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

		ResultSet stid = student.executeQuery("select Student_Id from student_info where Name='" + str1 + "'");
		while (stid.next()) {
			studentid = stid.getString(1);
			// cEname = rs.getString(1);
			// System.out.println(stid.getString(1));
			sidd.add(stid.getString(1));
		}
		try {

			while (true) {
				System.out.println("---------------------------------Enter your choice!!---------------------------");

				System.out.println("a.Display all courses \n b. View enrolled courses \n "
						+ "c. Enroll in a new course\n d. Clock-in time\r\n e. View Summary\r\n f. Exit");

				choice = sc.next();
//			try {
//				
//				 Pattern pattern = Pattern.compile("^[0-9]*$");
//				    Matcher matcher = pattern.matcher(choice);
//				    boolean matchFound = matcher.find();
//				    if(matchFound) {
//				    	throw new NumberFormatException
//			            ("value entered wrong");
//							}

				if (!choice.equals("a") && !choice.equals("b") && !choice.equals("c") && !choice.equals("d")
						&& !choice.equals("e") && !choice.equals("f")) {
					throw new IllegalArgumentException("value entered wrong");
				}
				switch (choice) {
				case "a":
					Statement stmtCourses = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					ResultSet viewCourses = stmtCourses.executeQuery("select * from course_info_table");
					ResultSetMetaData rsMetaData = viewCourses.getMetaData();
					int count = rsMetaData.getColumnCount();
					for (int i = 1; i <= count; i++) {
						System.out.print(rsMetaData.getColumnName(i) + "\t");
					}
					System.out.println("");
					while (viewCourses.next())
						System.out.println(viewCourses.getString(1) + "\t" + viewCourses.getString(2) + "\t\t"
								+ viewCourses.getString(3) + "\t" + viewCourses.getString(4) + "\t"
								+ viewCourses.getInt(5));
					break;
				case "b":

//			ResultSet stid = stmt.executeQuery("select Student_Id from student_info where Name='" + str1+"'");
//			while(stid.next()) {
//				studentid=stid.getString(1);
//				//cEname = rs.getString(1);
//				System.out.println(stid.getString(1));
//				sidd.add(stid.getString(1));}

					ResultSet rs1 = stmt
							.executeQuery("select * from enrolled_courses where StudentID='" + studentid + "'");
					ResultSetMetaData rsMetaData1 = rs1.getMetaData();
					int count1 = rsMetaData1.getColumnCount();
					for (int i = 1; i <= count1; i++) {
						System.out.print(rsMetaData1.getColumnName(i) + "\t");
					}
					System.out.println("");
					while (rs1.next()) {
						System.out.println(rs1.getString(1) + "\t" + rs1.getString(2) + "\t\t" + rs1.getString(3) + "\t"
								+ rs1.getString(4) + "\t" + rs1.getString(5));

					}

					break;
				case "c":
					System.out.println("Enter the Course ID");
					cid = sc.next();

					Statement stmtc = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					Statement stmtcd = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					Statement stif = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);

					ResultSet rsd = stif.executeQuery("select * from enrolled_courses where Course_id='" + cid
							+ "' and StudentID='" + studentid + "'");
					if (rsd.next()) {
						System.out.println("Data Already Present ");

					} else {
						ResultSet rsc = stmtc.executeQuery(
								"select Course_Name,Course_Id from course_info_table where Course_Id ='" + cid + "'");

						while (rsc.next()) {
							System.out.println(
									"You are successfully enrolled in" + rsc.getString(1) + " " + rsc.getString(2));

							PreparedStatement ps = conn
									.prepareStatement("insert into enrolled_courses values(?,?,?,?,?)");
							ps.setString(1, null);
							ps.setString(2, rsc.getString(2));
							ps.setString(3, rsc.getString(1));
							ps.setString(4, date);
							ps.setString(5, studentid);
							int n = ps.executeUpdate();
							System.out.println(n + " records affected");

						}
					}
					break;
				case "d":

					Connection cond = DriverManager.getConnection("jdbc:mysql://localhost:3306/coursedetails", "root",
							"root");

					Statement vstmt = cond.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
					Statement stmtsid = cond.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					Statement updatesid = cond.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					Statement checktime = cond.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_UPDATABLE);
					PreparedStatement ps2;
					System.out.println("Enter the Course ID");
					vcid = sc.next();
					System.out.println("Enter the TimeSpent");

					// String flag = Integer.toString(time);

					time = sc.nextInt();
					if (Integer.toString(time).matches("[^0-9]*$")) {
						throw new InputMismatchException("Wrong");
					}

					ResultSet ct = vstmt.executeQuery(
							"select Course_Id,Course_Name,Duration from course_info_table where Course_Id ='" + vcid
									+ "'");

//				ResultSet check = checktime.executeQuery(
//						"select Course_Id,Course_Name,Duration from course_info_table where Course_Id ='" + vcid + "'");

					ResultSet ctc = updatesid.executeQuery("select TimeSpent,Duration from view_summary where Cid ='"
							+ vcid + "'and Sid='" + studentid + "'");
					if (ctc.next()) {
						// System.out.println(ctc.getInt(1)+time);
						updateTime = ctc.getInt(1) + time;
						// System.out.println(ctc.getInt(2));
						updateComp = updateTime * 100 / ctc.getInt(2);
						// menu();
						ps2 = cond.prepareStatement("update view_summary set TimeSpent=?,Completion=? where Cid ='"
								+ vcid + "' and Sid='" + studentid + "'");
						ps2.setInt(1, updateTime);
						ps2.setInt(2, updateComp);
						int i = ps2.executeUpdate();
						System.out.println(i + " records updated");
//					
//					updateComp=updateTime*100/ ct.getInt(3);
//					PreparedStatement ps3=cond.prepareStatement("update view_summary set Completion=? where Cid ='" + vcid+"'");
//					ps3.setInt(1,updateComp);
//					int ij=ps3.executeUpdate();  
//					System.out.println(ij+" records updated"); 
					} else {

						// ResultSet vsid = vstmt.executeQuery("select Student_Id from student_info
						// where Name='" + str1+"'");
						while (ct.next()) {
							comp = time * 100 / ct.getInt(3);
							System.out.println("You are successfully clocked" + time + " hrs in" + ct.getString(2));
							PreparedStatement ps1 = cond
									.prepareStatement("insert into view_summary values(?,?,?,?,?,?,?)");
							ps1.setString(1, null);
							ps1.setString(2, ct.getString(1));
							ps1.setString(3, ct.getString(2));
							ps1.setInt(4, ct.getInt(3));
							ps1.setInt(5, time);
							ps1.setFloat(6, comp);
							ps1.setString(7, studentid);
							int n = ps1.executeUpdate();
							System.out.println(n + " records affected");
						}
					}
//			Statement vstmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//
//			System.out.println("Enter the Course ID");
//			vcid=sc.next();
//			System.out.println("Enter the TimeSpent");
//			time=sc.nextInt();
//			ResultSet ct = vstmt.executeQuery("select Course_Id,Course_Name,Duration from course_info_table where Course_Id ='"+vcid+"'");
//			ResultSet vsid = vstmt.executeQuery("select Student_Id from student_info where Name='" + str1+"'");
//			while(ct.next()) {
//				System.out.println("You are successfully clocked" +time+" hrs in  ");
//				
//				PreparedStatement ps=conn.prepareStatement("insert into enrolled_courses values(?,?,?,?,?)");
//				ps.setString(1,null);  
//				ps.setString(2,rsc.getString(2));
//				ps.setString(3,rsc.getString(1));
//				ps.setString(4,date);
//				for(int i =0;i<sid.size();i++) {
//				ps.setString(5,sid.get(i));}
//				int n=ps.executeUpdate();  
//				System.out.println(n+" records affected");

//				}

					break;
				case "e":
					// System.out.println(studentid);

//				for (int j = 0; j < sidd.size(); j++) {
					ResultSet summary = stmt.executeQuery("select * from view_summary where Sid='" + studentid + "'");
					ResultSetMetaData vSummary = summary.getMetaData();
					int count2 = vSummary.getColumnCount();
					for (int i = 1; i <= count2; i++) {
						System.out.print(vSummary.getColumnName(i) + "\t");
					}

					System.out.println("");
					while (summary.next())
						System.out.println(summary.getInt(1) + "\t" + summary.getString(2) + "\t\t"
								+ summary.getString(3) + "\t" + summary.getInt(4) + "\t" + summary.getInt(5) + "\t"
								+ summary.getInt(6) + "\t" + summary.getString(7));

//				}
					break;
				case "f":
					System.out.println("You have been logegd out!!");
					System.out.println("-----------------------------------------------------");
					Login();
					break;
				default:
					System.out.println("Enter correct choice");
				}

			} // conn.close();

//				 
//		}  
//			catch (NumberFormatException e) {
//				 
//                // Print the message if exception occurred
//                System.out.println(
//                    "NumberFormatException occurred");
//		}
		} catch (IllegalArgumentException i) {
			System.out.println("Do not enter illegal  character . Want to Continue Y/N");
			cont = sc.next();
			if (cont.equalsIgnoreCase("Y")) {
				menu();
			}
		} catch (InputMismatchException e) {
			try {

				throw new NumberFormatException();
			} catch (NumberFormatException ex) {
				// Print the message if exception occurred
				System.out.println("NumberFormatException occurred");
				// System.out.println("---------------------------------Enter your
				// choice!!---------------------------");
				choice = sc.next();
				menu();
			}
		}

	}
}