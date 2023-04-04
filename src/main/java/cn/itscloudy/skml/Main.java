package cn.itscloudy.skml;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        int height = 50;

        frame.setBounds(500, 500, 500, height);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // >>>
        // FOCUS BUTTON
        JLabel focusLabel = new JLabel();
        focusLabel.setBackground(Color.BLUE);
        focusLabel.setOpaque(true);
        focusLabel.setFont(new Font("Default", Font.BOLD, 12));
        focusLabel.setBounds(0, 0, 10, height);
        focusLabel.setBackground(Color.RED);
        // KEY STATUS
        Font labelFont = new Font("Default", Font.PLAIN, 14);
        Font borderFont = new Font("Default", Font.PLAIN, 12);

        JLabel keyStatus = new JLabel();
        keyStatus.setBackground(new Color(200, 200, 110));
        keyStatus.setFont(labelFont);
        keyStatus.setOpaque(true);
        keyStatus.setBounds(10, 0, 330, height);
        LineBorder keyBoard = new LineBorder(Color.YELLOW, 1);
        TitledBorder keyTitle = new TitledBorder(keyBoard, "Key Input");
        keyTitle.setTitleFont(borderFont);
        keyTitle.setTitleColor(Color.DARK_GRAY);
        keyStatus.setBorder(keyTitle);
        // MOUSE STATUS
        JLabel mouseStatus = new JLabel();
        mouseStatus.setBackground(new Color(110, 200, 200));
        mouseStatus.setFont(labelFont);
        mouseStatus.setOpaque(true);
        mouseStatus.setBounds(340, 0, 110, height);
        LineBorder mouseBoard = new LineBorder(Color.CYAN, 1);
        TitledBorder mouseTitle = new TitledBorder(mouseBoard, "Mouse Input");
        mouseTitle.setTitleFont(borderFont);
        mouseTitle.setTitleColor(Color.DARK_GRAY);
        mouseStatus.setBorder(mouseTitle);
        // Focus
        FocusAdapter focusListener = new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                focusLabel.setBackground(Color.GREEN);
            }

            @Override
            public void focusLost(FocusEvent e) {
                focusLabel.setBackground(Color.RED);
            }
        };
        KeyAdapter keyListener = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                StringBuilder sb = new StringBuilder("<html>");
                appendModifiers(e, sb);
                char keyChar = e.getKeyChar();
                sb.append(grayKey(" CO:")).append(e.getKeyCode())
                        .append(grayKey(" CH:")).append((keyChar >= 0x20 && keyChar <= 0x7E) ?
                                keyChar : htmlStyle("?", "color:orange"))
                        .append(grayKey(" ICH:")).append((int) keyChar)
                        .append(grayKey(" L:")).append(e.getKeyLocation())
                        .append(grayKey(" ECO:")).append(e.getExtendedKeyCode());
                sb.append("</html>");
                keyStatus.setText(sb.toString());
            }
        };

        MouseAdapter mouseListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                StringBuilder sb = new StringBuilder("<html>");
                appendModifiers(e, sb);
                int button = e.getButton();
                sb.append(grayKey(" BT:")).append(button);
                sb.append("</html>");
                mouseStatus.setText(sb.toString());
            }
        };

        // CLOSE BUTTON
        JButton closeButton = new JButton("×");
        closeButton.setBorder(null);
        closeButton.setBackground(Color.LIGHT_GRAY);
        closeButton.setOpaque(true);
        closeButton.setFont(new Font("Default", Font.BOLD, 30));
        closeButton.setBounds(450, 0, 50, height);
        closeButton.addActionListener(actionEvent -> System.exit(0));
        closeButton.setToolTipText("Exit this program.");
        closeButton.addFocusListener(focusListener);
        closeButton.addKeyListener(keyListener);
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(Color.LIGHT_GRAY);
            }
        });

        focusLabel.addMouseListener(mouseListener);
        keyStatus.addMouseListener(mouseListener);
        mouseStatus.addMouseListener(mouseListener);
        // <<<<
        // Drag
        Dragger.drag(frame, focusLabel, keyStatus, mouseStatus);
        // FRAME
        frame.add(focusLabel);
        frame.add(keyStatus);
        frame.add(mouseStatus);
        frame.add(closeButton);
        frame.setVisible(true);
    }

    private static String grayKey(String key) {
        return htmlStyle(key, "color:#555555");
    }
    private static String htmlStyle(String key, String style) {
        return "<span style=\"" + style + "\">" + key + "</span>";
    }

    private static void appendModifiers(InputEvent e, StringBuilder sb) {
        int i = sb.length();
        if (e.isMetaDown()) {
            sb.append("M");
        }
        if (e.isShiftDown()) {
            sb.append("S");
        }
        if (e.isControlDown()) {
            sb.append("C");
        }
        if (e.isAltDown()) {
            sb.append("A");
        }
        if (e.isAltGraphDown()) {
            sb.append("G");
        }
        if (sb.length() > 0) {
            sb.insert(i, "<span style=\"color:red\">");
            sb.append("</span>");
        }
    }
}
