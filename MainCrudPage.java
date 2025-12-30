import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainCrudPage extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtStu;
	private JTextField txtNum;
	private JTextField txtCou;
	private JTable table;
	private JTextField txtId;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainCrudPage frame = new MainCrudPage();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	
	public void connect() {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/rwb", "root", "");
			}
		catch(SQLException ex) {
	        ex.printStackTrace();
		}
	} 		
	
	public void table_load() 
	{try 
	{
	pst= con.prepareStatement("select * from record");	
	rs= pst.executeQuery();
	table.setModel (DbUtils.resultSetToTableModel(rs));
	}catch(SQLException e ) {
		e.printStackTrace();
	}
	}
	
	
	
	
	/**
	 * Create the frame.
	 */
	public MainCrudPage() {
		connect();	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 648, 427);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Register ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 52, 314, 182);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student name:");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblNewLabel.setBounds(10, 27, 115, 26);
		panel.add(lblNewLabel);
		
		txtStu = new JTextField();
		txtStu.setBounds(129, 33, 171, 18);
		panel.add(txtStu);
		txtStu.setColumns(10);
		
		JLabel lblPhoneNumber = new JLabel("Phone number:");
		lblPhoneNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblPhoneNumber.setBounds(10, 63, 115, 26);
		panel.add(lblPhoneNumber);
		
		txtNum = new JTextField();
		txtNum.setColumns(10);
		txtNum.setBounds(129, 69, 96, 18);
		panel.add(txtNum);
		
		JLabel lblCourse = new JLabel("Course:");
		lblCourse.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCourse.setBounds(10, 98, 115, 26);
		panel.add(lblCourse);
		
		txtCou = new JTextField();
		txtCou.setColumns(10);
		txtCou.setBounds(70, 104, 171, 18);
		panel.add(txtCou);
		
		JScrollPane tables = new JScrollPane();
		tables.setBounds(344, 8, 280, 313);
		contentPane.add(tables);
		
		table = new JTable();
		tables.setViewportView(table);
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			String stuName,course,num;
			 
			
			stuName = txtStu.getText();
			course = txtCou.getText();
			num = txtNum.getText();
			try{
				pst= con.prepareStatement ("insert into record(name,phone,course) values (?,?,?)");
				pst.setString(1,stuName);
				pst.setString(2,num);
				pst.setString(3,course);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null, "record added");
			table_load();
				txtStu.setText("");
				txtCou.setText("");
				txtNum.setText("");
				txtStu.requestFocus();
			}catch(SQLException e1) {
			
				e1.printStackTrace();
			};
			
			}
		});
		btnSave.setBounds(10, 244, 85, 44);
		contentPane.add(btnSave);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtStu.setText("");
				txtCou.setText("");
				txtNum.setText("");
			}
		});
		btnClear.setBounds(105, 244, 85, 44);
		contentPane.add(btnClear);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
			System.exit(0);
			
			}
		});
		btnExit.setBounds(200, 244, 85, 44);
		contentPane.add(btnExit);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(10, 298, 314, 54);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JLabel labelTxt = new JLabel("Student id ");
		labelTxt.setFont(new Font("Tahoma", Font.BOLD, 12));
		labelTxt.setBounds(10, 21, 143, 12);
		panel_1.add(labelTxt);
		
		txtId = new JTextField();
		txtId.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
			try {
				String id = txtId.getText();
				pst = con.prepareStatement("select name,phone,course from record where id = ?");
				pst.setString(1, id);
				ResultSet rs =pst.executeQuery();
					
			if(rs.next()==true) {
				String name = rs.getString(1);
				String phone = rs.getString(2);
				String course = rs.getString(3);

				
				txtStu.setText(name);
				txtNum.setText(phone);
				txtCou.setText(course);
				}else {
					txtStu.setText("");
					txtNum.setText("");
					txtCou.setText("");
					
				}
				
			} catch(SQLException ex) {
			
			}
			
			
			}
		});
		txtId.setBounds(81, 19, 206, 18);
		panel_1.add(txtId);
		txtId.setColumns(10);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String stuName,course,num,id;
				 
				
				stuName = txtStu.getText();
				course = txtCou.getText();
				num = txtNum.getText();
				id =txtId.getText();
				try{
					pst= con.prepareStatement ("update record set name = ?, course = ?, phone =? where id=?");
					pst.setString(1,stuName);
					pst.setString(2,num);
					pst.setString(3,course);
					pst.setString(4,id);

					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "record update");
				table_load();
					txtStu.setText("");
					txtCou.setText("");
					txtNum.setText("");
					txtStu.requestFocus();
				}catch(SQLException e1) {
				
					e1.printStackTrace();
				};
				
				
				
				
				
				
				
			}
		});
		btnUpdate.setBounds(352, 336, 85, 44);
		contentPane.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
String id;
				 
				
				
				id =txtId.getText();
				try{
					pst= con.prepareStatement ("delete from record where id=?");
					
					pst.setString(1,id);

					pst.executeUpdate();
					JOptionPane.showMessageDialog(null, "record update");
				table_load();
					txtStu.setText("");
					txtCou.setText("");
					txtNum.setText("");
					txtStu.requestFocus();
				}catch(SQLException e1) {
				
					e1.printStackTrace();
				};
				
			
			
			
			
			
			
			
			
			}
		});
		btnDelete.setBounds(524, 336, 85, 44);
		contentPane.add(btnDelete);
		
		JLabel lblNewLabel_1 = new JLabel("UniPedro");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 24));
		lblNewLabel_1.setBounds(91, 10, 191, 32);
		contentPane.add(lblNewLabel_1);
		table_load();
	}
}
