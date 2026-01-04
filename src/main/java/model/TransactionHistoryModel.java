package model;

import config.DBConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class TransactionHistoryModel {

    public ResultSet getAll() throws Exception {
        String sql = """
            SELECT 
                t.id AS transaction_id,
                e.name AS cashier,
                t.transaction_date,
                SUM(td.subtotal) AS total
            FROM transactions t
            JOIN employees e ON t.employee_id = e.id
            JOIN transaction_details td ON td.transaction_id = t.id
            GROUP BY t.id, e.name, t.transaction_date
            ORDER BY t.transaction_date DESC
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        return ps.executeQuery();
    }

    public ResultSet getDetails(int transactionId) throws Exception {
        String sql = """
            SELECT 
                p.name AS product,
                td.quantity,
                td.price,
                td.subtotal
            FROM transaction_details td
            JOIN products p ON p.id = td.product_id
            WHERE td.transaction_id = ?
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, transactionId);
        return ps.executeQuery();
    }
}
