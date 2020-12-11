import java.awt.EventQueue;
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
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Main extends JFrame{

	JMenu menuBuy, menuTransaction, menuProduct, menuUser;
	JMenuBar mb;
	JMenuItem miLogout, miExit, miStaffManage;
	JDesktopPane dp;
//		
		Login login;
		BuyProduct bp;
		ManageProduct mp = new ManageProduct();
		Transaction tr;
		ManageStaff ms = new ManageStaff();
	int RoleId=0;
	public Main() {
		
	}
	public void initiallize() {
		menuUser = new JMenu("User");
		menuBuy = new JMenu("Buy");
		menuTransaction = new JMenu("Transaction");
		menuProduct = new JMenu("Product");
		miStaffManage = new JMenuItem("Staff Manage");
		miLogout = new JMenuItem("Logout");
		miExit = new JMenuItem("Exit");
		dp = new JDesktopPane();
		mb = new JMenuBar();
		mb.add(menuUser);
		mb.add(menuBuy);
		mb.add(menuProduct);
		mb.add(menuTransaction);
		
		menuUser.add(miStaffManage);
		menuUser.add(miLogout);
		menuUser.add(miExit);
		
		add(dp);
		miStaffManage.setVisible(false);
		menuBuy.setVisible(false);
		menuProduct.setVisible(false);
		menuTransaction.setVisible(false);
		

		menuBuy.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent arg0) {
				dp.add(bp);
				bp.setVisible(true);
				bp.viewTableCart();
				bp.viewTableProduct();
			}
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				
			}
		});

		menuProduct.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent arg0) {
				dp.add(mp);
				mp.setVisible(true);
				mp.viewProduct();
			}
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				
			}
		});
		
		menuTransaction.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent arg0) {
				dp.add(tr);
				tr.setVisible(true);
				tr.viewTableDetail();
				tr.viewTableHeader();
			}
			
			@Override
			public void menuDeselected(MenuEvent arg0) {
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				
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
		initiallize();
		try {
			String query = "SELECT * FROM staff WHERE StaffEmail='"+email+"'";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			if(rs.next()) {
				RoleId=rs.getInt(7);
				if(RoleId==1) {
					menuProduct.setVisible(true);
					menuTransaction.setVisible(true);
					miStaffManage.setVisible(true);
				}else {
					menuBuy.setVisible(true);
					menuTransaction.setVisible(true);
				}
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
