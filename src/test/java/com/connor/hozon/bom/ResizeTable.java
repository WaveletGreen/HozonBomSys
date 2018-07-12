package com.connor.hozon.bom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

class EvenOddRenderer implements TableCellRenderer {

    private List<Integer> columnIndex;
    private List<Integer> rowIndex;

    public EvenOddRenderer(List<Integer> columnIndex, List<Integer> rowIndex) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
    }

    public static final DefaultTableCellRenderer DEFAULT_RENDERER =
            new DefaultTableCellRenderer();

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        Component renderer =
                DEFAULT_RENDERER.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
        Color foreground, background;

//        if (row == 0 && column == 2) {
//            foreground = Color.red;
//            background = Color.BLUE;
//        } else {
//            foreground = Color.BLACK;
//            background = Color.WHITE;
//        }
        if (rowIndex.contains(row) && columnIndex.contains(column)) {
            foreground = Color.red;
            background = Color.BLUE;
        } else {
            foreground = Color.BLACK;
            background = Color.WHITE;
        }
        renderer.setForeground(foreground);
        renderer.setBackground(background);
        return renderer;
    }
}

public class ResizeTable {
    public static void main(String args[]) {

        final Object rowData[][] = {
                {"1", "one", "I"},
                {"2", "two", "II"},
                {"3", "three", "III"}};
        final String columnNames[] = {"#", "English", "Roman"};

        final JTable table = new JTable(rowData, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        List<Integer> rowIndex = new ArrayList<>();
        List<Integer> columnIndex = new ArrayList<>();

        rowIndex.add(1);
        rowIndex.add(2);

        columnIndex.add(0);
        columnIndex.add(0);

        table.setDefaultRenderer(Object.class, new EvenOddRenderer(rowIndex, columnIndex));

        JFrame frame = new JFrame("Resizing Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(scrollPane, BorderLayout.CENTER);

        frame.setSize(300, 150);
        frame.setVisible(true);

    }
}