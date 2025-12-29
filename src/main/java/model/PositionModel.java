package model;

import config.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PositionModel {

    // ambil semua data
    public ResultSet getAll() throws Exception {
        String sql = "SELECT * FROM positions";
        Statement st = DBConnection.getConnection().createStatement();
        return st.executeQuery(sql);
    }

    // cek posisi sudah ada atau belum
    public boolean exists(String name) throws Exception {
        String sql = "SELECT id FROM positions WHERE name = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        return rs.next();
    }

    // insert
    public void insert(String name) throws Exception {
        String sql = "INSERT INTO positions (name) VALUES (?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.executeUpdate();
    }

    // update
    public void update(int id, String name) throws Exception {
        String sql = "UPDATE positions SET name = ? WHERE id = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, id);
        ps.executeUpdate();
    }

    // delete
    public void delete(int id) throws Exception {
        String sql = "DELETE FROM positions WHERE id = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    // search
    public ResultSet search(String keyword) throws Exception {
        String sql = "SELECT * FROM positions WHERE name LIKE ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        return ps.executeQuery();
    }
}