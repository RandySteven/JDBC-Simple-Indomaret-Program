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
	JMenuItem miLogout, miExit;
	JDesktopPane dp;
//		
		Login login;
		BuyProduct bp = new BuyProduct();
		ManageProduct mp = new ManageProduct();
		Transaction tr = new Transaction();
	int RoleId=0;
	int id=0;
	public void initiallize() {
		menuUser = new JMenu("User");
		menuBuy = new JMenu("Buy");
		menuTransaction = new JMenu("Transaction");
		menuProduct = new JMenu("Product");
		miLogout = new JMenuItem("Logout");
		miExit = new JMenuItem("Exit");
		dp = new JDesktopPane();
		mb = new JMenuBar();
		mb.add(menuUser);
		mb.add(menuBuy);
		mb.add(menuProduct);
		mb.add(menuTransaction);
		menuUser.add(miLogout);
		menuUser.add(miExit);
		add(dp);
		menuBuy.setVisible(false);
		menuProduct.setVisible(false);
		menuTransaction.setVisible(false);
//		try {
//			String query = "SELECT * FROM Staff WHERE StaffId="+login.staffId+" ";
//			Statement st = con.createStatement();
//			ResultSet rs = st.executeQuery(query);
//			if(rs.next()) {
//				id = rs.getInt(1);
//				RoleId=rs.getInt(7);
//				if(RoleId==1) {
//					menuProduct.setVisible(true);
//					menuTransaction.setVisible(true);
//				}else {
//					menuBuy.setVisible(true);
//					menuTransaction.setVisible(true);
//				}
//			}
//		} catch (Exception e) {
////			JOptionPane.showMessageDialog(null, e.getMessage());
//		}

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
				tr.id = id;
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
	}
	
	Connection con;
	public Main() {
		con = sqlConnector.connection();
		initiallize();
		setJMenuBar(mb);
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Main();
	}

}
