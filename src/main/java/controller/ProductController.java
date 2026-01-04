package controller;

import model.ProductModel;
import model.CategoryModel;

import java.sql.ResultSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ProductController {

    private ProductModel productModel = new ProductModel();
    private CategoryModel categoryModel = new CategoryModel();

    // ================= LOAD DATA =================

    public void loadData(DefaultTableModel table) {
        table.setRowCount(0);
        try {
            ResultSet rs = productModel.getAll();
            int no = 1;
            while (rs.next()) {
                table.addRow(new Object[]{
                        no++,
                        rs.getInt("id"),
                        rs.getString("product_name"),
                        rs.getString("category_name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public void loadCategories(JComboBox<String> comboBox) {
        comboBox.removeAllItems();
        try {
            ResultSet rs = categoryModel.getAll();
            while (rs.next()) {
                comboBox.addItem(rs.getString("name"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // ================= INSERT =================

    public void insert(
            String name,
            String categoryName,
            String price,
            String stock,
            DefaultTableModel table
    ) {
        try {
            if (name.isEmpty() || categoryName == null || price.isEmpty() || stock.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi");
                return;
            }

            double parsedPrice;
            int parsedStock;

            try {
                parsedPrice = Double.parseDouble(price);
                parsedStock = Integer.parseInt(stock);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Harga dan stok harus berupa numerik");
                return;
            }

            if (parsedPrice <= 0 || parsedStock < 0) {
                JOptionPane.showMessageDialog(null, "Harga atau stok tidak valid");
                return;
            }

            int categoryId = productModel.getCategoryIdByName(categoryName);
            productModel.insert(name, categoryId, parsedPrice, parsedStock);

            JOptionPane.showMessageDialog(null, "Produk berhasil ditambahkan");
            loadData(table);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // ================= UPDATE =================

    public void update(
            int id,
            String name,
            String categoryName,
            String price,
            String stock,
            DefaultTableModel table
    ) {
        try {
            if (id == -1) {
                JOptionPane.showMessageDialog(null, "Tidak ada produk yang dipilih");
                return;
            }

            double parsedPrice;
            int parsedStock;

            try {
                parsedPrice = Double.parseDouble(price);
                parsedStock = Integer.parseInt(stock);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Harga dan stok harus berupa numerik");
                return;
            }

            int categoryId = productModel.getCategoryIdByName(categoryName);
            productModel.update(id, name, categoryId, parsedPrice, parsedStock);

            JOptionPane.showMessageDialog(null, "Produk berhasil diperbarui");
            loadData(table);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // ================= DELETE =================

    public void delete(int id, DefaultTableModel table) {
        try {
            if (id == -1) {
                JOptionPane.showMessageDialog(null, "Tidak ada produk yang dipilih");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Delete this product?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                productModel.delete(id);
                JOptionPane.showMessageDialog(null, "Produk berhasil dihapus");
                loadData(table);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // ================= SEARCH =================

    public void search(String keyword, DefaultTableModel table) {
        table.setRowCount(0);
        try {
            ResultSet rs = productModel.search(keyword);
            int no = 1;
            while (rs.next()) {
                table.addRow(new Object[]{
                        no++,
                        rs.getInt("id"),
                        rs.getString("product_name"),
                        rs.getString("category_name"),
                        rs.getDouble("price"),
                        rs.getInt("stock")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
}
