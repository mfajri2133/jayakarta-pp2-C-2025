package view;

import controller.ProductController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ProductView extends JFrame {

    private JTextField txtName, txtPrice, txtStock, txtSearch;
    private JComboBox<String> cbCategory;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnSave, btnUpdate, btnDelete, btnClear;

    private ProductController controller = new ProductController();
    private int selectedId = -1;

    public ProductView() {
        setTitle("TOKO JAYAKARTA - PRODUK");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //  FORM PANEL 
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Produk"));

        formPanel.add(new JLabel("Nama Produk"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Kategori"));
        cbCategory = new JComboBox<>();
        formPanel.add(cbCategory);

        formPanel.add(new JLabel("Harga"));
        txtPrice = new JTextField();
        formPanel.add(txtPrice);

        formPanel.add(new JLabel("Stok"));
        txtStock = new JTextField();
        formPanel.add(txtStock);

        add(formPanel, BorderLayout.NORTH);

        //  BUTTON PANEL 
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnSave = new JButton("Simpan");
        btnSave.setBackground(new Color(46, 204, 113)); // hijau
        btnSave.setForeground(Color.WHITE);

        btnUpdate = new JButton("Ubah");
        btnUpdate.setBackground(new Color(241, 196, 15)); // kuning
        btnUpdate.setForeground(Color.BLACK);
        btnUpdate.setEnabled(false);

        btnDelete = new JButton("Hapus");
        btnDelete.setBackground(new Color(231, 76, 60)); // merah
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setEnabled(false);

        btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(189, 195, 199)); // abu
        btnClear.setForeground(Color.BLACK);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        //  SEARCH PANEL 
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Pencarian"));

        searchPanel.add(new JLabel("Cari:"), BorderLayout.WEST);
        txtSearch = new JTextField();
        searchPanel.add(txtSearch, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        //  TABLE 
        tableModel = new DefaultTableModel(
                new String[]{"No", "ID", "Produk", "Kategori", "Harga", "Stok"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);
        table.getColumnModel().getColumn(1).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Daftar Produk")
        );

        add(scrollPane, BorderLayout.CENTER);

        //  LOAD DATA 
        controller.loadCategories(cbCategory);
        controller.loadData(tableModel);

        //  EVENTS 
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                selectedId = Integer.parseInt(
                        tableModel.getValueAt(row, 1).toString()
                );

                txtName.setText(tableModel.getValueAt(row, 2).toString());
                cbCategory.setSelectedItem(
                        tableModel.getValueAt(row, 3).toString()
                );
                txtPrice.setText(tableModel.getValueAt(row, 4).toString());
                txtStock.setText(tableModel.getValueAt(row, 5).toString());

                btnSave.setEnabled(false);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        });

        btnSave.addActionListener(e -> {
            controller.insert(
                    txtName.getText(),
                    cbCategory.getSelectedItem().toString(),
                    txtPrice.getText(),
                    txtStock.getText(),
                    tableModel
            );
            resetForm();
        });

        btnUpdate.addActionListener(e -> {
            controller.update(
                    selectedId,
                    txtName.getText(),
                    cbCategory.getSelectedItem().toString(),
                    txtPrice.getText(),
                    txtStock.getText(),
                    tableModel
            );
            resetForm();
        });

        btnDelete.addActionListener(e -> {
            controller.delete(selectedId, tableModel);
            resetForm();
        });

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                controller.search(txtSearch.getText(), tableModel);
            }
        });

        btnClear.addActionListener(e -> resetForm());
    }

    private void resetForm() {
        txtName.setText("");
        txtPrice.setText("");
        txtStock.setText("");
        txtSearch.setText("");
        selectedId = -1;

        btnSave.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        table.clearSelection();
        controller.loadData(tableModel);
    }
    
    private void resetForm() {
        txtName.setText("");
        txtPrice.setText("");
        txtStock.setText("");
        txtSearch.setText("");
        selectedId = -1;

        btnSave.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        table.clearSelection();
        controller.loadData(tableModel);
    }
}
}