import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ManageStaff extends JInternalFrame{

	JPanel northPanel, centerPanel, southPanel;
	public void initiallize() {
		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		
		add(northPanel, BorderLayout.NORTH);
//		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
		north();
		center();
		south();
	}
	
	JLabel lblTitle;
	public void north() {
		lblTitle = new JLabel("Staff List");
		northPanel.add(lblTitle);
		lblTitle.setHorizontalTextPosition(JLabel.CENTER);
	}
	
	JTable tblStaff;
	JScrollPane scpStaff;
	public void center() {
		tblStaff = new JTable();
		scpStaff = new JScrollPane();
		scpStaff.getViewport().add(tblStaff);
		add(scpStaff, BorderLayout.CENTER);
		viewStaff();
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
			// TODO: handle exception
		}
		
	}
	
	public void south() {
		
	}
	
	Connection con;
	public ManageStaff() {
		super("Manage Staff", true, true, true, true);
		con = sqlConnector.connection();
		initiallize();
		setVisible(true);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
	}


}
