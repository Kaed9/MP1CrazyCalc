import javax.swing.*;
import javax.swing.plaf.*;
import java.awt.*;
import java.awt.font.*;
import java.awt.event.*;
import java.util.EmptyStackException;

public class CrazyCalculator extends JFrame implements ActionListener, Runnable{
	private JPanel whole, north, south, east, west;
	private JPanel field, center, numbers, calculatorPanel;
	private JButton[] num = new JButton[20];
	private JTextField text;
	private String[] listTranslation, listEvaluation;
	private int translateCurr = 0, evaluateCurr = 0, translateCtr = 0, evaluateCtr = 0;
	private String[][] translateTable, evaluateTable;
	private String op = "", str = "";
	private Timer timer = new Timer(1, this);
	private boolean activeWindow, runningAnimation = false;
	private Color background = new Color(220,220,220), digitColor= new Color(140, 110, 100), operatorColor = new Color(82, 182, 172);
	private int fontSize = 30;
	private JPanel translateAnimPanel, evaluateAnimPanel;
	private TranslateAnimation trans = new TranslateAnimation();
	private EvaluateAnimation eval = new EvaluateAnimation();
	private Thread animationThread = null;
	private JTabbedPane translateTabs = new JTabbedPane(), evaluateTabs = new JTabbedPane();
	private CalcTable tables = new CalcTable();
	private JTextField translateResultField, evaluateResultField;
	private short width = 1282, height = 642;

	public CrazyCalculator(){
		super("Crazy Calculator");
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("CalcIcon.jpg"));
		ImageIcon icon = new ImageIcon(image);
		setIconImage(icon.getImage());
		setLayout(null);
		
		whole = new JPanel();
		whole.setLayout(new BorderLayout(5, 5));

		field = new JPanel();
		field.setLayout(new BorderLayout());

		text = new JTextField(77);
		text.setFont(new Font("Helvetica", Font.BOLD, fontSize));
		text.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		text.setBackground(new Color(245,245,245));
		text.setForeground(digitColor);
		text.setHorizontalAlignment(SwingConstants.RIGHT);
		text.setEditable(false);
		field.add(text, BorderLayout.CENTER);
		field.setBackground(background);

		center = new JPanel();
		center.setLayout(new GridLayout(1, 1, 15, 15));


		GridBagLayout gridbag = new GridBagLayout();
		numbers = new JPanel(gridbag);
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.weightx = 1.0;
		constraint.weighty = 1.0;
		constraint.ipadx = 100;
		constraint.ipady = 100;
		for(int i = 0; i < 20; i++) {
			num[i] = new JButton();
			num[i].setFont(new Font("Helvetica", Font.PLAIN, 23));
			constraint.gridx = i%5;
			constraint.gridy = (int)((float)i/5.00);
			if(i == 15){
				num[i].setEnabled(false);
				num[++i] = new JButton();
				num[i].setFont(new Font("Helvetica", Font.PLAIN, 23));
				constraint.gridwidth = 2;
				numbers.add(num[i], constraint);
				constraint.gridwidth = 1;
			}else{
				numbers.add(num[i], constraint);
			}
			num[i].setBorder(javax.swing.BorderFactory.createLineBorder(background));
		}
		
		num[18].setFont(new Font("Helvetica", Font.PLAIN, 18));
		num[0].setText("7");
		num[1].setText("8");
		num[2].setText("9");
		num[3].setText("(");
		num[4].setText(")");
		num[5].setText("4");
		num[6].setText("5");
		num[7].setText("6");
		num[8].setText("/");
		num[9].setText("*");
		num[10].setText("1");
		num[11].setText("2");
		num[12].setText("3");;
		num[13].setText("-");
		num[14].setText("+");
		num[15].setEnabled(false);
		num[16].setText("0");
		num[17].setText("C");
		num[18].setText("DEL");
		num[19].setText("=");
		
		for(int i = 0; i < 20; i++){
			try{
				num[i].setBackground(operatorColor);
				num[i].setForeground(Color.WHITE);
			}catch(NumberFormatException e){
				num[i].setBackground(digitColor);
				num[i].setForeground(Color.WHITE);
			}
		}
		
		for(int i = 0; i < 20; i++){
			try{
				int number = Integer.parseInt(num[i].getText());
				num[i].setBackground(digitColor);
				num[i].setForeground(Color.WHITE);
			}catch(NumberFormatException e){
				num[i].setBackground(operatorColor);
				num[i].setForeground(Color.WHITE);
			}
		}
		center.add(numbers);

		whole.add(field, BorderLayout.NORTH);
		whole.add(center, BorderLayout.CENTER);

		north = new JPanel();
		south = new JPanel();
		east = new JPanel();
		west = new JPanel();
		
		Font font = new Font("Helvetica", Font.PLAIN, 15);
		Font font2 = new Font("Helvetica", Font.PLAIN, 20);
		JPanel resultLabelsPanel = new JPanel(new GridLayout(2,1));
		resultLabelsPanel.setBackground(background);
		JLabel transResultLabel = new JLabel("Infix to Postfix Result: ");
		transResultLabel.setFont(font);
		transResultLabel.setForeground(digitColor);
		transResultLabel.setBackground(background);
		JLabel evalResultLabel = new JLabel("Postfix Evaluation Result: ");
		evalResultLabel.setFont(font);
		evalResultLabel.setForeground(digitColor);
		evalResultLabel.setBackground(background);
		resultLabelsPanel.add(transResultLabel);
		resultLabelsPanel.add(evalResultLabel);
		
		JPanel resultFieldsPanel = new JPanel(new GridLayout(2,1));
		resultFieldsPanel.setBackground(background);
		translateResultField = new JTextField(20);
		translateResultField.setFont(font2);
		translateResultField.setForeground(digitColor);
		translateResultField.setBackground(background);
		evaluateResultField = new JTextField(20);
		evaluateResultField.setFont(font2);
		evaluateResultField.setForeground(digitColor);
		evaluateResultField.setBackground(background);
		resultFieldsPanel.add(translateResultField);
		resultFieldsPanel.add(evaluateResultField);
		
		JLabel resultsLabel = new JLabel("Results", SwingConstants.CENTER);
		resultsLabel.setFont(new Font("Helvetica", Font.PLAIN, 23));
		resultsLabel.setForeground(digitColor);
		resultsLabel.setBackground(background);
		
		south.setLayout(new BorderLayout());
		south.add(resultsLabel, BorderLayout.NORTH);
		south.add(resultLabelsPanel, BorderLayout.WEST);
		south.add(resultFieldsPanel, BorderLayout.CENTER);
		
		calculatorPanel = new JPanel();
		calculatorPanel.setLayout(new BorderLayout());
		calculatorPanel.setBackground(background);
		calculatorPanel.add(north, BorderLayout.NORTH);
		calculatorPanel.add(south, BorderLayout.SOUTH);
		calculatorPanel.add(east, BorderLayout.EAST);
		calculatorPanel.add(west, BorderLayout.WEST);
		calculatorPanel.add(whole, BorderLayout.CENTER);

		north.setBackground(background);
		south.setBackground(background);
		east.setBackground(background);
		west.setBackground(background);
		whole.setBackground(background);

		setVisible(true);
		activeWindow = true;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(width,height);
		setResizable(false);

		for(int j = 0; j < 20; j++) {
			num[j].addActionListener(this);
		}
		timer.start();
		whole.addKeyListener(new KeyReader());
		addWindowListener(new WindowAdapter(){
			public void windowActivated(WindowEvent e){
				activeWindow = true;
			}
			public void windowDeactivated(WindowEvent e){
				activeWindow = false;
			}
		});
		
		translateAnimPanel = new JPanel(new BorderLayout());
		translateAnimPanel.add(trans, BorderLayout.CENTER);
		translateAnimPanel.setBackground(background);
		JLabel tl = new JLabel("Infix to Postfix Animation", SwingConstants.CENTER);
		tl.setFont(new Font("Helvetica", Font.PLAIN, 18));
		tl.setForeground(digitColor);
		translateAnimPanel.add(tl, BorderLayout.NORTH);
		
		evaluateAnimPanel = new JPanel(new BorderLayout());
		evaluateAnimPanel.add(eval, BorderLayout.CENTER);
		evaluateAnimPanel.setBackground(background);
		JLabel el = new JLabel("Postfix Evaluation Animation", SwingConstants.CENTER);
		el.setFont(new Font("Helvetica", Font.PLAIN, 18));
		el.setForeground(digitColor);
		evaluateAnimPanel.add(el, BorderLayout.NORTH);
		
		translateTabs.addTab("Infix to Postfix Animation", translateAnimPanel);
		translateTabs.addTab("Infix to Postfix Table", tables.transPanel);
		translateTabs.setBackground(digitColor);
		evaluateTabs.addTab("Postfix Evaluation Animation", evaluateAnimPanel);
		evaluateTabs.addTab("Postfix Evaluation Table", tables.evalPanel);
		evaluateTabs.setBackground(digitColor);
		add(calculatorPanel);
		add(translateTabs);
		add(evaluateTabs);
		Dimension dimension = calculatorPanel.getPreferredSize();
		calculatorPanel.setBounds(0, 0, 655, 612);
		translateTabs.setBounds(655, 0, 620, 319);
		evaluateTabs.setBounds(655, 319, 620, 293);
		repaint();
		revalidate();
		
		animationThread = null;
		animationThread = new Thread(this);
		animationThread.start();
		
		trans.pausePlay.addActionListener(this);
		eval.pausePlay.addActionListener(this);
	}

	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == timer && activeWindow){
			whole.requestFocus();
			return;
		}
		if(runningAnimation){return;}
		if(event.getSource() == trans.pausePlay){
			trans.runSignal = true;
		}else if(event.getSource() == eval.pausePlay){
			eval.runSignal = true;
		}
		for(int i = 0; i < num.length; i++) {
			if(event.getSource() == num[i]) {
				if(num[i].getText() == "C") {
					text.setText(" ");
					op = "";
					break;
				} else if(num[i].getText() == "=") {
					PseudoArray<String> postfixed = infixToPostfix(op);
					op = postfixCalculate(postfixed);
					text.setText(op);
					op = "";
					fontSize = 30;
					text.setFont(new Font("Helvetica", Font.BOLD, fontSize));
					break;
				} else if(num[i].getText() == "DEL") {
					String screenText = text.getText();
					if(screenText.trim().equals("Error!")){
						screenText = "";
					}else if(screenText.length() == 0){

					}else{
						for(int j = 0; j < screenText.length(); j++){
							if(screenText.charAt(j) == '\n'){
								screenText = screenText.substring(0, j);
								break;
							}
						}
						screenText = screenText.substring(0, screenText.length()-1);
					}
					text.setText(" " + screenText.trim());
					op = screenText;
					if(text.getText().length()*(fontSize+1) < 1140 && fontSize < 30){
						fontSize+=1;
						text.setFont(new Font("Helvetica", Font.BOLD, fontSize));
					}
				} else {
					op += num[i].getText();
					text.setText(op);
					break;
				}
			}
		}
		
		if(text.getText().length()*fontSize >= 1140 && fontSize > 12){
			fontSize--;
			text.setFont(new Font("Helvetica", Font.BOLD, fontSize));
		}
	}

	private class KeyReader extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			if(runningAnimation){return;}
			String pressed = String.valueOf(e.getKeyChar());
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				PseudoArray<String> postfixed = infixToPostfix(op);
				op = postfixCalculate(postfixed);
				text.setText(op);
				fontSize = 30;
				text.setFont(new Font("Helvetica", Font.BOLD, fontSize));
				op = "";
			} else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				String screenText = text.getText();
				if(screenText.trim().equals("Error!")){
					screenText = "";
				}else if(screenText.length() == 0){

				}else{
					for(int j = 0; j < screenText.length(); j++){
						if(screenText.charAt(j) == '\n'){
							screenText = screenText.substring(0, j);
							break;
						}
					}
					screenText = screenText.substring(0, screenText.length()-1);
				}
				text.setText(" " + screenText.trim());
				op = screenText;
				
				if(text.getText().length()*(fontSize+1) < 1140 && fontSize < 30){
					fontSize+=1;
					text.setFont(new Font("Helvetica", Font.BOLD, fontSize));
				}
				
			} else if((pressed.equals("+")) || (pressed.equals("-")) || (pressed.equals("*")) || (pressed.equals("/")) || (pressed.equals("(")) || (pressed.equals(")"))) {
				op += pressed;
				text.setText(op);
			}
			for(int i = 0; i < 10; i++){
				if(pressed.equals(String.valueOf(i))){
					op += i;
					text.setText(op);
				}
			}
			if(text.getText().length()*fontSize >= 1140 && fontSize > 12){
				fontSize--;
				text.setFont(new Font("Helvetica", Font.BOLD, fontSize));
			}
		}
	}
	
	public static void main(String[] args) {
		new CrazyCalculator();
	}
	
	public PseudoArray<String> infixToPostfix(String infix){
		listTranslation = null;
		PseudoQueue<String> statusList = new PseudoQueue<>();
		translateCtr = 0;
		translateCurr = 0;
		infix = infix.trim();
		if(infix.equals("")){
			infix = "0";
		}
		PseudoStack<Character> operations = new PseudoStack<>();
		PseudoArray<String> postfix = new PseudoArray<>(infix.length());
		char top = 0;
		boolean stackEmpty = false;
		str = "";
		for(int i = 0; i < infix.length(); i++){
			if(infix.charAt(i) > 47 && infix.charAt(i) < 58){ // checks if the element is a digit
				int j = 1;
				while( (i+j < infix.length()) && (infix.charAt(i+j) > 47 && infix.charAt(i+j) < 58)){ // checks the elements next to it until it's not a digit
					j++;
				}
				postfix.add(infix.substring(i,i+j));
				str = infix.substring(i, i+j) + "\nCommit\n";
				i += j-1;
				if(i+1 < infix.length() && infix.charAt(i+1) == '('){
					infix = infix.substring(0,i+1) + "*" + infix.substring(i+1, infix.length());
				}
			}else{ // reads a non digit character in String infix
				str = String.valueOf(infix.charAt(i) + "\n");
				try{
					top = operations.peek();
				}catch(EmptyStackException e){top = 0;}
				if( ((infix.charAt(i) == '+' || infix.charAt(i) == '-') && (top == '+' || top == '-' || top == '*' || top == '/')) || ((infix.charAt(i) == '*' || infix.charAt(i) == '/') && (top == '*' || top == '/')) ){
					String poppedOperation = "";
					try{
						poppedOperation = String.valueOf(operations.pop());
						postfix.add(poppedOperation);
					}catch(EmptyStackException e){}
					str += "Pop |" + poppedOperation + "|";
					operations.push(infix.charAt(i));
					top = operations.pop();
					char top2 = 0;
					try{
						top2 = operations.peek();
					}catch(EmptyStackException ex){}
					finally{
						operations.push(top);
					}
					while(((top == '+' || top == '-') && (top2=='+' || top2 == '-')) || ((top == '*' || top == '/') && (top2=='+' || top2 == '-' || top2=='*' || top2 == '/'))){
						try{
							top = operations.pop();
							char buffertop = operations.pop();
							postfix.add(String.valueOf(buffertop));
							str += "|"+buffertop+"|";
							operations.push(top);
							top = operations.pop();
						}catch(EmptyStackException ex){break;}
						try{
							top2 = operations.peek();
						}catch(EmptyStackException ex){break;}
						finally{
							operations.push(top);
						}
					}
					str+= ", Commit and Push |" + String.valueOf(infix.charAt(i)) + "|\n";
				}else if(top == 0){
					operations.push(infix.charAt(i));
					str += "Push |" + String.valueOf(infix.charAt(i)) + "|\n";
				}else if( infix.charAt(i) == ')' ){
					try{
						top = operations.pop();
					}catch(EmptyStackException e){
						String commit = "";
						for(int j = 0; j < postfix.size(); j++){
							commit += postfix.get(j)==null?"":postfix.get(j);
						}
						str += "Error! ')' not found!\n";
						str = infix.substring(0, i+1) + "\n" + str + commit + operations.toString() + "\n";
						statusList.enqueue(str);
						translateCtr++;
						listTranslation = new String[translateCtr];
						for(int x = 0; x < translateCtr; x++){
							listTranslation[x] = statusList.dequeue();
						}
						return null;
					}
					str += "Pop |" + top + "|";
					while( top != '(' ){
						postfix.add(String.valueOf(top));
						try{
							top = operations.pop();
						}catch(EmptyStackException e){
							String commit = "";
							for(int j = 0; j < postfix.size(); j++){
								commit += postfix.get(j)==null?"":postfix.get(j);
							}
							str += ". Error! Stack empty and ')' not found!\n";
							str = infix.substring(0, i+1) + "\n" + str + commit + operations.toString() + "\n";
							statusList.enqueue(str);
							translateCtr++;
							listTranslation = new String[translateCtr];
							for(int x = 0; x < translateCtr; x++){
								listTranslation[x] = statusList.dequeue();
							}
							return null;
						}
						str += "|" + String.valueOf(top) + "|";
					}
					str += " and Commit\n";
					if( i+1< infix.length() && (infix.charAt(i+1) == '(' || (infix.charAt(i+1) > 47 && infix.charAt(i+1) < 58) )){
						infix = infix.substring(0,i+1) + "*" + infix.substring(i+1, infix.length());
					}
				}else{
					operations.push(infix.charAt(i));
					str += "Push |" + String.valueOf(infix.charAt(i)) + "|\n";
				}
			}
			String commited = "";
			for(int j = 0; j < postfix.size(); j++){
				commited += postfix.get(j)==null?"":postfix.get(j);
			}
			str = infix.substring(0,i+1) + "\n" + str + commited + "\n" + operations.toString() + "\n";
			statusList.enqueue(str);
			translateCtr++;
		}
		
		//After checking the end of infix string, all operations in the stack is popped
		try{
			top = operations.pop();
			str = infix + "\nEND\nPop |" + String.valueOf(top) + "|";
		}catch(EmptyStackException e){
			stackEmpty = true;
		}
		while(!stackEmpty){
			try{
				postfix.add(String.valueOf(top));
			}catch(ArrayIndexOutOfBoundsException e){
				return null;
			}
			try{
				top = operations.pop();
				str += "|" + String.valueOf(top) + "|";
			}catch(EmptyStackException e){
				stackEmpty = true;
			}
			if(top == '(' || top == ')'){
				return null;
			}
		}
		str += "\n";
		String commited = "";
		for(int j = 0; j < postfix.size(); j++){
			commited += postfix.get(j)==null?"":postfix.get(j);
		}
		str += commited + "\n";
		statusList.enqueue(str);
		translateCtr++;
		listTranslation = new String[translateCtr];
		for(int i = 0; i < translateCtr; i++){
			listTranslation[i] = statusList.dequeue();
		}
		return postfix;
	}
	
	public String postfixCalculate(PseudoArray<String> postfix){
		listEvaluation = null;
		PseudoQueue<String> statusList = new PseudoQueue<>();
		evaluateCtr = 0;
		evaluateCurr = 0;
		if(postfix == null){
			str = " \n \nError Occured!";
			statusList.enqueue(str);
			evaluateCtr++;
			listEvaluation = new String[evaluateCtr];
			for(int j = 0; j < evaluateCtr; j++){
				listEvaluation[j] = statusList.dequeue();
			}
			printToCmd();
			return "Error!";
		}
		PseudoStack<Double> numbers = new PseudoStack<>();
		for(int i = 0; i < postfix.size(); i++){
			str = "";
			for(int j = 0; j <= i; j++){
				if(!(postfix.get(j)==null)){
					str += postfix.get(j);
				}else{
					continue;
				}
			}
			str += "\n";
			try{// tries to parse an element in the array, if an exception occurs it is automatically not a number(or it is an operation)
				if(!(postfix.get(i)==null)){
					double num = Double.parseDouble(postfix.get(i));
					numbers.push(num);
					str += num + "\nPush |" + num + "|\n";
				}else{
					continue;
				}
			}catch(NumberFormatException e){
				str += postfix.get(i) + "\n";
				double number = 0;
				try{
					if(postfix.get(i).equals("+")){
						double num1 = numbers.pop(), num2 = numbers.pop();
						number = num1+num2;
						str += "Pop |" + num1 + "||" + num2 + "| then add. Result: " + number + ". ";
					}else if(postfix.get(i).equals("-")){
						double num1 = numbers.pop(), num2 = numbers.pop();
						number = num2-num1;
						str += "Pop |" + num1 + "||" + num2 + "| then subtract. Result: " + number + ". ";
					}else if(postfix.get(i).equals("*")){
						double num1 = numbers.pop(), num2 = numbers.pop();
						number = num1*num2;
						str += "Pop |" + num1 + "||" + num2 + "| then multiply. Result: " + number + ". ";
					}else if(postfix.get(i).equals("/")){
						double divisor = numbers.pop(), dividend = numbers.pop();
						number = dividend/divisor;
						if(divisor == 0.0){
							throw new NullPointerException();
						}
						str += "Pop |" + divisor + "||" + dividend + "| then divide. Result: " + number + ". ";
					}else{
						str += "Error!";
						statusList.enqueue(str);
						evaluateCtr++;
						for(int j = 0; j < evaluateCtr; j++){
							listEvaluation[j] = statusList.dequeue();
						}
						printToCmd();
						return "Error!";
					}
				}catch(NullPointerException ex){
					str += "Error! Stop Evaluation!";
					statusList.enqueue(str);
					evaluateCtr++;
					listEvaluation = new String[evaluateCtr];
					for(int j = 0; j < evaluateCtr; j++){
						listEvaluation[j] = statusList.dequeue();
					}
					printToCmd();
					return "Error!";
				}catch(EmptyStackException exc){
					str += "Error! Stop Evaluation!";
					statusList.enqueue(str);
					evaluateCtr++;
					listEvaluation = new String[evaluateCtr];
					for(int j = 0; j < evaluateCtr; j++){
						listEvaluation[j] = statusList.dequeue();
					}
					printToCmd();
					return "Error!";
				}
				numbers.push(number);
				str += "Push |"+ number + "|\n";
			}
			str += numbers.toString() + "\n";
			statusList.enqueue(str);
			evaluateCtr++;
		}
		double result = 0.0f;
		try{
			result = numbers.pop();
		}catch(EmptyStackException e){}
		String ret = String.valueOf(result);
		if(result%1.0==0.0 && (long)result < Long.MAX_VALUE){
			long toInteger = (long)result;
			ret = String.valueOf(toInteger);
		}
		for(int i = 0; i < ret.length(); i++){
			if(ret.charAt(i) == 'E'){
				ret = "(" + ret.substring(0, i) + ")*10^(" + ret.substring(i+1, ret.length()) + ")";
				break;
			}
		}
		listEvaluation = new String[evaluateCtr];
		for(int i = 0; i < evaluateCtr; i++){
			listEvaluation[i] = statusList.dequeue();
		}
		printToCmd();
		evaluateResultField.setText(ret);
		return ret;
	}
	
	public void printToCmd(){
		try{
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		}catch(Exception e){}
		translateTable = null;
		evaluateTable = null;
		translateTable = new String[translateCtr][5];
		evaluateTable = new String[evaluateCtr][4];
		int[] translateTableWidth = {6,4,6,8,5};
		int[] evaluateTableWidth = {6,4,6,5};
		
		for(int i = 0; i < translateCtr; i++){
			for(int j = 0, start = 0, end = 0; j < 5; j++){
				while(end < listTranslation[i].length() && listTranslation[i].charAt(end) != '\n'){end++;}
				translateTable[i][j] = listTranslation[i].substring(start, end);
				if(translateTable[i][j].length() > translateTableWidth[j]){
					translateTableWidth[j] = translateTable[i][j].length();
				}
				start = ++end;
				if(start >= listTranslation[i].length() && translateTable[i][j] == null){
					translateTable[i][j] = " ";
				}
			}
		}
		
		for(int i = 0; i < evaluateCtr; i++){
			for(int j = 0, start = 0, end = 0; j < 4; j++){
				if(start >= listEvaluation[i].length() && evaluateTable[i][j] == null){
					evaluateTable[i][j] = " ";
					continue;
				}
				while(listEvaluation[i].length() > end && listEvaluation[i].charAt(end) != '\n'){end++;}
				evaluateTable[i][j] = listEvaluation[i].substring(start, end);
				if(evaluateTable[i][j].length() > evaluateTableWidth[j]){
					evaluateTableWidth[j] = evaluateTable[i][j].length();
				}
				start = ++end;
			}
		}
		
		String spaces = "                                                                                                                                                                                                                                                                                                               ";
		System.out.println("\nInfix To Postfix:");
		System.out.println("Parsed" + spaces.substring(0, translateTableWidth[0]-6) + "|Read" + spaces.substring(0, translateTableWidth[1]-4) + "|Action" + spaces.substring(0, translateTableWidth[2]-6) + "|Commited" + spaces.substring(0, translateTableWidth[3]-8) + "|Stack" + spaces.substring(0, translateTableWidth[4]-5) + "|");
		for(int i = 0; i < translateCtr; i++){
			for(int j = 0; j < 5; j++){
				System.out.print(translateTable[i][j] + spaces.substring(0,translateTableWidth[j]-translateTable[i][j].length()) + "|");
			}
			System.out.println();
		}
		
		System.out.println("\nPostfix Evaluation:");
		System.out.println("Parsed" + spaces.substring(0, evaluateTableWidth[0]-6) + "|Read" + spaces.substring(0, evaluateTableWidth[1]-4) + "|Action" + spaces.substring(0, evaluateTableWidth[2]-6) + "|Stack" + spaces.substring(0, evaluateTableWidth[3]-5) + "|");
		for(int i = 0; i < evaluateCtr; i++){
			for(int j = 0; j < 4; j++){
				System.out.print(evaluateTable[i][j] + spaces.substring(0,evaluateTableWidth[j]-evaluateTable[i][j].length()) + "|");
			}
			System.out.println();
		}
		tables.printTable(translateTable, evaluateTable);
		translateResultField.setText(translateTable[translateCtr-1][3]);
	}
	
	public void run(){
		while(1==1){
			System.out.print("");//a magical statement, do not delete, you have been warned
			if(trans.runSignal){
				try{
					runningAnimation = true;
					trans.clean();
					trans.pausePlay.setIcon(trans.pauseIcon);
					trans.play(translateTable);
				}catch(NullPointerException e){
					trans.clean();
				}finally{
					trans.pausePlay.setIcon(trans.playIcon);
					runningAnimation = false;
					trans.runSignal = false;
				}
			}
			if(eval.runSignal){
				try{
					runningAnimation = true;
					eval.clean();
					eval.pausePlay.setIcon(eval.pauseIcon);
					eval.play(evaluateTable);
				}catch(NullPointerException e){
					eval.clean();
				}finally{
					eval.pausePlay.setIcon(eval.playIcon);
					runningAnimation = false;
					eval.runSignal = false;
				}
			}
		}
	}
}
