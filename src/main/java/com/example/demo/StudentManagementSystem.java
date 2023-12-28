package com.example.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.util.HashMap;
import java.util.Map;

public class StudentManagementSystem extends JFrame {

    private Map<String, Map<String, String>> studentData;

    private JTextArea displayArea;
    private JTextField nameField;
    private JComboBox<String> courseComboBox;
    private JComboBox<String> gradeComboBox;

    public StudentManagementSystem() {
        super("Student Management System");

        studentData = new HashMap<>();

        // Create components
        displayArea = new JTextArea(10, 30);
        nameField = new JTextField(15);
        courseComboBox = new JComboBox<>(new String[]{"Math", "Physics", "Computer Science"});
        gradeComboBox = new JComboBox<>(new String[]{"A", "B", "C", "D", "F"});

        JButton registerButton = new JButton("Register");
        JButton enrollButton = new JButton("Enroll");
        JButton saveGradeButton = new JButton("Save Grade");
        JButton displayDetailsButton = new JButton("Display Details");
        JButton updateDetailsButton = new JButton("Update Details");
        JButton printButton = new JButton("Print");

        // Layout components
        setLayout(new FlowLayout());
        add(new JLabel("Name: "));
        add(nameField);
        add(new JLabel("Course: "));
        add(courseComboBox);
        add(new JLabel("Grade: "));
        add(gradeComboBox);
        add(registerButton);
        add(enrollButton);
        add(saveGradeButton);
        add(displayDetailsButton);
        add(updateDetailsButton);
        add(printButton);
        add(new JScrollPane(displayArea));

        // Event handling
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerStudent();
            }
        });

        enrollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enrollStudent();
            }
        });

        saveGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveGrade();
            }
        });

        displayDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayDetails();
            }
        });

        updateDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateDetails();
            }
        });

        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printContent();
            }
        });

        // Frame settings
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setVisible(true);
    }

    private void registerStudent() {
        String studentName = nameField.getText();
        // Format the name to capitalize the first letter of each word
        studentName = capitalizeEachWord(studentName);
        if (!studentData.containsKey(studentName)) {
            studentData.put(studentName, new HashMap<>());
            displayArea.append("Student registered: " + studentName + "\n");
        } else {
            displayArea.append("Student already registered: " + studentName + "\n");
        }
    }

    private void enrollStudent() {
        String studentName = nameField.getText();
        // Format the name to capitalize the first letter of each word
        studentName = capitalizeEachWord(studentName);
        String selectedCourse = (String) courseComboBox.getSelectedItem();

        if (studentData.containsKey(studentName)) {
            studentData.get(studentName).put(selectedCourse, null);
            displayArea.append(studentName + " enrolled in " + selectedCourse + "\n");
        } else {
            displayArea.append("Student not registered: " + studentName + "\n");
        }
    }

    private void saveGrade() {
        String studentName = nameField.getText();
        // Format the name to capitalize the first letter of each word
        studentName = capitalizeEachWord(studentName);
        String selectedCourse = (String) courseComboBox.getSelectedItem();
        String selectedGrade = (String) gradeComboBox.getSelectedItem();

        if (studentData.containsKey(studentName) && studentData.get(studentName).containsKey(selectedCourse)) {
            studentData.get(studentName).put(selectedCourse, selectedGrade);
            displayArea.append("Grade saved for " + studentName + " in " + selectedCourse + ": " + selectedGrade + "\n");
        } else {
            displayArea.append("Invalid operation. Ensure student is registered and enrolled in the selected course.\n");
        }
    }

    private void displayDetails() {
        String studentName = nameField.getText();
        // Format the name to capitalize the first letter of each word
        studentName = capitalizeEachWord(studentName);

        if (studentData.containsKey(studentName)) {
            displayArea.append("Student Details for " + studentName + ":\n");
            displayArea.append("Enrolled Courses: " + studentData.get(studentName).keySet().toString() + "\n");
            displayArea.append("Grades:\n");
            for (Map.Entry<String, String> entry : studentData.get(studentName).entrySet()) {
                displayArea.append(entry.getKey() + ": " + entry.getValue() + "\n");
            }
        } else {
            displayArea.append("Student not registered: " + studentName + "\n");
        }
    }

    private void updateDetails() {
        String studentName = nameField.getText();
        // Format the name to capitalize the first letter of each word
        studentName = capitalizeEachWord(studentName);

        if (studentData.containsKey(studentName)) {
            studentData.remove(studentName);
            registerStudent(); // Re-register the student with the corrected details
            displayArea.append("Details updated for " + studentName + "\n");
        } else {
            displayArea.append("Student not found: " + studentName + "\n");
        }
    }

    private void printContent() {
        try {
            displayArea.print();
        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }

    private String capitalizeEachWord(String str) {
        StringBuilder result = new StringBuilder();
        String[] words = str.split("\\s");
        for (String word : words) {
            result.append(word.substring(0, 1).toUpperCase()).append(word.substring(1).toLowerCase()).append(" ");
        }
        return result.toString().trim();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StudentManagementSystem();
            }
        });
    }
}