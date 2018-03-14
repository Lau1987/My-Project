/** Author: Laudro E Pineda
 * CMIS 242 Intermediate Programming
 * Professor Dinsoreanu
 * Purpose: This program will compile to manage a student database and handle variables
 *that will be used to compute a GPA.
 */


package program;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashMap;



public class StudentDatabase extends JFrame {
    static final int WINDOWWIDTH = 300, WINDOWHEIGHT = 200;
    private static final String[] LETTER_GRADES =
            { "A", "B", "C", "D", "F"};
    private static Integer[] CREDS = { 1, 3, 4, 6 };

    private static HashMap<String, Student> studentMajorGradeMap =
            new HashMap<String, Student>();
    private static JTextField fieldForID = new JTextField();
    private static JTextField fieldForName = new JTextField();
    private static JTextField fieldForMajor = new JTextField();
    private static JComboBox fieldForChooseSelection = new JComboBox();
    private static JButton buttonForProcessRequest = new JButton(
            "Process Request");
    private static JOptionPane frame = new JOptionPane();
    private static JLabel labelForID = new JLabel("Id: ");
    private static JLabel labelForName = new JLabel("Name: ");
    private static JLabel labelForMajor = new JLabel("Major: ");
    private static JLabel labelForChooseSelection = new JLabel(
            "Choose Selection: ");

    private void setFrame(int width, int height) {
        setSize(width, height);
        setLocationRelativeTo(null);
    }

    public void display() {
        setVisible(true);
    }

    public StudentDatabase() {
        super("Project 4");

        JPanel mainPanel = createMainFrame();
        addPanels(mainPanel);
        createFieldForChooseSelection(mainPanel);
        createProcessRequestButton(mainPanel);
    }

    private void addPanels(JPanel mainPanel) {
        JComponent[] components = { labelForID, fieldForID,
                labelForName, fieldForName, labelForMajor,
                fieldForMajor,
                labelForChooseSelection };

        addToPanel(mainPanel, components);
    }

    private void addToPanel(JPanel p, JComponent[] c) {
        for (JComponent component : c)
            p.add(component);
    }

    private void addItemsToField(JComboBox field, String[] items) {
        for (String item : items)
            field.addItem(item);
    }

    // Create the choose selection
    private void createFieldForChooseSelection(JPanel mainPanel) {
        mainPanel.add(fieldForChooseSelection);

        String[] items = { "Insert", "Delete", "Find", "Update" };
        addItemsToField(fieldForChooseSelection, items);
    }

    private void createProcessRequestButton(JPanel mainPanel) {
        // Action Listener for process button.
        mainPanel.add(buttonForProcessRequest);
        buttonForProcessRequest
                .addActionListener(new
                        buttonForProcessRequestListener());
    }

    private JPanel createMainFrame() {
        setFrame(WINDOWWIDTH, WINDOWHEIGHT);
        setResizable(false);
        JPanel mainPanel = new JPanel();
        add(mainPanel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.setLayout(new GridLayout(5, 5, 10, 10));
        return mainPanel;
    }

    class buttonForProcessRequestListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Object selection =
                    fieldForChooseSelection.getSelectedItem();

            if (selection.equals("Insert"))
                insertPerformed();

            if (selection.equals("Delete"))
                deletePerformed();

            if (selection.equals("Find"))
                findPerformed();

            if (selection.equals("Update"))
                updatePerformed();
        }

        private void preventBlankFields(JTextField[] requiredFields) {
            for (JTextField field : requiredFields)
                preventBlankField(field);
        }

        private void updatePerformed() {
            // Always prevent these fields from being blank
            preventBlankFields(new JTextField[]
                    { fieldForID, fieldForName,
                            fieldForMajor });
            String id = fieldForID.getText();
            Student student = studentMajorGradeMap.get(id);
            if (student == null) {
                showMessage("Entry not found in database!");
                return;
            }

            if (!studentInfoMatchesInput(student)) {
                JOptionPane.showMessageDialog(frame,
                        "ID/information mismatch!");
                return;
            }

            String grade = askForGrade(LETTER_GRADES);
            Integer cred = askForCred(CREDS);

            if (grade == null || cred == null) {
                showMessage("Cancelled.");
            } else {
                Integer gradeAsNumber = gradeStringToInt(grade);
                student.courseCompleted(cred, gradeAsNumber);
                showMessage("Success!");
            }
        }

        private boolean studentInfoMatchesInput(Student student) {
            String name = fieldForName.getText();
            String major = fieldForMajor.getText();
            boolean nameMatches =  student.getName().equals(name);
            boolean majorMatches = student.getMajor().equals(major);
            boolean studentInfoMatchesInput =
                    (nameMatches && majorMatches);
            return studentInfoMatchesInput;
        }

        private Integer askForCred(Integer[] creds) {
            return (Integer) JOptionPane.showInputDialog(null,
                    "Choose " + "Credit", "Credit",
                    JOptionPane.QUESTION_MESSAGE, null,
                    creds, creds[0]);
        }

        private String askForGrade(String[] letterGrade) {
            return (String) JOptionPane.showInputDialog(frame,
                    "Choose Grade", "Grade",
                    JOptionPane.QUESTION_MESSAGE, null, letterGrade,
                    letterGrade[0]);
        }

        private Integer gradeStringToInt(String grade) {
            return Arrays.asList("F" + "D", "C", "B", "A")
                    .indexOf(grade) + 1;

        }

        private void findPerformed() {
            preventBlankFields(new JTextField[]
                    { fieldForID, fieldForName,
                            fieldForMajor });
            String id = fieldForID.getText();
            Student student = studentMajorGradeMap.get(id);
            if (student == null) {
                showMessage("Entry not found in database!");
            }
            else if (!studentInfoMatchesInput(student)) {
                JOptionPane.showMessageDialog(frame,
                        "ID/information mismatch!");

            }
            else if (studentMajorGradeMap.containsKey(id)) {
                JOptionPane.showMessageDialog(null, student, "Find",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else  {
                showMessage("Entry not found in database!");
            }

        }

        private void deletePerformed() {
            preventBlankFields(new JTextField[]
                    { fieldForID, fieldForName,
                            fieldForMajor });
            String id = fieldForID.getText();
            Student student = studentMajorGradeMap.get(id);
            if (student == null) {
                showMessage("Entry not found in database!");
            }
            else if (!studentInfoMatchesInput(student)) {
                JOptionPane.showMessageDialog(frame,
                        "ID/information mismatch!");
            } else if (studentMajorGradeMap.containsKey(fieldForID.getText())) {
                studentMajorGradeMap.remove(fieldForID.getText());
                showMessage("Success!");
            } else {
                showMessage("Entry not found in database!");
            }

        }

        private void insertPerformed() {
            String id = fieldForID.getText();

            if (studentMajorGradeMap.containsKey(id)) {
                showMessage("Please enter an ID that does "
                        + "not already exist.");
            } else {
                String name = fieldForName.getText();
                String major = fieldForMajor.getText();
                Student student = new Student(name, major);
                studentMajorGradeMap.put(id, student);
                showMessage("Success!");
            }
        }

        private void showMessage(String msg) {
            JOptionPane.showMessageDialog(frame, msg);
        }

        private boolean isBlank(JTextField field) {
            return field.getText().equals("");
        }

        private void preventBlankField(JTextField requiredField) {
            if (isBlank(requiredField))
                showMessage("Entry cannot be blank. Try again.");
        }

    }

    public static void main(String[] args) {
        StudentDatabase db = new StudentDatabase();
        db.display();
    }
}

