package controller;

import model.TransactionHistoryModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class TransactionHistoryController {

    private TransactionHistoryModel model = new TransactionHistoryModel();

    public void loadTransactions(DefaultTableModel table) {
        table.setRowCount(0);
        try {
            ResultSet rs = model.getAll();
            int no = 1;
            while (rs.next()) {
                table.addRow(new Object[]{
                        no++,
                        rs.getInt("transaction_id"),
                        rs.getString("cashier"),
                        rs.getTimestamp("transaction_date"),
                        rs.getDouble("total")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void loadDetails(int transactionId, DefaultTableModel table) {
        table.setRowCount(0);
        try {
            ResultSet rs = model.getDetails(transactionId);
            int no = 1;
            while (rs.next()) {
                table.addRow(new Object[]{
                        no++,
                        rs.getString("product"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDouble("subtotal")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
