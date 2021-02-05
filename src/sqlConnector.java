import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class sqlConnector {

	public static Connection connection() {
		Connection con = null;
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			String url = "jdbc:mysql://localhost:3306/project-indomaret?useTimezone=true&serverTimezone=UTC";
			String user = "root";
			String password = "";
//			String createTableRole = "CREATE TABLE role ("
//					+ "RoleId INT NOT NULL AUTO_INCREMENT,"
//					+ "RoleName VARCHAR(10),"
//					+ "PRIMARY KEY (RoleId))";
//			String createTableCategory = "CREATE TABLE category("
//					+ "CategoryId INT NOT NULL AUTO_INCREMENT,"
//					+ "CategoryName VARCHAR(10),"
//					+ "PRIMARY KEY(CategoryId)"
//					+ ")";
//			String createTableStaff = "CREATE TABLE staff ("
//					+ "StaffId INT NOT NULL AUTO_INCREMENT,"
//					+ "StaffName VARCHAR(30),"
//					+ "StaffSalary INT,"
//					+ "StaffEmail VARCHAR(30),"
//					+ "StaffPassword VARCHAR(30),"
//					+ "StaffPhoto LONGBLOB,"
//					+ "RoleId INT,"
//					+ "FOREIGN KEY (RoleId) REFERENCES role(RoleId) ON DELETE CASCADE ON UPDATE CASCADE,"
//					+ "PRIMARY KEY (StaffId)"
//					+ ")";
//			String createTableProduct = "CREATE TABLE product ("
//					+ "ProductId INT NOT NULL AUTO_INCREMENT,"
//					+ "ProductName VARCHAR(30),"
//					+ "ProductPrice INT,"
//					+ "ProductStock INT,"
//					+ "ProductImage LONGBLOB,"
//					+ "CategoryId INT,"
//					+ "FOREIGN KEY (CategoryId) REFERENCES category(CategoryId) ON DELETE CASCADE ON UPDATE CASCADE,"
//					+ "PRIMARY KEY(ProductId)"
//					+ ")";
//			String createTableHeaderTransaction = "CREATE TABLE HeaderTransaction("
//					+ "TransactionId INT NOT NULL AUTO_INCREMENT,"
//					+ "StaffId INT,"
//					+ "TransactionDate DATE,"
//					+ "FOREIGN KEY (StaffId) REFERENCES staff (StaffId) ON DELETE CASCADE ON UPDATE CASCADE,"
//					+ "PRIMARY KEY(TransactionId)"
//					+ ")";
//			String createTableDetailTransaction = "CREATE TABLE DetailTransaction("
//					+ "TransactionId INT NOT NULL,"
//					+ "ProductId INT NOT NULL,"
//					+ "Quantity INT,"
//					+ "FOREIGN KEY (TransactionId) REFERENCES HeaderTransaction (TransactionId) ON DELETE CASCADE ON UPDATE CASCADE,"
//					+ "FOREIGN KEY (ProductId) REFERENCES product (ProductId) ON DELETE CASCADE ON UPDATE CASCADE,"
//					+ "PRIMARY KEY (TransactionId, ProductId)"
//					+ ")";
//			String createTableCart = "CREATE TABLE cart ("
//					+ "StaffId INT NOT NULL,"
//					+ "ProductId INT NOT NULL,"
//					+ "Quantity INT,"
//					+ "FOREIGN KEY (StaffId) REFERENCES staff (StaffId) ON DELETE CASCADE ON UPDATE CASCADE,"
//					+ "FOREIGN KEY (ProductId) REFERENCES product (ProductId) ON DELETE CASCADE ON UPDATE CASCADE,"
//					+ "PRIMARY KEY (StaffId, ProductId)"
//					+ ")";
			con = DriverManager.getConnection(url, user, password);
			System.out.println("Connection success");
//			JOptionPane.showMessageDialog(null, "Connection success");
//			Statement st = con.createStatement();
//			int opt1 = JOptionPane.showConfirmDialog(null, "Do you want to create role table");
//			switch (opt1) {
//			case JOptionPane.YES_OPTION:
//				st.execute(createTableRole);
//				break;
//
//			default:
//				break;
//			}
//			int opt2 = JOptionPane.showConfirmDialog(null, "Do you want to create category table");
//			switch (opt2) {
//			case JOptionPane.YES_OPTION:
//				st.execute(createTableCategory);
//				break;
//
//			default:
//				break;
//			}
//			int opt3 = JOptionPane.showConfirmDialog(null, "Do you want to create staff table");
//			switch (opt3) {
//			case JOptionPane.YES_OPTION:
//				st.execute(createTableStaff);
//				break;
//
//			default:
//				break;
//			}
//			int opt4 = JOptionPane.showConfirmDialog(null, "Do you want to create product table");
//			switch (opt4) {
//			case JOptionPane.YES_OPTION:
//				st.execute(createTableProduct);
//				break;
//
//			default:
//				break;
//			}
//			int opt5 = JOptionPane.showConfirmDialog(null, "Do you want to create Header Transaction table");
//			switch (opt5) {
//			case JOptionPane.YES_OPTION:
//				st.execute(createTableHeaderTransaction);
//				break;
//
//			default:
//				break;
//			}
//			int opt6 = JOptionPane.showConfirmDialog(null, "Do you want to create Detail Transaction table");
//			switch (opt6) {
//			case JOptionPane.YES_OPTION:
//				st.execute(createTableDetailTransaction);
//				break;
//
//			default:
//				break;
//			}
//			int opt7 = JOptionPane.showConfirmDialog(null, "Do you want to create cart table");
//			switch (opt7) {
//			case JOptionPane.YES_OPTION:
//				st.execute(createTableCart);
//				break;
//
//			default:
//				break;
//			}
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Connection failed error : " + e.getMessage());
			e.printStackTrace();
		}
		return con;
	}

}
