import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import com.mysql.cj.jdbc.Blob;
import com.mysql.cj.xdevapi.Result;

public class ManageProduct extends JInternalFrame{

	JPanel leftPanel, rightPanel;
	JLabel lblLeftTitle, lblRightTitle;
	JPanel leftNorthPanel, leftCenterPanel, leftSouthPanel;
	JPanel rightNorthPanel, rightCenterPanel, rightSouthPanel;
	ArrayList<String> categoryList = new ArrayList<>();
	public void initiallize() {
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		add(leftPanel);
		add(rightPanel);
		left();
		right();
	}
	Integer categoryId;
	JLabel lblProductName, lblProductPrice, lblProductStock, lblProductPhoto, lblProductCategory;
	JTextField txtProductName, txtProductPrice, txtProductStock;
	JButton btnAdd, btnUpdate, btnDelete;
	JFileChooser productImageChooser;
	JComboBox categoryBox;
	JButton btnFileChooser;
	public void left() {
		try {
			String query = "SELECT * FROM category";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				categoryList.add(rs.getString(2));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		leftPanel.setLayout(new BorderLayout());
		leftNorthPanel = new JPanel();
		leftCenterPanel = new JPanel();
		leftSouthPanel = new JPanel();
		
		//North
		leftPanel.add(leftNorthPanel, BorderLayout.NORTH);
		lblLeftTitle = new JLabel("Manage Product");
		leftNorthPanel.add(lblLeftTitle);
		lblLeftTitle.setHorizontalTextPosition(JLabel.CENTER);
		
		//Center
		leftPanel.add(leftCenterPanel, BorderLayout.CENTER);
		leftCenterPanel.setLayout(new GridLayout(6,2,50,50));
		lblProductName = new JLabel("Product Name");
		lblProductPrice = new JLabel("Product Price");
		lblProductStock = new JLabel("Product Stock");
		lblProductPhoto = new JLabel("Product Photo");
		lblProductCategory = new JLabel("Product Category");
		btnFileChooser = new JButton("Choose File");
		txtProductName = new JTextField();
		txtProductPrice = new JTextField();
		txtProductStock = new JTextField();
		productImageChooser = new JFileChooser();
		categoryBox = new JComboBox<>();
		for (String category : categoryList) {
			categoryBox.addItem(category);
		}
		leftCenterPanel.add(lblProductName);
		leftCenterPanel.add(txtProductName);
		leftCenterPanel.add(lblProductPrice);
		leftCenterPanel.add(txtProductPrice);
		leftCenterPanel.add(lblProductStock);
		leftCenterPanel.add(txtProductStock);
		leftCenterPanel.add(lblProductPhoto);
		leftCenterPanel.add(btnFileChooser);
		leftCenterPanel.add(lblProductCategory);
		leftCenterPanel.add(categoryBox);
		
		btnFileChooser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				leftCenterPanel.add(productImageChooser);
				productImageChooser.showSaveDialog(null);
			}
		});
		
		//South
		leftPanel.add(leftSouthPanel, BorderLayout.SOUTH);
		leftSouthPanel.setLayout(new FlowLayout());
		btnAdd = new JButton("Add");
		btnUpdate = new JButton("Update");
		btnDelete = new JButton("Delete");
		leftSouthPanel.add(btnAdd);
		leftSouthPanel.add(btnUpdate);
		leftSouthPanel.add(btnDelete);
		
		btnAdd.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String query = "INSERT INTO product VALUES (?, ?, ?, ?, ?, ?)";
				String query2 = "SELECT * FROM product";
				int count = 0;
				try {
					String name = txtProductName.getText();
					Integer price = Integer.parseInt(txtProductPrice.getText());
					Integer stock = Integer.parseInt(txtProductStock.getText());
					String category = categoryBox.getSelectedItem().toString();
					String query3 = "SELECT * FROM category WHERE CategoryName='"+category+"'";
					File image = productImageChooser.getSelectedFile();
					String imagePath = image.getPath();
					InputStream in = new FileInputStream(imagePath);
					Statement st = con.createStatement();
					Statement st2 = con.createStatement();
					ResultSet rs = st.executeQuery(query2);
					ResultSet rs2 = st2.executeQuery(query3);
					while(rs.next()) {
						count += count;
					}
					Integer categoryId=0;
					while(rs2.next()) {
						categoryId = rs2.getInt(1);
					}
					PreparedStatement pst = con.prepareStatement(query);
					pst.setInt(1, count);
					pst.setString(2, name);
					pst.setInt(3, price);
					pst.setInt(4, stock);
					pst.setBlob(5, in);
					pst.setInt(6, categoryId);
					pst.execute();
					JOptionPane.showMessageDialog(null, "Insert product data success");
					pst.close();
					viewProduct();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Insert data failed : " + e.getMessage());
				}
			}
		});
	}
	
	JTable tblProduct;
	JScrollPane scpProduct;
	JPanel rightCenterTopPanel, rightCenterBottomPanel; 
	public void right() {
		tblProduct = new JTable();
		scpProduct = new JScrollPane();
		scpProduct.getViewport().add(tblProduct);
		
		rightPanel.setLayout(new BorderLayout());
		rightNorthPanel = new JPanel();
		rightCenterPanel = new JPanel();
		
		rightPanel.add(rightNorthPanel, BorderLayout.NORTH);
		lblRightTitle = new JLabel("Products");
		lblRightTitle.setHorizontalTextPosition(JLabel.CENTER);
		rightNorthPanel.add(lblRightTitle);
		
		rightCenterBottomPanel = new JPanel();
		rightCenterTopPanel = new JPanel();
		rightPanel.add(rightCenterPanel, BorderLayout.CENTER);
		rightCenterPanel.setLayout(new GridLayout(2,1));
		rightCenterPanel.add(rightCenterTopPanel);
		rightCenterPanel.add(rightCenterBottomPanel);
		
		rightCenterTopPanel.add(scpProduct);
		
		viewProduct();
		bottom();
		
		tblProduct.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = tblProduct.rowAtPoint(e.getPoint());
				Integer detailProductId = Integer.parseInt(tblProduct.getValueAt(row, 0).toString());
				String detailName = tblProduct.getValueAt(row, 1).toString();
				String detailPrice = tblProduct.getValueAt(row, 2).toString();
				String detailStock = tblProduct.getValueAt(row, 3).toString();
				txtDataName.setText(detailName);
				txtDataStock.setText(detailStock);
				txtDataPrice.setText(detailPrice);
				
				String query = "SELECT * FROM product WHERE ProductId="+detailProductId+" ";
				try {
					Statement st = con.createStatement();
					ResultSet rs = st.executeQuery(query);
					while(rs.next()) {
					//test	
					}
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
		});
	}

	public void viewProduct() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Id");
		model.addColumn("Product Name");
		model.addColumn("Product Price");
		model.addColumn("Product Stock");
		model.addColumn("Category");
		try {
			String query = "SELECT product.ProductId, product.ProductName, product.ProductPrice, product.ProductStock, "
					+ "category.CategoryName FROM product JOIN category ON product.CategoryId = category.CategoryId";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				model.addRow(new Object[] {
					rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getString(5)	
				});
			}
			tblProduct.setModel(model);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error : " + e.getMessage());
		}
	}
	
	JPanel leftBottomPanel, rightBottomPanel;
	JTextField txtDataName, txtDataPrice, txtDataStock;
	
	public void bottom() {
		rightCenterBottomPanel.setLayout(new GridLayout(1, 2));
		leftBottomPanel = new JPanel();
		rightBottomPanel = new JPanel();
		rightCenterBottomPanel.add(leftBottomPanel);
		rightCenterBottomPanel.add(rightBottomPanel);
		
		txtDataName = new JTextField();
		txtDataPrice = new JTextField();
		txtDataStock = new JTextField();
		leftBottomPanel.setLayout(new GridLayout(3, 1, 20, 20));
		leftBottomPanel.add(txtDataName);
		leftBottomPanel.add(txtDataPrice);
		leftBottomPanel.add(txtDataStock);
		
		txtDataName.setEditable(false);
		txtDataPrice.setEditable(false);
		txtDataStock.setEditable(false);
	}

	Connection con=null;
	public ManageProduct() {
		super("Manage product", true, true, true, true);
		con = sqlConnector.connection();
		initiallize();
		setLayout(new GridLayout(1, 2));
		
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
	}


}
