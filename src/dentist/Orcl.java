package dentist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;

public class Orcl {
	public Orcl() {}
//1.预约表管理的查询
	public Vector<Vector<Object>> getResResearch(Connection con,String date,String doc_name,String pat_name){
		  int num=0;
		  if(!date.equals("")) num++;
		  if(!doc_name.equals("")) num++;
		  if(!pat_name.equals("")) num++;
		  Vector<Vector<Object>> data=new Vector<Vector<Object>>();
		  PreparedStatement statement=null;
		  String sql =null;
		  ResultSet rs=null;
		  try {
			  if(num==3) { 
				  sql = "select rno,pat.pno,pname,rdate,doc.dno,dname,worker.wno,wname "
				  		+ "from reservation res,patient pat,doctor doc,worker "
				  		+ "where res.rdate=? and doc.dname=? and pat.pname=? "
				  		+ "and res.pno=pat.pno and res.dno=doc.dno and res.wno=worker.wno";
			      statement =  con.prepareStatement(sql);
			      statement.setString(1,date);
			      statement.setString(2,doc_name);
			      statement.setString(3,pat_name);
			  }
			  else if(num==2) { 
			    if(date.equals("")) {
			    	 sql = "select rno,pat.pno,pname,rdate,doc.dno,dname,worker.wno,wname "
						  		+ "from reservation res,patient pat,doctor doc,worker "
						  		+ "where doc.dname=? and pat.pname=? "
						  		+ "and res.pno=pat.pno and res.dno=doc.dno and res.wno=worker.wno";
					      statement =  con.prepareStatement(sql);
					      statement.setString(1,doc_name);
					      statement.setString(2,pat_name);
			    }
			    else if(doc_name.equals("")) { 
			    	 sql = "select rno,pat.pno,pname,rdate,doc.dno,dname,worker.wno,wname "
						  		+ "from reservation res,patient pat,doctor doc,worker "
						  		+ "where res.rdate=? and pat.pname=? "
						  		+ "and res.pno=pat.pno and res.dno=doc.dno and res.wno=worker.wno";
					      statement =  con.prepareStatement(sql);
					      statement.setString(1,date);
					      statement.setString(2,pat_name);
			    }
			    else { 
			    	sql = "select rno,pat.pno,pname,rdate,doc.dno,dname,worker.wno,wname "
					  		+ "from reservation res,patient pat,doctor doc,worker "
					  		+ "where res.rdate=? and doc.dname=? "
					  		+ "and res.pno=pat.pno and res.dno=doc.dno and res.wno=worker.wno";
				      statement =  con.prepareStatement(sql);
				      statement.setString(1,date);
				      statement.setString(2,doc_name);
			    }
			  }
			  else if(num==1){ 
				  if(!date.equals("")) { 
					  sql = "select rno,pat.pno,pname,rdate,doc.dno,dname,worker.wno,wname "
						  		+ "from reservation res,patient pat,doctor doc,worker "
						  		+ "where res.rdate=? "
						  		+ "and res.pno=pat.pno and res.dno=doc.dno and res.wno=worker.wno";
					      statement =  con.prepareStatement(sql);
					      statement.setString(1,date);
				  }
				  else if(!doc_name.equals("")){ 
					  sql = "select rno,pat.pno,pname,rdate,doc.dno,dname,worker.wno,wname "
						  		+ "from reservation res,patient pat,doctor doc,worker "
						  		+ "where doc.dname=? "
						  		+ "and res.pno=pat.pno and res.dno=doc.dno and res.wno=worker.wno";
					      statement =  con.prepareStatement(sql);
					      statement.setString(1,doc_name);
				  }
				  else { 
					  sql = "select rno,pat.pno,pname,rdate,doc.dno,dname,worker.wno,wname "
						  		+ "from reservation res,patient pat,doctor doc,worker "
						  		+ "where pat.pname=? "
						  		+ "and res.pno=pat.pno and res.dno=doc.dno and res.wno=worker.wno";
					      statement =  con.prepareStatement(sql);
					      statement.setString(1,pat_name);
				  }
			  }
			  else { 
				  sql = "select rno,pat.pno,pname,rdate,doc.dno,dname,worker.wno,wname "
				  		+ "from reservation res,patient pat,doctor doc,worker "
				  		+ "where res.pno=pat.pno and res.dno=doc.dno and res.wno=worker.wno";
			      statement =  con.prepareStatement(sql);
			  }
			  rs = statement.executeQuery();	  
			  while(rs.next()) {
				     Vector<Object> v=new Vector<Object>();
				     for(int i=1;i<=12;i++) {
				      if(i<=8) {
				    	  v.add(rs.getString(i));
				      }
				      else if(i==9) v.add("修改");
				      else if(i==10) v.add("删除");
				      else if(i==11) v.add("完成");
				      else v.add("打印");
				     }
				     data.add(v);
			  }
		  }catch(Exception e) {
			  JOptionPane.showMessageDialog(null,"查询失败！", "Error",  JOptionPane.ERROR_MESSAGE);
			  return null;
		  }
		  //JOptionPane.showMessageDialog(null, "查询成功!");
		  return data;
	}
//2.病人信息管理的查询
	public Vector<Vector<Object>> getPatResearch(Connection con,String pname,String pphone){
		  Vector<Vector<Object>> data=new Vector<Vector<Object>>();
		  PreparedStatement statement=null;
		  String sql =null;
		  ResultSet rs=null;
		  try {
			  if(!pname.equals("")&&!pphone.equals("")) {
				  sql = "select pno,pname,psex,pphone from patient pat where pat.pname=? and pat.pphone=?";
			      statement =  con.prepareStatement(sql);
			      statement.setString(1,pname);
			      statement.setString(2,pphone);
			  }
			  else if(pname.equals("")&&pphone.equals("")) {
				sql = "select pno,pname,psex,pphone from patient";
				statement =  con.prepareStatement(sql);
			  }
			  else if(pname.equals("")) {
			    	sql = "select pno,pname,psex,pphone from patient pat where pat.pphone=?";
			    	statement =  con.prepareStatement(sql);
					statement.setString(1,pphone);
			  }
			  else {
			    sql = "select pno,pname,psex,pphone from patient pat where pat.pname=?";
				statement =  con.prepareStatement(sql);
				statement.setString(1,pname);
			  }
			  rs = statement.executeQuery();
			  while(rs.next()) {
				     Vector<Object> v=new Vector<Object>();
				     for(int i=1;i<=7;i++) {
				      if(i==1||i==2||i==3||i==4)v.add(rs.getString(i));
				      else if(i==5) v.add("修改");
				      else if(i==6) v.add("删除");
				      else v.add("诊疗记录");
				     }
				     data.add(v);
			  }
		  }catch(Exception e) {
			  JOptionPane.showMessageDialog(null,"查询失败！", "Error",  JOptionPane.ERROR_MESSAGE);
			  return null;
		  }
		  //JOptionPane.showMessageDialog(null, "查询成功!");
		  return data;
	}
//3.诊疗记录(病人信息管理表项按钮)
	public Vector<Vector<Object>> getRecoverRecord(Connection con,String pno){
		  Vector<Vector<Object>> data=new Vector<Vector<Object>>();
		  PreparedStatement statement=null;
		  String sql =null;
		  ResultSet rs=null;
		  try {
			  sql = "select tno,pname,tcondition,tprogramme,ttime,doc.dno,dname "
				  		+ "from treatment tre,patient pat,doctor doc "
				  		+ "where tre.pno=? and tre.pno=pat.pno and tre.dno=doc.dno";
			      statement =  con.prepareStatement(sql);
			      statement.setString(1,pno);
			      rs = statement.executeQuery();
				  while(rs.next()) {
					     Vector<Object> v=new Vector<Object>();
					     for(int i=1;i<=8;i++) {
					      if(i<=7) v.add(rs.getString(i));
					      else v.add("修改");
					     }
					     data.add(v);
				  }
		  }catch(Exception e) {
			  JOptionPane.showMessageDialog(null,"查询失败！", "Error",  JOptionPane.ERROR_MESSAGE);
			  return null;
		  }
		  //JOptionPane.showMessageDialog(null, "查询成功!");
		  return data;
	}
//4.预约表表项(修改按钮)
	public int reser_Update(Connection connection,String rno,String rdate,String dno,String wno){
		String sql = "update Reservation set rdate=?,dno=? where rno=?";
	    PreparedStatement statement = null;
	    try {
	    	statement = connection.prepareStatement(sql);
	    	statement.setString(1, rdate);
	    	statement.setString(2, dno);
	    	statement.setString(3, rno);
	    	int num = statement.executeUpdate();
	    	if(num>0)
	    	{
	    		JOptionPane.showMessageDialog(null, "修改成功!");
	    		return 1;
	    	}
	    	else
	    	{
	    		JOptionPane.showMessageDialog(null, "修改失败!");
	    		return 0;
	    	}	
	    }catch (Exception e1) {
	     JOptionPane.showMessageDialog(null, "修改失败!", "错误警告", JOptionPane.ERROR_MESSAGE);
	     return 0;
	    }
	}
//5.病人信息管理表项(修改)	
	public int patient_Update(Connection connection,String pno,String pname,String psex,String pphone){
		String sql = "update Patient set pname=?,psex=?,pphone=? where pno=?";
	    PreparedStatement statement = null;
	    try {
	    	statement = connection.prepareStatement(sql);
	    	statement.setString(1, pname);
	    	statement.setString(2, psex);
	    	statement.setString(3, pphone);
	    	statement.setString(4, pno);
	    	int num = statement.executeUpdate();
	    	if(num>0)
	    	{
	    		JOptionPane.showMessageDialog(null, "修改成功!");
	    		return 1;
	    	}
	    	else
	    	{
	    		JOptionPane.showMessageDialog(null, "修改失败!");
	    		return 0;
	    	}	
	    }catch (Exception e1) {
	     JOptionPane.showMessageDialog(null, "修改失败!", "错误警告", JOptionPane.ERROR_MESSAGE);
	     return 0;
	    }
	}
//6.预约表管理表项(完成按钮)	
	public int reser_finish(Connection connection,String tno,String pno,String ttime,String tcondition,String tprogramme,String dno ){
		System.out.print("tno"+tno+" "+"pno"+pno+" "+"ttime"+ttime+" "+"tcondition"+tcondition+" "+"tprogramme"+tprogramme+" "+"dno"+dno);
		String sql = "insert into Treatment(tno,pno,ttime,tcondition,tprogramme,dno) values(?,?,?,?,?,?)";
	    PreparedStatement statement = null;
	    try {
	    	statement = connection.prepareStatement(sql);
	    	statement.setString(1, tno);
	    	statement.setString(2, pno);
	    	statement.setString(3, ttime);
	    	statement.setString(4, tcondition);
	    	statement.setString(5, tprogramme);
	    	statement.setString(6, dno);
	    	System.out.print("ok");
	    	int num = statement.executeUpdate();
	    	if(num>0)
	    	{
	    		JOptionPane.showMessageDialog(null, "预约完成!");
	    		return 1;
	    	}
	    	else
	    	{
	    		JOptionPane.showMessageDialog(null, "完成失败!");
	    		return 0;
	    	}	
	    }catch (Exception e1) {
	     JOptionPane.showMessageDialog(null, "完成失败!", "错误警告", JOptionPane.ERROR_MESSAGE);
	     return 0;
	    }
	}
//7.预约表管理表项(打印按钮)	
	Vector<Vector<Object>> reser_print(Connection con,String rno){
		System.out.println(rno);
	    Vector<Vector<Object>> data=new Vector<Vector<Object>>();
	    PreparedStatement statement=null;
	    String sql =null;
	    ResultSet rs=null;
	    try {
	      sql = "select rpno,pname,psex,pphone,d.dno,dname,dphone from (select rno,r.pno rpno,rdate,dno,wno,pname,psex,pphone from Reservation r left outer join Patient p on r.pno=p.pno) rp left outer join Doctor d on rp.dno=d.dno where rno=?";
	         statement =  con.prepareStatement(sql);
	         statement.setString(1,rno);
	         rs = statement.executeQuery();
	         while(rs.next()) {
	             Vector<Object> v=new Vector<Object>();
	             for(int i=1;i<=7;i++)
	            	 v.add(rs.getString(i));
	             data.add(v);
	         }
	     }catch(Exception e){
	    	 JOptionPane.showMessageDialog(null,"打印失败！", "Error",  JOptionPane.ERROR_MESSAGE);
	    	 }
	    return data;
	 }
//8.预约表管理表项(删除按钮)
	public int res_delete(Connection con,String rno){
		String sql = "delete from reservation where rno=?";
	    PreparedStatement statement = null;
	    try {
	    	statement = con.prepareStatement(sql);
	    	statement.setString(1, rno);
	    	int num = statement.executeUpdate();
	    	if(num>0)
	    	{
	    		JOptionPane.showMessageDialog(null, "删除成功!");
	    		return 1;
	    	}
	    	else
	    	{
	    		JOptionPane.showMessageDialog(null, "删除失败!");
	    		return 0;
	    	}	
	    }catch (Exception e1) {
	     JOptionPane.showMessageDialog(null, "删除失败!", "错误警告", JOptionPane.ERROR_MESSAGE);
	     return 0;
	    }
	}
//9.病人信息管理表项(删除按钮)
	public int pat_delete(Connection con,String pno){
		String sql = "delete from patient where pno=?";
	    PreparedStatement statement = null;
	    try {
	    	statement = con.prepareStatement(sql);
	    	statement.setString(1, pno);
	    	int num = statement.executeUpdate();
	    	if(num>0)
	    	{
	    		JOptionPane.showMessageDialog(null, "删除成功!");
	    		return 1;
	    	}
	    	else
	    	{
	    		JOptionPane.showMessageDialog(null, "删除失败!");
	    		return 0;
	    	}	
	    }catch (Exception e1) {
	     JOptionPane.showMessageDialog(null, "删除失败!", "错误警告", JOptionPane.ERROR_MESSAGE);
	     return 0;
	    }
	}
//10.预约表管理表项(添加按钮)
	public int res_add(Connection con,String rno,String pno,String rdate,String dno,String wno){
		String sql = "insert into reservation(rno,pno,rdate,dno,wno) values(?,?,?,?,?)";
	    PreparedStatement statement = null;
	    try {
	    	statement = con.prepareStatement(sql);
	    	statement.setString(1, rno);
	    	statement.setString(2, pno);
	    	statement.setString(3, rdate);
	    	statement.setString(4, dno);
	    	statement.setString(5, wno);
	    	int num = statement.executeUpdate();
	    	if(num>0)
	    	{
	    		JOptionPane.showMessageDialog(null, "添加成功!");
	    		return 1;
	    	}
	    	else
	    	{
	    		JOptionPane.showMessageDialog(null, "添加失败!");
	    		return 0;
	    	}	
	    }catch (Exception e1) {
	     JOptionPane.showMessageDialog(null, "添加失败!", "错误警告", JOptionPane.ERROR_MESSAGE);
	     return 0;
	    }
	}
//11.病人信息管理表项(添加按钮)
	public int pat_add(Connection con,String pno,String pname,String psex,String pphone){
		System.out.print("pno"+pno+" "+"pname"+pname+" "+"psex"+psex+" "+"pphone"+pphone);
		String sql = "insert into patient(pno,pname,psex,pphone) values(?,?,?,?)";
	    PreparedStatement statement = null;
	    try {
	    	statement = con.prepareStatement(sql);
	    	statement.setString(1, pno);
	    	statement.setString(2, pname);
	    	statement.setString(3, psex);
	    	statement.setString(4, pphone);	    	
	    	int num = statement.executeUpdate();
	    	if(num>0)
	    	{
	    		JOptionPane.showMessageDialog(null, "添加成功!");
	    		return 1;
	    	}
	    	else
	    	{
	    		JOptionPane.showMessageDialog(null, "添加失败!");
	    		return 0;
	    	}	
	    }catch (Exception e1) {
	     JOptionPane.showMessageDialog(null, "添加失败!", "错误警告", JOptionPane.ERROR_MESSAGE);
	     return 0;
	    }
	}
//12.诊疗记录(修改按钮)
	public int treatment_Update(Connection connection,String tno,String tcon,String tpro){
		String sql = "update treatment set tcondition=?,tprogramme=? where tno=?";
	    PreparedStatement statement = null;
	    try {
	    	statement = connection.prepareStatement(sql);
	    	statement.setString(1, tcon);
	    	statement.setString(2, tpro);
	    	statement.setString(3, tno);;
	    	int num = statement.executeUpdate();
	    	if(num>0)
	    	{
	    		JOptionPane.showMessageDialog(null, "修改成功!");
	    		return 1;
	    	}
	    	else
	    	{
	    		JOptionPane.showMessageDialog(null, "修改失败!");
	    		return 0;
	    	}	
	    }catch (Exception e1) {
	     JOptionPane.showMessageDialog(null, "修改失败!", "错误警告", JOptionPane.ERROR_MESSAGE);
	     return 0;
	    }
	}
}
