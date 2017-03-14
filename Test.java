import java.util.EmptyStackException;

public class Test{
	public static void main(String[] args){
		PseudoArray<Integer> nums = new PseudoArray<>(10);
		for(int i = 0; i < nums.size(); i++){
			System.out.println(nums.add(i*2));//try changing add(i*2) to add(i, i*2);
		}
		
		for(int i = 0; i < nums.size(); i++){
			System.out.println(nums.get(i));
		}
		
		PseudoQueue<String> q = new PseudoQueue<>();
		PseudoStack<String> s = new PseudoStack<>();
		for(int i = 0; i < 5; i++){
			q.enqueue(String.format("%d, %d", i, i+1));
			s.push(String.format("%d, %d", i, i*2));
		}
		
		for(int i = 0; i< 5; i++){
			System.out.println(q.dequeue());
		}
		for(int i = 0; i< 5; i++){
			System.out.println(s.pop());
		}
		PseudoArray<String> postfixed = infixToPostfix("3+3((5-2)/3)*4");
		System.out.println("\nResult: " + postfixCalculate(postfixed));
	}
	
	public static PseudoArray<String> infixToPostfix(String infix){
		PseudoStack<Character> operations = new PseudoStack<>();
		PseudoArray<String> postfix = new PseudoArray<>(infix.length());
		char top = 0;
		boolean stackEmpty = false;
		for(int i = 0; i < infix.length(); i++){
			if(infix.charAt(i) > 47 && infix.charAt(i) < 58){ // checks if the element is a digit
				int j = 1;
				while( (i+j < infix.length()) && (infix.charAt(i+j) > 47 && infix.charAt(i+j) < 58)){ // checks the elements next to it until it's not a digit
					System.out.println(j + " " + String.valueOf(infix.charAt(i+j)));
					j++;
				}
				postfix.add(infix.substring(i,i+j));
				i += j-1;
				if(i+1 < infix.length() && infix.charAt(i+1) == '('){
					infix = infix.substring(0,i+1) + "*" + infix.substring(i+1, infix.length());
				}
			}else{ // reads a non digit character in String infix
				try{
					top = operations.peek();
				}catch(EmptyStackException e){top = 0;}
				if(top == 0){
					operations.push(infix.charAt(i));
					System.out.println("Push |" + String.valueOf(infix.charAt(i)) + "|");
				}else if( ((infix.charAt(i) == '+' || infix.charAt(i) == '-') && (top == '+' || top == '-')) || ((infix.charAt(i) == '*' || infix.charAt(i) == '/') && (top == '*' || top == '/')) ){
					try{
						postfix.add(String.valueOf(operations.pop()));
					}catch(EmptyStackException e){}
					operations.push(infix.charAt(i));
					System.out.println("Pop |" + postfix.get(postfix.size()-1) + "| and push |" + operations.peek() + "|");
				}else if(infix.charAt(i) == '+' || infix.charAt(i) == '-' || infix.charAt(i) == '(' || infix.charAt(i) == '*' || infix.charAt(i) == '/' && top == '(' ){
					operations.push(infix.charAt(i));
					System.out.println("Push |" + String.valueOf(infix.charAt(i)) + "|");
				}else if( infix.charAt(i) == ')' ){
					try{
						top = operations.pop();
					}catch(EmptyStackException e){return null;}
					while( top != '(' ){
						postfix.add(String.valueOf(top));
						try{
							top = operations.pop();
						}catch(EmptyStackException e){return null;}
						System.out.println("Pop |" + String.valueOf(top) + "|");
					}
					if( i+1< infix.length() && (infix.charAt(i+1) == '(' || (infix.charAt(i+1) > 47 && infix.charAt(i+1) < 58) )){
						infix = infix.substring(0,i+1) + "*" + infix.substring(i+1, infix.length());
					}
				}
				
				if( i+1 >= infix.length() || (infix.charAt(i) != ')' && infix.charAt(i+1) != '(' && !(infix.charAt(i+1) > 47 && infix.charAt(i+1) < 58)) ){
					return null;
				}
			}
		}
		
		
		//After checking the end of infix string, all operations in the stack is popped
		try{
			top = operations.pop();
			System.out.println("Pop 1 |" + String.valueOf(top) + "|");
		}catch(EmptyStackException e){
			stackEmpty = true;
		}
		while(!stackEmpty){
			postfix.add(String.valueOf(top));
			try{
				top = operations.pop();
			}catch(EmptyStackException e){
				stackEmpty = true;
			}
			if(top == '(' || top == ')' && !(top == '+' || top == '-' || top == '*' || top == '/')){
				return null;
			}
			System.out.println("Pop 1 |" + String.valueOf(top) + "|");
		}
		return postfix;
	}
	
	public static String postfixCalculate(PseudoArray<String> postfix){
		if(postfix == null){
			return "Error";
		}
		PseudoStack<Integer> numbers = new PseudoStack<>();
		for(int i = 0; i < postfix.size(); i++){
			try{// tries to parse an element in the array, if an exception occurs it is automatically not a number(or it is an operation)
				int num = Integer.parseInt(postfix.get(i));
				numbers.push(num);
			}catch(NumberFormatException e){
				System.out.println("\nRead: " + postfix.get(i));
				int number = 0;
				try{
					if(postfix.get(i).equals("+")){
						number = numbers.pop()+numbers.pop();
						System.out.println("Add Result: " + number);
					}else if(postfix.get(i).equals("-")){
						number = (numbers.pop()-numbers.pop())*-1;
						System.out.println("Subtract Result: " + number);
					}else if(postfix.get(i).equals("*")){
						number = numbers.pop()*numbers.pop();
						System.out.println("Multiply Result: " + number);
					}else if(postfix.get(i).equals("/")){
						int divisor = numbers.pop();
						number = (int)( (float)numbers.pop()/(float)divisor );
						System.out.println("Divide Result: " + number);
					}else{
						return "Error!";
					}
				}catch(NullPointerException ex){
					break;
				}catch(EmptyStackException exc){
					return "Error!";
				}
				numbers.push(number);
				System.out.println("Pushed: "+ number);
			}
		}
		return String.valueOf(numbers.pop());
	}
}