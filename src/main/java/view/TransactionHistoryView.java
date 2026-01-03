package view;

import controller.TransactionHistoryController;
import util.PdfExporter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TransactionHistoryView extends JFrame {

    private JTable tableHeader, tableDetail;
    private DefaultTableModel headerModel, detailModel;
    private JButton btnExport;

    private TransactionHistoryController controller =
            new TransactionHistoryController();

    public TransactionHistoryView() {
        setTitle("TOKO JAYAKARTA - RIWAYAT TRANSAKSI");
        setSize(850, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //  HEADER TABLE
        headerModel = new DefaultTableModel(
                new String[]{"No", "ID", "Kasir", "Tanggal", "Total"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tableHeader = new JTable(headerModel);
        tableHeader.getColumnModel().getColumn(1).setMinWidth(0);
        tableHeader.getColumnModel().getColumn(1).setMaxWidth(0);
        tableHeader.getColumnModel().getColumn(1).setWidth(0);

        JScrollPane spHeader = new JScrollPane(tableHeader);
        spHeader.setBorder(
                BorderFactory.createTitledBorder("Daftar Transaksi")
        );

        //  DETAIL TABLE
        detailModel = new DefaultTableModel(
                new String[]{"No", "Produk", "Qty", "Harga", "Subtotal"}, 0
        ) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        tableDetail = new JTable(detailModel);
        JScrollPane spDetail = new JScrollPane(tableDetail);
        spDetail.setBorder(
                BorderFactory.createTitledBorder("Detail Transaksi")
        );

        JSplitPane splitPane = new JSplitPane(
                JSplitPane.VERTICAL_SPLIT,
                spHeader,
                spDetail
        );
        splitPane.setResizeWeight(0.4);
        splitPane.setDividerSize(6);
        splitPane.setContinuousLayout(true);

        add(splitPane, BorderLayout.CENTER);

        //  PANEL AKSI
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        btnExport = new JButton("Cetak Struk");
        btnExport.setBackground(new Color(155, 89, 182));
        btnExport.setForeground(Color.WHITE);

        actionPanel.add(btnExport);
        add(actionPanel, BorderLayout.SOUTH);

        //  LOAD DATA
        controller.loadTransactions(headerModel);

        //  EVENTS
        tableHeader.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tableHeader.getSelectedRow();
                if (row == -1) return;

                int transactionId =
                        Integer.parseInt(headerModel.getValueAt(row, 1).toString());
                controller.loadDetails(transactionId, detailModel);
            }
        });

        btnExport.addActionListener(e -> {
            int selectedRow = tableHeader.getSelectedRow();

            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(
                        TransactionHistoryView.this,
                        "Pilih transaksi terlebih dahulu!"
                );
                return;
            }

            String cashier = headerModel.getValueAt(selectedRow, 2).toString();
            String tanggal = headerModel.getValueAt(selectedRow, 3).toString();

            PdfExporter.exportReceipt(
                    cashier,
                    tanggal,
                    tableDetail,
                    "Struk_Transaksi"
            );
        });
    }
}