import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUI {

    public JFrame view;
    public JButton btnManageProduct = new JButton("Add Product");
    public JButton btnManageCustomer = new JButton("Add Customer");
    public JButton btnAddPurchase = new JButton("Add Purchase");

    public JButton btnUpdateProduct = new JButton("Update Product");
    public JButton btnUpdateCustomer = new JButton("Update Product");
    public JButton btnUpdatePurchase = new JButton("Update Product");
    public MainUI()   {
        this.view = new JFrame();

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setTitle("Store Management System");
        view.setSize(1000, 600);
        view.getContentPane().setLayout(new BoxLayout(view.getContentPane(), BoxLayout.PAGE_AXIS));


        JLabel title = new JLabel("Store Management System");

        title.setFont(title.getFont().deriveFont(24.0f));
        view.getContentPane().add(title);

        JPanel panelButtons = new JPanel(new FlowLayout());
        panelButtons.add(btnManageProduct);
        panelButtons.add(btnManageCustomer);
        panelButtons.add(btnAddPurchase);
        panelButtons.add(btnUpdateProduct);
        panelButtons.add(btnUpdateCustomer);
        panelButtons.add(btnUpdatePurchase);

        view.getContentPane().add(panelButtons);

        btnManageProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddProductView ui = new AddProductView();
                ui.run();
            }
        });

        btnManageCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddCustomerView ui = new AddCustomerView();
                ui.run();
            }
        });

        btnAddPurchase.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddPurchaseUI apur = new AddPurchaseUI();
                apur.run();
            }
        });

        btnUpdateProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UpdateProductUI ui = new UpdateProductUI();
                ui.run();
            }
        });
        btnUpdateCustomer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UpdateCustomerUI ui = new UpdateCustomerUI();
                ui.run();
            }
        });
        btnUpdatePurchase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UpdatePurchaseUI ui = new UpdatePurchaseUI();
                ui.run();
            }
        });
        /*
        btnUpdateProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                UpdateProductUI ui = new UpdateProductUI();
                ui.run();
            }
        });*/


    }
}