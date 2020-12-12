import java.awt.GridLayout;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class Graphics extends JFrame{

	public CategoryDataset createBarData() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		try {
			String query = "SELECT product.ProductName, category.CategoryName, SUM(DetailTransaction.Quantity)"
					+ " FROM product JOIN category ON product.CategoryId = category.CategoryId JOIN DetailTransaction ON"
					+ " DetailTransaction.ProductId = product.ProductId "
					+ "GROUP BY product.ProductId";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				dataset.addValue(rs.getInt(3), rs.getString(2), rs.getString(1));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		return dataset;
	}
	
	public PieDataset createPieData() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		try {
			String query = "SELECT product.ProductName, category.CategoryName, SUM(DetailTransaction.Quantity)"
					+ " FROM product JOIN category ON product.CategoryId = category.CategoryId JOIN DetailTransaction ON"
					+ " DetailTransaction.ProductId = product.ProductId "
					+ "GROUP BY product.ProductId";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				dataset.setValue(rs.getString(1), rs.getInt(3));
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
		
		return dataset;
	}
	
	public void viewTable() {
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Product Name");
		model.addColumn("Category");
		model.addColumn("Product Price");
		model.addColumn("Product Stock");
		model.addColumn("Quantity");
		
		try {
			String query = "SELECT product.ProductName, category.CategoryName, product.ProductPrice, product.ProductStock, SUM(DetailTransaction.Quantity)"
					+ " FROM product JOIN category ON product.CategoryId = category.CategoryId JOIN DetailTransaction ON"
					+ " DetailTransaction.ProductId = product.ProductId "
					+ "GROUP BY product.ProductId";
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getString(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)
				});
			tblData.setModel(model);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	Connection con;
	JPanel topPanel, bottomPanel;
	JPanel leftPanel, rightPanel;
	JTable tblData;
	JScrollPane scpData;
	public Graphics() {
		con = sqlConnector.connection();
		topPanel = new JPanel();
		bottomPanel = new JPanel();
		leftPanel = new JPanel();
		rightPanel = new JPanel();
		tblData = new JTable();
		scpData = new JScrollPane();
		scpData.getViewport().add(tblData);
		JFreeChart barChart = ChartFactory.createBarChart("Graphic Penjualan", "Category", "Nama Product", createBarData());
		JFreeChart pieChart = ChartFactory.createPieChart("Graphic Penjualan", createPieData(), true, true, false);
		ChartPanel chartPanel = new ChartPanel(pieChart);
		ChartPanel chartPanel2 = new ChartPanel(barChart);
		setLayout(new GridLayout(2, 1));
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		viewTable();
		add(topPanel);
		add(scpData);
		topPanel.setLayout(new GridLayout(1, 2));
		topPanel.add(leftPanel);
		topPanel.add(rightPanel);
		leftPanel.add(chartPanel);
		rightPanel.add(chartPanel2);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
	}

	public static void main(String[] args) {
		new Graphics();
	}

}
