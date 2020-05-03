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


class LoginUI extends JFrame implements ActionListener{  //登录界面的类，继承JFrame，接口ActionListener
	
	JPanel jp0,jp1,jp2,jp3,jp4; //从上至下，一共五个面板
	JLabel jL_login,jL_username,jL_password;  //三个标签，分别为：用户登录，用户名，密码
	JTextField jtf_username,jtf_password; //2个输入文本框，用于：用户名，密码
	JButton jb_login,jb_regis;  //登录、注册按钮
	Connection connection=null;
	
	public LoginUI(){
		super(); //调用父类的构造函数
		
		setTitle("牙科诊所管理系统");
		setLayout(new GridLayout(5,1)); //网格布局，5行1列			
		jp0 = new JPanel();
		jp0.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
		jp1 = new JPanel();
		jp1.setBorder(BorderFactory.createEmptyBorder(20, 10, 0, 10));
		jp2 = new JPanel();
		jp2.setBorder(BorderFactory.createEmptyBorder(0, 10, 20, 10));
		jp3 = new JPanel();
		jp4 = new JPanel();
		jtf_username = new JTextField(15);
		jtf_password = new JTextField(15);
		jL_username = new JLabel("用户登录");
		jL_login = new JLabel();
		jL_login.setFont(new Font("黑体",1,23));
		jL_login.setForeground(new Color(25,90,137));
		jL_username = new JLabel("用户账号：");
		jL_password = new JLabel("用户密码：");
		jb_login = new JButton("登录");
		jb_login.setBackground(new Color(255,127,39));
		jb_login.setForeground(Color.white);
		jb_login.addActionListener(this);
		jb_regis = new JButton("<html><u>注册账号</u></p></html>");
		jb_regis.addActionListener(this);
		jb_regis.setBackground(new Color(255,127,39));
		jb_regis.setBorder(BorderFactory.createEmptyBorder(10, 10, 20, 10));
		jb_regis.setOpaque(false);
		
		
    	jp0.setOpaque(false);
		jp1.setOpaque(false);
		jp2.setOpaque(false);
		jp3.setOpaque(false);
		jp4.setOpaque(false);
		
		jp0.add(jL_login);
		jp1.add(jL_username);
		jp1.add(jtf_username);
		jp2.add(jL_password);
		jp2.add(jtf_password);
		jp3.add(jb_login);
		jp4.add(jb_regis);
		
		add(jp0);
		add(jp1);
		add(jp2);
		add(jp3);
		add(jp4);
		
		validate();
		setVisible(true); //显示登陆界面
		setResizable(false);
		setSize(345,350);  //设置界面大小
		setLocationRelativeTo(null); //设置界面初始位置：屏幕中心
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	//设置关闭图标
	}

	public void actionPerformed(ActionEvent e) { 
		if(e.getSource()==jb_regis) {
			this.dispose();//释放一部分资源
			new registerUI();
		}
		else //e.getSource()==jb_login
		{
			String position="接待员";
			String username=jtf_username.getText();
			if(!username.equals("")){
				try{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:dentist","dentist","dentist");
					System.out.print("数据库连接成功！");
					String sql="select wpassword,wposition from worker where wno=?";
					PreparedStatement sm=connection.prepareStatement(sql);
					sm.setString(1, username);
					ResultSet rs=sm.executeQuery();
					if(rs.next()) {						
						if(rs.getString(1).equals(jtf_password.getText())){
							position=rs.getString(2);
							new MainUI(position,connection);
							this.dispose();
						}
						else
							JOptionPane.showMessageDialog(null,"密码不正确，请重新输入！", "警告",  JOptionPane.ERROR_MESSAGE);
					}
					else
						JOptionPane.showMessageDialog(null,"用户不存在！", "警告",  JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception e1){
					JOptionPane.showMessageDialog(null,"系统异常 或 用户不存在！", "警告",  JOptionPane.ERROR_MESSAGE);
				}
			}
			else
				JOptionPane.showMessageDialog(null,"未输入用户名", "警告",  JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void main(String[] args) {  //主函数
		new LoginUI();
	}
}