package dentist;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;

class MainUI extends JFrame implements ActionListener{  //的类，继承JFrame，接口ActionListener
	
	//String user;
	JTabbedPane jtp;
	JScrollPane jsp_res,jsp_pat;
	JPanel jp_infor_res,jp_infor_pat,jp_infor_welcome,jp_res,jp_pat,jp_icon;
	BoxLayout box1,box2,box3;
	JLabel jL_name,jL_phone,jL_date,jL_doc_name,jL_pat_name,labIcon;
	JTextField jtf_name,jtf_phone,jtf_date,jtf_doc_name,jtf_pat_name;
	JButton jb_search1,jb_search2,jb_add1,jb_add2;
	ImageIcon icon,icon2;
	Vector<Vector<Object>> res_data,pat_data;
	Vector<Object> res_data_title,pat_data_title;
	ButtonTable bt_res,bt_pat;
	Connection con=null;
	JTable jt_res,jt_pat;
	Orcl orcl = new Orcl();
	boolean position=false;
	
	public MainUI(String position,Connection con) {  //构造函数
		super("牙科诊所管理系统"); //调用父类的构造函数
		if(con==null) System.out.println("sss");
		
		if(position.equals("接待员")) this.position=true;
		else this.position=false;
		this.con = con;
		
		jtp = new JTabbedPane(JTabbedPane.TOP);
		jp_infor_res = new JPanel();
		jp_infor_pat = new JPanel();
		jp_infor_welcome = new JPanel();
		jp_res = new JPanel();
		jp_res.setLayout(new BorderLayout());
		jp_pat = new JPanel();
		jp_pat.setLayout(new BorderLayout());
		jsp_res = new JScrollPane();
		jsp_pat = new JScrollPane();
		box1 = new BoxLayout(jp_infor_res,BoxLayout.Y_AXIS);
		box2 = new BoxLayout(jp_infor_pat,BoxLayout.Y_AXIS);
		box3 = new BoxLayout(jp_infor_welcome,BoxLayout.Y_AXIS);
		
		jL_name = new JLabel("姓名：");
		jL_phone = new JLabel("联系方式：");
		jL_date = new JLabel("日期(年-月-日)：");
		jL_doc_name = new JLabel("医生名：");
		jL_pat_name = new JLabel("病人名：");
		
		jtf_name = new JTextField(6);
		jtf_phone = new JTextField(8);
		jtf_date = new JTextField(8);
		jtf_doc_name = new JTextField(6);
		jtf_pat_name = new JTextField(6);
		
		jb_search1 = new JButton("查询");
		jb_search1.addActionListener(this);
		jb_search2 = new JButton("查询");
		jb_search2.addActionListener(this);
		jb_add1 = new JButton("添加预约");
		jb_add1.addActionListener(this);
		jb_add2 = new JButton("添加病人");
		jb_add2.addActionListener(this);
		
		jb_add1.setBackground(new Color(199,224,249));
		jb_add2.setBackground(new Color(199,224,249));
		
		jp_infor_res.setLayout(box1);
		jp_infor_pat.setLayout(box2);
		jp_infor_welcome.setLayout(box3);
		jtp.add("欢迎界面",jp_infor_welcome);
		jtp.add("预约表管理",jp_infor_res);
		jtp.add("病人信息管理",jp_infor_pat);
		jtp.validate();
		
		pat_data_title=new Vector<Object>();
		pat_data_title.add("病人编号");
		pat_data_title.add("病人姓名");
		pat_data_title.add("病人性别");
		pat_data_title.add("联系方式");
		pat_data_title.add("");
		pat_data_title.add("");
		pat_data_title.add("");
		pat_data = orcl.getPatResearch(con, jtf_name.getText(), jtf_phone.getText());
		bt_pat = new ButtonTable(this, con, this.position,"patient", null, pat_data, pat_data_title, jtf_name.getText(), jtf_phone.getText(), null, 3);
		jt_pat = bt_pat.getTable();
		jsp_pat = new JScrollPane(jt_pat);
		JPanel jp_mid_pat = new JPanel();
		jp_mid_pat.add(jL_name);
		jp_mid_pat.add(jtf_name);
		jp_mid_pat.add(jL_phone);
		jp_mid_pat.add(jtf_phone);
		jp_mid_pat.add(jb_search1);
		jp_pat.add(jp_mid_pat,BorderLayout.CENTER);
		jp_pat.add(jb_add2,BorderLayout.EAST);
		jp_infor_pat.setLayout(new BorderLayout());
		jp_infor_pat.add(jp_pat,BorderLayout.NORTH);
		jp_infor_pat.add(jsp_pat,BorderLayout.CENTER);
		
		res_data_title=new Vector<Object>();
		res_data_title.add("预约编号");
		res_data_title.add("病人编号");
		res_data_title.add("病人姓名");
		res_data_title.add("预约日期");
		res_data_title.add("医生编号");
		res_data_title.add("医生姓名");
		res_data_title.add("负责人编号");
		res_data_title.add("负责人姓名");
		res_data_title.add("");
		res_data_title.add("");
		res_data_title.add("");
		res_data_title.add("");
		res_data = orcl.getResResearch(con, jtf_date.getText(), jtf_doc_name.getText(), jtf_pat_name.getText());
		bt_res = new ButtonTable(this, con,this.position ,"reservation", null, res_data, res_data_title, jtf_date.getText(), jtf_doc_name.getText(), jtf_pat_name.getText(), 4);
		jt_res = bt_res.getTable();
		jsp_res = new JScrollPane(jt_res);
		JPanel jp_mid_res = new JPanel();
		jp_mid_res.add(jL_date);
		jp_mid_res.add(jtf_date);
		jp_mid_res.add(jL_doc_name);
		jp_mid_res.add(jtf_doc_name);
		jp_mid_res.add(jL_pat_name);
		jp_mid_res.add(jtf_pat_name);
		jp_mid_res.add(jb_search2);
		jp_res.add(jp_mid_res,BorderLayout.CENTER);
		jp_res.add(jb_add1,BorderLayout.EAST);
		jp_infor_res.setLayout(new BorderLayout());
		jp_infor_res.add(jp_res,BorderLayout.NORTH);
		jp_infor_res.add(jsp_res,BorderLayout.CENTER);
		
		
		jp_icon = new JPanel();
		icon = new ImageIcon("images//design.png");
		icon2 = change(icon,1.12,1.05);
		labIcon = new JLabel(icon2);
		jp_icon.add(labIcon);
		jp_infor_welcome.add(jp_icon);
		
		add(jtp,BorderLayout.CENTER);
		
		this.setVisible(true);
		this.setSize(1200,800); //设置界面大小
		this.validate(); //界面重新排版
		this.setResizable(false);
		this.setLocationRelativeTo(null); //界面初始位置 屏幕中心
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); //设置关闭按钮
	}
	
	public void actionPerformed(ActionEvent e) { 
		if(e.getSource()==jb_add1) { //res添加
			UpdateDialog ud = new UpdateDialog(this, "添加预约", con,position, null, null, null, null, null, null);
			ud.setVisible(true);
			bt_res.data=orcl.getResResearch(con, jtf_date.getText(), jtf_doc_name.getText(), jtf_pat_name.getText());
			bt_res.myTableModel.reChanged();
		}
		else if(e.getSource()==jb_search2){ //res查询
			bt_res.data=orcl.getResResearch(con, jtf_date.getText(), jtf_doc_name.getText(), jtf_pat_name.getText());
			bt_res.myTableModel.reChanged();
		}
		else if(e.getSource()==jb_add2&&position) { //pat添加
			UpdateDialog ud = new UpdateDialog(this, "添加病人", con,position, null, null, null, null, null, null);
			ud.setVisible(true);
			bt_pat.data=orcl.getPatResearch(con, jtf_name.getText(), jtf_phone.getText());
			bt_pat.myTableModel.reChanged();
		}
		else if(position){ //pat查询
			bt_pat.data=orcl.getPatResearch(con, jtf_name.getText(), jtf_phone.getText());
			bt_pat.myTableModel.reChanged();
		}
	}
	
	 private ImageIcon change(ImageIcon image, double d,double e) {  //图片缩放
		   int width=(int)(image.getIconWidth()*d);
		   int height=(int)(image.getIconHeight()*e);
		   Image img=image.getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT);
		   ImageIcon image2=new ImageIcon(img);
		   return image2;
		 }
}


