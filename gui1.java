package project3Test;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.border.MatteBorder;

public class gui1 {

	private JFrame frame;
	private JTextField locationField;
	private JTextField dateField;
	private JTextArea chantDesc;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					gui1 window = new gui1();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @throws Exception 
	 */
	public gui1() throws Exception {
		initialize();
	}
	/**
	 * Initialize the contents of the frame.
	 * @throws Exception 
	 */
	private void initialize() throws Exception {

		MySQLAccess dao = new MySQLAccess();
	    dao.connectToDB();
		frame = new JFrame();
		frame.setBounds(100, 100, 720, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel SearchPanel = new JPanel();
		SearchPanel.setBounds(0, 0, 720, 121);
		frame.getContentPane().add(SearchPanel);
		SearchPanel.setLayout(null);
		
		JPanel title = new JPanel();
		title.setBounds(0, 0, 721, 25);
		SearchPanel.add(title);
		
		JLabel lblNewLabel = new JLabel("Find My Feast");
		title.add(lblNewLabel);
		
		JPanel location = new JPanel();
		location.setBounds(0, 24, 240, 50);
		SearchPanel.add(location);
		location.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Location");
		lblNewLabel_1.setBounds(20, 12, 61, 15);
		location.add(lblNewLabel_1);
		
		locationField = new JTextField();
		locationField.setBounds(100, 12, 114, 20);
		location.add(locationField);
		locationField.setColumns(10);
		
		JPanel date = new JPanel();
		date.setBounds(240, 24, 240, 50);
		SearchPanel.add(date);
		date.setLayout(null);
		
		JLabel lblNewLabel_2 = new JLabel("Date");
		lblNewLabel_2.setBounds(20, 12, 34, 15);
		date.add(lblNewLabel_2);
		
		dateField = new JTextField();
		dateField.setBounds(100, 12, 114, 20);
		date.add(dateField);
		dateField.setColumns(10);
		
		JPanel event = new JPanel();
		event.setBounds(480, 24, 240, 50);
		SearchPanel.add(event);
		event.setLayout(null);
		
		
		String[] events = new String[] {"", "Christmas", "Easter", "Birth",
				"Lent", "Epiphany", "Ember Day", "Summer", "Winter",
				"Spring", "Autumn", "Memorial", "Valentine", "Advent",
				"Funeral"};
		
		JLabel lblNewLabel_3 = new JLabel("Event");
		lblNewLabel_3.setBounds(20, 12, 120, 15);
		event.add(lblNewLabel_3);
		
		JComboBox comboBox = new JComboBox(events);
		comboBox.setBounds(100, 12, 114, 20);
		event.add(comboBox);
		
		JPanel search = new JPanel();
		search.setBounds(240, 77, 240, 32);
		SearchPanel.add(search);
		
		JButton btnNewButton = new JButton("Search");

		search.add(btnNewButton);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(12, 119, 694, 2);
		SearchPanel.add(separator);
		
		JPanel ResultsPanel = new JPanel();
		ResultsPanel.setBounds(0, 120, 720, 320);
		frame.getContentPane().add(ResultsPanel);
		ResultsPanel.setLayout(null);
		
		JPanel chants = new JPanel();
		chants.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		chants.setBounds(12, 28, 100, 279);
		ResultsPanel.add(chants);
		chants.setLayout(null);
		
		JScrollPane chantIDScroller = new JScrollPane();
		chantIDScroller.setBounds(1,1,100,278);
		chantIDScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		chants.add(chantIDScroller);
		
		JPanel chantInfo = new JPanel();
		chantInfo.setBounds(114, 28, 594, 280);
		ResultsPanel.add(chantInfo);
		chantInfo.setLayout(null);
		
		JScrollPane chantDescScroller = new JScrollPane();
		
		chantDesc = new JTextArea();
		chantDesc.setBounds(0, 0, 594, 280);
		chantInfo.add(chantDesc);
		chantDesc.setColumns(10);
		
		JLabel lblChant = new JLabel("Chant ID");
		lblChant.setBounds(34, 12, 70, 15);
		ResultsPanel.add(lblChant);
		
		JLabel lblNewLabel_4 = new JLabel("Chant Information");
		lblNewLabel_4.setBounds(141, 12, 70, 15);
		ResultsPanel.add(lblNewLabel_4);
		
		JButton btnNewButton_1 = new JButton("Save Chant");
		btnNewButton_1.setBounds(589, 442, 117, 25);
		frame.getContentPane().add(btnNewButton_1);
		
		//Search Button Listener
		btnNewButton.addActionListener(new ActionListener() { 
			  public void actionPerformed(ActionEvent e) { 
					try {
						ArrayList<String> chantIDArray = dao.getChantIDs(locationField.getText(), 
								  dateField.getText(), comboBox.getSelectedItem().toString());
						
						JList<String> newChantList = listGenerator(chantIDArray);
						newChantList.setBounds(1, 1, 80, 278);
						newChantList.setLayoutOrientation(JList.VERTICAL);
						newChantList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
						
						MouseListener mouseListener = new MouseAdapter() {
						    public void mouseClicked(MouseEvent e) {
						        if (e.getClickCount() == 2) {
						           String selectedItem = (String) newChantList.getSelectedValue();
						           try {
									String newRecord = dao.getManuscriptInformation(selectedItem);
									chantDesc.setText(newRecord);
									chantDesc.repaint();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
						           

						         }
						    }
						};
						newChantList.addMouseListener(mouseListener);
						
						JScrollPane newChantIDScroller = new JScrollPane(newChantList);
						newChantIDScroller.setBounds(1,1,100,278);
						newChantIDScroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
						chants.removeAll();
						chants.add(newChantIDScroller);
						chants.repaint();
						
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			  } 
			} );
	}
	
	private static JList<String> listGenerator(ArrayList<String> aL) {
		DefaultListModel<String> listModel = new DefaultListModel<String>();
		
		for (int i = 0; i < aL.size(); i++) {
			listModel.addElement(aL.get(i));
		}
		return new JList<String>(listModel);
	}
	
}
