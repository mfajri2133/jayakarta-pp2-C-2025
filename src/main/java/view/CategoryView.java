package view;
import controller.CategoryController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CategoryView extends JFrame {
    // Komponen UI
    private JTextField txtName, txtSearch;
    private JTable tableCategory;
    private DefaultTableModel tableModel;
    private JButton btnSave, btnUpdate, btnDelete, btnSearch, btnRefresh;
    
    // Controller & State
    private CategoryController controller = new CategoryController();
    private int selectedId = -1; // Menyimpan ID baris yang dipilih

    public CategoryView() {
        initComponents();
        controller.loadData(tableModel); // Load data awal
    }

    private void initComponents() {
        setTitle("Manajemen Kategori");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // Label & Input Nama
        JLabel lblName = new JLabel("Nama Kategori:");
        lblName.setBounds(20, 20, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(130, 20, 250, 25);
        add(txtName);

        // Tombol-Tombol CRUD
        btnSave = new JButton("Simpan");
        btnSave.setBounds(20, 60, 80, 25);
        add(btnSave);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(110, 60, 80, 25);
        add(btnUpdate);

        btnDelete = new JButton("Hapus");
        btnDelete.setBounds(200, 60, 80, 25);
        add(btnDelete);

        btnRefresh = new JButton("Refresh");
        btnRefresh.setBounds(290, 60, 90, 25);
        add(btnRefresh);

        // Pencarian
        txtSearch = new JTextField();
        txtSearch.setBounds(20, 110, 200, 25);
        add(txtSearch);

        btnSearch = new JButton("Cari");
        btnSearch.setBounds(230, 110, 80, 25);
        add(btnSearch);

        // Tabel
        String[] columns = {"No", "ID", "Nama Kategori"};
        tableModel = new DefaultTableModel(columns, 0);
        tableCategory = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableCategory);
        scrollPane.setBounds(20, 150, 540, 280);
        add(scrollPane);

        // --- EVENT HANDLERS ---

        // Simpan
        btnSave.addActionListener(e -> {
            controller.insert(txtName.getText(), tableModel);
            resetForm();
        });

        // Update
        btnUpdate.addActionListener(e -> {
            if (selectedId != -1) {
                controller.update(selectedId, txtName.getText(), tableModel);
                resetForm();
            } else {
                JOptionPane.showMessageDialog(null, "Pilih data di tabel terlebih dahulu!");
            }
        });

        // Hapus
        btnDelete.addActionListener(e -> {
            if (selectedId != -1) {
                int confirm = JOptionPane.showConfirmDialog(null, "Yakin hapus data?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    controller.delete(selectedId, tableModel);
                    resetForm();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Pilih data di tabel terlebih dahulu!");
            }
        });

        // Cari
        btnSearch.addActionListener(e -> controller.search(txtSearch.getText(), tableModel));

        // Refresh
        btnRefresh.addActionListener(e -> {
            controller.loadData(tableModel);
            resetForm();
        });

        // Klik Tabel (Ambil data ke field)
        tableCategory.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableCategory.getSelectedRow();
                selectedId = Integer.parseInt(tableModel.getValueAt(row, 1).toString());
                txtName.setText(tableModel.getValueAt(row, 2).toString());
            }
        });
    }

    private void resetForm() {
        txtName.setText("");
        txtSearch.setText("");
        selectedId = -1;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CategoryView().setVisible(true));
    }
}