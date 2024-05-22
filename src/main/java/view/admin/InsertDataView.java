package view.admin;

import dao.admin.AdminSqlInputDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InsertDataView extends JFrame {

    private JPanel inputPanel;
    private JTextField[] inputFields;
    private String selectedTable;

    public InsertDataView() {
        setTitle("데이터 입력");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        String[] tables = {"movie", "screen", "screening_schedule", "seat", "member", "reservation", "ticket"};
        JComboBox<String> tableSelector = new JComboBox<>(tables);
        tableSelector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectedTable = (String) tableSelector.getSelectedItem();
                setupInputFields(selectedTable);
            }
        });

        inputPanel = new JPanel(new GridLayout(0, 2));
        selectedTable = tables[0];
        setupInputFields(selectedTable);

        JButton insertButton = new JButton("입력");
        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertData();
            }
        });

        add(tableSelector, BorderLayout.NORTH);
        add(new JScrollPane(inputPanel), BorderLayout.CENTER);
        add(insertButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void setupInputFields(String table) {
        inputPanel.removeAll();

        String[] fieldNames;
        switch (table) {
            case "movie":
                fieldNames = new String[]{"Title", "Running Time", "Director", "Actor", "Genre", "Description", "Release Date", "Rating", "Age Limit"};
                break;
            case "screen":
                fieldNames = new String[]{"Seat Count", "Is Used", "Column Seats", "Row Seats"};
                break;
            case "screening_schedule":
                fieldNames = new String[]{"Start Date", "Day of Week", "Round", "Start Time", "Movie ID", "Screen ID", "Selling Price", "Standard Price"};
                break;
            case "seat":
                fieldNames = new String[]{"Is Used", "Row Number", "Column Number", "Screen ID"};
                break;
            case "member":
                fieldNames = new String[]{"Name", "Phone Number", "Email"};
                break;
            case "reservation":
                fieldNames = new String[]{"Payment Method", "Payment Status", "Payment Amount", "Payment Date", "Member ID"};
                break;
            case "ticket":
                fieldNames = new String[]{"Is Ticketed", "Screening Schedule ID", "Seat ID", "Reservation ID", "Screen ID"};
                break;
            default:
                fieldNames = new String[]{};
                break;
        }

        inputFields = new JTextField[fieldNames.length];
        for (int i = 0; i < fieldNames.length; i++) {
            JLabel label = new JLabel(fieldNames[i] + ":");
            JTextField textField = new JTextField();
            inputPanel.add(label);
            inputPanel.add(textField);
            inputFields[i] = textField;
        }

        inputPanel.revalidate();
        inputPanel.repaint();
    }

    private void insertData() {
        StringBuilder sql = new StringBuilder("INSERT INTO " + selectedTable + " (");
        StringBuilder values = new StringBuilder(" VALUES (");

        String[] fieldNames = getFieldNames(selectedTable);

        for (int i = 0; i < inputFields.length; i++) {
            String value = inputFields[i].getText();
            if (value.isEmpty()) {
                JOptionPane.showMessageDialog(this, "모든 필드를 입력하세요", "오류", JOptionPane.ERROR_MESSAGE);
                return;
            }
            sql.append(fieldNames[i]).append(", ");
            values.append("'").append(value).append("', ");
        }

        sql.setLength(sql.length() - 2); // 마지막 콤마 제거
        values.setLength(values.length() - 2); // 마지막 콤마 제거
        sql.append(")").append(values).append(")");

        AdminSqlInputDao dao = new AdminSqlInputDao();
        boolean success = dao.sqlInput(sql.toString(), inputPanel);

        if (success) {
            JOptionPane.showMessageDialog(this, "데이터가 성공적으로 삽입되었습니다", "성공", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "잘못된 데이터 값입니다", "오류", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String[] getFieldNames(String table) {
        switch (table) {
            case "movie":
                return new String[]{"title", "running_time", "director", "actor", "genre", "description", "release_date", "rating", "age_limit"};
            case "screen":
                return new String[]{"seat_count", "is_used", "column_seats", "row_seats"};
            case "screening_schedule":
                return new String[]{"start_date", "day_of_week", "round", "start_time", "movie_id", "screen_id", "selling_price", "standard_price"};
            case "seat":
                return new String[]{"is_used", "row_num", "column_num", "screen_id"};
            case "member":
                return new String[]{"name", "phone_number", "email"};
            case "reservation":
                return new String[]{"payment_method", "payment_status", "payment_amount", "payment_date", "member_id"};
            case "ticket":
                return new String[]{"is_ticketed", "screening_schedule_id", "seat_id", "reservation_id", "screen_id"};
            default:
                return new String[]{};
        }
    }
}
