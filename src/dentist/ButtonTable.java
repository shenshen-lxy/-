package dentist;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.sql.Connection;
import java.util.Vector;
 
public class ButtonTable {
	
    JTable table;
    Vector<Vector<Object>> data=null;
    Vector<Object> title = new Vector<Object>();
    Orcl orcl=new Orcl();
    Connection con;
    String no;
    int buttonNum;
    String own;
    JFrame f;
    MyTableModel myTableModel;
    String s1,s2,s3;
    boolean position=false;
 
    public class OneButtonRenderer implements TableCellRenderer {
    	
        private JButton button;
        private String str;
    
        public OneButtonRenderer() {
            button = new JButton();
        }
     
        public Component getTableCellRendererComponent(JTable table, Object s,
            boolean isSelected, boolean hasFocus, int row, int column) {
        	str=(String)s;
        	button.setText((String)s);
            return button;
        }
     
    }
    
    public class OneButtonEditor extends AbstractCellEditor implements TableCellEditor {

    	private static final long serialVersionUID = 1L;

    	private JButton button;
    	private String str;

    	public OneButtonEditor() {
    		button = new JButton();
    		button.addActionListener(new ActionListener() {
    			public void actionPerformed(ActionEvent e) {
    				if(own.equals("reservation")) {   //预约表
	    				if(str.equals("修改")&&position) {
	    					int row=table.getSelectedRow();
    						String rno=(String)data.get(row).get(0);
    						String tdate=(String)data.get(row).get(3);
    						String dno=(String)data.get(row).get(4);
    						String wno=(String)data.get(row).get(6);
    						UpdateDialog dialog=new UpdateDialog(f,"修改该预约",con,position,rno,tdate,dno,wno,null,null);
    						dialog.setVisible(true);
	    				}
	    				else if(str.equals("删除")&&position) {
    						if(JOptionPane.showConfirmDialog(null,"是否确定删除？", "Question",  JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) {
	    						int row=table.getSelectedRow();
	    						String rno=(String)data.get(row).get(0);
	    						orcl.res_delete(con, rno);
	    					};
    					}
	    				else if(str.equals("完成")&&!position) {
    						if(JOptionPane.showConfirmDialog(null,"是否确定完成？", "Question",  JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) {
    							int row=table.getSelectedRow();
        						String rno=(String)data.get(row).get(0);
        						String pno=(String)data.get(row).get(1);
        						String ttime=(String)data.get(row).get(3);
        						String dno=(String)data.get(row).get(4);
        						UpdateDialog dialog=new UpdateDialog(f,"完成该预约，并添加到诊疗记录",con,position,rno,pno,ttime,dno,null,null);
        						dialog.setVisible(true);
	    					};
    					}
	    				else if(str.equals("打印")&&position) {
	    					if(data==null) return;
	    					int row=table.getSelectedRow();
    						String rno=(String)data.get(row).get(0);
    						String rtime = (String)data.get(row).get(3);
    						JDialog dialog=new JDialog(f,"打印预约表");
    						Vector<Vector<Object>> data;
    						Vector<Object> dataTitle=new Vector<Object>();
    						data=orcl.reser_print(con,rno);
    						dialog.setLayout(new GridLayout(1,2));
    						JPanel jpa,jpb;
    						jpa=new JPanel(new GridLayout(4,1)); jpb=new JPanel(new GridLayout(5,1));
    						jpa.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
    						jpb.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));
    						JLabel jL1,jL2,jL3,jL4,jL5,jL6,jL7,jL8,jL9;
    						//System.out.println(data.get(0).get(0));
    						jL1=new JLabel("病人编号："+data.get(0).get(0));
    						jL2=new JLabel("病人姓名："+data.get(0).get(1));
    						jL3=new JLabel("病人性别："+data.get(0).get(2));
    						jL4=new JLabel("联系方式："+data.get(0).get(3));
    						jL5=new JLabel("预约编号："+rno);
    						jL6=new JLabel("诊疗时间："+rtime);
    						jL7=new JLabel("医生编号："+data.get(0).get(4));
    						jL8=new JLabel("医生姓名："+data.get(0).get(5));
    						jL9=new JLabel("医生联系方式："+data.get(0).get(6));
    						jpa.add(jL1); jpa.add(jL2); jpa.add(jL3); jpa.add(jL4);
    						jpb.add(jL5); jpb.add(jL6); jpb.add(jL7); jpb.add(jL8); jpb.add(jL9);
    						dialog.add(jpa); dialog.add(jpb); 
    						dialog.setSize(400, 200);
    						dialog.validate();
    						dialog.setResizable(false);
    						dialog.setLocationRelativeTo(null);
    						dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
    						dialog.setModal(true);
    						dialog.setVisible(true);	
    						
	    				}
	    				fireEditingStopped();
	    				data=orcl.getResResearch(con,s1,s2,s3);  //刷新table
    					table.repaint();
	    					
    				}
    				if(own.equals("patient")) {  //病人信息表
    					if(str.equals("修改")&&position) {
    						int row=table.getSelectedRow();
    						String pno=(String)data.get(row).get(0);
    						String pname=(String)data.get(row).get(1);
    						String psex=(String)data.get(row).get(2);
    						String pphone=(String)data.get(row).get(3);
    						UpdateDialog dialog=new UpdateDialog(f,"修改该病人信息",con,position,pno,pname,psex,pphone,null,null);
    						dialog.setVisible(true);
    					}
    					else if(str.equals("删除")&&position) {
    						if(JOptionPane.showConfirmDialog(null,"是否确定删除？","Question",  JOptionPane.YES_NO_OPTION)== JOptionPane.YES_OPTION) {
	    						int row=table.getSelectedRow();
	    						String pno=(String)data.get(row).get(0);
	    						orcl.pat_delete(con, pno);
	    					};
    					}
    					else if(str.equals("诊疗记录")) {
	    					int row=table.getSelectedRow();
	    					String pno=(String)data.get(row).get(0);
	    					JDialog dialog=new JDialog(f,"诊疗记录");
	    					Vector<Vector<Object>> data;
	    					Vector<Object> dataTitle=new Vector<Object>();
	    					data=orcl.getRecoverRecord(con,pno);
	    					dataTitle.add("诊疗编号");  dataTitle.add("病人名");  dataTitle.add("病情");   dataTitle.add("诊疗方案");  
	    					dataTitle.add("诊疗日期");   dataTitle.add("医生编号");   dataTitle.add("医生名");   dataTitle.add("");
	    					ButtonTable bt = new ButtonTable(f,con,position,"treatment",null,data,dataTitle,pno,null,null,1);
	    					JTable jt=bt.getTable();
	    					JScrollPane jsp=new JScrollPane(jt);
	    					dialog.setLayout(new BorderLayout());
	    					dialog.add(jsp,BorderLayout.CENTER);
	    					dialog.setSize(800, 300);
	    					dialog.validate();
	    					dialog.setResizable(false);
	    					dialog.setLocationRelativeTo(null);
	    					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
	    					dialog.setModal(true);
	    					dialog.setVisible(true);
	    				}
    					fireEditingStopped();
    					data=orcl.getPatResearch(con,s1,s2);  //刷新table
    					table.repaint();
    				}
    				if(own.equals("treatment")) {  //<诊疗记录>表
    					if(str.equals("修改")&&!position) {
    						int row=table.getSelectedRow();
    						String tno=(String)data.get(row).get(0);
    						String tcon=(String)data.get(row).get(2);
    						String tpro=(String)data.get(row).get(3);
    						UpdateDialog dialog=new UpdateDialog(f,"修改该诊疗记录",con,position,tno,tcon,tpro,null,null,null);
    						dialog.setVisible(true);
    					}
    					fireEditingStopped();
    					data=orcl.getRecoverRecord(con,s1);  //刷新table
    					table.repaint();
    				}
    				//fireEditingStopped();
    				
    			}
    		});
    	}
    	
    	@Override
    	public Component getTableCellEditorComponent(JTable table, Object s,
    			boolean isSelected, int row, int column) {
    		str = (String) s;
    		button.setText((String)s);
    		return button;
    	}

    	@Override
    	public Object getCellEditorValue() {
    		return str;
    	}
    }
    
    public class MyTableModel extends AbstractTableModel{
    	@Override
        public Object getValueAt(int row, int column) {
    		Vector<Object> v=data.get(row);
            return v.get(column);
        }
        
        @Override public String getColumnName(int columnIndex) {
            return (String)title.get(columnIndex);
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public int getColumnCount() {
            return title.size();
        }
        @Override
        public void setValueAt(Object s, int row, int column){
        	Vector<Object> v=data.get(row);
        	v.set(column,s);
        	data.set(row, v);
            fireTableCellUpdated(row, column);
        }
         
        @Override
        public boolean isCellEditable(int row, int column) {
        	if(buttonNum==0) {
        		return false;
        	}
        	else if(buttonNum==1) {
        		if (column == (title.size()-1)) return true;
        		else return false;
            }
        	else if(buttonNum==2) {
        		if ((column == (title.size()-1))||(column == (title.size()-2))) return true;
        		else return false;
        	}
        	else if(buttonNum==3) {
        		if ((column == (title.size()-1))||(column == (title.size()-2))||(column == (title.size()-3))) return true;
        		else return false;
        	}
        	else if(buttonNum==4) {
        		if ((column == (title.size()-1))||(column == (title.size()-2))||(column == (title.size()-3))||(column == (title.size()-4))) return true;
        		else return false;
        	}
        	return false;
        }
        
        public void reChanged() {  //更新表数据
        	this.fireTableDataChanged();
        }
    }
 
    public ButtonTable(JFrame f,Connection con,boolean position,String own,String no,Vector<Vector<Object>> data,Vector<Object> title,
    		           String s1,String s2,String s3,int buttonNum) {  //s1预约日期/病人名/病人编号   s2医生名/病人手机   s3病人名 
    	this.position=position;
    	this.data=data;
    	this.title=title;
    	this.con=con;
    	this.no=no;
    	this.buttonNum=buttonNum;
    	this.own=own;
    	this.f=f;
    	this.s1=s1;
    	this.s2=s2;
    	this.s3=s3;
    	
    	myTableModel=new MyTableModel();
        table = new JTable(myTableModel);
        if(buttonNum==0){
        	table.setRowSelectionAllowed(false);
        }
        else if(buttonNum==1) {
        	table.getColumnModel().getColumn(title.size()-1).setCellEditor(new OneButtonEditor());
        	table.getColumnModel().getColumn(title.size()-1).setCellRenderer(new OneButtonRenderer());
        	table.setRowSelectionAllowed(false);
        }
        else if(buttonNum==2){
        	table.getColumnModel().getColumn(title.size()-1).setCellEditor(new OneButtonEditor());
        	table.getColumnModel().getColumn(title.size()-1).setCellRenderer(new OneButtonRenderer());
        	table.getColumnModel().getColumn(title.size()-2).setCellEditor(new OneButtonEditor());
        	table.getColumnModel().getColumn(title.size()-2).setCellRenderer(new OneButtonRenderer());
        	table.setRowSelectionAllowed(false);
        }
        else if(buttonNum==3){
        	table.getColumnModel().getColumn(title.size()-1).setCellEditor(new OneButtonEditor());
        	table.getColumnModel().getColumn(title.size()-1).setCellRenderer(new OneButtonRenderer());
        	table.getColumnModel().getColumn(title.size()-2).setCellEditor(new OneButtonEditor());
        	table.getColumnModel().getColumn(title.size()-2).setCellRenderer(new OneButtonRenderer());
        	table.getColumnModel().getColumn(title.size()-3).setCellEditor(new OneButtonEditor());
        	table.getColumnModel().getColumn(title.size()-3).setCellRenderer(new OneButtonRenderer());
        	table.setRowSelectionAllowed(false);
        }
        else if(buttonNum==4){
        	table.getColumnModel().getColumn(title.size()-1).setCellEditor(new OneButtonEditor());
        	table.getColumnModel().getColumn(title.size()-1).setCellRenderer(new OneButtonRenderer());
        	table.getColumnModel().getColumn(title.size()-2).setCellEditor(new OneButtonEditor());
        	table.getColumnModel().getColumn(title.size()-2).setCellRenderer(new OneButtonRenderer());
        	table.getColumnModel().getColumn(title.size()-3).setCellEditor(new OneButtonEditor());
        	table.getColumnModel().getColumn(title.size()-3).setCellRenderer(new OneButtonRenderer());
        	table.getColumnModel().getColumn(title.size()-4).setCellEditor(new OneButtonEditor());
        	table.getColumnModel().getColumn(title.size()-4).setCellRenderer(new OneButtonRenderer());
        	table.setRowSelectionAllowed(false);
        }
    }
    
    public JTable getTable() {
    	return table;
    }
    
}