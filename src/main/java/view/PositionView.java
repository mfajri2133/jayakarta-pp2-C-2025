package view;

import controller.PositionController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PositionView extends JFrame {

    private JTextField txtName, txtSearch;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnSave, btnUpdate, btnDelete, btnClear;

    private PositionController controller = new PositionController();
    private int selectedId = -1;

    public PositionView() {
        setTitle("TOKO JAYAKARTA - JABATAN");
        setSize(600, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //  FORM PANEL 
        JPanel formPanel = new JPanel(new GridLayout(1, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Jabatan"));

        formPanel.add(new JLabel("Nama Jabatan"));
        txtName = new JTextField();
        formPanel.add(txtName);

        add(formPanel, BorderLayout.NORTH);

        //  BUTTON PANEL 
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnSave = new JButton("Simpan");
        btnSave.setBackground(new Color(46, 204, 113)); // hijau
        btnSave.setForeground(Color.WHITE);

        btnUpdate = new JButton("Ubah");
        btnUpdate.setBackground(new Color(241, 196, 15)); // kuning
        btnUpdate.setForeground(Color.BLACK);
        btnUpdate.setEnabled(false);

        btnDelete = new JButton("Hapus");
        btnDelete.setBackground(new Color(231, 76, 60)); // merah
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setEnabled(false);

        btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(189, 195, 199)); // abu
        btnClear.setForeground(Color.BLACK);

        buttonPanel.add(btnSave);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);

        //  SEARCH PANEL 
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Pencarian"));

        searchPanel.add(new JLabel("Cari:"), BorderLayout.WEST);
        txtSearch = new JTextField();
        searchPanel.add(txtSearch, BorderLayout.CENTER);

        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.add(formPanel, BorderLayout.NORTH);
        topPanel.add(buttonPanel, BorderLayout.CENTER);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        //  TABLE 
        tableModel = new DefaultTableModel(
                new String[]{"No", "ID", "Nama Jabatan"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        table = new JTable(tableModel);
        table.getColumnModel().getColumn(1).setMinWidth(0);
        table.getColumnModel().getColumn(1).setMaxWidth(0);
        table.getColumnModel().getColumn(1).setWidth(0);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(
                BorderFactory.createTitledBorder("Daftar Jabatan")
        );

        add(scrollPane, BorderLayout.CENTER);

        //  LOAD DATA 
        controller.loadData(tableModel);

        //  EVENTS 
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) return;

                selectedId = Integer.parseInt(
                        tableModel.getValueAt(row, 1).toString()
                );
                txtName.setText(
                        tableModel.getValueAt(row, 2).toString()
                );

                btnSave.setEnabled(false);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        });

        btnSave.addActionListener(e -> {
            String name = txtName.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Nama jabatan tidak boleh kosong!"
                );
                return;
            }

            controller.insert(name, tableModel);
            resetForm();
        });

        btnUpdate.addActionListener(e -> {
            String name = txtName.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Nama jabatan baru harus diisi!"
                );
                return;
            }

            controller.update(selectedId, name, tableModel);
            resetForm();
        });

        btnDelete.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Hapus data ini?",
                    "Konfirmasi",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                controller.delete(selectedId, tableModel);
                resetForm();
            }
        });

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                controller.search(txtSearch.getText(), tableModel);
            }
        });

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