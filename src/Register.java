import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Register extends JFrame{

	JPanel northPanel, centerPanel, southPanel;
	Main main = new Main();
	public void initiallize() {
		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();

		add(northPanel);
		add(centerPanel);
		add(southPanel);
		
		north();
		center();
		south();
	}
	
	JLabel lblRegisterTitle;
	public void north() {
		lblRegisterTitle = new JLabel("Register Staff");
		northPanel.add(lblRegisterTitle);
		lblRegisterTitle.setHorizontalTextPosition(JLabel.CENTER);
	}
	
	JLabel lblStaffName, lblStaffEmail, lblStaffPassword, lblStaffPhoto;
	JTextField txtStaffName, txtStaffEmail;
	JPasswordField txtStaffPass;
	JButton btnStaffPhoto;
	JFileChooser staffImageFile;
	public void center() {
		centerPanel.setLayout(new GridLayout(4, 2));
		lblStaffName = new JLabel("Staff Name");
		lblStaffEmail = new JLabel("Staff Email");
		lblStaffPassword = new JLabel("Staff Password");
		lblStaffPhoto = new JLabel("Staff Photo");
		txtStaffName = new JTextField();
		txtStaffEmail = new JTextField();
		txtStaffPass = new JPasswordField();
		btnStaffPhoto = new JButton("Staff Photo");
		staffImageFile = new JFileChooser();
		centerPanel.add(lblStaffName);
		centerPanel.add(txtStaffName);
		centerPanel.add(lblStaffEmail);
		centerPanel.add(txtStaffEmail);
		centerPanel.add(lblStaffPassword);
		centerPanel.add(txtStaffPass);
		centerPanel.add(lblStaffPhoto);
		centerPanel.add(btnStaffPhoto);
		btnStaffPhoto.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				add(staffImageFile);
				staffImageFile.showSaveDialog(null);
			}
		});
	}
	
	int count=0;
	JButton btnRegis;
	public void south() {
		btnRegis = new JButton("Register");
		southPanel.add(btnRegis);
		btnRegis.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String query = "INSERT INTO Staff VALUES (?, ?, ?, ?, ?, ?, ?)";
					String query2 = "SELECT * FROM Staff";
					File file = staffImageFile.getSelectedFile();
					String imagePath = file.getPath();
					InputStream in = new FileInputStream(imagePath);
					PreparedStatement pst = con.prepareStatement(query);
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(query2);
					while(rs.next()) {
						count += count;
					}
					pst.setInt(1, count);
					pst.setString(2, txtStaffName.getText());
					pst.setInt(3, 4500000);
					pst.setString(4, txtStaffEmail.getText());
					pst.setString(5, txtStaffPass.getText());
					pst.setBlob(6, in);
					pst.setInt(7, 2);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Insert new staff success");
					pst.close();
					dispose();
					main.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Insert staff failed : " + e.getMessage());
				}
			}
		});
	}
	
	Connection con;
	public Register() {
		con = sqlConnector.connection();
		initiallize();
		setLayout(new BorderLayout());
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Register();
	}

}
