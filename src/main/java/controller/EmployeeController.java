package controller;

import model.EmployeeModel;
import model.PositionModel;

import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class EmployeeController {

    private EmployeeModel model = new EmployeeModel();
    private PositionModel positionModel = new PositionModel();

    public void loadPositions(JComboBox<String> cb) {
        try {
            cb.removeAllItems();
            ResultSet rs = positionModel.getAll();
            while (rs.next()) {
                cb.addItem(rs.getString("name"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void loadData(DefaultTableModel table) {
        table.setRowCount(0);
        try {
            ResultSet rs = model.getAll();
            int no = 1;
            while (rs.next()) {
                table.addRow(new Object[]{
                        no++,
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getString("email")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void insert(String name, String position, String email, DefaultTableModel table) {
        try {
            if (name.isEmpty() || email.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama dan email harus diisi");
                return;
            }

            int positionId = model.getPositionIdByName(position);
            model.insert(name, positionId, email);
            loadData(table);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void update(int id, String name, String position, String email, DefaultTableModel table) {
        try {
            int positionId = model.getPositionIdByName(position);
            model.update(id, name, positionId, email);
            loadData(table);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void delete(int id, DefaultTableModel table) {
        try {
            model.delete(id);
            loadData(table);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void search(String keyword, DefaultTableModel table) {
        table.setRowCount(0);
        try {
            ResultSet rs = model.search(keyword);
            int no = 1;
            while (rs.next()) {
                table.addRow(new Object[]{
                        no++,
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getString("email")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
