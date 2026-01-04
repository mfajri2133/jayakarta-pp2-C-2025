package model;

import config.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransactionModel {

    public int createTransaction(int employeeId) throws Exception {
        String sql = "INSERT INTO transactions (employee_id) VALUES (?)";
        PreparedStatement ps = DBConnection.getConnection()
                .prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
        ps.setInt(1, employeeId);
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) return rs.getInt(1);
        throw new Exception("Gagal membuat transaksi");
    }

    public void insertDetail(
            int transactionId,
            int productId,
            int qty,
            double price
    ) throws Exception {

        String sql = """
            INSERT INTO transaction_details
            (transaction_id, product_id, quantity, price, subtotal)
            VALUES (?, ?, ?, ?, ?)
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, transactionId);
        ps.setInt(2, productId);
        ps.setInt(3, qty);
        ps.setDouble(4, price);
        ps.setDouble(5, qty * price);
        ps.executeUpdate();
    }

    public void reduceStock(int productId, int qty) throws Exception {
        String sql = "UPDATE products SET stock = stock - ? WHERE id = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, qty);
        ps.setInt(2, productId);
        ps.executeUpdate();
    }
}
