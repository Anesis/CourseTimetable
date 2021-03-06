package Utility;

import Main.Courses;
import Main.SettingsFrame;
import sun.swing.DefaultLookup;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CustomTableCellRenderer extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel c = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        c.setForeground(table.getForeground());
        c.setBackground(table.getBackground());

        if (c.getText().equals("CONFILT!")) {
            c.setBackground(Color.RED);
        }

        Color fg = null;
        Color bg = null;

        JTable.DropLocation dropLocation = table.getDropLocation();
        if (dropLocation != null
                && !dropLocation.isInsertRow()
                && !dropLocation.isInsertColumn()
                && dropLocation.getRow() == row
                && dropLocation.getColumn() == column) {

            fg = DefaultLookup.getColor(this, ui, "Table.dropCellForeground");
            bg = DefaultLookup.getColor(this, ui, "Table.dropCellBackground");
            isSelected = true;
        }

        if (isSelected) {
            c.setForeground(fg == null ? table.getSelectionForeground() : fg);
            c.setBackground(bg == null ? table.getSelectionBackground() : bg);
        } else {
            Color unselectedBackground = c.getBackground();
            Color background = unselectedBackground != null
                    ? unselectedBackground
                    : table.getBackground();
            if (background == null || background instanceof javax.swing.plaf.UIResource) {
                Color alternateColor = DefaultLookup.getColor(this, ui, "Table.alternateRowColor");
                if (alternateColor != null && row % 2 != 0) {
                    background = alternateColor;
                }
            }
            Color unselectedForeground = c.getForeground();
            c.setForeground(unselectedForeground != null
                    ? unselectedForeground
                    : table.getForeground());
            c.setBackground(background);
        }

        setFont(table.getFont());

        if (hasFocus) {
            Border border = null;
            if (isSelected) {
                border = DefaultLookup.getBorder(this, ui, "Table.focusSelectedCellHighlightBorder");
            }
            if (border == null) {
                border = DefaultLookup.getBorder(this, ui, "Table.focusCellHighlightBorder");
            }
            setBorder(border);

            if (!isSelected && table.isCellEditable(row, column)) {
                Color col;
                col = DefaultLookup.getColor(this, ui, "Table.focusCellForeground");
                if (col != null) {
                    c.setForeground(col);
                }
                col = DefaultLookup.getColor(this, ui, "Table.focusCellBackground");
                if (col != null) {
                    c.setBackground(col);
                }
            }
        } else {
            setBorder(c.getBorder());
        }

        for (int i = 0; i < Courses.getCourseList().size(); i++) {
            Courses course = Courses.getCourseList().get(i);
            if (course.getName() != null && course.getDuration() != null && course.getDay() != null && course.getStartTime() != null) {
                int numOfTilesCovered = Courses.getDurationNum(course.getDuration());
                if (value.equals(course.getIdentifier(course, "name"))) {
                    c.setText(course.getName());
                    if (numOfTilesCovered == 0) {
                        c.setBackground(Courses.getCourseList().get(i).getColor());
                        c.setForeground(SettingsFrame.getContrastColor(Courses.getCourseList().get(i).getColor()));
                        c.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
                        c.setFont(new Font(Font.SERIF, Font.BOLD, 15));
                    } else if (numOfTilesCovered == 1 || numOfTilesCovered == 2) {
                        c.setBackground(Courses.getCourseList().get(i).getColor());
                        c.setForeground(SettingsFrame.getContrastColor(Courses.getCourseList().get(i).getColor()));
                        c.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
                        c.setFont(new Font(Font.SERIF, Font.BOLD, 15));
                    } else if (numOfTilesCovered >= 3) {
                        c.setBackground(Courses.getCourseList().get(i).getColor());
                        c.setForeground(SettingsFrame.getContrastColor(Courses.getCourseList().get(i).getColor()));
                        c.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
                        c.setFont(new Font(Font.SERIF, Font.BOLD, 15));
                    }
                } else if (value.equals(course.getIdentifier(course, "loc")) || value.equals(course.getIdentifier(course, "type")) || value.equals(course.getIdentifier(course, "type_loc"))) {
                    if (value.equals(course.getIdentifier(course, "loc"))) {
                        c.setText(" [" + course.getLocation() + "]");
                    } else if (value.equals(course.getIdentifier(course, "type"))) {
                        c.setText(course.getType());
                    } else if (value.equals(course.getIdentifier(course, "type_loc"))) {
                        c.setText(course.getType() + " [" + course.getLocation() + "]");
                    }
                    if (numOfTilesCovered == 1) {
                        c.setBackground(Courses.getCourseList().get(i).getColor());
                        c.setForeground(SettingsFrame.getContrastColor(Courses.getCourseList().get(i).getColor()));
                        c.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
                        c.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
                    } else if (numOfTilesCovered >= 2) {
                        c.setBackground(Courses.getCourseList().get(i).getColor());
                        c.setForeground(SettingsFrame.getContrastColor(Courses.getCourseList().get(i).getColor()));
                        c.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
                        c.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
                    }
                } else if (value.equals(course.getIdentifier(course, "sec")) || value.equals(course.getIdentifier(course, "cre")) || value.equals(course.getIdentifier(course, "sec_cre"))) {
                    if (value.equals(course.getIdentifier(course, "sec"))) {
                        c.setText("Section " + course.getSection());
                    } else if (value.equals(course.getIdentifier(course, "cre"))) {
                        if (course.getCredits() == 1) {
                            c.setText("Credit " + course.getCredits());
                        } else {
                            c.setText("Credits " + course.getCredits());
                        }
                    } else if (value.equals(course.getIdentifier(course, "sec_cre"))) {
                        c.setText("Section " + course.getSection() + "  " + course.getCredits());
                    }


                    if (numOfTilesCovered == 2 || numOfTilesCovered == 3) {
                        c.setBackground(Courses.getCourseList().get(i).getColor());
                        c.setForeground(SettingsFrame.getContrastColor(Courses.getCourseList().get(i).getColor()));
                        c.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
                        c.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
                    } else if (numOfTilesCovered >= 4) {
                        c.setBackground(Courses.getCourseList().get(i).getColor());
                        c.setForeground(SettingsFrame.getContrastColor(Courses.getCourseList().get(i).getColor()));
                        c.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
                        c.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
                    }
                } else if (value.equals(course.getIdentifier(course, "top"))) {
                    c.setBackground(Courses.getCourseList().get(i).getColor());
                    c.setForeground(Courses.getCourseList().get(i).getColor());
                    c.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK));
                    c.setText("");
                    c.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 0));
                } else if (value.equals(course.getIdentifier(course, "bot"))) {
                    c.setBackground(Courses.getCourseList().get(i).getColor());
                    c.setForeground(Courses.getCourseList().get(i).getColor());
                    c.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, Color.BLACK));
                    c.setText("");
                    c.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 0));
                } else if (value.equals(course.getIdentifier(course, "mid"))) {
                    c.setBackground(Courses.getCourseList().get(i).getColor());
                    c.setForeground(Courses.getCourseList().get(i).getColor());
                    c.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 1, Color.BLACK));
                    c.setText("");
                    c.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 0));
                }
            }
        }
        return c;
    }
}
