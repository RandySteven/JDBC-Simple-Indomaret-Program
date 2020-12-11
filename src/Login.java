import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends JFrame{
	JPanel northPanel, centerPanel, southPanel;
	JLabel lblTitle, lblEmail, lblPassword;
	JTextField txtEmail;
	JPasswordField txtPass;
	JButton btnLogin, btnRegister;
	Register regis = new Register();
	
	
	public void initiallize() {
		northPanel = new JPanel();
		centerPanel = new JPanel();
		southPanel = new JPanel();
		
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);

		lblTitle = new JLabel("Login");
		northPanel.add(lblTitle);
		lblTitle.setHorizontalTextPosition(JLabel.CENTER);
		
		centerPanel.setLayout(new GridLayout(2, 2, 20, 20));
		lblEmail = new JLabel("Email");
		lblPassword = new JLabel("Password");
		txtEmail = new JTextField();
		txtPass = new JPasswordField();
		centerPanel.add(lblEmail);
		centerPanel.add(txtEmail);
		centerPanel.add(lblPassword);
		centerPanel.add(txtPass);
		
		southPanel.setLayout(new FlowLayout());
		btnLogin = new JButton("Login");
		btnRegister = new JButton("Register");
		southPanel.add(btnLogin);
		southPanel.add(btnRegister);
		btnRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				regis.setVisible(true);
				dispose();
			}
		});
		
		btnLogin.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(txtEmail.getText().isEmpty()&&txtPass.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "You cannot put blank text");
				}else {			
					String email = txtEmail.getText();
					String pass = txtPass.getText();
					String query = "SELECT * FROM staff WHERE StaffEmail='"+email+"' AND StaffPassword='"+pass+"' ";
					try {
						Statement st = con.createStatement();
						ResultSet rs = st.executeQuery(query);
						if(rs.next()) {				
							int staffId = rs.getInt(1);
							dispose();
							Main main = new Main(email);
							main.setVisible(true);
						}
					} catch (Exception e) {
						int opt = JOptionPane.showConfirmDialog(null, "You have not account yet. Do you want to create one? " + e.getMessage());
						switch (opt) {
						case JOptionPane.YES_OPTION:
							regis.setVisible(true);
							dispose();
							break;
						default:
							System.exit(0);
							break;
						}
					}
				}
			}
		});
	}
	
	Connection con;
	public Login() {
		con = sqlConnector.connection();
		initiallize();
		setSize(500, 350);
		setVisible(true);
		setLocationRelativeTo(null);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Login();
	}


}
