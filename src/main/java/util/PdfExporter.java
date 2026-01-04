package util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PdfExporter {

    public static void exportReceipt(
            String cashierName,
            String transactionDate,
            JTable table,
            String fileName
    ) {
        try {
            // === Ukuran kecil seperti struk ===
            Rectangle receiptSize = new Rectangle(300, 600);
            Document document = new Document(receiptSize, 10, 10, 10, 10);

            PdfWriter.getInstance(
                    document,
                    new FileOutputStream(fileName + ".pdf")
            );

            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 9);
            Font boldFont = new Font(Font.FontFamily.HELVETICA, 9, Font.BOLD);

            // === HEADER TOKO ===
            Paragraph store = new Paragraph("TOKO JAYAKARTA\n", titleFont);
            store.setAlignment(Element.ALIGN_CENTER);
            document.add(store);

            Paragraph addr = new Paragraph(
                    "Jl. Setiabudhi No. 123\n\n",
                    normalFont
            );
            addr.setAlignment(Element.ALIGN_CENTER);
            document.add(addr);

            // === INFO TRANSAKSI ===
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            document.add(new Paragraph("Kasir   : " + cashierName, normalFont));
            document.add(new Paragraph("Tanggal : " + sdf.format(new Date()), normalFont));
            document.add(new Paragraph("-----------------------------------", normalFont));

            // === TABEL ITEM ===
            PdfPTable pdfTable = new PdfPTable(4);
            pdfTable.setWidths(new float[]{3, 1, 2, 2});
            pdfTable.setWidthPercentage(100);

            addCell(pdfTable, "Produk", boldFont);
            addCell(pdfTable, "Qty", boldFont);
            addCell(pdfTable, "Harga", boldFont);
            addCell(pdfTable, "Sub", boldFont);

            double grandTotal = 0;

            DefaultTableModel model = (DefaultTableModel) table.getModel();

            for (int i = 0; i < model.getRowCount(); i++) {
                String product = model.getValueAt(i, 1).toString();     // Produk
                int qty = Integer.parseInt(model.getValueAt(i, 2).toString()); // Qty
                double price = Double.parseDouble(model.getValueAt(i, 3).toString()); // Harga
                double subtotal = Double.parseDouble(model.getValueAt(i, 4).toString()); // Subtotal

                grandTotal += subtotal;

                addCell(pdfTable, product, normalFont);
                addCell(pdfTable, String.valueOf(qty), normalFont);
                addCell(pdfTable, format(price), normalFont);
                addCell(pdfTable, format(subtotal), normalFont);
            }


            document.add(pdfTable);
            document.add(new Paragraph("-----------------------------------", normalFont));

            // === TOTAL ===
            Paragraph total = new Paragraph(
                    "TOTAL : " + format(grandTotal),
                    boldFont
            );
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            document.add(new Paragraph("\nTerima kasih :)", normalFont));
            document.close();

            JOptionPane.showMessageDialog(null, "Struk berhasil dibuat!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    // === Helper Cell ===
    private static void addCell(PdfPTable table, String text, Font font) {
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);
    }

    private static String format(double value) {
        return String.format("Rp %, .0f", value).replace(",", ".");
    }
}