import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Transaction extends JInternalFrame{

	JPanel leftPanel, rightPanel;
	int id=0;
	
	public void initiallize() {
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		add(leftPanel);
		add(rightPanel);
		
		left();
		right();
	}
	
//	Main main;
	public void query(String email) {
		try {
			String query = "SELECT * FROM staff WHERE StaffEmail='"+email+"'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				id = rs.getInt(1);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}
	
	JLabel lblTitleHeader;
	JTable tblHeader;
	JScrollPane scpHeader;
	public void left() {
		leftPanel.setLayout(new BorderLayout());
		lblTitleHeader = new JLabel("Header Transaction");
		leftPanel.add(lblTitleHeader, BorderLayout.NORTH);
		lblTitleHeader.setHorizontalAlignment(JLabel.CENTER);
		
		tblHeader = new JTable();
		scpHeader = new JScrollPane();
		scpHeader.getViewport().add(tblHeader);
		leftPanel.add(scpHeader, BorderLayout.CENTER);
		
		viewTableHeader();
	}
	
	JTable tblDetail;
	JScrollPane scpDetail;
	JLabel lblTitleDetail;
	public void right() {
		rightPanel.setLayout(new BorderLayout());
		lblTitleDetail = new JLabel("Detail Transaction");
		rightPanel.add(lblTitleDetail, BorderLayout.NORTH);
		lblTitleDetail.setHorizontalAlignment(JLabel.CENTER);
		
		tblDetail = new JTable();
		scpDetail = new JScrollPane();
		scpDetail.getViewport().add(tblDetail);
		rightPanel.add(scpDetail, BorderLayout.CENTER);
		
		viewTableDetail();
	}
	
	public void viewTableHeader() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Transaction Id");
		model.addColumn("Transaction Date");
		model.addColumn("Total Price");
		try {
			String query = "SELECT HeaderTransaction.TransactionId, HeaderTransaction.TransactionDate, SUM(product.ProductPrice * DetailTransaction.Quantity) "
					+ "FROM DetailTransaction JOIN product ON DetailTransaction.ProductId = product.ProductId JOIN HeaderTransaction"
					+ " ON HeaderTransaction.TransactionId = DetailTransaction.TransactionId "
					+ "WHERE HeaderTransaction.StaffId="+id+" "
					+ "GROUP BY HeaderTransaction.TransactionId";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getInt(1), rs.getDate(2), rs.getInt(3)
				});
			}
			tblHeader.setModel(model);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
		}
	}
	
	public void viewTableDetail() {
		DefaultTableModel model = new DefaultTableModel();		
		model.addColumn("Transaction Id");
		model.addColumn("Product Name");
		model.addColumn("Product Price");
		model.addColumn("Qty");
		try {
			String query = "SELECT DetailTransaction.TransactionId, product.ProductName, product.ProductPrice, DetailTransaction.Quantity "
					+ "FROM DetailTransaction JOIN product ON DetailTransaction.ProductId = product.ProductId JOIN HeaderTransaction"
					+ " ON HeaderTransaction.TransactionId = DetailTransaction.TransactionId "
					+ "WHERE HeaderTransaction.StaffId="+id+" "
							+ " ORDER BY DetailTransaction.TransactionId ASC";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)
				});
			}
			tblDetail.setModel(model);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	Connection con;
	String email;
	public Transaction(String email) {
		super("Transaction History", true, true, true, true);
		con = sqlConnector.connection();
		query(email);
		initiallize();
		setLayout(new GridLayout(1, 2));
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
	}
	
}
