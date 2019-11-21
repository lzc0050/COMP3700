
import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class AddProductView extends JFrame {


    public JFrame view;
    public JButton btnLoad = new JButton("Load Product");
    public JButton btnSave = new JButton("Save Product");

    public JTextField txtProductID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtPrice = new JTextField(20);
    public JTextField txtQuantity = new JTextField(20);



    public AddProductView() {

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Update Product Information");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));


        String[] labels = {"Product ID ", "Name ", "Price ", "Quantity "};
    /*    int numPairs = labels.length;
       //Object p = this.getContentPane();
        for (int i = 0; i < numPairs; i++) {
            JLabel l = new JLabel(labels[i], JLabel.TRAILING);
            JPanel p = new JPanel(new FlowLayout());
            this.getContentPane().add(p);
            p.add(l);
            JTextField textField = new JTextField(10);
            l.setLabelFor(textField);
            p.add(textField);
        }
*/

/*
        JTextField txtProductID = new JTextField(30);
        JTextField txtProductName = new JTextField(30);
        JTextField txtProductPrice = new JTextField(30);
        JTextField txtProductQuantity = new JTextField(30);
        JTextField txtProductVendor = new JTextField(30);
*/


        JPanel line1 = new JPanel();
        line1.add(new JLabel("Product ID"));
        line1.add(txtProductID);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel();
        line2.add(new JLabel("Name"));
        line2.add(txtName);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel();
        line3.add(new JLabel("Price"));
        line3.add(txtPrice);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel();
        line4.add(new JLabel("Quantity"));
        line4.add(txtQuantity);
        view.getContentPane().add(line4);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        btnLoad.addActionListener(new LoadButtonListener());

        btnSave.addActionListener(new SaveButtonListener());
    }

    public void run() {
        view.setVisible(true);
    }

    class LoadButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();
            Gson gson = new Gson();
            String id = txtProductID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be null!");
                return;
            }

            try {
                product.productID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ProductID is invalid!");
                return;
            }

            // do client/server

            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_PRODUCT;
                msg.data = Integer.toString(product.productID);
                output.println(gson.toJson(msg)); // send to Server

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Product NOT exists!");
                }
                else {
                    product = gson.fromJson(msg.data, ProductModel.class);
                    txtName.setText(product.name);
                    txtPrice.setText(Double.toString(product.price));
                    txtQuantity.setText(Double.toString(product.quantity));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        /*
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();
            String id = txtProductID.getText();
            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be null!");
                return;
            }
            try {
                product.productID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ProductID is invalid!");
                return;
            }
            // call data access!
            product = javaApp.getInstance().getDataAdapter().loadProduct(product.productID);
            if (product == null) {
                JOptionPane.showMessageDialog(null, "Product NOT exists!");
            } else {
                txtName.setText(product.name);
                txtPrice.setText(Double.toString(product.price));
                txtQuantity.setText(Double.toString(product.quantity));
            }
        }
        */
    }

    class SaveButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();
            Gson gson = new Gson();
            String id = txtProductID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be null!");
                return;
            }

            try {
                product.productID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ProductID is invalid!");
                return;
            }

            String name = txtName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Product name cannot be empty!");
                return;
            }

            product.name = name;

            String price = txtPrice.getText();
            try {
                product.price = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Price is invalid!");
                return;
            }

            String quant = txtQuantity.getText();
            try {
                product.quantity = Integer.parseInt(quant);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity is invalid!");
                return;
            }

            // all product infor is ready! Send to Server!


            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_PRODUCT;
                msg.data = gson.toJson(product);
                output.println(gson.toJson(msg)); // send to Server

                msg = gson.fromJson(input.nextLine(), MessageModel.class); // receive from Server

                if (msg.code == MessageModel.OPERATION_OK) {
                    JOptionPane.showMessageDialog(null, "Product is SAVED successfully!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Product is saved successfully!");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        /*
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            ProductModel product = new ProductModel();
            String id = txtProductID.getText();
            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "ProductID cannot be null!");
                return;
            }
            try {
                product.productID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "ProductID is invalid!");
                return;
            }
            String name = txtName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Product name cannot be empty!");
                return;
            }
            product.name = name;
            String price = txtPrice.getText();
            try {
                product.price = Double.parseDouble(price);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Price is invalid!");
                return;
            }
            String quant = txtQuantity.getText();
            try {
                product.quantity = Integer.parseInt(quant);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Quantity is invalid!");
                return;
            }
            int  res = javaApp.getInstance().getDataAdapter().saveProduct(product);
            if (res == IDataAdapter.PRODUCT_SAVE_FAILED)
                JOptionPane.showMessageDialog(null, "Product is NOT saved successfully!");
            else
                JOptionPane.showMessageDialog(null, "Product is SAVED successfully!");
            switch (javaApp.getInstance().getDataAdapter().saveProduct(product)) {
                case SQLDataBase.PRODUCT_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "Product NOT added successfully! Duplicate product ID!");
                default:
                    JOptionPane.showMessageDialog(null, "Product added successfully!" + product);
            }
        }
        */
    }


}