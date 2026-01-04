package model;

import config.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductModel {

    // ================= GET ALL (JOIN CATEGORY) =================
    public ResultSet getAll() throws Exception {
        String sql = """
            SELECT 
                p.id,
                p.name AS product_name,
                c.name AS category_name,
                p.price,
                p.stock
            FROM products p
            JOIN categories c ON p.category_id = c.id
        """;

        Statement st = DBConnection.getConnection().createStatement();
        return st.executeQuery(sql);
    }

    public ResultSet getAvailableProducts() throws Exception {
        String sql = """
        SELECT id, name, price, stock
        FROM products
        WHERE stock > 0
    """;
        return DBConnection.getConnection()
                .prepareStatement(sql)
                .executeQuery();
    }

    // ================= INSERT =================
    public void insert(String name, int categoryId, double price, int stock) throws Exception {
        String sql = """
            INSERT INTO products (name, category_id, price, stock)
            VALUES (?, ?, ?, ?)
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, categoryId);
        ps.setDouble(3, price);
        ps.setInt(4, stock);
        ps.executeUpdate();
    }

    // ================= UPDATE =================
    public void update(int id, String name, int categoryId, double price, int stock) throws Exception {
        String sql = """
            UPDATE products
            SET name = ?, category_id = ?, price = ?, stock = ?
            WHERE id = ?
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, categoryId);
        ps.setDouble(3, price);
        ps.setInt(4, stock);
        ps.setInt(5, id);
        ps.executeUpdate();
    }

    // ================= DELETE =================
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM products WHERE id = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    // ================= SEARCH =================
    public ResultSet search(String keyword) throws Exception {
        String sql = """
            SELECT 
                p.id,
                p.name AS product_name,
                c.name AS category_name,
                p.price,
                p.stock
            FROM products p
            JOIN categories c ON p.category_id = c.id
            WHERE p.name LIKE ?
        """;

        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        return ps.executeQuery();
    }

    // ================= GET CATEGORY ID BY NAME =================
    public int getCategoryIdByName(String categoryName) throws Exception {
        String sql = "SELECT id FROM categories WHERE name = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, categoryName);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt("id");
        }
        throw new Exception("Kategori tidak ditemukan");
    }
}
