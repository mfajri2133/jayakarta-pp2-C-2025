package view;

import controller.EmployeeController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class EmployeeView extends JFrame {

    private JTextField txtName, txtEmail, txtSearch;
    private JComboBox<String> cbPosition;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton btnSave, btnUpdate, btnDelete, btnClear;

    private EmployeeController controller = new EmployeeController();
    private int selectedId = -1;

    public EmployeeView() {
        setTitle("TOKO JAYAKARTA - PEGAWAI");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //  FORM PANEL
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Pegawai"));

        formPanel.add(new JLabel("Nama"));
        txtName = new JTextField();
        formPanel.add(txtName);

        formPanel.add(new JLabel("Jabatan"));
        cbPosition = new JComboBox<>();
        formPanel.add(cbPosition);

        formPanel.add(new JLabel("Email"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);

        // filler supaya grid rapi
        formPanel.add(new JLabel(""));
        formPanel.add(new JLabel(""));

        //  BUTTON PANEL
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnSave = new JButton("Simpan");
        btnSave.setBackground(new Color(46, 204, 113));
        btnSave.setForeground(Color.WHITE);

        btnUpdate = new JButton("Ubah");
        btnUpdate.setBackground(new Color(241, 196, 15));
        btnUpdate.setEnabled(false);

        btnDelete = new JButton("Hapus");
        btnDelete.setBackground(new Color(231, 76, 60));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setEnabled(false);

        btnClear = new JButton("Clear");
        btnClear.setBackground(new Color(189, 195, 199));

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
                new String[]{"No", "ID", "Nama", "Jabatan", "Email"}, 0
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
                BorderFactory.createTitledBorder("Daftar Pegawai")
        );

        add(scrollPane, BorderLayout.CENTER);

        //  LOAD DATA
        controller.loadPositions(cbPosition);
        controller.loadData(tableModel);

        //  EVENTS
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) return;

                selectedId = Integer.parseInt(
                        tableModel.getValueAt(row, 1).toString()
                );

                txtName.setText(tableModel.getValueAt(row, 2).toString());
                cbPosition.setSelectedItem(tableModel.getValueAt(row, 3).toString());
                txtEmail.setText(tableModel.getValueAt(row, 4).toString());

                btnSave.setEnabled(false);
                btnUpdate.setEnabled(true);
                btnDelete.setEnabled(true);
            }
        });

        btnSave.addActionListener(e -> {
            controller.insert(
                    txtName.getText(),
                    cbPosition.getSelectedItem().toString(),
                    txtEmail.getText(),
                    tableModel
            );
            resetForm();
        });

        btnUpdate.addActionListener(e -> {
            controller.update(
                    selectedId,
                    txtName.getText(),
                    cbPosition.getSelectedItem().toString(),
                    txtEmail.getText(),
                    tableModel
            );
            resetForm();
        });

        btnDelete.addActionListener(e -> {
            controller.delete(selectedId, tableModel);
            resetForm();
        });

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                controller.search(txtSearch.getText(), tableModel);
            }
        });

        btnClear.addActionListener(e -> resetForm());
    }

    private void resetForm() {
        txtName.setText("");
        txtEmail.setText("");
        txtSearch.setText("");
        selectedId = -1;

        btnSave.setEnabled(true);
        btnUpdate.setEnabled(false);
        btnDelete.setEnabled(false);

        table.clearSelection();
        controller.loadData(tableModel);
    }
}