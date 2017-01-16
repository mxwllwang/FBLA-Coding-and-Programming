package fec;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JLabel;
import javax.swing.ListSelectionModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.TreeMap;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.SpinnerNumberModel;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.awt.Label;
import javax.swing.JTextPane;

public class ECFC {

    private JFrame frmEcfcGeneralOperations;
    private JTable tableE;
    private DefaultTableModel dtmE;
    private JTable tableC;
    private DefaultTableModel dtmC;

    private JTextField textFieldFNE;
    private JTextField textFieldMNE;
    private JTextField textFieldLNE;
    private JTextField textFieldPos;
    private JTextArea textFieldNotesE;
    private JFormattedTextField formattedTextFieldHourlyWage;
    private JRadioButton rdbtnOtherE;
    private JRadioButton rdbtnMaleE;
    private JRadioButton rdbtnFemaleE;
    private JSpinner spinnerE;

    // number of table values
    private static TreeMap<Long, Employee> e;
    private static TreeMap<Long, Customer> c;
    // private static BufferedReader brE;
    // private static BufferedReader brC;
    // private static BufferedWriter fileOutE;
    // private static BufferedWriter fileOutC;

    private JTextField textFieldFNC;
    private JTextField textFieldMNC;
    private JTextField textFieldLNC;
    private JRadioButton rdbtnOtherC;
    private JRadioButton rdbtnMaleC;
    private JRadioButton rdbtnFemaleC;
    private JSpinner spinnerC;
    private JTextArea textFieldNotesC;
    private JTextField textFieldEmailC;
    private JTextField textFieldEmailE;

    /**
     * public Employee(String fn, String mn, String ln, int a, int g, double s,
     * String n, String p, Date lu, String e)
     * 
     * Customer(String fn, String mn, String ln, int a, int g, String n, String
     * e, Date lu)
     **/

    /**
     * Launch the application.
     * 
     * @throws FileNotFoundException
     * 
     * @throws InterruptedException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException,
            IOException, ClassNotFoundException, InterruptedException {

        // Extracts treemaps from data file

        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                    "ECFCdata.ser"));

            e = (TreeMap<Long, Employee>) in.readObject();
            c = (TreeMap<Long, Customer>) in.readObject();

            in.close();
        } catch (java.io.FileNotFoundException pp) {
            new File("ECFCdata.ser");

            e = new TreeMap<Long, Employee>();
            c = new TreeMap<Long, Customer>();
        }

        // shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {

                // Creating employee file
                try {
                    ObjectOutputStream out = new ObjectOutputStream(
                            new FileOutputStream("ECFCdata.ser"));

                    out.writeObject(e);
                    out.writeObject(c);

                    out.close();
                } catch (IOException i) {
                    i.printStackTrace();
                }

            }
        }, "Shutdown-thread"));

        // Initialize
        Thread.sleep(1000);
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    ECFC window = new ECFC();
                    window.frmEcfcGeneralOperations.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /** creates application */
    public ECFC() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {

        // specifies window
        frmEcfcGeneralOperations = new JFrame();
        frmEcfcGeneralOperations.setTitle("ECFC General Operations");
        frmEcfcGeneralOperations.getContentPane().setBackground(
                new Color(144, 238, 144));
        frmEcfcGeneralOperations.setBounds(400, 400, 1400, 1000);
        frmEcfcGeneralOperations.setMinimumSize(new Dimension(700, 600));
        frmEcfcGeneralOperations
                .setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frmEcfcGeneralOperations
                .addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(
                            java.awt.event.WindowEvent windowEvent) {
                        if (JOptionPane.showConfirmDialog(
                                frmEcfcGeneralOperations,
                                "Progress will save automatically.",
                                "Close Window?", JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
                            System.exit(0);
                        }
                    }
                });

        // Employee details

        JPanel cardE = new JPanel();
        cardE.setBackground(new Color(240, 255, 240));

        String columnNamesE[] = { "Last Name", "First Name", "Middle Name",
                "Age", "Gender", "Position", "Hourly Wage", "Email",
                "Work Schedule", "Notes", "Last Updated" };
        dtmE = new DefaultTableModel(columnNamesE, 0);

        Employee[] employees = new Employee[e.size()];
        int i = 0;
        if (e.size() != 0) {
            for (Object o : e.values().toArray()) {
                employees[i++] = (Employee) o;
            }
            for (Employee ee : employees) {
                addRowE(ee);
            }
        }
        cardE.setLayout(new BorderLayout(0, 0));

        JPanel northE = new JPanel();
        cardE.add(northE, BorderLayout.NORTH);

        // Clear all fields for Employee
        JButton btnClearFieldsE = new JButton("Clear all Fields");
        btnClearFieldsE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                textFieldFNE.setText("");
                textFieldMNE.setText("");
                textFieldLNE.setText("");
                formattedTextFieldHourlyWage.setText("");
                spinnerE.setValue(13);
                textFieldPos.setText("");
                textFieldNotesE.setText("");
                textFieldEmailE.setText("");
            }
        });
        btnClearFieldsE.setToolTipText("Restarts all forms on this page.");
        northE.add(btnClearFieldsE);

        JButton btnDeleteE = new JButton("Delete Selected");
        btnDeleteE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent p) {
                if (tableE.getSelectedRow() != -1) {
                    int i = tableE.getSelectedRow();
                    e.remove(((Date) tableE.getValueAt(i, 10)).getTime());
                    dtmE.removeRow(i);
                }
            }
        });
        northE.add(btnDeleteE);
        tableE = new JTable(dtmE);
        tableE.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableE.setAutoCreateRowSorter(true);
        tableE.getColumnModel().getColumn(0).setPreferredWidth(65);
        tableE.getColumnModel().getColumn(1).setPreferredWidth(65);
        tableE.getColumnModel().getColumn(2).setPreferredWidth(65);
        tableE.getColumnModel().getColumn(3).setPreferredWidth(5);
        tableE.getColumnModel().getColumn(4).setPreferredWidth(30);
        tableE.getColumnModel().getColumn(5).setPreferredWidth(100);
        tableE.getColumnModel().getColumn(6).setPreferredWidth(60);
        tableE.getColumnModel().getColumn(7).setPreferredWidth(110);
        tableE.getColumnModel().getColumn(8).setPreferredWidth(95);
        // tableE.getColumnModel().getColumn(9).setPreferredWidth(40);
        tableE.getColumnModel().getColumn(10).setPreferredWidth(150);

        JScrollPane scrollPaneCenterE = new JScrollPane(tableE);
        cardE.add(scrollPaneCenterE, BorderLayout.CENTER);

        // Customer details
        JPanel cardC = new JPanel();
        cardC.setBackground(new Color(240, 255, 240));

        String columnNamesC[] = { "Last Name", "First Name", "Middle Name",
                "Age", "Gender", "Attendance", "Email", "Notes", "Last Updated" };
        dtmC = new DefaultTableModel(columnNamesC, 0);

        Customer[] customers = new Customer[c.size()];
        int j = 0;
        if (e.size() != 0) {
            for (Object o : c.values().toArray()) {
                customers[j++] = (Customer) o;
            }
            for (Customer cc : customers) {
                addRowC(cc);
            }
        }

        frmEcfcGeneralOperations.getContentPane().setLayout(
                new BorderLayout(0, 0));
        cardC.setLayout(new BorderLayout(0, 0));
        tableC = new JTable(dtmC);
        tableC.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableC.setAutoCreateRowSorter(true);
        tableC.getColumnModel().getColumn(0).setPreferredWidth(65);
        tableC.getColumnModel().getColumn(1).setPreferredWidth(65);
        tableC.getColumnModel().getColumn(2).setPreferredWidth(65);
        tableC.getColumnModel().getColumn(3).setPreferredWidth(5);
        tableC.getColumnModel().getColumn(4).setPreferredWidth(30);
        tableC.getColumnModel().getColumn(5).setPreferredWidth(95);
        // tableC.getColumnModel().getColumn(6).setPreferredWidth(40);
        tableC.getColumnModel().getColumn(7).setPreferredWidth(150);

        JScrollPane scrollPaneCenterC = new JScrollPane(tableC);
        cardC.add(scrollPaneCenterC);

        // Help details
        JPanel cardA = new JPanel();

        // Initializes tabbedpane
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        frmEcfcGeneralOperations.getContentPane().add(tabbedPane);
        tabbedPane.addTab("Employees", cardE);
        tabbedPane.addTab("Members", cardC);

        // Customer Components
        JPanel northC = new JPanel();
        cardC.add(northC, BorderLayout.NORTH);

        // clears all fields
        JButton btnClearFieldsC = new JButton("Clear all Fields");
        btnClearFieldsC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textFieldFNC.setText("");
                textFieldMNC.setText("");
                textFieldLNC.setText("");
                textFieldEmailC.setText("");
                spinnerC.setValue(0);
                textFieldNotesC.setText("");
            }
        });
        btnClearFieldsC.setToolTipText("Restarts all forms on this page.");
        northC.add(btnClearFieldsC);

        JButton btnDeleteC = new JButton("Delete Selected");
        btnDeleteC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent p) {
                if (tableC.getSelectedRow() != -1) {
                    int i = tableC.getSelectedRow();
                    c.remove(((Date) tableC.getValueAt(i, 8)).getTime());
                    dtmC.removeRow(i);
                }
            }
        });

        northC.add(btnDeleteC);

        JPanel westC = new JPanel();
        westC.setBorder(new EmptyBorder(5, 5, 5, 5));
        cardC.add(westC, BorderLayout.WEST);
        GridBagLayout gbl_westC = new GridBagLayout();
        gbl_westC.columnWidths = new int[] { 112, 0 };
        gbl_westC.rowHeights = new int[] { 31, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0 };
        gbl_westC.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl_westC.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0,
                Double.MIN_VALUE };
        westC.setLayout(gbl_westC);

        JLabel lblAddNewCustomer = new JLabel("Add new Customer");
        GridBagConstraints gbc_lblAddNewCustomer = new GridBagConstraints();
        gbc_lblAddNewCustomer.insets = new Insets(0, 0, 5, 0);
        gbc_lblAddNewCustomer.gridx = 0;
        gbc_lblAddNewCustomer.gridy = 0;
        westC.add(lblAddNewCustomer, gbc_lblAddNewCustomer);

        JLabel lblFirstNameC = new JLabel("First Name");
        GridBagConstraints gbc_lblFirstNameC = new GridBagConstraints();
        gbc_lblFirstNameC.insets = new Insets(0, 0, 5, 0);
        gbc_lblFirstNameC.gridx = 0;
        gbc_lblFirstNameC.gridy = 1;
        westC.add(lblFirstNameC, gbc_lblFirstNameC);

        textFieldFNC = new JTextField();
        textFieldFNC.setColumns(10);
        GridBagConstraints gbc_textFieldFNC = new GridBagConstraints();
        gbc_textFieldFNC.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldFNC.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldFNC.gridx = 0;
        gbc_textFieldFNC.gridy = 2;
        westC.add(textFieldFNC, gbc_textFieldFNC);

        JLabel lblMiddleNameC = new JLabel("Middle Name (opt.)");
        GridBagConstraints gbc_lblMiddleNameC = new GridBagConstraints();
        gbc_lblMiddleNameC.insets = new Insets(0, 0, 5, 0);
        gbc_lblMiddleNameC.gridx = 0;
        gbc_lblMiddleNameC.gridy = 3;
        westC.add(lblMiddleNameC, gbc_lblMiddleNameC);

        textFieldMNC = new JTextField();
        textFieldMNC.setColumns(10);
        GridBagConstraints gbc_textFieldMNC = new GridBagConstraints();
        gbc_textFieldMNC.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldMNC.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldMNC.gridx = 0;
        gbc_textFieldMNC.gridy = 4;
        westC.add(textFieldMNC, gbc_textFieldMNC);

        JLabel lblLastNameC = new JLabel("Last Name");
        GridBagConstraints gbc_lblLastNameC = new GridBagConstraints();
        gbc_lblLastNameC.insets = new Insets(0, 0, 5, 0);
        gbc_lblLastNameC.gridx = 0;
        gbc_lblLastNameC.gridy = 5;
        westC.add(lblLastNameC, gbc_lblLastNameC);

        textFieldLNC = new JTextField();
        textFieldLNC.setColumns(10);
        GridBagConstraints gbc_textFieldLNC = new GridBagConstraints();
        gbc_textFieldLNC.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldLNC.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldLNC.gridx = 0;
        gbc_textFieldLNC.gridy = 6;
        westC.add(textFieldLNC, gbc_textFieldLNC);

        JLabel lblAgeC = new JLabel("Age");
        GridBagConstraints gbc_lblAgeC = new GridBagConstraints();
        gbc_lblAgeC.insets = new Insets(0, 0, 5, 0);
        gbc_lblAgeC.gridx = 0;
        gbc_lblAgeC.gridy = 7;
        westC.add(lblAgeC, gbc_lblAgeC);

        spinnerC = new JSpinner();
        spinnerC.setModel(new SpinnerNumberModel(0, 0, 120, 1));
        spinnerC.setToolTipText("");
        GridBagConstraints gbc_spinnerC = new GridBagConstraints();
        gbc_spinnerC.insets = new Insets(0, 0, 5, 0);
        gbc_spinnerC.gridx = 0;
        gbc_spinnerC.gridy = 8;
        westC.add(spinnerC, gbc_spinnerC);

        JLabel lblGenderC = new JLabel("Gender");
        GridBagConstraints gbc_lblGenderC = new GridBagConstraints();
        gbc_lblGenderC.insets = new Insets(0, 0, 5, 0);
        gbc_lblGenderC.gridx = 0;
        gbc_lblGenderC.gridy = 9;
        westC.add(lblGenderC, gbc_lblGenderC);

        rdbtnMaleC = new JRadioButton("Male");
        GridBagConstraints gbc_rdbtnMaleC = new GridBagConstraints();
        gbc_rdbtnMaleC.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnMaleC.gridx = 0;
        gbc_rdbtnMaleC.gridy = 10;
        westC.add(rdbtnMaleC, gbc_rdbtnMaleC);

        rdbtnFemaleC = new JRadioButton("Female");
        GridBagConstraints gbc_rdbtnFemaleC = new GridBagConstraints();
        gbc_rdbtnFemaleC.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnFemaleC.gridx = 0;
        gbc_rdbtnFemaleC.gridy = 11;
        westC.add(rdbtnFemaleC, gbc_rdbtnFemaleC);

        rdbtnOtherC = new JRadioButton("Other");
        GridBagConstraints gbc_rdbtnOtherC = new GridBagConstraints();
        gbc_rdbtnOtherC.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnOtherC.gridx = 0;
        gbc_rdbtnOtherC.gridy = 12;
        westC.add(rdbtnOtherC, gbc_rdbtnOtherC);

        ButtonGroup bgC = new ButtonGroup();
        bgC.add(rdbtnMaleC);
        bgC.add(rdbtnFemaleC);
        bgC.add(rdbtnOtherC);

        JLabel lblEmailC = new JLabel("Email Address (opt.)");
        GridBagConstraints gbc_lblEmailC = new GridBagConstraints();
        gbc_lblEmailC.insets = new Insets(0, 0, 5, 0);
        gbc_lblEmailC.gridx = 0;
        gbc_lblEmailC.gridy = 13;
        westC.add(lblEmailC, gbc_lblEmailC);

        textFieldEmailC = new JTextField();
        GridBagConstraints gbc_textFieldEmailC = new GridBagConstraints();
        gbc_textFieldEmailC.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldEmailC.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldEmailC.gridx = 0;
        gbc_textFieldEmailC.gridy = 14;
        westC.add(textFieldEmailC, gbc_textFieldEmailC);

        JLabel lblNotesC = new JLabel("Notes");
        GridBagConstraints gbc_lblNotesC = new GridBagConstraints();
        gbc_lblNotesC.insets = new Insets(0, 0, 5, 0);
        gbc_lblNotesC.gridx = 0;
        gbc_lblNotesC.gridy = 15;
        westC.add(lblNotesC, gbc_lblNotesC);

        textFieldNotesC = new JTextArea();
        textFieldNotesC.setColumns(15);
        GridBagConstraints gbc_textFieldNotesC = new GridBagConstraints();
        gbc_textFieldNotesC.fill = GridBagConstraints.BOTH;
        gbc_textFieldNotesC.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldNotesC.gridx = 0;
        gbc_textFieldNotesC.gridy = 16;
        westC.add(textFieldNotesC, gbc_textFieldNotesC);

        // Create new customer
        JButton btnCreateC = new JButton("Create");
        btnCreateC.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Checks if fields are complete
                if (textFieldFNC.getText() == null
                        || textFieldFNC.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Please Specify First Name");
                    return;
                }
                if (textFieldLNC.getText() == null
                        || textFieldLNC.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Please Specify Last Name");
                    return;
                }
                if ((int) spinnerC.getValue() == 0) {
                    JOptionPane.showMessageDialog(null, "Please specify age");
                    return;
                }
                int g;
                if (rdbtnMaleC.getModel().isSelected()) {
                    g = 1;
                } else if (rdbtnFemaleC.getModel().isSelected()) {
                    g = 2;
                } else if (rdbtnOtherC.getModel().isSelected()) {
                    g = 3;
                } else {
                    JOptionPane
                            .showMessageDialog(null, "Please Specify Gender");
                    return;
                }

                // now creates new customer
                long updated = System.currentTimeMillis();
                Customer cu = new Customer(textFieldFNC.getText(), textFieldMNC
                        .getText(), textFieldLNC.getText(), (int) spinnerC
                        .getValue(), g, textFieldNotesC.getText(),
                        textFieldEmailC.getText(), updated);
                c.put(cu.lastUpdated, cu);

                // addRowC(cu);

                // clears all fields
                textFieldFNC.setText("");
                textFieldMNC.setText("");
                textFieldLNC.setText("");
                textFieldEmailC.setText("");
                spinnerC.setValue(0);
                g = 0;
                textFieldNotesC.setText("");
            }
        });
        btnCreateC.setBackground(new Color(152, 251, 152));
        GridBagConstraints gbc_btnCreateC = new GridBagConstraints();
        gbc_btnCreateC.gridx = 0;
        gbc_btnCreateC.gridy = 17;
        westC.add(btnCreateC, gbc_btnCreateC);

        JPanel southC = new JPanel();
        cardC.add(southC, BorderLayout.SOUTH);
        tabbedPane.addTab("Customer Attendance", cardA);

        // Employee components
        JPanel westE = new JPanel();
        westE.setBorder(new EmptyBorder(5, 5, 5, 5));
        cardE.add(westE, BorderLayout.WEST);
        GridBagLayout gbl_westE = new GridBagLayout();
        gbl_westE.columnWidths = new int[] { 112, 0 };
        gbl_westE.rowHeights = new int[] { 31, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        gbl_westE.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
        gbl_westE.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
        westE.setLayout(gbl_westE);

        JLabel lblAddNewEmployee = new JLabel("Add new Employee");
        GridBagConstraints gbc_lblAddNewEmployee = new GridBagConstraints();
        gbc_lblAddNewEmployee.insets = new Insets(0, 0, 5, 0);
        gbc_lblAddNewEmployee.gridx = 0;
        gbc_lblAddNewEmployee.gridy = 0;
        westE.add(lblAddNewEmployee, gbc_lblAddNewEmployee);

        JLabel lblFirstNameE = new JLabel("First Name");
        GridBagConstraints gbc_lblFirstNameE = new GridBagConstraints();
        gbc_lblFirstNameE.insets = new Insets(0, 0, 5, 0);
        gbc_lblFirstNameE.gridx = 0;
        gbc_lblFirstNameE.gridy = 1;
        westE.add(lblFirstNameE, gbc_lblFirstNameE);

        textFieldFNE = new JTextField();
        GridBagConstraints gbc_textFieldFNE = new GridBagConstraints();
        gbc_textFieldFNE.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldFNE.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldFNE.gridx = 0;
        gbc_textFieldFNE.gridy = 2;
        westE.add(textFieldFNE, gbc_textFieldFNE);
        textFieldFNE.setColumns(10);

        JLabel lblMiddleNameE = new JLabel("Middle Name (opt.)");
        GridBagConstraints gbc_lblMiddleNameE = new GridBagConstraints();
        gbc_lblMiddleNameE.insets = new Insets(0, 0, 5, 0);
        gbc_lblMiddleNameE.gridx = 0;
        gbc_lblMiddleNameE.gridy = 3;
        westE.add(lblMiddleNameE, gbc_lblMiddleNameE);

        textFieldMNE = new JTextField();
        GridBagConstraints gbc_textFieldMNE = new GridBagConstraints();
        gbc_textFieldMNE.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldMNE.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldMNE.gridx = 0;
        gbc_textFieldMNE.gridy = 4;
        westE.add(textFieldMNE, gbc_textFieldMNE);
        textFieldMNE.setColumns(10);

        JLabel lblLastNameE = new JLabel("Last Name");
        GridBagConstraints gbc_lblLastNameE = new GridBagConstraints();
        gbc_lblLastNameE.insets = new Insets(0, 0, 5, 0);
        gbc_lblLastNameE.gridx = 0;
        gbc_lblLastNameE.gridy = 5;
        westE.add(lblLastNameE, gbc_lblLastNameE);

        textFieldLNE = new JTextField();
        GridBagConstraints gbc_textFieldLNE = new GridBagConstraints();
        gbc_textFieldLNE.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldLNE.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldLNE.gridx = 0;
        gbc_textFieldLNE.gridy = 6;
        westE.add(textFieldLNE, gbc_textFieldLNE);
        textFieldLNE.setColumns(10);

        JLabel lblAgeE = new JLabel("Age");
        GridBagConstraints gbc_lblAgeE = new GridBagConstraints();
        gbc_lblAgeE.insets = new Insets(0, 0, 5, 0);
        gbc_lblAgeE.gridx = 0;
        gbc_lblAgeE.gridy = 7;
        westE.add(lblAgeE, gbc_lblAgeE);

        spinnerE = new JSpinner();
        spinnerE.setToolTipText("Min. Working Age is 14.");
        spinnerE.setModel(new SpinnerNumberModel(13, 13, 80, 1));
        GridBagConstraints gbc_spinnerE = new GridBagConstraints();
        gbc_spinnerE.insets = new Insets(0, 0, 5, 0);
        gbc_spinnerE.gridx = 0;
        gbc_spinnerE.gridy = 8;
        westE.add(spinnerE, gbc_spinnerE);

        JLabel lblGenderE = new JLabel("Gender");
        GridBagConstraints gbc_lblGenderE = new GridBagConstraints();
        gbc_lblGenderE.insets = new Insets(0, 0, 5, 0);
        gbc_lblGenderE.gridx = 0;
        gbc_lblGenderE.gridy = 9;
        westE.add(lblGenderE, gbc_lblGenderE);

        rdbtnMaleE = new JRadioButton("Male");
        GridBagConstraints gbc_rdbtnMaleE = new GridBagConstraints();
        gbc_rdbtnMaleE.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnMaleE.gridx = 0;
        gbc_rdbtnMaleE.gridy = 10;
        westE.add(rdbtnMaleE, gbc_rdbtnMaleE);

        rdbtnFemaleE = new JRadioButton("Female");
        GridBagConstraints gbc_rdbtnFemaleE = new GridBagConstraints();
        gbc_rdbtnFemaleE.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnFemaleE.gridx = 0;
        gbc_rdbtnFemaleE.gridy = 11;
        westE.add(rdbtnFemaleE, gbc_rdbtnFemaleE);

        rdbtnOtherE = new JRadioButton("Other");
        GridBagConstraints gbc_rdbtnOtherE = new GridBagConstraints();
        gbc_rdbtnOtherE.insets = new Insets(0, 0, 5, 0);
        gbc_rdbtnOtherE.gridx = 0;
        gbc_rdbtnOtherE.gridy = 12;
        westE.add(rdbtnOtherE, gbc_rdbtnOtherE);

        ButtonGroup bgE = new ButtonGroup();
        bgE.add(rdbtnMaleE);
        bgE.add(rdbtnFemaleE);
        bgE.add(rdbtnOtherE);

        JLabel lblhourlyWage = new JLabel("Hourly Wage ($__._)");
        GridBagConstraints gbc_lblhourlyWage = new GridBagConstraints();
        gbc_lblhourlyWage.insets = new Insets(0, 0, 5, 0);
        gbc_lblhourlyWage.gridx = 0;
        gbc_lblhourlyWage.gridy = 13;
        westE.add(lblhourlyWage, gbc_lblhourlyWage);

        formattedTextFieldHourlyWage = new JFormattedTextField(
                java.text.NumberFormat.getCurrencyInstance());
        GridBagConstraints gbc_formattedTextFieldHourlyWage = new GridBagConstraints();
        gbc_formattedTextFieldHourlyWage.insets = new Insets(0, 0, 5, 0);
        gbc_formattedTextFieldHourlyWage.fill = GridBagConstraints.HORIZONTAL;
        gbc_formattedTextFieldHourlyWage.gridx = 0;
        gbc_formattedTextFieldHourlyWage.gridy = 14;
        westE.add(formattedTextFieldHourlyWage,
                gbc_formattedTextFieldHourlyWage);

        JLabel lblPosition = new JLabel("Position");
        GridBagConstraints gbc_lblPosition = new GridBagConstraints();
        gbc_lblPosition.insets = new Insets(0, 0, 5, 0);
        gbc_lblPosition.gridx = 0;
        gbc_lblPosition.gridy = 15;
        westE.add(lblPosition, gbc_lblPosition);

        textFieldPos = new JTextField();
        GridBagConstraints gbc_textFieldPos = new GridBagConstraints();
        gbc_textFieldPos.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldPos.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldPos.gridx = 0;
        gbc_textFieldPos.gridy = 16;
        westE.add(textFieldPos, gbc_textFieldPos);
        textFieldPos.setColumns(10);

        JLabel lblEmailE = new JLabel("Email Address (opt.)");
        GridBagConstraints gbc_lblEmailE = new GridBagConstraints();
        gbc_lblEmailE.insets = new Insets(0, 0, 5, 0);
        gbc_lblEmailE.gridx = 0;
        gbc_lblEmailE.gridy = 17;
        westE.add(lblEmailE, gbc_lblEmailE);

        textFieldEmailE = new JTextField();
        GridBagConstraints gbc_textFieldEmailE = new GridBagConstraints();
        gbc_textFieldEmailE.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldEmailE.fill = GridBagConstraints.HORIZONTAL;
        gbc_textFieldEmailE.gridx = 0;
        gbc_textFieldEmailE.gridy = 18;
        westE.add(textFieldEmailE, gbc_textFieldEmailE);
        textFieldEmailE.setColumns(10);

        JLabel lblNotesE = new JLabel("Notes");
        GridBagConstraints gbc_lblNotesE = new GridBagConstraints();
        gbc_lblNotesE.insets = new Insets(0, 0, 5, 0);
        gbc_lblNotesE.gridx = 0;
        gbc_lblNotesE.gridy = 19;
        westE.add(lblNotesE, gbc_lblNotesE);

        textFieldNotesE = new JTextArea();
        GridBagConstraints gbc_textFieldNotesE = new GridBagConstraints();
        gbc_textFieldNotesE.insets = new Insets(0, 0, 5, 0);
        gbc_textFieldNotesE.fill = GridBagConstraints.BOTH;
        gbc_textFieldNotesE.gridx = 0;
        gbc_textFieldNotesE.gridy = 20;
        westE.add(textFieldNotesE, gbc_textFieldNotesE);
        textFieldNotesE.setColumns(15);

        // Creates Employee
        JButton btnCreateE = new JButton("Create");
        btnCreateE.setBackground(new Color(152, 251, 152));
        btnCreateE.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

                // Checks if fields are complete
                if (textFieldFNE.getText() == null
                        || textFieldFNE.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Please Specify First Name");
                    return;
                }
                if (textFieldLNE.getText() == null
                        || textFieldLNE.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Please Specify Last Name");
                    return;
                }
                if ((int) spinnerE.getValue() == 0) {
                    JOptionPane.showMessageDialog(null, "Please specify age");
                    return;
                } else if ((int) spinnerE.getValue() < 14) {
                    JOptionPane.showMessageDialog(null, "Age invalid");
                    return;
                }
                int g;
                if (rdbtnMaleE.getModel().isSelected()) {
                    g = 1;
                } else if (rdbtnFemaleE.getModel().isSelected()) {
                    g = 2;
                } else if (rdbtnOtherE.getModel().isSelected()) {
                    g = 3;
                } else {
                    JOptionPane
                            .showMessageDialog(null, "Please Specify Gender");
                    return;
                }
                if (formattedTextFieldHourlyWage.getText() == null
                        || formattedTextFieldHourlyWage.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Please Specify Hourly Wage");
                    return;
                }
                if (textFieldPos.getText() == null
                        || textFieldPos.getText().equals("")) {
                    JOptionPane.showMessageDialog(null,
                            "Please Specify Employee Position");
                    return;
                }

                // now creates new employee
                long updated = System.currentTimeMillis();
                Employee emp = new Employee(textFieldFNE.getText(),
                        textFieldMNE.getText(), textFieldLNE.getText(),
                        (int) spinnerE.getValue(), g,
                        (long) formattedTextFieldHourlyWage.getValue(),
                        textFieldPos.getText(), textFieldNotesE.getText(),
                        updated, textFieldEmailE.getText());
                e.put(emp.lastUpdated, emp);
                addRowE(emp);

                // clears all fields
                textFieldFNE.setText("");
                textFieldMNE.setText("");
                textFieldLNE.setText("");
                formattedTextFieldHourlyWage.setText("");
                spinnerE.setValue(13);
                g = 0;
                textFieldPos.setText("");
                textFieldNotesE.setText("");
                textFieldEmailE.setText("");
            }
        });

        GridBagConstraints gbc_btnCreateE = new GridBagConstraints();
        gbc_btnCreateE.gridx = 0;
        gbc_btnCreateE.gridy = 21;
        westE.add(btnCreateE, gbc_btnCreateE);

        JPanel southE = new JPanel();
        cardE.add(southE, BorderLayout.SOUTH);

        JLabel lblInfoE = new JLabel("Weekly Work Schedule for:");
        lblInfoE.setHorizontalAlignment(SwingConstants.LEFT);
        southE.add(lblInfoE);

        JPanel cardH = new JPanel();
        tabbedPane.addTab("Help", null, cardH, null);
        
        Label label = new Label("Version Information");
        cardH.add(label);
        
        JTextPane txtpnDueToTime = new JTextPane();
        txtpnDueToTime.setText("Due to time constraints, supports only Employee and Customer creation and deletion. Editing can be achieved through creating and then deleting. ");
        cardH.add(txtpnDueToTime);

    }

    // ln, mn, fn, a, g, p, s, ws, n
    public void addRowE(Employee e) {
        String g;
        if (e.gender == 1) {
            g = "Male";
        } else if (e.gender == 2) {
            g = "Female";
        } else {
            g = "/";
        }
        Date d = new Date(e.lastUpdated);
        dtmE.addRow(new Object[] { e.lastName, e.firstName, e.middleName,
                e.age, g, e.position, "$" + e.wage, e.email, e.workschedule,
                e.notes, d });
    }

    public void addRowC(Customer e) {
        String g;
        if (e.gender == 1) {
            g = "Male";
        } else if (e.gender == 2) {
            g = "Female";
        } else {
            g = "/";
        }
        Date d = new Date(e.lastUpdated);
        dtmC.addRow(new Object[] { e.lastName, e.firstName, e.middleName,
                e.age, g, e.attendance, e.email, e.notes, d });
    }

    public Object[][] getTableData(JTable table) {
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        for (int i = 0; i < nRow; i++)
            for (int j = 0; j < nCol; j++)
                tableData[i][j] = dtm.getValueAt(i, j);
        return tableData;
    }

}
