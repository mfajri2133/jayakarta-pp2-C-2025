package model;

import config.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class CategoryModel {

    // ambil semua data
    public ResultSet getAll() throws Exception {
        String sql = "SELECT * FROM categories";
        Statement st = DBConnection.getConnection().createStatement();
        return st.executeQuery(sql);
    }

    // cek kategori sudah ada atau belum
    public boolean exists(String name) throws Exception {
        String sql = "SELECT id FROM categories WHERE name = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    // insert
    public void insert(String name) throws Exception {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.executeUpdate();
    }

    // update
    public void update(int id, String name) throws Exception {
        String sql = "UPDATE categories SET name = ? WHERE id = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    // delete
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM categories WHERE id = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    // search
    public ResultSet search(String keyword) throws Exception {
        String sql = "SELECT * FROM categories WHERE name LIKE ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        return ps.executeQuery();
    }
}
