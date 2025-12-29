package model;

import config.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProductModel {

    // Ambil semua data produk
    public ResultSet getAll() throws Exception {
        String sql = "SELECT * FROM products";
        Statement st = DBConnection.getConnection().createStatement();
        return st.executeQuery(sql);
    }

    // Cek apakah nama produk sudah ada
    public boolean exists(String name) throws Exception {
        String sql = "SELECT id FROM products WHERE name = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    // Insert
    public void insert(String name, int categoryId, double price, int stock) throws Exception {
        String sql = "INSERT INTO products (name, category_id, price, stock) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, categoryId);
        ps.setDouble(3, price);
        ps.setInt(4, stock);
        ps.executeUpdate();
    }

    // Update
    public void update(int id, String name, int categoryId, double price, int stock) throws Exception {
        String sql = "UPDATE products SET name = ?, category_id = ?, price = ?, stock = ? WHERE id = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, categoryId);
        ps.setDouble(3, price);
        ps.setInt(4, stock);
        ps.setInt(5, id);
        ps.executeUpdate();
    }

    // Delete
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM products WHERE id = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    // Search
    public ResultSet search(String keyword) throws Exception {
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        return ps.executeQuery();
    }
}

//    // Opsi Tambahan: Ambil data produk + Nama Kategorinya (berguna untuk tampilan tabel)
//    public ResultSet getAllWithCategory() throws Exception {
//        String sql = "SELECT p.id, p.name, c.name AS category_name, p.price, p.stock " +
//                     "FROM products p " +
//                     "JOIN categories c ON p.category_id = c.id";
//        Statement st = DBConnection.getConnection().createStatement();
//        return st.executeQuery(sql);
//    }