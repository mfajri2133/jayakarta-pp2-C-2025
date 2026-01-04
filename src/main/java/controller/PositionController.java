package controller;

import model.PositionModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;

public class PositionController {

    private PositionModel model = new PositionModel();

    public void loadData(DefaultTableModel table) {
        table.setRowCount(0);
        try {
            ResultSet rs = model.getAll();
            int no = 1;
            while (rs.next()) {
                table.addRow(new Object[]{
                        no++,
                        rs.getInt("id"),
                        rs.getString("name")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void insert(String name, DefaultTableModel table) {
        try {
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama jabatan harus diisi");
                return;
            }

            if (model.exists(name)) {
                JOptionPane.showMessageDialog(null, "Jabatan sudah ada");
                return;
            }

            model.insert(name);
            JOptionPane.showMessageDialog(null, "Jabatan berhasil ditambahkan");
            loadData(table);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void update(int id, String name, DefaultTableModel table) {
        try {
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Nama jabatan harus diisi");
                return;
            }

            model.update(id, name);
            JOptionPane.showMessageDialog(null, "Jabatan berhasil diperbarui");
            loadData(table);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void delete(int id, DefaultTableModel table) {
        try {
            model.delete(id);
            JOptionPane.showMessageDialog(null, "Jabatan berhasil dihapus");
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
                        rs.getString("name")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}