import java.sql.*;

public class SQLDataBase implements IDataAdapter {

    public static final int CONNECTION_OPEN_OK = 100;
    public static final int CONNECTION_OPEN_FAIL = 101;

    public static final int CONNECTION_CLOSE_OK = 200;
    public static final int CONNECTION_CLOSE_FAIL = 201;

    public static final int PRODUCT_DUPLICATE_ERROR = -28;
    public static final int PRODUCT_ADD_SUCCESS = 1;

    public static final int CUSTOMER_DUPLICATE_ERROR = -27;
    public static final int CUSTOMER_ADD_SUCCESS = 2;

    public static final int PURCHASE_DUPLICATE_ERROR = -27;
    public static final int PURCHASE_ADD_SUCCESS = 2;


    Connection conn = null;

    public int connect(String dbfile) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + dbfile;
            // create a connection to the database
            conn = DriverManager.getConnection(url);


            System.out.println("Connection to SQLite has been established.");
/*
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Product");
            while (rs.next())
                System.out.println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3) + " " + rs.getString(4));
*/
        } catch (SQLException e) {
            System.out.println(e.getMessage());


        }

        return CONNECTION_OPEN_OK;

    }

    public int disconnect() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.out.print(e.getMessage());
            return CONNECTION_CLOSE_FAIL;
        }
        return CONNECTION_CLOSE_OK;
    }

    public int saveProduct(ProductModel product) {
        try {
            String sql = "INSERT INTO Products(ProductId, Name, Price, Quantity) VALUES " + product;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed")) {
                try {
                    String sql = "UPDATE Products SET Name = '" + product.name + "', Price = '" + product.price + "', Quantity = '" + product.quantity + "' WHERE ProductID = " + product.productID;
                    System.out.println(sql);
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                    return PRODUCT_ADD_SUCCESS;

                } catch (Exception f) {
                    String msg2 = e.getMessage();
                    System.out.println(msg);
                }
                return PRODUCT_DUPLICATE_ERROR;
            }

        }
        return PRODUCT_ADD_SUCCESS;
    }

    public ProductModel loadProduct(int productID) {
        ProductModel product = new ProductModel();
        try {
            String sql = "SELECT ProductId, Name, Price, Quantity FROM Products WHERE ProductId = " + productID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            product.productID = rs.getInt("ProductId");
            product.name = rs.getString("Name");
            product.price = rs.getDouble("Price");
            product.quantity = rs.getDouble("Quantity");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return product;
    }


    public int saveCustomer(CustomerModel customer) {

        /*
        try {
            if (loadCustomer(customer.customerID) == null) {           // this is a new customer!
                Statement stmt = conn.createStatement();
                stmt.execute("INSERT INTO Customer(customerID, name, address, number) VALUES ("
                        + customer.customerID + ","
                        + '\'' + customer.name + '\'' + ","
                        + customer.address + ","
                        + customer.number + ")");
                return CUSTOMER_ADD_SUCCESS;
            }
            else {
                Statement stmt = conn.createStatement();
                stmt.executeUpdate("UPDATE Customer SET "
                        + "customerID = " + customer.customerID + ","
                        + "name = " + '\'' + customer.name + '\'' + ","
                        + "address = " + customer.address + ","
                        + "number = " + customer.number +
                        " WHERE customerID = " + customer.customerID);
                return CUSTOMER_ADD_SUCCESS;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return CUSTOMER_DUPLICATE_ERROR;
        }
*/
        try {
            String sql = "INSERT INTO Customer(customerID, name, address, number) VALUES " + customer;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed")) {
                try {
                    String sql = "UPDATE Customer SET name = '" + customer.name + "', address = '" + customer.address + "', number = '" + customer.number + "' WHERE customerID = '" + customer.customerID + "'";
                    System.out.println(sql);
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                    return CUSTOMER_ADD_SUCCESS;

                } catch (Exception f) {
                    String msg2 = e.getMessage();
                    System.out.println(msg);
                }
                return CUSTOMER_DUPLICATE_ERROR;
            }

        }
        return CUSTOMER_ADD_SUCCESS;
    }

    public CustomerModel loadCustomer(int id) {
        CustomerModel customer = null;

        try {
            String sql = "SELECT * FROM Customer WHERE customerID = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                customer = new CustomerModel();
                customer.customerID = id;
                customer.name = rs.getString("name");
                customer.number = rs.getString("number");
                customer.address = rs.getString("address");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }

    public int savePurchase(PurchaseModel purchase) {
        try {
            String sql = "INSERT INTO Purchases VALUES " + purchase;
            System.out.println(sql);
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);

        } catch (Exception e) {
            String msg = e.getMessage();
            System.out.println(msg);
            if (msg.contains("UNIQUE constraint failed")) {
                try {
                    String sql = "UPDATE Purchases SET customerID = '" + purchase.mCustomerID + "', price = '" + purchase.mPrice + "' WHERE purchaseID = " + purchase.mPurchaseID;
                    System.out.println(sql);
                    Statement stmt = conn.createStatement();
                    stmt.executeUpdate(sql);
                    return PURCHASE_ADD_SUCCESS;

                } catch (Exception f) {
                    String msg2 = e.getMessage();
                    System.out.println(msg);
                }
                return PURCHASE_DUPLICATE_ERROR;
            }

        }
        return PURCHASE_ADD_SUCCESS;
    }

    public PurchaseModel loadPurchase(int purchaseID) {
        PurchaseModel purchase = null;

        try {
            String sql = "SELECT * FROM Purchases WHERE purchaseId = " + purchaseID;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                purchase = new PurchaseModel();
                purchase.mPurchaseID = rs.getInt("purchaseId");
                purchase.mCustomerID = rs.getInt("customerId");
                purchase.mProductID = rs.getInt("productId");
                purchase.mQuantity = rs.getInt("quantity");
                purchase.mCost = rs.getDouble("cost");
                purchase.mTax = rs.getDouble("tax");
                purchase.mTotal = rs.getDouble("total");
                purchase.mDate = rs.getString("date");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return purchase;
    }

    public PurchaseListModel loadPurchaseHistory(int id) {
        PurchaseListModel res = new PurchaseListModel();
        try {
            String sql = "SELECT * FROM Purchases WHERE CustomerId = " + id;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                PurchaseModel purchase = new PurchaseModel();
                purchase.mCustomerID = id;
                purchase.mPurchaseID = rs.getInt("PurchaseID");
                purchase.mProductID = rs.getInt("ProductID");
                purchase.mPrice = rs.getDouble("Price");
                purchase.mQuantity = rs.getDouble("Quantity");
                purchase.mCost = rs.getDouble("Cost");
                purchase.mTax = rs.getDouble("Tax");
                purchase.mTotal = rs.getDouble("Total");
                purchase.mDate = rs.getString("Date");

                res.purchases.add(purchase);
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public ProductListModel searchProduct(String name, double minPrice, double maxPrice) {
        ProductListModel res = new ProductListModel();
        try {
            String sql = "SELECT * FROM Products WHERE Name LIKE \'%" + name + "%\' "
                    + "AND Price >= " + minPrice + " AND Price <= " + maxPrice;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ProductModel product = new ProductModel();
                product.productID = rs.getInt("ProductID");
                product.name = rs.getString("Name");
                product.price = rs.getDouble("Price");
                product.quantity = rs.getDouble("Quantity");
                res.products.add(product);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return res;
    }

    public UserModel loadUser(String username) {
        UserModel user = null;

        try {
            String sql = "SELECT * FROM Users WHERE Username = \"" + username + "\"";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                user = new UserModel();
                user.mUsername = username;
                user.mPassword = rs.getString("Password");
                user.mFullname = rs.getString("Fullname");
                user.mUserType = rs.getInt("Usertype");
                if (user.mUserType == UserModel.CUSTOMER)
                    user.mCustomerID = rs.getInt("CustomerID");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return user;
    }




}