import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class ManageStaff extends JInternalFrame{

	JPanel leftPanel, rightPanel;
	JPanel northPanel;
	JPanel northRightPanel, centerRightPanel, southRightPanel;
	public void initiallize() {
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		
		northPanel = new JPanel();
		northRightPanel = new JPanel();
		centerRightPanel = new JPanel();
		southRightPanel = new JPanel();
		
		add(leftPanel);
		add(rightPanel);
		leftPanel.setLayout(new BorderLayout());
		rightPanel.setLayout(new BorderLayout());
		//left
		northLeft();
		centerLeft();
		
		//right
		northRight();
		centerRight();
		southRight();
	}
	
	JLabel lblTitle;
	public void northLeft() {
		leftPanel.add(northPanel, BorderLayout.NORTH);
		lblTitle = new JLabel("Staff List");
		northPanel.add(lblTitle);
		lblTitle.setHorizontalTextPosition(JLabel.CENTER);
	}
	
	JTable tblStaff;
	JScrollPane scpStaff;
	public void centerLeft() {
		tblStaff = new JTable();
		scpStaff = new JScrollPane();
		scpStaff.getViewport().add(tblStaff);
		leftPanel.add(scpStaff, BorderLayout.CENTER);
		viewStaff();
		tblStaff.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tblStaff.rowAtPoint(e.getPoint());
				String id = tblStaff.getValueAt(row, 0).toString();
				String name = tblStaff.getValueAt(row, 1).toString();
				String salary = tblStaff.getValueAt(row, 3).toString();
				
				txtStaffId.setText(id);
				txtStaffName.setText(name);
				txtStaffSalary.setText(salary);
			}
		});
	}
	
	public void viewStaff() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Staff Id");
		model.addColumn("Staff Name");
		model.addColumn("Staff Email");
		model.addColumn("Staff Salary");
		model.addColumn("Staff Role");
		
		try {
			String query = "SELECT staff.StaffId, staff.StaffName, staff.StaffEmail, staff.StaffSalary, role.RoleName "
					+ "FROM staff JOIN role ON staff.RoleId = role.RoleId";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getString(5)
				});
			}
			tblStaff.setModel(model);
		} catch (Exception e) {
		}
	}
	
	JLabel lblRightTittle;
	public void northRight() {
		rightPanel.add(northRightPanel, BorderLayout.NORTH);
		lblRightTittle = new JLabel("Manage User");
		northRightPanel.add(lblRightTittle);
		lblRightTittle.setHorizontalTextPosition(JLabel.CENTER);
	}
	
	JLabel lblStaffId, lblStaffName, lblStaffSalary, lblStaffRole;
	JTextField txtStaffId, txtStaffName, txtStaffSalary;
	JComboBox cbRole;
	ArrayList<String> roleList = new ArrayList<>();
	
	public void centerRight() {
		rightPanel.add(centerRightPanel, BorderLayout.CENTER);
		String query = "SELECT * FROM role";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				roleList.add(rs.getString(2));
			}
		} catch (Exception e) {
			
		}
		centerRightPanel.setLayout(new GridLayout(4, 2));
		lblStaffId = new JLabel("Staff Id");
		lblStaffName = new JLabel("Staff Name");
		lblStaffSalary = new JLabel("Staff Salary");
		lblStaffRole = new JLabel("Role");
		
		txtStaffId = new JTextField();
		txtStaffName = new JTextField();
		txtStaffSalary = new JTextField();
		txtStaffId.setEditable(false);
		
		cbRole = new JComboBox<>();
		for(String role : roleList) {
			cbRole.addItem(role);
		}
		centerRightPanel.add(lblStaffId);
		centerRightPanel.add(txtStaffId);
		centerRightPanel.add(lblStaffName);
		centerRightPanel.add(txtStaffName);
		centerRightPanel.add(lblStaffSalary);
		centerRightPanel.add(txtStaffSalary);
		centerRightPanel.add(lblStaffRole);
		centerRightPanel.add(cbRole);
	}
	
	JButton btnUpdate, btnDelete;
	
	public void clear() {
		txtStaffName.setText(null);
		txtStaffId.setText(null);
		txtStaffSalary.setText(null);
	}
	
	public void southRight() {
		rightPanel.add(southRightPanel, BorderLayout.SOUTH);
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		southRightPanel.add(btnUpdate);
		southRightPanel.add(btnDelete);
		
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String name = txtStaffName.getText();
					Integer salary = Integer.parseInt(txtStaffSalary.getText());
					Integer id = Integer.parseInt(txtStaffId.getText());
					Integer roleId = 0;
					String roleName = cbRole.getSelectedItem().toString();
					String query2 = "SELECT * FROM role WHERE RoleName='"+roleName+"' ";
					Statement st = con.createStatement();
					ResultSet rs2 = st.executeQuery(query2);
					while(rs2.next()) {
						roleId = rs2.getInt(1);
					}
					String query = "UPDATE staff SET StaffName='"+name+"', StaffSalary="+salary+", RoleId="+roleId+" WHERE StaffId="+id+"";
					st.execute(query);
					JOptionPane.showMessageDialog(null, "Successs update staff");
					st.close();
					viewStaff();
					clear();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
		
		btnDelete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Integer id = Integer.parseInt(txtStaffId.getText());
					String query = "DELETE FROM staff WHERE StaffId="+id+"";
					Statement st = con.createStatement();
					st.execute(query);
					JOptionPane.showMessageDialog(null, "Delete success");
					st.close();
					viewStaff();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});
	}
	
	Connection con;
	public ManageStaff() {
		super("Manage Staff", true, true, true, true);
		con = sqlConnector.connection();
		setLayout(new GridLayout(1, 2));
		initiallize();
		setVisible(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	}


}
