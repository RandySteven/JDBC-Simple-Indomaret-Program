import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Main extends JFrame{

	JMenu menuTransaction, menuTransactionHistory, menuProduct, menuUser, menuGraphics;
	JMenuBar mb;
	JMenuItem miLogout, miExit, miStaffManage;
	JDesktopPane dp;
//		
		TransactionGraphics tg;
		Login login;
		BuyProduct bp;
		ManageProduct mp = new ManageProduct();
		Transaction tr;
		ManageStaff ms = new ManageStaff();
	int RoleId=0;
	public Main() {
		setContentPane(new JPanel() {
			public void paintComponent(Graphics g) {
				Image img = Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/images/indomaret.jpeg"));
				g.drawImage(img, this.getWidth(), this.getHeight(), this);
			}
		});
	}
	
	public void initiallize() {
		menuGraphics = new JMenu("Transaction Graphics");
		menuUser = new JMenu("User");
		menuTransaction = new JMenu("Transaction");
		menuTransactionHistory = new JMenu("Transaction History");
		menuProduct = new JMenu("Manage Product");
		miStaffManage = new JMenuItem("Manage Staff");
		miLogout = new JMenuItem("Logout");
		miExit = new JMenuItem("Exit");
		dp = new JDesktopPane();
		mb = new JMenuBar();
		mb.add(menuUser);
		mb.add(menuTransaction);
		mb.add(menuProduct);
		mb.add(menuTransactionHistory);
		mb.add(menuGraphics);
		menuUser.add(miStaffManage);
		menuUser.add(miLogout);
		menuUser.add(miExit);
		
		add(dp);
		miStaffManage.setVisible(false);
		menuTransaction.setVisible(false);
		menuProduct.setVisible(false);
		menuTransactionHistory.setVisible(false);
		menuGraphics.setVisible(false);

		menuGraphics.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				tg = new TransactionGraphics();
				dp.add(tg);
				tg.setVisible(true);
			}
		});
		
		menuTransaction.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dp.add(bp);
				bp.setVisible(true);
				bp.viewTableCart();
				bp.viewTableProduct();
				tr.viewTableHeader();
				tr.viewTableDetail();
				tg.viewTable();
				tg = new TransactionGraphics();
			}
		});
		
		menuProduct.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {				
				dp.add(mp);
				mp.setVisible(true);
				mp.viewProduct();
			}
		});
		
		menuTransactionHistory.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {				
				dp.add(tr);
				tr.setVisible(true);
				tr.viewTableDetail();
				tr.viewTableHeader();
			}
		});
		
		miLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				login.setVisible(true);
			}
		});
		
		miExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
		miStaffManage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dp.add(ms);
				ms.setVisible(true);
			}
		});
	}
	
	Connection con;
	public Main(String email) {
		
		con = sqlConnector.connection();
		bp = new BuyProduct(email);
		tr = new Transaction(email);
		setTitle("Indomerat Cashier");
		initiallize();
		
		try {
			String query = "SELECT * FROM staff WHERE StaffEmail='"+email+"'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				RoleId=rs.getInt(7);
				if(RoleId==1) {
					menuProduct.setVisible(true);
					miStaffManage.setVisible(true);
					menuGraphics.setVisible(true);
				}
				menuTransaction.setVisible(true);
				menuTransactionHistory.setVisible(true);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
		}
		setJMenuBar(mb);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		new Main();
	}
}
