package dentist;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UpdateDialog extends JDialog implements ActionListener{
	JPanel jp1,jp2,jp3,jp4,jp5,jp6;
	JLabel jL1,jL2,jL3,jL4,jL5,jL6;
	JTextField jtf1,jtf2,jtf3,jtf4,jtf5,jtf6;
	JButton jb;
	
	Connection con;
	Orcl orcl=new Orcl();
	String title,no,s1,s2,s3,s4,s5;
	boolean position=false;
	
	public UpdateDialog(JFrame f,String title,Connection con,boolean position,String no,String s1,String s2,String s3,String s4,String s5) {
		super(f,title);
		setModal(true);
		this.con=con;
		this.position=position;
		this.title=title;
		this.no=no;
		this.s1=s1;
		this.s2=s2;
		this.s3=s3;
		this.s4=s4;
		this.s5=s5;
		
		setLayout(new GridLayout(6,1));
		jp1=new JPanel(); 
		jp2=new JPanel(); 
		jp3=new JPanel();
		jp4=new JPanel();
		jp5=new JPanel();
		jp6=new JPanel();
		jtf1=new JTextField(8);
		jtf2=new JTextField(8);
		jtf3=new JTextField(8);
		jtf4=new JTextField(8);
		jtf5=new JTextField(8);
		jtf6=new JTextField(8);
		jb=new JButton("确认");
		jb.addActionListener(this);
		if(title=="修改该预约") {   //no预约编号  s1预约日期  s2医生编号  s3负责人编号  
			jL1=new JLabel("预约诊疗日期：");
			jL2=new JLabel(" 医 生 编 号 ：");
			jtf1.setText(s1);
			jtf2.setText(s2);
			jp1.add(jL1); jp1.add(jtf1);
			jp2.add(jL2); jp2.add(jtf2);
			jp4.add(jb);
			add(jp1); add(jp2); add(jp4);
		}
		if(title=="修改该病人信息") {  //no病人编号  s1姓名  s2性别  s3联系方式
			jL1=new JLabel(" 姓   名  ：");
			jL2=new JLabel(" 性   别  ：");
			jL3=new JLabel("联系方式：");
			jtf1.setText(s1);
			jtf2.setText(s2);
			jtf3.setText(s3);
			jp1.add(jL1); jp1.add(jtf1);
			jp2.add(jL2); jp2.add(jtf2);
			jp3.add(jL3); jp3.add(jtf3);
			jp4.add(jb);
			add(jp1); add(jp2); add(jp3);  add(jp4);
		}
		if(title=="修改该诊疗记录") { //no诊疗编号  s1病情  s2诊疗方案
			jL1=new JLabel(" 病   情  ：");
			jL2=new JLabel("诊疗方案：");
			jtf1.setText(s1);
			jtf2.setText(s2);
			jp1.add(jL1); jp1.add(jtf1);
			jp2.add(jL2); jp2.add(jtf2);
			jp4.add(jb);
			add(jp1); add(jp2); add(jp4);
		}
		if(title=="完成该预约，并添加到诊疗记录") { //no预约编号  s1病人编号  s2预约日期   s3医生编号 
			jL1=new JLabel("诊疗编号：");
			jL2=new JLabel(" 病   情  ：");
			jL3=new JLabel("诊疗方案：");
			
			jp1.add(jL1); jp1.add(jtf1);
			jp2.add(jL2); jp2.add(jtf2);
			jp3.add(jL3); jp3.add(jtf3);
			jp4.add(jb);
			add(jp1); add(jp2); add(jp3); add(jp4);
		}
		if(title=="添加预约") { //no预约编号  s1病人编号  s2预约日期  s3医生编号  s5负责人编号  
			jL1=new JLabel("预约编号：");
			   jL2=new JLabel("病人编号：");
			   jL3=new JLabel("预约日期：");
			   jL5=new JLabel("医生编号：");
			   jL6=new JLabel("负责人编号");
			   jp1.add(jL1); jp1.add(jtf1);
			   jp2.add(jL2); jp2.add(jtf2);
			   jp3.add(jL3); jp3.add(jtf3);
			   jp5.add(jL5); jp5.add(jtf5);
			   jp6.add(jL6); jp6.add(jtf6);
			   jp4.add(jb);
			   add(jp1); add(jp2); add(jp3); add(jp5); add(jp6); add(jp4); 
		}
		if(title=="添加病人") { //no病人编号  s1姓名  s2性别  s3联系方式
			jL1=new JLabel("病人编号：");
			jL2=new JLabel(" 姓   名  ：");
			jL3=new JLabel(" 性   别  ：");
			jL5=new JLabel("联系方式：");
			jp1.add(jL1); jp1.add(jtf1);
			jp2.add(jL2); jp2.add(jtf2);
			jp3.add(jL3); jp3.add(jtf3);
			jp5.add(jL5); jp5.add(jtf5);
			jp4.add(jb);
			add(jp1); add(jp2); add(jp3); add(jp5); add(jp4);
		}
		jb=new JButton("确认");
		jb.addActionListener(this);
		jb.setPreferredSize(new Dimension(90,25));
		jb.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
		
		this.setSize(300,300); //设置界面大小
		this.validate(); //界面重新排版
		this.setResizable(false);
		this.setLocationRelativeTo(null); //界面初始位置 屏幕中心
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); //设置关闭按钮
	}
	
	public void actionPerformed(ActionEvent e) {
		if(title=="修改该预约"&&position) {
			s1=jtf1.getText();
			s2=jtf2.getText();
			orcl.reser_Update(con,no,s1,s2,s3);
		}
		if(title=="修改该病人信息"&&position) {
			s1=jtf1.getText();
			s2=jtf2.getText();
			s3=jtf3.getText();
			orcl.patient_Update(con,no,s1,s2,s3);
		}
		if(title=="修改该诊疗记录"&&!position) {
			s1=jtf1.getText();
			s2=jtf2.getText();
			orcl.treatment_Update(con,no,s1,s2);
		}
		if(title=="完成该预约，并添加到诊疗记录"&&!position) {
			//s1=;
			//s2=jtf2.getText();
			orcl.reser_finish(con,jtf1.getText(),s1,s2,jtf2.getText(),jtf3.getText(),s3);
		}
		if(title=="添加预约") {
			no=jtf1.getText();
			s1=jtf2.getText();
			s2=jtf3.getText();
			s3=jtf5.getText();
			s5=jtf6.getText();
			orcl.res_add(con,no, s1, s2, s3,s5);
		}
		if(title=="添加病人"&&position) {
			no=jtf1.getText();
			s1=jtf2.getText();
			s2=jtf3.getText();
			s3=jtf5.getText();
			orcl.pat_add(con,no,s1, s2, s3);
		}
		dispose();
	}
}
