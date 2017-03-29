import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.EmptyStackException;

public class CrazyCalculator extends JFrame implements ActionListener{
	private JPanel whole, north, south, east, west;
	private JPanel field, center, numbers;
	private JButton[] num = new JButton[20];
	private JTextField text;
	private JTextArea area;
	private String op = "", str = "";
	private Timer timer = new Timer(1, this);
	private boolean activeWindow;
	private Color background = new Color(220,220,220), digitColor= new Color(140, 110, 100), operatorColor = new Color(82, 182, 172);
	private int fontSize = 30;
	
	public CrazyCalculator()
	{
		super("Crazy Calculator");
		
<<<<<<< HEAD
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("CalcIcon.jpg"));
=======
		Image image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("CalcIcon.jpeg"));
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
		ImageIcon icon = new ImageIcon(image);
		
		setIconImage(icon.getImage());
		setLayout(new BorderLayout());
		setVisible(true);
		activeWindow = true;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,500);
		setResizable(false);
		
		text = new JTextField(77);
		text.setForeground(digitColor);
		text.setBackground(new Color(245, 245, 245));
		text.setHorizontalAlignment(SwingConstants.RIGHT);
<<<<<<< HEAD
		text.setFont(new Font("Helvetica", Font.BOLD, fontSize));
=======
		text.setFont(new Font("Monospace", Font.BOLD, fontSize));
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
		text.setEditable(false);
		text.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		text.setText("0");
		

		area = new JTextArea(10, 43);
		area.setBackground(new Color(245, 245, 245));
		area.setForeground(digitColor);
		area.setEditable(false);
		
		

		GridBagLayout gridbag = new GridBagLayout();
		numbers = new JPanel(gridbag);
		GridBagConstraints constraint = new GridBagConstraints();
		constraint.fill = GridBagConstraints.HORIZONTAL;
		constraint.weightx = 1.0;
		constraint.weighty = 1.0;
		constraint.ipadx = 100;
		constraint.ipady = 50;
		
		for(int i = 0; i < 20; i++) {
			num[i] = new JButton();
			num[i].setFont(new Font("Monospace", Font.PLAIN, 18));
			constraint.gridx = i%5;
			constraint.gridy = (int)((float)i/5.00);
			if(i == 15)
			{
				num[++i] = new JButton();
				num[i].setFont(new Font("Monospace", Font.PLAIN, 18));
				
				constraint.gridwidth = 2;
				numbers.add(num[i], constraint);
				constraint.gridwidth = 1;
			}
			
			else{
				numbers.add(num[i], constraint);
			}
			
		} 
		
		for(int i = 0; i < 20; i++)
			num[i].setBorder(javax.swing.BorderFactory.createLineBorder(Color.WHITE));

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
<<<<<<< HEAD
		num[18].setFont(new Font("Helvetica", Font.BOLD, 14));
=======
		num[18].setFont(new Font("Monospace", Font.BOLD, 14));
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
		num[19].setText("=");
		
		for(int i = 0; i < 20; i++){
			try{
				Integer.parseInt(num[i].getText());
				num[i].setBackground(digitColor);
				num[i].setForeground(Color.WHITE);
			}catch(NumberFormatException e){
				num[i].setBackground(operatorColor);
				num[i].setForeground(Color.WHITE);
			}
		}

		field = new JPanel();
		field.setLayout(new GridLayout(1,1));
		field.setBackground(new Color(245,245,245));
		field.add(text);
		
		center = new JPanel();
		center.setLayout(new GridLayout(1, 1, 15, 15));
		center.setBackground(background);
		center.add(numbers);
		
		north = new JPanel();
		north.setBackground(background);
		
		south = new JPanel();
		south.add(new JScrollPane(area), BorderLayout.CENTER);
		south.setBackground(background);
		
		east = new JPanel();
		east.setBackground(background);
		
		west = new JPanel();
		west.setBackground(background);
		
		
		whole = new JPanel();
		whole.setLayout(new BorderLayout(5, 5));
		whole.setBackground(background);
		whole.add(field, BorderLayout.NORTH);
		whole.add(center, BorderLayout.CENTER);
<<<<<<< HEAD
=======
		
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6

		add(north, BorderLayout.NORTH);
		add(south, BorderLayout.SOUTH);
		add(east, BorderLayout.EAST);
		add(west, BorderLayout.WEST);
		add(whole, BorderLayout.CENTER);

<<<<<<< HEAD
		setVisible(true);
		activeWindow = true;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(500,500);
		setResizable(false);
=======
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
		
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
		repaint();
		revalidate();
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
				} else if(num[i].getText() == "DEL") {
					String screenText = text.getText();
					if(screenText.trim().equals("Error!")){
						screenText = "";
					if(text.getText().length()*(fontSize+1) < 810&& fontSize < 30){
						fontSize+=1;
<<<<<<< HEAD
						text.setFont(new Font("Helvetica", Font.BOLD, fontSize));
					}
					}else{
=======
						text.setFont(new Font("Monospace", Font.BOLD, fontSize));
					}
					}
					else{
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
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
					if(fontSize == 12)
						break;
					op += num[i].getText();
					text.setText(op);
					break;
					
					
				}
			}
		}
		
<<<<<<< HEAD
		if(text.getText().length()*fontSize >= 810 && fontSize > 12){
			fontSize--;
			text.setFont(new Font("Helvetica", Font.BOLD, fontSize));
=======
		if(text.getText().length()*fontSize >= 810){
			fontSize--;
			text.setFont(new Font("Monospace", Font.BOLD, fontSize));
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
		}
	}

	private class KeyReader extends KeyAdapter
	{
		public void keyPressed(KeyEvent e)
		{
			String pressed = String.valueOf(e.getKeyChar());
			
			if(e.getKeyCode() == KeyEvent.VK_ENTER || pressed.equals("="))
			{
				area.setText("");
				PseudoArray<String> postfixed = infixToPostfix(op);
				op = postfixCalculate(postfixed);
				text.setText(op);
				op = "";
			} 
			else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE) 
			{
				String screenText = text.getText();
				if(screenText.trim().equals("Error!"))
				{
					screenText = "";
				}
				else if(screenText.length() == 0)
				{

				}
				else
				{
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
				
				if(text.getText().length()*(fontSize+1) < 810&& fontSize < 30){
					fontSize+=1;
<<<<<<< HEAD
					text.setFont(new Font("Helvetica", Font.BOLD, fontSize));
=======
					text.setFont(new Font("Monospace", Font.BOLD, fontSize));
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
				}
			}
			else if((pressed.equals("+")) || (pressed.equals("-")) || (pressed.equals("*")) || (pressed.equals("/")) || (pressed.equals("(")) || (pressed.equals(")"))){
				op += pressed;
				text.setText(op);
			}
			
			for(int i = 0; i < 10; i++){
				if(pressed.equals(String.valueOf(i))){
					if(fontSize == 12)
						break;
					op += i;
					text.setText(op);
				}
			}
			
<<<<<<< HEAD
			if(text.getText().length()*fontSize >= 810 && fontSize > 12){
				fontSize--;
				text.setFont(new Font("Helvetica", Font.BOLD, fontSize));
=======
			if(text.getText().length()*fontSize >= 810){
				fontSize--;
				text.setFont(new Font("Monospace", Font.BOLD, fontSize));
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
			}
		}
	}

	public static void main(String[] args) {
		new CrazyCalculator();
	}

	public PseudoArray<String> infixToPostfix(String infix){
		infix = infix.trim();
		PseudoStack<Character> operations = new PseudoStack<>();
		PseudoArray<String> postfix = new PseudoArray<>(infix.length());
		char top = 0;
		boolean stackEmpty = false;
		str = "Infix to Postfix:\n";
		System.out.println("\nInfix to Postfix:\n");
		System.out.println("       READ		ACTION			STACK			COMMITTED			PARSED		");
		area.append(str);
		for(int i = 0; i < infix.length(); i++){
			if(infix.charAt(i) > 47 && infix.charAt(i) < 58){ 
				int j = 1;
				while( (i+j < infix.length()) && (infix.charAt(i+j) > 47 && infix.charAt(i+j) < 58)){
					j++;
				}
				postfix.add(infix.substring(i,i+j));
				str = "Read: " + infix.substring(i, i+j) + "\nAction: Commit\n";
				System.out.print("	" + infix.substring(i, i + j) + "		Commit		");
				area.append(str);
				i += j-1;
				if(i+1 < infix.length() && infix.charAt(i+1) == '('){
					infix = infix.substring(0,i+1) + "*" + infix.substring(i+1, infix.length());
				}
			}else{
				str = "Read: " + String.valueOf(infix.charAt(i) + "\nAction: ");
				System.out.print("	" + String.valueOf(infix.charAt(i)) + "	");
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
					System.out.print("     Pop and Push   	");
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
					System.out.print("	Push " + String.valueOf(infix.charAt(i)) + "		");
					area.append(str);
				}else if( infix.charAt(i) == ')' ){
					try{
						top = operations.pop();
					}catch(EmptyStackException e){
						return null;
					}
					str = "Pop";
					System.out.print("	Pop ");
					while( top != '(' ){
						postfix.add(String.valueOf(top));
						try{
							top = operations.pop();
						}catch(EmptyStackException e){
							return null;
						}
						str += "|" + String.valueOf(top) + "|";
						System.out.print(String.valueOf(top) + "		");
					}
					str += "\n";
					area.append(str);
					if( i+1< infix.length() && (infix.charAt(i+1) == '(' || (infix.charAt(i+1) > 47 && infix.charAt(i+1) < 58) )){
						infix = infix.substring(0,i+1) + "*" + infix.substring(i+1, infix.length());
					}
				}else{
					operations.push(infix.charAt(i));
					str = "Push |" + String.valueOf(infix.charAt(i)) + "|\n";
					System.out.print("			");
					area.append(str);
				}
			}
			String commited = "";
			for(int j = 0; j < postfix.size(); j++){
				commited += postfix.get(j)==null?"":postfix.get(j);
			}
			str = "Parsed: " + infix.substring(0,i+1) + "\nCommitted: " + commited + "\nStack: " + operations.toString() + "\n";
			try {
			String yo = operations.toString();
			for(int w = 0; w < yo.length(); w++) {
				if(yo.charAt(w) != '|')
					System.out.print(yo.charAt(w));
			}
			System.out.print("				" + commited + "			 " + infix.substring(0, i + 1));
			} catch(StringIndexOutOfBoundsException stEx) { }
			area.append(str + "\n");
			System.out.println();
		}

		try{
			top = operations.pop();
			str = "Read: END\nPop |" + String.valueOf(top) + "|";
<<<<<<< HEAD
			System.out.print("       END		Pop " + String.valueOf(top) + "		");
			String yoy = operations.toString();
			for(int w = 0; w < yoy.length(); w++) {
				if(yoy.charAt(w) != '|')
					System.out.print(yoy.charAt(w));
			}
			String committed = "";
			for(int j = 0; j < postfix.size(); j++){
				committed += postfix.get(j)==null?"":postfix.get(j);
			}
			System.out.println("				" + committed + top + "			 " + infix.substring(0, infix.length()));
=======
			
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
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
				System.out.print("       			Pop " + String.valueOf(top) + "		");
				String yoyo = operations.toString();
				for(int w = 0; w < yoyo.length(); w++) {
					if(yoyo.charAt(w) != '|')
						System.out.print(yoyo.charAt(w));
				}
				String committed = "";
				for(int j = 0; j < postfix.size(); j++){
					committed += postfix.get(j)==null?"":postfix.get(j);
				}
				System.out.println("				" + committed + top + "			 " + infix.substring(0, infix.length()));
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
		System.out.print("\nPostfix Result: ");
		area.append(str);
		for(int i = 0; i < postfix.ctr(); i++){
			str = "" + postfix.get(i);
			area.append(str);
			System.out.print(str);
		}
		area.append("\n");
		System.out.println("\n\nPostfix Calculation: \n");
		System.out.println("       READ		POP			ACTION			RESULT			PUSH		");
		PseudoStack<Double> numbers = new PseudoStack<>();
		for(int i = 0; i < postfix.size(); i++){
			try{
				if(!(postfix.get(i)==null)){
					double num = Double.parseDouble(postfix.get(i));
					numbers.push(num);
					str = "Pushed " + num + "\n";
				}
			}catch(NumberFormatException e){
				str = "\nRead: " + postfix.get(i) + "\n";
<<<<<<< HEAD
				System.out.print("        " + postfix.get(i) + "	");
=======
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
				area.append(str);
				double number = 0;
				try{
					if(postfix.get(i).equals("+")){
						double num1 = numbers.pop(), num2 = numbers.pop();
						number = num1+num2;
						
						str = "Popped " + num1 + " and " + num2 + " then add. Result: " + number + "\n";
<<<<<<< HEAD
						System.out.print("    " + num1 + " & " + num2 + "		       Add" + "			 " + number + "			" + number);
=======
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
						area.append(str);
					}else if(postfix.get(i).equals("-")){
						double num1 = numbers.pop(), num2 = numbers.pop();
						number = num2-num1;
						
						str = "Popped " + num1 + " and " + num2 + " then subtract. Result: " + number + "\n";
<<<<<<< HEAD
						System.out.print("    " + num1 + " & " + num2 + "		       Subtract" + "			 " + number + "			" + number);
=======
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
						area.append(str);
					}else if(postfix.get(i).equals("*")){
						double num1 = numbers.pop(), num2 = numbers.pop();
						number = num1*num2;
						
						str = "Popped " + num1 + " and " + num2 + " then multiply. Result: " + number + "\n";
<<<<<<< HEAD
						System.out.print("    " + num1 + " & " + num2 + "		       Multiply" + "			 " + number + "			" + number);
=======
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
						area.append(str);
					}else if(postfix.get(i).equals("/")){
						double divisor = numbers.pop(), dividend = numbers.pop();
						number = dividend/divisor;
						
						str = "Popped " + divisor + " and " + dividend + " then divide. Result: " + number + "\n";
<<<<<<< HEAD
						System.out.print("    " + divisor + " & " + dividend + "		       Divide" + "			 " + number + "			" + number);
=======
>>>>>>> 486b2397ec5258b4264d855c7fefe99b225409f6
						area.append(str);
					}else{
						return "Error!";
					}
				}catch(NullPointerException ex){
					break;
				}catch(EmptyStackException exc){
					str = "Error Occurred!";
					System.out.println("\n\nERROR OCCURED");
					area.append(str);
					System.out.println("\n-------------------------------------------------------------------------------------------------------------------------");
					return "Error!";
				}
				numbers.push(number);
				str = "Pushed: "+ number + "\n";
				area.append(str);
				System.out.println();
			}
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
		System.out.print("\n" + str);
		area.append(str);
		for(int i = 0; i < ret.length(); i++){
			if(ret.charAt(i) == 'E'){
				ret = "(" + ret.substring(0, i) + ")*10^(" + ret.substring(i+1, ret.length()) + ")";
				break;
			}
		}
		System.out.println("\n-------------------------------------------------------------------------------------------------------------------------");
		fontSize = 30;
		return ret;
	}
}
