import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class CalcTable{
	String translateTable[][];
	String evaluateTable[][];
	JPanel transPanel, evalPanel;
	JTextArea[] transTable = new JTextArea[5];
	JTextArea[] evalTable = new JTextArea[4];
	JScrollPane[] transTableScroll = new JScrollPane[5];
	JScrollPane[] evalTableScroll = new JScrollPane[4];
	AdjustmentListener listener = new MyAdjustmentListener();
	JScrollBar transBar, evalBar;
	
	Font font = new Font("Helvetica", Font.PLAIN, 15);
	Color background = new Color(220,220,220), digitColor= new Color(100, 70, 60), operatorColor = new Color(82, 182, 172);
	int[] widths = {10,3,12,10,11};
	int[] widths2 = {14,4,14,14};
	String[] columnTitlesTrans = {"Parsed", "Read", "Action", "Committed", "Stack"};
	String[] columnTitlesEval = {"Parsed", "Read", "Action", "Stack"};
	
	public CalcTable(){
		transPanel = new JPanel();
		//transPanel.setLayout(new FlowLayout());
		transPanel.setLayout(null);
		transPanel.setBackground(background);
		
		JPanel[] columnsTrans = new JPanel[5];
		int left = 0;
		for(int i = 0; i < 5; i++){
			transTable[i] = new JTextArea(13, widths[i]);
			transTable[i].setFont(font);
			transTable[i].setEditable(false);
			
			JLabel columnTitle = new JLabel(columnTitlesTrans[i], SwingConstants.CENTER);
			columnTitle.setFont(font);
			columnTitle.setForeground(digitColor);
			columnTitle.setBackground(background);
			columnsTrans[i] = new JPanel(new BorderLayout());
			columnsTrans[i].add(columnTitle, BorderLayout.NORTH);
			columnsTrans[i].setBackground(background);
			if(i != 4){
				transTableScroll[i] = new JScrollPane(transTable[i], JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				columnsTrans[i].add(transTableScroll[i], BorderLayout.CENTER);
			}else{
				transTableScroll[i] = new JScrollPane(transTable[i]);
				columnsTrans[i].add(transTableScroll[i], BorderLayout.CENTER);
				transBar = transTableScroll[i].getVerticalScrollBar();
				transBar.addAdjustmentListener(listener);
			}
			transTable[i].setBackground(background);
			transTable[i].setFont(font);
			transTable[i].setForeground(digitColor);
			columnsTrans[i].setBackground(background);
			transPanel.add(columnsTrans[i]);
			Dimension dimension = columnsTrans[i].getPreferredSize();
			Insets insets = transPanel.getInsets();
			columnsTrans[i].setBounds(left, -1, dimension.width, dimension.height);
			left += dimension.width;
		}
		
		evalPanel = new JPanel();
		evalPanel.setLayout(null);
		evalPanel.setBackground(background);
		
		JPanel[] columnsEval = new JPanel[4];
		left = 0;
		for(int i = 0; i < 4; i++){
			evalTable[i] = new JTextArea(12, widths2[i]);
			evalTable[i].setFont(font);
			evalTable[i].setEditable(false);
			
			JLabel columnTitle = new JLabel(columnTitlesEval[i], SwingConstants.CENTER);
			columnTitle.setFont(font);
			columnTitle.setForeground(digitColor);
			columnTitle.setBackground(background);
			columnsEval[i] = new JPanel(new BorderLayout());
			columnsEval[i].add(columnTitle, BorderLayout.NORTH);
			columnsEval[i].setBackground(background);
			if(i!=3){
				evalTableScroll[i] = new JScrollPane(evalTable[i], JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				columnsEval[i].add(evalTableScroll[i], BorderLayout.CENTER);
			}else{
				evalTableScroll[i] = new JScrollPane(evalTable[i]);
				columnsEval[i].add(evalTableScroll[i], BorderLayout.CENTER);
				evalBar = evalTableScroll[i].getVerticalScrollBar();
				evalBar.addAdjustmentListener(listener);
			}
			evalTable[i].setBackground(background);
			evalTable[i].setFont(font);
			evalTable[i].setForeground(digitColor);
			columnsEval[i].setBackground(background);
			evalPanel.add(columnsEval[i]);
			Dimension dimension = columnsEval[i].getPreferredSize();
			Insets insets = transPanel.getInsets();
			columnsEval[i].setBounds(left, -1, dimension.width, dimension.height);
			left += dimension.width;
		}
	}
	
	public void printTable(String[][] transTables, String[][] evalTables){
		translateTable = new String[transTables.length][];
		for(int i = 0; i < translateTable.length; i++){
			translateTable[i] = new String[transTables[i].length];
			for(int j = 0; j < translateTable[i].length; j++){
				translateTable[i][j] = transTables[i][j];
			}
		}
		
		evaluateTable = new String[evalTables.length][];
		for(int i = 0; i < evalTables.length; i++){
			evaluateTable[i] = new String[evalTables[i].length];
			for(int j = 0; j < evaluateTable[i].length; j++){
				evaluateTable[i][j] = evalTables[i][j];
			}
		}
		
		for(int i = 0; i < 5; i++){
			transTable[i].setText("");
			for(int j = 0; j < translateTable.length; j++){
				transTable[i].append(translateTable[j][i] + "\n");
			}
		}
		
		for(int i = 0; i < 4; i++){
			evalTable[i].setText("");
			for(int j = 0; j < evaluateTable.length; j++){
				evalTable[i].append(evaluateTable[j][i] + "\n");
			}
		}
	}
	
	private class MyAdjustmentListener implements AdjustmentListener{
		public void adjustmentValueChanged(AdjustmentEvent e){
			if(e.getSource() == transBar && e.getAdjustmentType() == AdjustmentEvent.TRACK){
				for(int i = 0; i < 4; i++){
					transTableScroll[i].getVerticalScrollBar().setValue(e.getValue());
				}
			}else if(e.getSource() == evalBar && e.getAdjustmentType() == AdjustmentEvent.TRACK){
				for(int i = 0; i < 3; i++){
					evalTableScroll[i].getVerticalScrollBar().setValue(e.getValue());
				}
			}
		}
	}
}