package application;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import businesslogic.Landmark;

public class PanelData extends JPanel implements ActionListener, TableModelListener {

	/**
	 * 
	 */
	public ArrayList<Landmark> tableData = new ArrayList<Landmark>();
	private static final long serialVersionUID = 1L;
	private static JPopupMenu tableOption = new JPopupMenu();
	public static int tableDataSize = 0;
	private JMenuItem popOptDelete = new JMenuItem("Delete");
	public static DefaultTableModel model;
	public static JTextArea jText = new JTextArea();
	public static JList<String> landmarkListView;
	public static JTable landmarkTable;
	public static ArrayList<Landmark> landmarkData;

	public PanelData() {
		super();
		// setBounds(100, 100, 500, 375);
		this.setFocusable(true);
		this.requestFocusInWindow();
		tableData = new ArrayList<Landmark>();
		try {
			creerPanel();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	void creerPanel() {
		this.setComponentPopupMenu(tableOption);
		if (!tableData.isEmpty()) {
			tableDataSize = tableData.size();
			createTable(tableData);
		}
	}

	public void createTable(ArrayList<Landmark> tmpLandmarkList) {
		landmarkTable = new JTable(3, 0);
		tableData = tmpLandmarkList;
		landmarkData = tmpLandmarkList;
		String[] header = { "X", "Y", "Type" };
		Object[][] data = new Object[tmpLandmarkList.size()][];
		for (int i = 0; i < tmpLandmarkList.size(); i++) {
			String x = tmpLandmarkList.get(i).getPosX() + "";
			String y = tmpLandmarkList.get(i).getPosY() + "";
			String typeOfLandmark = tmpLandmarkList.get(i).getIsLandmark().toString();
			Object[] rowData = { x, y, typeOfLandmark };
			data[i] = rowData;
		}
		model = new DefaultTableModel(data, header) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@SuppressWarnings("rawtypes")
			public Class[] types = new Class[] { java.lang.Object.class, java.lang.Object.class,
					java.lang.Object.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			@Override
			public boolean isCellEditable(int row, int column) {
				// make read only fields except column
				return false;
			}
		};

		landmarkTable = new JTable(model);
		for (int i = 0; i < landmarkTable.getColumnCount(); i++) {
			TableColumn column1 = landmarkTable.getTableHeader().getColumnModel().getColumn(i);
			column1.setHeaderValue(header[i]);
		}
		landmarkTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					showPopupTable(e);
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getButton() == MouseEvent.BUTTON3) {
					showPopupTable(e);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				if (e.getButton() == MouseEvent.BUTTON3) {
					showPopupTable(e);
				}
			}
		});
		JScrollPane tableContainer = new JScrollPane(landmarkTable);
		this.add(tableContainer, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(landmarkTable);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(200, 600));
		tableDataSize = tableData.size();

		this.add(scrollPane);
		this.revalidate();
		this.repaint();
	}

	public void showPopupTable(MouseEvent event) {
		if (event.isPopupTrigger()) {
			popOptDelete.addActionListener(this);
			tableOption.add(popOptDelete);
			tableOption.show(PanelData.landmarkTable, event.getX(), event.getY());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(popOptDelete)) {
			int[] selRows = landmarkTable.getSelectedRows();
			for (int i : selRows) {
				float x = Float.parseFloat(model.getValueAt(i, 0).toString());
				float y = Float.parseFloat(model.getValueAt(i, 1).toString());
				for (int j = 0; j < Affichage.listLandmark.size(); j++) {
					Landmark l = Affichage.listLandmark.get(j);
					if (x == l.getPosX() && y == l.getPosY()) {
						Affichage.selLandmark.add(j);
						break;
					}
				}
			}
			Collections.sort(Affichage.selLandmark);
			Collections.reverse(Affichage.selLandmark);
			if (Affichage.selLandmark.size() > 0) {
				for (int i = 0; i < Affichage.selLandmark.size(); i++) {
					model.removeRow(i);
					int item = Affichage.selLandmark.get(i);
					Affichage.listLandmark.remove(item);
					this.revalidate();
					this.repaint();
				}
				if (!Affichage.selLandmark.isEmpty())
					Affichage.selLandmark.clear();
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		// If landmark is changed
		super.paintComponent(g);
		if (!tableData.isEmpty()) {
			if (tableData.size() != Affichage.listLandmark.size()) {
				for (Landmark l : Affichage.listLandmark) {
					boolean isExist = false;
					for (Landmark k : tableData) {
						if (l.getPosX() == k.getPosX() && l.getPosY() == k.getPosY()) {
							isExist = true;
							break;
						}
					}
					if (!isExist)
						tableData.add(l);
				}
			}
		}
	}

	@Override
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub

	}
}
