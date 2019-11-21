import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import com.google.gson.Gson;

public class AddCustomerView extends JFrame {

    public JFrame view;
    public JButton btnLoad = new JButton("Load Customer");
    public JButton btnSave = new JButton("Save Customer");

    public JTextField txtCustomerID = new JTextField(20);
    public JTextField txtName = new JTextField(20);
    public JTextField txtAddress = new JTextField(20);
    public JTextField txtNumber = new JTextField(20);


    public AddCustomerView() {

        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        view.setTitle("Manage Customer Information");
        view.setSize(600, 400);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));

/*
        String[] labels = {"Customer ID", "Name", "Address", "Number"};
        int numPairs = labels.length;
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
        JTextField txtCustomerID = new JTextField(30);
        JTextField txtCustomerName = new JTextField(30);
        JTextField txtCustomerPrice = new JTextField(30);
        JTextField txtCustomerQuantity = new JTextField(30);
        JTextField txtCustomerVendor = new JTextField(30);
*/
        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnLoad);
        panelButtons.add(btnSave);
        view.getContentPane().add(panelButtons);

        JPanel line1 = new JPanel(new FlowLayout());
        line1.add(new JLabel("Customer ID "));
        line1.add(txtCustomerID);
        view.getContentPane().add(line1);

        JPanel line2 = new JPanel(new FlowLayout());
        line2.add(new JLabel("Name "));
        line2.add(txtName);
        view.getContentPane().add(line2);

        JPanel line3 = new JPanel(new FlowLayout());
        line3.add(new JLabel("Address "));
        line3.add(txtAddress);
        view.getContentPane().add(line3);

        JPanel line4 = new JPanel(new FlowLayout());
        line4.add(new JLabel("Number "));
        line4.add(txtNumber);
        view.getContentPane().add(line4);

        btnLoad.addActionListener(new LoadButtonListener());

        btnSave.addActionListener(new SaveButtonListener());


    }

    public void run() {
        view.setVisible(true);
    }

    class SaveButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();
            Gson gson = new Gson();
            String id = txtCustomerID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!");
                return;
            }

            try {
                customer.customerID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                return;
            }

            String name = txtName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer name cannot be empty!");
                return;
            }

            customer.name = name;

            String address = txtAddress.getText();
            if (address.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer address cannot be empty!");
                return;
            }

            customer.address = address;

            String number = txtNumber.getText();
            if (number.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer number cannot be empty!");
                return;
            }
            customer.number = number;

            // do client/server

            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_CUSTOMER;
                msg.data = gson.toJson(customer);
                output.println(gson.toJson(msg)); // send to Server

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_OK) {
                    customer = gson.fromJson(msg.data, CustomerModel.class);
                    txtName.setText(customer.name);
                    txtNumber.setText(customer.number);
                    txtAddress.setText(customer.address);
                    JOptionPane.showMessageDialog(null, "Customer is SAVED successfully!");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Customer is SAVED successfully!");

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
/*
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();
            Gson gson = new Gson();
            String id = txtCustomerID.getText();
            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!");
                return;
            }
            try {
                customer.customerID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                return;
            }
            String name = txtName.getText();
            if (name.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer name cannot be empty!");
                return;
            }
            customer.name = name;
            String address = txtAddress.getText();
            if (address.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer address cannot be empty!");
                return;
            }
            customer.address = address;
            String number = txtNumber.getText();
            if (number.length() == 0) {
                JOptionPane.showMessageDialog(null, "Customer number cannot be empty!");
                return;
            }
            customer.number = number;
            try {
                MessageModel msg = new MessageModel();
                msg.code = MessageModel.PUT_CUSTOMER;
                msg.data = gson.toJson(customer);
           //  msg = javaApp.getInstance().getNetworkAdapter().send(msg, "localhost", 1000);
                if (msg.code == MessageModel.OPERATION_FAILED)
                    JOptionPane.showMessageDialog(null, "Customer is NOT saved successfully!");
                else
                    JOptionPane.showMessageDialog(null, "Customer is SAVED successfully!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            switch (javaApp.getInstance().getDataAdapter().saveCustomer(customer)) {
                case SQLDataBase.CUSTOMER_DUPLICATE_ERROR:
                    JOptionPane.showMessageDialog(null, "Customer NOT added successfully! Duplicate customer ID!");
                default:
                    JOptionPane.showMessageDialog(null, "Customer added successfully!" + customer);
            }
        }
*/
    }

    class LoadButtonListener implements ActionListener {


        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            CustomerModel customer = new CustomerModel();
            Gson gson = new Gson();
            String id = txtCustomerID.getText();

            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!");
                return;
            }

            try {
                customer.customerID = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                return;
            }



            try {
                Socket link = new Socket("localhost", 1000);
                Scanner input = new Scanner(link.getInputStream());
                PrintWriter output = new PrintWriter(link.getOutputStream(), true);

                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_CUSTOMER;
                msg.data = Integer.toString(customer.customerID);
                output.println(gson.toJson(msg)); // send to Server

                msg = gson.fromJson(input.nextLine(), MessageModel.class);

                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Customer NOT exists!");
                }
                else {
                    customer = gson.fromJson(msg.data, CustomerModel.class);
                    txtName.setText(customer.name);
                    txtNumber.setText(customer.number);
                    txtAddress.setText(customer.address);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
/*
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            Gson gson = new Gson();
            String id = txtCustomerID.getText();
            if (id.length() == 0) {
                JOptionPane.showMessageDialog(null, "CustomerID cannot be null!");
                return;
            }
            try {
                int i = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "CustomerID is invalid!");
                return;
            }
            try {
                int i = Integer.parseInt(id);
                MessageModel msg = new MessageModel();
                msg.code = MessageModel.GET_CUSTOMER;
                msg.data = id;
               // javaApp.getInstance().getDataAdapter().loadCustomer(i);
                if (msg.code == MessageModel.OPERATION_FAILED) {
                    JOptionPane.showMessageDialog(null, "Customer NOT exists!");
                } else {
                    CustomerModel customer = gson.fromJson(msg.data, CustomerModel.class);
                    txtName.setText(customer.name);
                    txtNumber.setText(customer.number);
                    txtAddress.setText(customer.address);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
*/
    }

}