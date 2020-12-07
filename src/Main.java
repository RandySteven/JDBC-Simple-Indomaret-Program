import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class Main extends JFrame{

	JMenu menuBuy, menuTransaction, menuProduct;
	JMenuBar mb;
	JDesktopPane dp;
		
		BuyProduct bp = new BuyProduct();
		ManageProduct mp = new ManageProduct();
		Transaction tr = new Transaction();
	public void initiallize() {
		menuBuy = new JMenu("Buy");
		menuTransaction = new JMenu("Transaction");
		menuProduct = new JMenu("Product");
		dp = new JDesktopPane();
		mb = new JMenuBar();
		mb.add(menuBuy);
		mb.add(menuProduct);
		mb.add(menuTransaction);
		add(dp);
		
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
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
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
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
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
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void menuCanceled(MenuEvent arg0) {
				// TODO Auto-generated method stub
				
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
