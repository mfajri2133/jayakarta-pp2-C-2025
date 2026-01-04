package view;

import controller.TransactionController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TransactionView extends JFrame {

    private JComboBox<String> cbEmployee;
    private JComboBox<String> cbProduct;
    private JTextField txtPrice;
    private JTextField txtQty;

    private JTable table;
    private DefaultTableModel tableModel;

    private JButton btnAddItem;
    private JButton btnRemoveItem;
    private JButton btnSave;

    private TransactionController controller = new TransactionController();

    public TransactionView() {
        setTitle("TOKO JAYAKARTA - TRANSAKSI");
        setSize(800, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // PANEL FORM
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Transaksi"));

        formPanel.add(new JLabel("Kasir"));
        cbEmployee = new JComboBox<>();
        formPanel.add(cbEmployee);

        formPanel.add(new JLabel("Produk"));
        cbProduct = new JComboBox<>();
        formPanel.add(cbProduct);

        formPanel.add(new JLabel("Harga"));
        txtPrice = new JTextField();
        txtPrice.setEditable(false);
        formPanel.add(txtPrice);

        formPanel.add(new JLabel("Qty"));
        txtQty = new JTextField();
        formPanel.add(txtQty);

        add(formPanel, BorderLayout.NORTH);

        // PANEL KONTROL ITEM
        JPanel itemControlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        itemControlPanel.setBorder(BorderFactory.createTitledBorder("Kontrol Item"));

        btnAddItem = new JButton("Tambah Item");
        btnAddItem.setBackground(new Color(46, 204, 113));
        btnAddItem.setForeground(Color.WHITE);

        btnRemoveItem = new JButton("Hapus Item");
        btnRemoveItem.setBackground(new Color(231, 76, 60));
        btnRemoveItem.setForeground(Color.WHITE);

        itemControlPanel.add(btnAddItem);
        itemControlPanel.add(btnRemoveItem);

        // TABLE
        tableModel = new DefaultTableModel(
                new String[]{"Produk ID", "Produk", "Qty", "Harga"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel centerPanel = new JPanel(new BorderLayout(5, 5));
        centerPanel.add(itemControlPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        add(centerPanel, BorderLayout.CENTER);

        // PANEL AKSI
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnSave = new JButton("Simpan Transaksi");
        btnSave.setBackground(new Color(52, 152, 219));
        btnSave.setForeground(Color.WHITE);

        actionPanel.add(btnSave);
        add(actionPanel, BorderLayout.SOUTH);

        // LOAD DATA
        controller.loadEmployees(cbEmployee);
        controller.loadProducts(cbProduct);

        // EVENT
        cbProduct.addActionListener(e -> {
            if (cbProduct.getSelectedItem() != null) {
                String[] parts = cbProduct.getSelectedItem().toString().split(" - ");
                txtPrice.setText(parts[2]);
            }
        });

        btnAddItem.addActionListener(e -> {
            try {
                if (cbProduct.getSelectedItem() == null || txtQty.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Produk dan Qty wajib diisi");
                    return;
                }

                String selected = cbProduct.getSelectedItem().toString();
                String[] parts = selected.split(" - ");

                int productId = Integer.parseInt(parts[0]);
                String productName = parts[1];
                double price = Double.parseDouble(parts[2]);
                int stock = Integer.parseInt(parts[3].replace("stok:", ""));
                int qty = Integer.parseInt(txtQty.getText());

                if (qty <= 0) {
                    JOptionPane.showMessageDialog(this, "Qty harus lebih dari 0");
                    return;
                }

                if (qty > stock) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Stok tidak mencukupi! Stok tersedia: " + stock
                    );
                    return;
                }

                boolean found = false;

                //  CEK APAKAH PRODUK SUDAH ADA DI TABLE
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    int existingProductId = Integer.parseInt(
                            tableModel.getValueAt(i, 0).toString()
                    );

                    if (existingProductId == productId) {
                        int existingQty = Integer.parseInt(
                                tableModel.getValueAt(i, 2).toString()
                        );

                        tableModel.setValueAt(
                                existingQty + qty,
                                i,
                                2
                        );

                        found = true;
                        break;
                    }
                }

                //  JIKA BELUM ADA, TAMBAH ROW BARU
                if (!found) {
                    tableModel.addRow(new Object[]{
                            productId, productName, qty, price
                    });
                }

                //  UPDATE STOK SEMENTARA
                int sisaStok = stock - qty;
                cbProduct.removeItem(selected);

                if (sisaStok > 0) {
                    cbProduct.addItem(
                            productId + " - " + productName + " - " + price + " - stok:" + sisaStok
                    );
                }

                txtQty.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid");
            }
        });

        btnRemoveItem.addActionListener(e -> {
            int row = table.getSelectedRow();

            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Pilih item yang ingin dihapus");
                return;
            }

            int productId = Integer.parseInt(tableModel.getValueAt(row, 0).toString());
            String productName = tableModel.getValueAt(row, 1).toString();
            int qty = Integer.parseInt(tableModel.getValueAt(row, 2).toString());
            double price = Double.parseDouble(tableModel.getValueAt(row, 3).toString());

            boolean found = false;

            for (int i = 0; i < cbProduct.getItemCount(); i++) {
                String item = cbProduct.getItemAt(i);
                if (item.startsWith(productId + " -")) {
                    String[] parts = item.split(" - ");
                    int stock = Integer.parseInt(parts[3].replace("stok:", ""));
                    cbProduct.removeItemAt(i);
                    cbProduct.addItem(
                            productId + " - " + productName + " - " + price + " - stok:" + (stock + qty)
                    );
                    found = true;
                    break;
                }
            }

            if (!found) {
                cbProduct.addItem(
                        productId + " - " + productName + " - " + price + " - stok:" + qty
                );
            }

            tableModel.removeRow(row);
        });

        btnSave.addActionListener(e -> {
            controller.saveTransaction(
                    cbEmployee.getSelectedItem().toString(),
                    tableModel
            );
            tableModel.setRowCount(0);
        });
    }
}