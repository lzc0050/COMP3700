import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Calendar;

public class AddPurchaseUI {
    public JFrame view;
    public JButton btnLoad = new JButton("Load");
    public JButton btnSave = new JButton("Save");

    public JTextField txtPurchaseID = new JTextField(20);
    public JTextField txtCustomerID = new JTextField(10);
    public JTextField txtProductID = new JTextField(10);
    public JTextField txtName = new JTextField(20);
    public JTextField txtPrice = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);

    public JLabel labPrice = new JLabel("Product Price: ");
    public JLabel labDate = new JLabel("Date of Purchase: ");

    public JLabel labCustomerName = new JLabel("Customer Name: ");
    public JLabel labProductName = new JLabel("Product Name: ");

    public JLabel labTax = new JLabel("Tax: ");
    public JLabel labCost = new JLabel("Cost: ");
    public JLabel labTotalCost = new JLabel("Total Cost: ");

    ProductModel product;
    PurchaseModel purchase;
    CustomerModel customer;


    public AddPurchaseUI() {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Add Purchase");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

        JPanel line = new JPanel(new FlowLayout());
        line.add(new JLabel("PurchaseID "));
        line.add(txtPurchaseID);
        line.add(labDate);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("CustomerID "));
        line.add(txtCustomerID);
        line.add(labCustomerName);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("ProductID "));
        line.add(txtProductID);
        line.add(labProductName);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(new JLabel("Quantity "));
        line.add(txtQuantity);
        line.add(labPrice);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(labCost);
        line.add(labTax);
        line.add(labTotalCost);
        view.getContentPane().add(line);

        line = new JPanel(new FlowLayout());
        line.add(btnLoad);
        line.add(btnSave);
        view.getContentPane().add(line);

        txtProductID.addFocusListener(new ProductIDFocusListener());
        txtCustomerID.addFocusListener(new CustomerIDFocusListener());
        txtQuantity.getDocument().addDocumentListener(new QuantityChangeListener());

        btnLoad.addActionListener(new LoadButtonListener());

        btnSave.addActionListener(new SaveButtonListener());


    }

    public void run() {
        purchase = new PurchaseModel();
        purchase.mDate = Calendar.getInstance().getTime().toString();
        labDate.setText("Date of purchase: " + purchase.mDate);
        view.setVisible(true);
    }

    private class ProductIDFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {
            String s = txtProductID.getText();

            if (s.length() == 0) {
                labProductName.setText("Product Name: [not specified!]");
                return;
            }

            System.out.println("ProductID = " + s);

            try {
                purchase.mProductID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid ProductID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // get the product
            try {
                Gson gson = new Gson();
                Socket link = new Socket("localhost", 1024);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_PRODUCT;
                msg.data = Integer.toString(purchase.mProductID);
                output.println(gson.toJson(msg)); // send to Server

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null,
                            "Error: No product with id = " + purchase.mProductID + " in store!", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                    labProductName.setText("Product Name: ");

                    return;
                }
                else {
                    product = gson.fromJson(msg.data, ProductModel.class);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            labProductName.setText("Product Name: " + product.name);
            purchase.mCost = product.price;
            labPrice.setText("Product Price: " + product.price);

        }
   /*     @Override
        public void focusGained(FocusEvent focusEvent) {
        }
        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }
        private void process() {
            String s = txtProductID.getText();
            if (s.length() == 0) {
                labProductName.setText("Product Name: [not specified!]");
                return;
            }
            System.out.println("ProductID = " + s);
            try {
                purchase.mProductID = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid ProductID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            product = javaApp.getInstance().getDataAdapter().loadProduct(purchase.mProductID);
            if (product == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No product with id = " + purchase.mProductID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labProductName.setText("Product Name: ");
                return;
            }
            labProductName.setText("Product Name: " + product.name);
            purchase.mPrice = product.price;
            labPrice.setText("Product Price: " + product.price);
        }
*/
    }

    private class CustomerIDFocusListener implements FocusListener {
        @Override
        public void focusGained(FocusEvent focusEvent) {

        }

        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }

        private void process() {
            String s = txtCustomerID.getText();

            if (s.length() == 0) {
                labCustomerName.setText("Customer Name: [not specified!]");
                return;
            }

            System.out.println("CustomerID = " + s);

            try {
                purchase.mCustomerID = Integer.parseInt(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid CustomerID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // get the customer
            try {
                Gson gson = new Gson();
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_CUSTOMER;
                msg.data = Integer.toString(purchase.mCustomerID);
                output.println(gson.toJson(msg)); // send to Server

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null,
                            "Error: No customer with id = " + purchase.mCustomerID + " in store!", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                    labCustomerName.setText("Customer Name: ");

                    return;
                }
                else {
                    customer = gson.fromJson(msg.data, CustomerModel.class);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            labCustomerName.setText("Customer Name: " + customer.name);
        }
       /* @Override
        public void focusGained(FocusEvent focusEvent) {
        }
        @Override
        public void focusLost(FocusEvent focusEvent) {
            process();
        }
        private void process() {
            String s = txtCustomerID.getText();
            if (s.length() == 0) {
                labCustomerName.setText("Customer Name: [not specified!]");
                return;
            }
            System.out.println("CustomerID = " + s);
            try {
                purchase.mCustomerID = Integer.parseInt(s);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Invalid CustomerID", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
            customer = javaApp.getInstance().getDataAdapter().loadCustomer(purchase.mCustomerID);
            if (customer == null) {
                JOptionPane.showMessageDialog(null,
                        "Error: No customer with id = " + purchase.mCustomerID + " in store!", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                labCustomerName.setText("Customer Name: ");
                return;
            }
            labCustomerName.setText("Product Name: " + customer.name);
        }*/

    }

    private class QuantityChangeListener implements DocumentListener {
        public void changedUpdate(DocumentEvent e) {
            process();
        }

        public void removeUpdate(DocumentEvent e) {
            process();
        }

        public void insertUpdate(DocumentEvent e) {
            process();
        }

        private void process() {
            String s = txtQuantity.getText();

            if (s.length() == 0) {
                //labCustomerName.setText("Customer Name: [not specified!]");
                return;
            }

            System.out.println("Quantity = " + s);

            try {
                purchase.mQuantity = Double.parseDouble(s);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter an valid quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (purchase.mQuantity <= 0) {
                JOptionPane.showMessageDialog(null,
                        "Error: Please enter a valid quantity", "Error Message",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (purchase.mQuantity > product.quantity) {
                JOptionPane.showMessageDialog(null,
                        "Not enough available products!", "Information",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            purchase.mCost = purchase.mQuantity * product.price;
            purchase.mTax = purchase.mCost * 0.09;
            purchase.mTotal = purchase.mCost + purchase.mTax;

            labCost.setText("Cost: $" + String.format("%8.2f", purchase.mCost).trim());
            labTax.setText("Tax: $" + String.format("%8.2f", purchase.mTax).trim());
            labTotalCost.setText("Total: $" + String.format("%8.2f", purchase.mTotal).trim());

        }

    }


    class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {

            String id = txtPurchaseID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }

            try {
                purchase.mPurchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }

            try {
                Gson gson = new Gson();
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_PURCHASE;
                msg.data = gson.toJson(purchase);
                output.println(gson.toJson(msg)); // send to Server

                msg = gson.fromJson(input.nextLine(), MessageModel.class); // receive from Server

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Purchase is NOT saved successfully!");
                }
                else {
                    TXTReceiptBuilder receipt = new TXTReceiptBuilder();
                    receipt.appendHeader("Purchase added successfully!");
                    receipt.appendCustomer(customer);
                    receipt.appendProduct(product);
                    receipt.appendPurchase(purchase);
                    receipt.appendFooter("");
                    JOptionPane.showMessageDialog(null, receipt.sb);
                    view.dispose();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
/*
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Gson gson = new Gson();
            String id = txtPurchaseID.getText();
            PurchaseModel purchase = new PurchaseModel();
            if (purchase == null) {
                JOptionPane.showMessageDialog(null, "A Purchase must be loaded in first!");
                return;
            }
            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }
            try {
                purchase.mPurchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }
            String customerID = txtCustomerID.getText();
            if (customerID.length() == 0) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be empty!");
                return;
            }
            try {
                purchase.mCustomerID = Integer.parseInt(customerID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                return;
            }
            String productID = txtProductID.getText();
            if (productID.length() == 0) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be empty!");
                return;
            }
            try {
                purchase.mProductID = Integer.parseInt(productID);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ProductID is invalid!");
                return;
            }
            String quantity = txtQuantity.getText();
            if (quantity.length() == 0) {
                JOptionPane.showMessageDialog(null, "Quantity cannot be empty!");
                return;
            }
            try {
                purchase.mQuantity = Integer.parseInt(quantity);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity is invalid!");
                return;
            }
            // Load the selected product to get its price
            ProductModel product = new ProductModel();
            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);
                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_PRODUCT;
                msg.data = Integer.toString(purchase.mProductID);
                output.println(gson.toJson(msg)); // send to Server
                msg = gson.fromJson(input.nextLine(), MessageModel.class);
                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null,
                            "Error: No product with id = " + purchase.mProductID + " in store!", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    product = gson.fromJson(msg.data, ProductModel.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            purchase.mCost = product.price * purchase.mQuantity;
            purchase.mTax = purchase.mCost * .09;
            purchase.mTotal = purchase.mCost + purchase.mTax;
            // Load customer info for the receipt
            CustomerModel customer = new CustomerModel();
            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);
                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_CUSTOMER;
                msg.data = Integer.toString(purchase.mCustomerID);
                output.println(gson.toJson(msg)); // send to Server
                msg = gson.fromJson(input.nextLine(), MessageModel.class);
                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null,
                            "Error: No customer with id = " + purchase.mCustomerID + " in store!", "Error Message",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    customer = gson.fromJson(msg.data, CustomerModel.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // all product info is ready! Send to Server!
            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);
                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_PURCHASE;
                msg.data = gson.toJson(purchase);
                output.println(gson.toJson(msg)); // send to Server
                msg = gson.fromJson(input.nextLine(), MessageModel.class); // receive from Server
                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Purchase is saved successfully!");
                } else {
                    TXTReceiptBuilder receipt = new TXTReceiptBuilder();
                    receipt.appendHeader("Purchase added successfully!");
                    receipt.appendCustomer(customer);
                    receipt.appendProduct(product);
                    receipt.appendPurchase(purchase);
                    receipt.appendFooter("");
                    JOptionPane.showMessageDialog(null, receipt.sb);
                    view.dispose();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String id = txtPurchaseID.getText();
                if (id.length() == 0) {
                    JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                    return;
                }
                try {
                    purchase.mPurchaseID = Integer.parseInt(id);
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                    return;
                }
                switch (javaApp.getInstance().getDataAdapter().savePurchase(purchase)) {
                    case SQLDataBase.PURCHASE_DUPLICATE_ERROR:
                        JOptionPane.showMessageDialog(null, "Purchase NOT added successfully! Duplicate purchase ID!");
                    default:
                        JOptionPane.showMessageDialog(null, "Purchase added successfully!" + purchase);
                }
            }
        }
        */
    }

    class LoadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            PurchaseModel purchase = new PurchaseModel();
            Gson gson = new Gson();
            String id = txtPurchaseID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "PurchaseID cannot be null!");
                return;
            }

            try {
                purchase.mPurchaseID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "PurchaseID is invalid!");
                return;
            }


            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_CUSTOMER;
                msg.data = Integer.toString(purchase.mPurchaseID);
                output.println(gson.toJson(msg)); // send to Server

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Purchase NOT exists!");
                } else {
                    purchase = gson.fromJson(msg.data, PurchaseModel.class);
                    //txtName.setText(purchase.);
                    txtPrice.setText(Double.toString(purchase.mPrice));
                    txtQuantity.setText(Double.toString(purchase.mQuantity));
                    txtCustomerID.setText(Double.toString(purchase.mCustomerID));
                    txtProductID.setText(Double.toString(purchase.mProductID));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }


}
