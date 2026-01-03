package view;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("TOKO JAYAKARTA - MAIN MENU");
        setSize(450, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        //  TITLE
        JLabel lblTitle = new JLabel("TOKO JAYAKARTA", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        add(lblTitle, BorderLayout.NORTH);

        //  MENU PANEL
        JPanel menuPanel = new JPanel(new GridLayout(6, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 80, 20, 80));

        JButton btnCategory = createButton("Manajemen Kategori");
        JButton btnPosition = createButton("Manajemen Jabatan");
        JButton btnProduct = createButton("Manajemen Produk");
        JButton btnEmployee = createButton("Manajemen Pegawai");
        JButton btnTransaction = createButton("Transaksi");
        JButton btnHistory = createButton("Riwayat Transaksi");

        menuPanel.add(btnCategory);
        menuPanel.add(btnPosition);
        menuPanel.add(btnProduct);
        menuPanel.add(btnEmployee);
        menuPanel.add(btnTransaction);
        menuPanel.add(btnHistory);

        add(menuPanel, BorderLayout.CENTER);

        //  ACTION
        btnCategory.addActionListener(e -> new CategoryView().setVisible(true));
        btnPosition.addActionListener(e -> new PositionView().setVisible(true));
        btnProduct.addActionListener(e -> new ProductView().setVisible(true));
        btnEmployee.addActionListener(e -> new EmployeeView().setVisible(true));
        btnTransaction.addActionListener(e -> new TransactionView().setVisible(true));
        btnHistory.addActionListener(e -> new TransactionHistoryView().setVisible(true));
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(52, 152, 219));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}