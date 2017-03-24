import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EmptyStackException;

public class CrazyCalculator extends JFrame implements ActionListener{
	private JPanel whole, north, south, east, west;
	private JPanel field, center, numbers, numbers1, numbers2;
	private JButton[] num = new JButton[20];
	private JTextField text;
	private JScrollBar textScrollBar;
	private JTextArea area;
	private String op = "", str = "";
	private Timer timer = new Timer(1, this);
	private boolean activeWindow;
	private Color background = new Color(122,122,122), digitButtons = new Color(200,200,200), operatorColor = new Color(255,161,0);
	
	public CrazyCalculator(){
		super("Crazy Calculator");
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("CalcIcon.jpg"));
		ImageIcon icon = new ImageIcon(image);
		setIconImage(icon.getImage());
		setLayout(new BorderLayout());
		
		whole = new JPanel();
		whole.setLayout(new BorderLayout(5, 5));
		
		field = new JPanel();
		field.setLayout(new GridLayout(2, 1));
		
		text = new JTextField(77);
		text.setFont(new Font("Courier New", Font.BOLD, 20));
		text.setBackground(background);
		text.setForeground(Color.WHITE);
		text.setHorizontalAlignment(SwingConstants.RIGHT);
		text.setEditable(false);
		textScrollBar = new JScrollBar(JScrollBar.HORIZONTAL);
		BoundedRangeModel brm = text.getHorizontalVisibility();
		textScrollBar.setModel(brm);
		textScrollBar.setVisible(false);
		field.add(text);
		field.add(textScrollBar);
		field.setBackground(background);
		
		center = new JPanel();
		center.setLayout(new GridLayout(1, 1, 15, 15));
		
		GridBagLayout gridbag = new GridBagLayout();
		numbers = new JPanel(gridbag);
		GridBagConstraints constraint = new GridBagConstraints();
		numbers.setBackground(background);
		
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.weightx = 1.0;
		constraint.weighty = 1.0;
		constraint.ipadx = 100;
		constraint.ipady = 50;
		for(int i = 0; i < 20; i++) {
			num[i] = new JButton();
			num[i].setFont(new Font("Courier New", Font.BOLD, 18));
			constraint.gridx = i%5;
			constraint.gridy = (int)((float)i/5.00);
			if(i == 15){
				num[++i] = new JButton();
				num[i].setFont(new Font("Courier New", Font.BOLD, 18));
				constraint.gridwidth = 2;
				numbers.add(num[i], constraint);
				constraint.gridwidth = 1;
			}else{
				numbers.add(num[i], constraint);
			}
		}
		
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
		num[17].setText("<=");
		num[18].setText("C");
		num[19].setText("=");
		
		for(int i = 0; i < 20; i++){
			try{
				int number = Integer.parseInt(num[i].getText());
				num[i].setBackground(digitButtons);
			}catch(NumberFormatException e){
				num[i].setBackground(operatorColor);
			}
		}
		center.add(numbers);
		
		whole.add(field, BorderLayout.NORTH);
		whole.add(center, BorderLayout.CENTER);
		
		north = new JPanel();
		south = new JPanel();
		east = new JPanel();
		west = new JPanel();
		
		area = new JTextArea(10, 69);
		area.setFont(new Font("Courier New", Font.BOLD, 12));
		area.setBackground(background);
		area.setForeground(Color.WHITE);
		area.setEditable(false);
		south.add(new JScrollPane(area), BorderLayout.CENTER);
		
		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
		add(east, BorderLayout.EAST);
		add(west, BorderLayout.WEST);
		add(whole, BorderLayout.CENTER);
		
		north.setBackground(background);
		south.setBackground(background);
		east.setBackground(background);
		west.setBackground(background);
		whole.setBackground(background);
		
		setVisible(true);
		activeWindow = true;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500, 550);
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
	}
	
	public void actionPerformed(ActionEvent event) {
		if(event.getSource() == timer && activeWindow){
			whole.requestFocus();
			return;
		}
		for(int i = 0; i < num.length; i++) {
			if(event.getSource() == num[i]) {
				if(num[i].getText() == "C") {
					area.setText("");
					text.setText(" ");
					op = "";
					break;
				} else if(num[i].getText() == "=") {
					area.setText("");
					PseudoArray<String> postfixed = infixToPostfix(op);
					op = postfixCalculate(postfixed);
					text.setText(op);
					op = "";
					break;
				} else if(num[i].getText() == "<=") {
					String screenText = text.getText();
					screenText = op;
					System.out.println("|" + screenText + "|");
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
				} else {
					op += num[i].getText();
					text.setText(op);
					break;
				}
			}
		}
		if(text.getText().trim().length()>67){
			textScrollBar.setVisible(true);
		}else{
			textScrollBar.setVisible(false);
		}
	}
	
	private class KeyReader extends KeyAdapter{
		public void keyPressed(KeyEvent e){
			String pressed = String.valueOf(e.getKeyChar());
			if(e.getKeyCode() == KeyEvent.VK_ENTER || pressed.equals("=")) {
				area.setText("");
				PseudoArray<String> postfixed = infixToPostfix(op);
				op = postfixCalculate(postfixed);
				text.setText(op);
				op = "";
			} else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				String screenText = text.getText();
				screenText = op;
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
			if(text.getText().trim().length()>67){
				textScrollBar.setVisible(true);
			}else{
				textScrollBar.setVisible(false);
			}
		}
	}
	
	public static void main(String[] args) {
		new CrazyCalculator();
	}
	
	public PseudoArray<String> infixToPostfix(String infix){
		int width = infix.length();
		infix = infix.trim();
		PseudoStack<Character> operations = new PseudoStack<>();
		PseudoArray<String> postfix = new PseudoArray<>(infix.length());
		char top = 0;
		boolean stackEmpty = false;
		str = "Infix to Postfix:\n";
		area.append(str);
		for(int i = 0; i < infix.length(); i++){
			if(infix.charAt(i) > 47 && infix.charAt(i) < 58){ // checks if the element is a digit
				int j = 1;
				while( (i+j < infix.length()) && (infix.charAt(i+j) > 47 && infix.charAt(i+j) < 58)){ // checks the elements next to it until it's not a digit
					j++;
				}
				postfix.add(infix.substring(i,i+j));
				str = "Read: " + infix.substring(i, i+j) + "\nAction: Commit\n";
				area.append(str);
				i += j-1;
				if(i+1 < infix.length() && infix.charAt(i+1) == '('){
					infix = infix.substring(0,i+1) + "*" + infix.substring(i+1, infix.length());
				}
			}else{ // reads a non digit character in String infix
				str = "Read: " + String.valueOf(infix.charAt(i) + "\nAction: ");
				area.append(str);
				try{
					top = operations.peek();
				}catch(EmptyStackException e){top = 0;}
				if( ((infix.charAt(i) == '+' || infix.charAt(i) == '-') && (top == '+' || top == '-' || top == '*' || top == '/')) || ((infix.charAt(i) == '*' || infix.charAt(i) == '/') && (top == '*' || top == '/')) ){
					String poppedOperation = "";
					try{
						poppedOperation = String.valueOf(operations.pop());
						postfix.add(poppedOperation);
					}catch(EmptyStackException e){}
					str = "Pop |" + poppedOperation + "| and Push |" + String.valueOf(infix.charAt(i)) + "|";
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
					str+="\n";
					area.append(str);
				}else if(top == 0){
					operations.push(infix.charAt(i));
					str = "Push |" + String.valueOf(infix.charAt(i)) + "|\n";
					area.append(str);
				}else if( infix.charAt(i) == ')' ){
					try{
						top = operations.pop();
					}catch(EmptyStackException e){System.out.println("here");return null;}
					str = "Pop";
					while( top != '(' ){
						postfix.add(String.valueOf(top));
						try{
							top = operations.pop();
						}catch(EmptyStackException e){System.out.println("here1");return null;}
						str += "|" + String.valueOf(top) + "|";
					}
					str += "\n";
					area.append(str);
					if( i+1< infix.length() && (infix.charAt(i+1) == '(' || (infix.charAt(i+1) > 47 && infix.charAt(i+1) < 58) )){
						infix = infix.substring(0,i+1) + "*" + infix.substring(i+1, infix.length());
					}
				}else{
					operations.push(infix.charAt(i));
					str = "Push |" + String.valueOf(infix.charAt(i)) + "|\n";
					area.append(str);
				}
			}
			String commited = "";
			for(int j = 0; j < postfix.size(); j++){
				commited += postfix.get(j)==null?"":postfix.get(j);
			}
			str = "Parsed: " + infix.substring(0,i+1) + "\nCommited: " + commited + "\nStack: " + operations.toString() + "\n";
			area.append(str + "\n");
		}
		
		//After checking the end of infix string, all operations in the stack is popped
		try{
			top = operations.pop();
			str = "Read: END\nPop |" + String.valueOf(top) + "|";
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
		area.append(str);
		str = "Infix expression: " + infix + "\n";
		area.append(str);
		return postfix;
	}
	
	public String postfixCalculate(PseudoArray<String> postfix){
		if(postfix == null){
			str = "Error Occurred!";
			area.append(str);
			return "Error!";
		}
		str = "Postfix result: ";
		area.append(str);
		for(int i = 0; i < postfix.ctr(); i++){
			str = "" + postfix.get(i);
			area.append(str);
		}
		area.append("\n");
		PseudoStack<Double> numbers = new PseudoStack<>();
		str = "\nPostfix Evaluation:\n";
		area.append(str);
		for(int i = 0; i < postfix.size(); i++){
			try{// tries to parse an element in the array, if an exception occurs it is automatically not a number(or it is an operation)
				if(!(postfix.get(i)==null)){
					double num = Double.parseDouble(postfix.get(i));
					numbers.push(num);
					str = "\nRead: " + num + "\nAction: Push " + num + "\n";
					area.append(str);
				}else{
					continue;
				}
			}catch(NumberFormatException e){
				str = "\nRead: " + postfix.get(i) + "\nAction: ";
				area.append(str);
				double number = 0;
				try{
					if(postfix.get(i).equals("+")){
						double num1 = numbers.pop(), num2 = numbers.pop();
						number = num1+num2;
						str = "Popped " + num1 + " and " + num2 + " then add. Result: " + number + "\n";
					}else if(postfix.get(i).equals("-")){
						double num1 = numbers.pop(), num2 = numbers.pop();
						number = num2-num1;
						str = "Popped " + num1 + " and " + num2 + " then subtract. Result: " + number + "\n";
					}else if(postfix.get(i).equals("*")){
						double num1 = numbers.pop(), num2 = numbers.pop();
						number = num1*num2;
						str = "Popped " + num1 + " and " + num2 + " then multiply. Result: " + number + "\n";
					}else if(postfix.get(i).equals("/")){
						double divisor = numbers.pop(), dividend = numbers.pop();
						if(divisor == 0.0){
							return "Error!";
						}
						number = dividend/divisor;
						str = "Popped " + divisor + " and " + dividend + " then divide. Result: " + number + "\n";
					}else{
						str = "Error!";
						return "Error!";
					}
				}catch(NullPointerException ex){
					break;
				}catch(EmptyStackException exc){
					str = "Error Occurred!";
					area.append(str);
					return "Error!";
				}
				numbers.push(number);
				str += "Push result: "+ number + "\n";
				area.append(str);
			}
			str = "Stack: " + numbers.toString() + "\n";
			area.append(str);
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
		str = "\nResult: " + ret + "\n";
		area.append(str);
		for(int i = 0; i < ret.length(); i++){
			if(ret.charAt(i) == 'E'){
				ret = "(" + ret.substring(0, i) + ")*10^(" + ret.substring(i+1, ret.length()) + ")";
				break;
			}
		}
		return ret;
	}
}