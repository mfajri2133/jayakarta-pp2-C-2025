package view;

import controller.PositionController;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PositionView extends JFrame {
    // Komponen sesuai standar Swing JFC
    private JTextField txtName, txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnSave, btnUpdate, btnDelete, btnClear;
    
    private PositionController controller = new PositionController();
    private int selectedId = -1; 

    public PositionView() {
        // Setup Frame
        setTitle("Management Position - Case Study");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // --- Label & Input (Fitur Input) ---
        JLabel lblTitle = new JLabel("POSITION DATA MANAGEMENT");
        lblTitle.setBounds(150, 10, 200, 25);
        add(lblTitle);

        JLabel lblName = new JLabel("Position Name:");
        lblName.setBounds(20, 50, 100, 25);
        add(lblName);

        txtName = new JTextField();
        txtName.setBounds(20, 75, 440, 30);
        add(txtName);

        // --- Buttons (Fitur Create, Update, Delete) ---
        btnSave = new JButton("Save");
        btnSave.setBounds(20, 115, 100, 30);
        add(btnSave);

        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(130, 115, 100, 30);
        btnUpdate.setEnabled(false); // Validasi: Hanya aktif jika data dipilih
        add(btnUpdate);

        btnDelete = new JButton("Delete");
        btnDelete.setBounds(240, 115, 100, 30);
        btnDelete.setEnabled(false); // Validasi: Hanya aktif jika data dipilih
        add(btnDelete);

        btnClear = new JButton("Clear");
        btnClear.setBounds(350, 115, 100, 30);
        add(btnClear);

        // --- Search Section ---
        JLabel lblSearch = new JLabel("Search:");
        lblSearch.setBounds(20, 165, 60, 25);
        add(lblSearch);

        txtSearch = new JTextField();
        txtSearch.setBounds(80, 165, 380, 30);
        add(txtSearch);

        // --- JTable (Fitur Melihat Data) ---
        tableModel = new DefaultTableModel(new String[]{"No", "ID", "Position Name"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Tabel read-only sesuai ketentuan
            }
        };
        
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(20, 210, 440, 270);
        add(scrollPane);

        // --- LOGIKA & VALIDASI ---

        // Load Data Awal (Poin 7: Melihat data di JTable)
        controller.loadData(tableModel);

        // Event: Klik Baris Tabel (Poin 5 & 6: Persiapan Ubah/Hapus)
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    selectedId = Integer.parseInt(table.getValueAt(row, 1).toString());
                    txtName.setText(table.getValueAt(row, 2).toString());
                    
                    btnSave.setEnabled(false);
                    btnUpdate.setEnabled(true);
                    btnDelete.setEnabled(true);
                }
            }
        });

        // Event: Save (Poin 4 & 8: Tambah Data & Validasi)
        btnSave.addActionListener(e -> {
            String name = txtName.getText().trim();
            if (name.isEmpty()) { // Validasi Input (Poin 8)
                JOptionPane.showMessageDialog(this, "Validasi: Nama posisi tidak boleh kosong!");
            } else {
                controller.insert(name, tableModel);
                resetForm();
            }
        });

        // Event: Update (Poin 5)
        btnUpdate.addActionListener(e -> {
            String name = txtName.getText().trim();
            if (name.isEmpty()) { // Validasi Input (Poin 8)
                JOptionPane.showMessageDialog(this, "Validasi: Nama posisi baru harus diisi!");
            } else {
                controller.update(selectedId, name, tableModel);
                resetForm();
            }
        });

        // Event: Delete (Poin 6)
        btnDelete.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Hapus data ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                controller.delete(selectedId, tableModel);
                resetForm();
            }
        });

        // Event: Search (Melihat data secara spesifik)
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                controller.search(txtSearch.getText(), tableModel);
            }
        });

        // Event: Clear
        btnClear.addActionListener(e -> resetForm());
    }

    private void resetForm() {
        txtName.setText("");
        txtSearch.setText("");
        selectedId = -1;
        btnSave.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);
        table.clearSelection();
        controller.loadData(tableModel);
    }
}