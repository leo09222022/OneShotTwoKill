package payment.gui;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Map;

import javax.swing.*;

import product.database.ProductVO;

public class PaymentGUI extends JFrame {

    public PaymentGUI(Map<ProductVO, Integer> cartMap) {
        setTitle("결제 화면!");
        setSize(300, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel itemListPanel = new JPanel();
        itemListPanel.setLayout(new BoxLayout(itemListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemListPanel);

        int totalPrice = 0;

        for (Map.Entry<ProductVO, Integer> entry : cartMap.entrySet()) {
            ProductVO product = entry.getKey();
            int quantity = entry.getValue();
            int price = product.getSalePrice() * quantity;
            totalPrice += price;

            JLabel label = new JLabel(product.getProductName() + " - " + quantity + "개 / " +
                    NumberFormat.getInstance().format(price) + "원");
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            itemListPanel.add(label);
        }

        JLabel totalLabel = new JLabel("총 금액: " + NumberFormat.getInstance().format(totalPrice) + "원", JLabel.CENTER);
        totalLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        add(scrollPane, BorderLayout.CENTER);
        add(totalLabel, BorderLayout.SOUTH);

        setVisible(true);
    }
}
