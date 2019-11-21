import javax.swing.*;

public class javaApp {

    IDataAdapter adapter = null;
    public static  String dbms = "SQLite";
    public static  String path = "/Users/jonathan/Downloads/Store/store.db";

    private static javaApp instance = null;


    public static javaApp getInstance() {
        if (instance == null) {/*
            String dbfile = path;
            JFileChooser fc = new JFileChooser();
            if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                dbfile = fc.getSelectedFile().getAbsolutePath();
*/
            instance = new javaApp(dbms, path);
        }
        return instance;
    }

    private javaApp(String dbms, String dbfile) {
        if (dbms.equals("Oracle"))
            adapter = new OracleDataAdapter();
        else
        if (dbms.equals("SQLite"))
            adapter = new SQLDataBase();
        else
        if (dbms.equals("Network"))
            adapter = new NetworkDataAdapter();

        adapter.connect(dbfile);

    }

    public IDataAdapter getDataAdapter() {
        return adapter;
    }
    /*
        public INetworkAdapter getNetworkAdapter() {
            return adapter;
        }
    */
    public void setDataAdapter(IDataAdapter a) {
        adapter = a;
    }

    public void run() {
        MainUI ui = new MainUI();
        ui.view.setVisible(true);

    }

/*
    public static void main(String[] args) {
        AddProductView frame = new AddProductView();
        SQLDataBase adapter = new SQLDataBase();
        AddProductController control = new AddProductController(frame, adapter);
ProductModel product = adapter.loadProduct(3);
      //  control.view = frame;
  //  control.adapter = adapter;
        frame.pack();
        frame.setVisible(true);
        frame.btnAdd.addActionListener(control.addButtonListener);
        frame.btnCancel.addActionListener(control.cancelButtonListener);
    }
*/

    public static void main(String[] args) {
        System.out.println("Hello class!");
        //   javaApp.getInstance().init();
        if (args.length > 0) { // having runtime arguments
            dbms = args[0];
            if (args.length == 1) { // do not have 2nd arguments for dbfile
                if (dbms.equals("SQLite")) {
                    JFileChooser fc = new JFileChooser();
                    if (fc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
                        path = fc.getSelectedFile().getAbsolutePath();
                }
                else
                    path = JOptionPane.showInputDialog("Enter address of database server as host:port");
            }
            else
                path = args[1];
        }
        javaApp.getInstance().run();
    /*
    AddProductView ap = new AddProductView();
    ap.run();
    AddCustomerView cp = new AddCustomerView();
    cp.run();
    */

    }
}