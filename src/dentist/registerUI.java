package dentist;


import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

class registerUI extends JFrame implements ActionListener{  //登录界面的类，继承JFrame，接口ActionListener
	
	JPanel jp0,jp1,jp2,jp3,jp4,jp5,jp6,jp7,jp8,jp; 
	JLabel jL_login,jL_username,jL_name,jL_sex,jL_position,jL_phone,jL_password,jL_keyword;  
	JTextField jtf_username,jtf_name,jtf_sex,jtf_position,jtf_phone,jtf_password,jtf_keyword; 
	JButton jb_regis,jb_back;  
	
	public registerUI(){
		super(); //调用父类的构造函数

		setTitle("牙科诊所管理系统");
		setLayout(new GridLayout(9,1)); //网格布局，9行1列			
		jp0 = new JPanel();
		jp0.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
		jp1 = new JPanel();
		jp1.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 5));
		jp2 = new JPanel();
		jp2.setBorder(BorderFactory.createEmptyBorder(5, 5, 0, 5));
		jp3 = new JPanel();
		jp4 = new JPanel();
		jp5 = new JPanel();
		jp6 = new JPanel();
		jp7 = new JPanel();
		jp8 = new JPanel();
		jtf_username = new JTextField(12);
		jtf_name= new JTextField(12);
		jtf_sex = new JTextField(12);
		jtf_position= new JTextField(12);
		jtf_phone= new JTextField(12);
		jtf_password= new JTextField(12);
		jtf_keyword= new JTextField(12);
		jL_login = new JLabel("用户注册");
		jL_login.setFont(new Font("黑体",1,18));
		jL_login.setForeground(new Color(25,90,137));
		jL_username = new JLabel("人员编号：");
		jL_name=new JLabel("姓       名 ：");
		jL_sex=new JLabel("性       别 ：");
		jL_position=new JLabel("职       位 ：");
		jL_phone=new JLabel("联系方式：");
		jL_password= new JLabel("用户密码：");
		jL_keyword = new JLabel("注册密钥：");
		jp = new JPanel();
		jb_back = new JButton("返回");
		jb_back.setBackground(new Color(255,127,39));
		jb_back.setForeground(Color.white);
		jb_back.addActionListener(this);
		jb_regis = new JButton("注册");
		jb_regis.setBackground(new Color(255,127,39));
		jb_regis.setForeground(Color.white);
		jb_regis.addActionListener(this);
		
    		jp0.setOpaque(false);
		jp1.setOpaque(false);
		jp2.setOpaque(false);
		jp3.setOpaque(false);
		jp4.setOpaque(false);
		jp5.setOpaque(false);
		jp6.setOpaque(false);
		jp7.setOpaque(false);
		jp8.setOpaque(false);
		
		jp0.add(jL_login);
		jp1.add(jL_username);
		jp1.add(jtf_username);
		jp2.add(jL_name);
		jp2.add(jtf_name);
		jp3.add(jL_sex);
		jp3.add(jtf_sex);
		jp4.add(jL_position);
		jp4.add(jtf_position);
		jp5.add(jL_phone);
		jp5.add(jtf_phone);
		jp6.add(jL_password);
		jp6.add(jtf_password);
		jp7.add(jL_keyword);
		jp7.add(jtf_keyword);
		jp.add(jb_regis);
		jp.add(jb_back);
		jp8.add(jp);
		
		add(jp0);
		add(jp1);
		add(jp2);
		add(jp3);
		add(jp4);
		add(jp5);
		add(jp6);
		add(jp7);
		add(jp8);
		
		validate();
		setResizable(false);
		setVisible(true); //显示注册界面
		setSize(345,500);  //设置界面大小
		setLocationRelativeTo(null); //设置界面初始位置：屏幕中心
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//设置关闭图标
	}

	public void actionPerformed(ActionEvent e) { 
		if(e.getSource()==jb_back) { //返回按钮
			this.dispose();
			new LoginUI();
		}
		else { //注册按钮
			if(!jtf_username.getText().equals("")&&!jtf_name.getText().equals("")&&!jtf_sex.getText().equals("")&&!jtf_position.getText().equals("")&&!jtf_phone.getText().equals("")&&!jtf_password.getText().equals("")&&!jtf_keyword.getText().equals("")){
			try{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:dentist","dentist","dentist");
				if(jtf_keyword.getText().equals("123456")){
				String sql="insert into worker(wno,wname,wsex,wposition,wpassword,wphone) values(?,?,?,?,?,?)";
				PreparedStatement sm=connection.prepareStatement(sql);
				sm.setString(1, jtf_username.getText());
				sm.setString(2, jtf_name.getText());
				sm.setString(3, jtf_sex.getText());
				sm.setString(4, jtf_position.getText());
				sm.setString(5, jtf_password.getText());
				sm.setString(6, jtf_phone.getText());
				sm.executeUpdate();
				}
				else
					JOptionPane.showMessageDialog(null,"注册密钥错误", "警告",  JOptionPane.ERROR_MESSAGE);
			}
			catch(Exception e1){
				JOptionPane.showMessageDialog(null,"用户已存在", "警告",  JOptionPane.ERROR_MESSAGE);
			}
			JOptionPane.showMessageDialog(null, "添加成功!");
}
else
	JOptionPane.showMessageDialog(null,"请输入所有数据", "警告",  JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
