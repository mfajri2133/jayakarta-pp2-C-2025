package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ProductView extends JFrame {

    // ===== Components =====
    private JTable tableProducts;
    private DefaultTableModel tableModel;

    private JTextField txtId;
    private JTextField txtName;
    private JComboBox<String> cbCategory;
    private JTextField txtPrice;
    private JTextField txtStock;

    private JButton btnAdd;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnClear;

    public ProductView() {
        setTitle("Manajemen Produk - Toko Jayakarta");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        // ===== Panel Utama =====
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ===== Form Panel =====
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));

        txtId = new JTextField();
        txtId.setEditable(false);

        txtName = new JTextField();
        cbCategory = new JComboBox<>();
        txtPrice = new JTextField();
        txtStock = new JTextField();

        formPanel.add(new JLabel("ID Produk"));
        formPanel.add(txtId);

        formPanel.add(new JLabel("Nama Produk"));
        formPanel.add(txtName);

        formPanel.add(new JLabel("Kategori"));
        formPanel.add(cbCategory);

        formPanel.add(new JLabel("Harga"));
        formPanel.add(txtPrice);

        formPanel.add(new JLabel("Stok"));
        formPanel.add(txtStock);

        // ===== Button Panel =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));

        btnAdd = new JButton("Tambah");
        btnUpdate = new JButton("Ubah");
        btnDelete = new JButton("Hapus");
        btnClear = new JButton("Clear");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        // ===== Table =====
        tableModel = new DefaultTableModel(
                new Object[]{"ID", "Nama Produk", "Kategori", "Harga", "Stok"}, 0
        );

        tableProducts = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableProducts);

        // ===== Add to Main Panel =====
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
    }

    // ===== Getter (dipakai Controller) =====
    public JTable getTableProducts() {
        return tableProducts;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public JTextField getTxtId() {
        return txtId;
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public JComboBox<String> getCbCategory() {
        return cbCategory;
    }

    public JTextField getTxtPrice() {
        return txtPrice;
    }

    public JTextField getTxtStock() {
        return txtStock;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnUpdate() {
        return btnUpdate;
    }

    public JButton getBtnDelete() {
        return btnDelete;
    }

    public JButton getBtnClear() {
        return btnClear;
    }
}
