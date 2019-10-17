import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLiteDataAdapter implements IDataAdapter {

    Connection conn = null;

    public int connect(String dbfile) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + dbfile;
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_OPEN_FAILED;
        }
        return CONNECTION_OPEN_OK;
    }

    @Override
    public int disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return CONNECTION_CLOSE_FAILED;
        }
        return CONNECTION_CLOSE_OK;
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = new ProductModel();

        try {
            String sql = "SELECT ProductID, Name, Price, Quantity FROM Products WHERE ProductID = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.mProductID = rs.getInt("ProductId");
            product.mName = rs.getString("Name");
            product.mPrice = rs.getDouble("Price");
            product.mQuantity = rs.getDouble("Quantity");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return product;
    }
    public int saveProduct(ProductModel product) {
        try {
            String sql = "INSERT INTO Products(ProductID, Name, Price, Quantity) VALUES " + product;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PRODUCT_DUPLICATE_ERROR;
        }

        return PRODUCT_SAVED_OK;
    }

    public CustomerModel loadCustomer(int CustomerID) {
        CustomerModel Customer = new CustomerModel();

        try {
            String sql = "SELECT CustomerID, Name, Phone, Address FROM Customer WHERE CustomerID = " + CustomerID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Customer.mCustomerID = rs.getInt("CustomerId");
            Customer.mName = rs.getString("Name");
            Customer.mPhone = rs.getString("Phone");
            Customer.mAddress = rs.getString("Address");


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Customer;
    }

    public int saveCustomer (CustomerModel customer) {
        try {
            String sql = "INSERT INTO Customer(CustomerID, Name, Phone, Address) VALUES " + customer;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return CUSTOMER_DUPLICATE_ERROR;
        }

        return CUSTOMER_SAVED_OK;
    }

    public PurchaseModel loadPurchase(int PurchaseID) {
        PurchaseModel Purchase = new PurchaseModel();

        try {
            String sql = "SELECT PurchaseID, CustomerID, ProductID, Price, Quantity, Cost, Tax, Total, Date FROM Purchase WHERE PurchaseId = " + PurchaseID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            Purchase.mPurchaseID = rs.getInt("PurchaseID");
            Purchase.mCustomerID = rs.getInt("CustomerID");
            Purchase.mProductID = rs.getInt("ProductID");
            Purchase.mQuantity = rs.getDouble("Quantity");



        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return Purchase;
    }

    public int savePurchase (PurchaseModel purchase) {
        try {
            String sql = "INSERT INTO Purchase(PurchaseID, CustomerID, ProductID, Price, Quantity, Cost, Tax, Total, Date) VALUES " + purchase;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed"))
                return PURCHASE_DUPLICATE_ERROR;
        }

        return PURCHASE_SAVED_OK;
    }

}
