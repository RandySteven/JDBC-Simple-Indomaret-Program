import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Taskbar.State;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.table.DefaultTableModel;

public class BuyProduct extends JInternalFrame{

	JPanel leftPanel, rightPanel;
	JPanel northLeftPanel, centerLeftPanel, southLeftPanel;
	JPanel northRightPanel, centerRightPanel, southRightPanel;
	
	
	JTable tblProduct, tblCart;
	JScrollPane scpProduct, scpCart;
	
	JLabel lblProductTitle, lblCartTitle;
	
	JLabel lblName, lblPrice, lblQty, lblStock;
	JTextField txtName, txtPrice, txtStock;
	JSpinner spnQty;
	JButton btnAddToCart, btnCheckout, btnRemove;
		Main main;
		Transaction tr;
	public void initiallize() {
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		
		add(leftPanel);
		add(rightPanel);
	}
	
	public void clear() {
		txtName.setText(null);
		txtPrice.setText(null);
		txtStock.setText(null);
	}
	
	public void query(String email) {
		String search = "SELECT * FROM staff WHERE StaffEmail='"+email+"'";
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(search);
			if(rs.next()) {
				staffId = rs.getInt(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	JPanel tablePanel, dataPanel;
	Integer productId=0;
	public void left() {
		leftPanel.setLayout(new BorderLayout());
		
		northLeftPanel = new JPanel();
		centerLeftPanel = new JPanel();
		southLeftPanel = new JPanel();
		
		leftPanel.add(northLeftPanel, BorderLayout.NORTH);
		lblProductTitle = new JLabel("Product");
		northLeftPanel.add(lblProductTitle);
		lblProductTitle.setHorizontalTextPosition(JLabel.CENTER);
		
		leftPanel.add(centerLeftPanel, BorderLayout.CENTER);
		centerLeftPanel.setLayout(new GridLayout(2, 1));
		tblProduct = new JTable();
		scpProduct = new JScrollPane();
		scpProduct.getViewport().add(tblProduct);
		tablePanel = new JPanel();
		centerLeftPanel.add(scpProduct);
		
		dataPanel = new JPanel();
		dataPanel.setLayout(new GridLayout(4, 2, 30, 30));
		lblName = new JLabel("Product Name");
		txtName = new JTextField();
		lblPrice = new JLabel("Product Price");
		txtPrice = new JTextField();
		lblQty = new JLabel("Quantity");
		spnQty = new JSpinner();
		lblStock = new JLabel("Stock");
		txtStock = new JTextField();
		dataPanel.add(lblName);
		dataPanel.add(txtName);
		dataPanel.add(lblPrice);
		dataPanel.add(txtPrice);
		dataPanel.add(lblStock);
		dataPanel.add(txtStock);
		dataPanel.add(lblQty);
		dataPanel.add(spnQty);
		centerLeftPanel.add(dataPanel);
		
		leftPanel.add(southLeftPanel, BorderLayout.SOUTH);
		southLeftPanel.setLayout(new FlowLayout());
		btnAddToCart = new JButton("Add to Cart");
		btnRemove = new JButton("Remove from Cart");
		southLeftPanel.add(btnAddToCart);
		southLeftPanel.add(btnRemove);
			
		txtName.setEditable(false);
		txtPrice.setEditable(false);
		txtStock.setEditable(false);
		
		btnAddToCart.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(Integer.parseInt(spnQty.getValue().toString())<=0) {
					JOptionPane.showMessageDialog(null, "Tidak bisa memasukkan product");
				}else {					
					String query = "INSERT INTO cart VALUES (?, ?, ?)";
					Integer qty = Integer.parseInt(spnQty.getValue().toString());
					int productid = productId;
					try {
						PreparedStatement pst = con.prepareStatement(query);
						pst.setInt(1, staffId);
						pst.setInt(2, productid);
						pst.setInt(3, qty);
						pst.execute();
						JOptionPane.showMessageDialog(null, "Insert to cart success");
						pst.close();
						viewTableCart();
						txtName.setText(null);
						txtPrice.setText(null);
						txtStock.setText(null);
						spnQty.setValue(0);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, "Insert to cart failed : " + e.getMessage());
					}
				}
			}
		});
		
		viewTableProduct();
		
		tblProduct.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tblProduct.rowAtPoint(e.getPoint());
				productId = Integer.parseInt(tblProduct.getValueAt(row, 0).toString());
				String name = tblProduct.getValueAt(row, 1).toString();
				String price = tblProduct.getValueAt(row, 2).toString();
				String stock = tblProduct.getValueAt(row, 3).toString();
				
				txtName.setText(name);
				txtPrice.setText(price);
				txtStock.setText(stock);
			}
		});
		
	}
	
	JPanel cartTablePanel, cartTotalPricePanel;
	JLabel lblTotal, lblBayar;
	JTextField txtTotal, txtBayar;
	int count = 0;
	Integer bayar = 0;
	public void right() {
		rightPanel.setLayout(new BorderLayout());
		northRightPanel = new JPanel();
		centerRightPanel = new JPanel();
		southRightPanel = new JPanel();
		
		rightPanel.add(northRightPanel, BorderLayout.NORTH);
		lblCartTitle = new JLabel("Cart");
		northRightPanel.add(lblCartTitle);
		lblCartTitle.setHorizontalTextPosition(JLabel.CENTER);

		rightPanel.add(centerRightPanel, BorderLayout.CENTER);
		centerRightPanel.setLayout(new GridLayout(2,1));
		
		tblCart = new JTable();
		scpCart = new JScrollPane();
		scpCart.getViewport().add(tblCart);
		cartTablePanel = new JPanel();
//		cartTablePanel.add(scpCart);
		centerRightPanel.add(scpCart);

		cartTotalPricePanel = new JPanel();
		cartTotalPricePanel.setLayout(new GridLayout(2, 2, 30, 30));
		lblTotal = new JLabel("Total");
		lblBayar = new JLabel("Bayar");
		txtTotal = new JTextField(0);
		txtBayar = new JTextField(0);
		txtTotal.setSize(500, 300);
		cartTotalPricePanel.add(lblBayar);
		cartTotalPricePanel.add(txtBayar);
		cartTotalPricePanel.add(lblTotal);
		cartTotalPricePanel.add(txtTotal);
		centerRightPanel.add(cartTotalPricePanel);
		
		rightPanel.add(southRightPanel, BorderLayout.SOUTH);
		btnCheckout = new JButton("Check out");
		southRightPanel.add(btnCheckout);
		
		viewTableCart();
		txtTotal.setEditable(false);
		txtTotal.setBorder(null);
		btnCheckout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String query = "INSERT INTO HeaderTransaction VALUE (?, ?, ?)";
				String query2 = "SELECT * FROM HeaderTransaction";
				String search = "SELECT * FROM staff WHERE StaffEmail='"+email+"' ";
				
				String query4 = "SELECT * FROM cart";
				try {
					Statement searchSt = con.createStatement();
					ResultSet searchRs = searchSt.executeQuery(search);
					if(searchRs.next()) {
						staffId = searchRs.getInt(1);
					}
					
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(query2);
					while(rs.next()) {
						count += count;
					}
						bayar = Integer.parseInt(txtBayar.getText());
				
						int kembalian = bayar - totalPrice;
						Timestamp ts = new Timestamp(new java.util.Date().getTime());
						PreparedStatement pst = con.prepareStatement(query);
						pst.setInt(1, count);
						pst.setInt(2, staffId);
						pst.setTimestamp(3, ts);
						pst.execute();
						JOptionPane.showMessageDialog(null, "Insert header success");
						pst.close();
						Statement st2 = con.createStatement();
						ResultSet rs2 = st2.executeQuery(query4);
						Statement st3 = con.createStatement();
						Statement st4 = con.createStatement();
						while(rs2.next()) {
							String query3 = "INSERT INTO DetailTransaction VALUE (LAST_INSERT_ID(), "+rs2.getInt(2)+", "+rs2.getInt(3)+")";
							String query6 = "UPDATE product SET ProductStock=ProductStock-"+rs2.getInt(3)+" WHERE ProductId="+rs2.getInt(2)+" ";
							st3.addBatch(query3);
							st3.addBatch(query6);
						}
						String query5 = "DELETE FROM cart WHERE StaffId="+staffId+" ";
						st3.executeBatch();
						JOptionPane.showMessageDialog(null, "Insert detail success");
						JOptionPane.showMessageDialog(null, "Terimakasih sudah belanja disini kembalian anda adalah : " + kembalian);
						st3.closeOnCompletion();
						st4.execute(query5);
						st4.close();
						viewTableCart();
						viewTableProduct();
						txtTotal.setText("0");
						txtName.setText(null);
						txtPrice.setText(null);
						txtStock.setText(null);
						spnQty.setValue(0);
						txtBayar.setText(null);
						totalPrice = 0;
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Failed insert database : " + e.getMessage());
				}
				
			}
		});
		
		tblCart.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tblCart.rowAtPoint(e.getPoint());
				String name = tblCart.getValueAt(row, 0).toString();
				
				String query = "SELECT * FROM product WHERE ProductName='"+name+"' ";
				try {
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(query);
					while(rs.next()) {
						product_id = rs.getInt(1);
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
		
		btnRemove.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				try {
					String query = "DELETE FROM cart WHERE ProductId="+product_id+" ";
					Statement st = con.createStatement();
					st.execute(query);
					JOptionPane.showMessageDialog(null, "Delete from cart success");
					st.close();
					viewTableCart();
					totalPrice = 0;
					txtTotal.setText(null);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	}
	int product_id;
	
	public void viewTableProduct() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Product Id");
		model.addColumn("Product Name");
		model.addColumn("Product Price");
		model.addColumn("Product Stock");
		
		try {
			String query = "SELECT * FROM product";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)
				});
			}
			tblProduct.setModel(model);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	Integer totalPrice=0;
	int price = 0;
	public void viewTableCart() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Product Name");
		model.addColumn("Product Price");
		model.addColumn("Product Quantity");
		model.addColumn("Product Total");
		try {
			String query = "SELECT product.ProductName, product.ProductPrice, "
					+ "cart.Quantity, product.ProductPrice * cart.Quantity  "
					+ "FROM cart JOIN product ON cart.ProductId = product.ProductId";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				model.addRow(new Object[] {						
						rs.getString(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)
				});
				price = rs.getInt(4);
			}
			tblCart.setModel(model);
			totalPrice = totalPrice + price;
			txtTotal.setText(totalPrice.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public BuyProduct() {
		
	}
	String email;
	int staffId=0;
	Connection con;
	public BuyProduct(String email) {
		super("Buy Product", true, true, true, true);
		con = sqlConnector.connection();
		
		query(email);
		initiallize();		
		
		left();
		right();
		setLayout(new GridLayout(1, 2));
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
	}

	
}
