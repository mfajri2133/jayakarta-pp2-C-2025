package controller;

import model.TransactionModel;
import model.EmployeeModel;
import model.ProductModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class TransactionController {

    private TransactionModel model = new TransactionModel();
    private EmployeeModel employeeModel = new EmployeeModel();
    private ProductModel productModel = new ProductModel();

    // ===== LOAD EMPLOYEE =====
    public void loadEmployees(JComboBox<String> combo) {
        try {
            combo.removeAllItems();
            ResultSet rs = employeeModel.getAll();
            while (rs.next()) {
                combo.addItem(
                        rs.getInt("id") + " - " + rs.getString("name")
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // ===== LOAD PRODUCT =====
    public void loadProducts(JComboBox<String> combo) {
        try {
            combo.removeAllItems();
            ResultSet rs = productModel.getAvailableProducts();

            while (rs.next()) {
                combo.addItem(
                        rs.getInt("id") + " - " +
                                rs.getString("name") + " - " +
                                rs.getDouble("price") + " - stok:" +
                                rs.getInt("stock")
                );
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // ===== SAVE TRANSACTION =====
    public void saveTransaction(String employeeItem, DefaultTableModel table) {
        try {
            if (employeeItem == null) {
                JOptionPane.showMessageDialog(null, "Pilih kasir");
                return;
            }

            if (table.getRowCount() == 0) {
                JOptionPane.showMessageDialog(null, "Detail transaksi kosong");
                return;
            }

            int employeeId = Integer.parseInt(employeeItem.split(" - ")[0]);
            int transactionId = model.createTransaction(employeeId);

            for (int i = 0; i < table.getRowCount(); i++) {
                int productId = Integer.parseInt(table.getValueAt(i, 0).toString());
                int qty = Integer.parseInt(table.getValueAt(i, 2).toString());
                double price = Double.parseDouble(table.getValueAt(i, 3).toString());

                model.insertDetail(transactionId, productId, qty, price);
                model.reduceStock(productId, qty);
            }

            JOptionPane.showMessageDialog(null, "Transaksi berhasil disimpan");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
