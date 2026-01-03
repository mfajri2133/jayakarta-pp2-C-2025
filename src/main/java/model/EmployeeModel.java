package model;

import config.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class EmployeeModel {

    public ResultSet getAll() throws Exception {
        String sql = """
            SELECT e.id, e.name, p.name AS position, e.email
            FROM employees e
            JOIN positions p ON e.position_id = p.id
        """;
        Statement st = DBConnection.getConnection().createStatement();
        return st.executeQuery(sql);
    }

    public int getPositionIdByName(String name) throws Exception {
        String sql = "SELECT id FROM positions WHERE name = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) return rs.getInt("id");
        throw new Exception("Jabatan tidak ditemukan");
    }

    public void insert(String name, int positionId, String email) throws Exception {
        String sql = "INSERT INTO employees (name, position_id, email) VALUES (?,?,?)";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, positionId);
        ps.setString(3, email);
        ps.executeUpdate();
    }

    public void update(int id, String name, int positionId, String email) throws Exception {
        String sql = "UPDATE employees SET name=?, position_id=?, email=? WHERE id=?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, name);
        ps.setInt(2, positionId);
        ps.setString(3, email);
        ps.setInt(4, id);
        ps.executeUpdate();
    }

    public void delete(int id) throws Exception {
        String sql = "DELETE FROM employees WHERE id=?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public ResultSet search(String keyword) throws Exception {
        String sql = """
            SELECT e.id, e.name, p.name AS position, e.email
            FROM employees e
            JOIN positions p ON e.position_id = p.id
            WHERE e.name LIKE ?
        """;
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        return ps.executeQuery();
    }
}
