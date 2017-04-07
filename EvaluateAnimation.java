import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class EvaluateAnimation extends JPanel{
	String[][] evaluateTable = null;
	JTextField readFieldEvaluate = new JTextField(2), parseFieldEvaluate = new JTextField(15), actionFieldEvaluate = new JTextField(20);
	JLabel readLabelEvaluate = new JLabel("Read: "), parseLabelEvaluate = new JLabel("Parsed: "), actionLabelEvaluate = new JLabel("Action: "), commitLabelEvaluate = new JLabel("Committed:"), listLabelEvaluate = new JLabel("LinkedList key: "), queueKeyLabelEvaluate = new JLabel("Array key:     "), queueBufferLabelEvaluate = new JLabel("Array Buffer:"), stackKeyLabelEvaluate = new JLabel("Queue key:     "), stackBufferLabelEvaluate = new JLabel("Queue Buffer:");
	JLabel pseudoArrayLabelEvaluate = new JLabel("PseudoArray", SwingConstants.CENTER), queueLabelEvaluate = new JLabel("PseudoQueue", SwingConstants.CENTER), stackLabelEvaluate = new JLabel("PseudoStack", SwingConstants.CENTER);
	JPanel upperEvaluate, arrayFieldEvaluate, queueFieldEvaluate, stackFieldEvaluate, arrayFieldEvaluate_List, queueFieldEvaluate_Key, queueFieldEvaluate_Buffer, stackFieldEvaluate_Key, stackFieldEvaluate_Buffer;
	JPanel alEvaluate, qkEvaluate, qbEvaluate, skEvaluate, sbEvaluate;
	JLabel[] alsEvaluate, qksEvaluate, qbsEvaluate, sksEvaluate, sbsEvaluate;
	int listLength = 0, delay = 250;
	Color background = new Color(220,220,220), digitColor= new Color(100, 70, 60), operatorColor = new Color(82, 182, 172);
	Font font = new Font("Helvetica", Font.PLAIN, 15);
	JButton pausePlay, stop;
	ImageIcon pauseIcon, playIcon, stopIcon;
	boolean runSignal = false, paused = false;
	
	public EvaluateAnimation(){
		setLayout(null);
		setBackground(background);
		upperEvaluate = new JPanel(new BorderLayout());
		JPanel l = new JPanel(new GridLayout(3,1));
		JPanel f = new JPanel(new GridLayout(3,1));
		parseFieldEvaluate.setEditable(false);
		readFieldEvaluate.setEditable(false);
		actionFieldEvaluate.setEditable(false);
		parseLabelEvaluate.setFont(new Font("Helvetica", Font.PLAIN, 15));
		readLabelEvaluate.setFont(new Font("Helvetica", Font.PLAIN, 15));
		actionLabelEvaluate.setFont(new Font("Helvetica", Font.PLAIN, 15));
		parseLabelEvaluate.setForeground(digitColor);;
		readLabelEvaluate.setForeground(digitColor);
		actionLabelEvaluate.setForeground(digitColor);
		readFieldEvaluate.setBackground(background);
		readFieldEvaluate.setFont(font);
		readFieldEvaluate.setForeground(digitColor);
		parseFieldEvaluate.setBackground(background);
		parseFieldEvaluate.setFont(font);
		parseFieldEvaluate.setForeground(digitColor);
		actionFieldEvaluate.setBackground(background);
		actionFieldEvaluate.setFont(font);
		actionFieldEvaluate.setForeground(digitColor);
		
		l.setBackground(background);
		l.add(parseLabelEvaluate);
		f.add(parseFieldEvaluate);
		l.add(readLabelEvaluate);
		f.add(readFieldEvaluate);
		l.add(actionLabelEvaluate);
		f.add(actionFieldEvaluate);
		upperEvaluate.add(l, BorderLayout.WEST);
		upperEvaluate.add(f, BorderLayout.CENTER);
		
		arrayFieldEvaluate = new JPanel(new BorderLayout());
		arrayFieldEvaluate.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, operatorColor));
		arrayFieldEvaluate.setBackground(background);
		arrayFieldEvaluate_List = new JPanel(new BorderLayout());
		arrayFieldEvaluate_List.setBackground(background);
		arrayFieldEvaluate_List.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, operatorColor));
		alEvaluate = new JPanel(new FlowLayout(FlowLayout.LEADING, 2, 2));
		alEvaluate.setBackground(background);
		listLabelEvaluate.setFont(font);
		listLabelEvaluate.setForeground(digitColor);
		pseudoArrayLabelEvaluate.setFont(font);
		pseudoArrayLabelEvaluate.setForeground(digitColor);
		
		arrayFieldEvaluate_List.add(listLabelEvaluate, BorderLayout.WEST);
		arrayFieldEvaluate_List.add(alEvaluate, BorderLayout.CENTER);
		arrayFieldEvaluate.add(pseudoArrayLabelEvaluate, BorderLayout.NORTH);
		arrayFieldEvaluate.add(arrayFieldEvaluate_List, BorderLayout.CENTER);
		
		queueFieldEvaluate = new JPanel(new BorderLayout());
		queueFieldEvaluate.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, operatorColor));
		queueFieldEvaluate.setBackground(background);
		queueFieldEvaluate_Key = new JPanel(new BorderLayout());
		queueFieldEvaluate_Key.setBackground(background);
		queueFieldEvaluate_Key.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, operatorColor));
		queueFieldEvaluate_Buffer = new JPanel(new BorderLayout());
		queueFieldEvaluate_Buffer.setBackground(background);
		queueFieldEvaluate_Buffer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, operatorColor));
		JPanel q = new JPanel(new GridLayout(2,1));
		qkEvaluate = new JPanel(new FlowLayout(FlowLayout.LEADING, 2, 2));
		qkEvaluate.setBackground(background);
		qbEvaluate = new JPanel(new FlowLayout(FlowLayout.LEADING, 2, 2));
		qbEvaluate.setBackground(background);
		queueKeyLabelEvaluate.setFont(font);
		queueKeyLabelEvaluate.setForeground(digitColor);
		queueBufferLabelEvaluate.setFont(font);
		queueBufferLabelEvaluate.setForeground(digitColor);
		queueLabelEvaluate.setFont(font);
		queueLabelEvaluate.setForeground(digitColor);
		
		queueFieldEvaluate_Key.add(queueKeyLabelEvaluate, BorderLayout.WEST);
		queueFieldEvaluate_Key.add(qkEvaluate, BorderLayout.CENTER);
		queueFieldEvaluate_Buffer.add(queueBufferLabelEvaluate, BorderLayout.WEST);
		queueFieldEvaluate_Buffer.add(qbEvaluate, BorderLayout.CENTER);
		q.add(queueFieldEvaluate_Key);
		q.add(queueFieldEvaluate_Buffer);
		queueFieldEvaluate.add(queueLabelEvaluate, BorderLayout.NORTH);
		queueFieldEvaluate.add(q, BorderLayout.CENTER);
		
		stackFieldEvaluate = new JPanel(new BorderLayout());
		stackFieldEvaluate.setBackground(background);
		stackFieldEvaluate.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, operatorColor));
		stackFieldEvaluate_Key = new JPanel(new BorderLayout());
		stackFieldEvaluate_Key.setBackground(background);
		stackFieldEvaluate_Key.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, operatorColor));
		stackFieldEvaluate_Buffer = new JPanel(new BorderLayout());
		stackFieldEvaluate_Buffer.setBackground(background);
		stackFieldEvaluate_Buffer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, operatorColor));
		JPanel s = new JPanel(new GridLayout(2,1));
		skEvaluate = new JPanel(new FlowLayout(FlowLayout.LEADING, 2, 2));
		skEvaluate.setBackground(background);
		sbEvaluate = new JPanel(new FlowLayout(FlowLayout.LEADING, 2, 2));
		sbEvaluate.setBackground(background);
		stackKeyLabelEvaluate.setFont(font);
		stackKeyLabelEvaluate.setForeground(digitColor);
		stackBufferLabelEvaluate.setFont(font);		
		stackBufferLabelEvaluate.setForeground(digitColor);
		stackLabelEvaluate.setFont(font);
		stackLabelEvaluate.setForeground(digitColor);
		
		stackFieldEvaluate_Key.add(stackKeyLabelEvaluate, BorderLayout.WEST);
		stackFieldEvaluate_Key.add(skEvaluate, BorderLayout.CENTER);
		stackFieldEvaluate_Buffer.add(stackBufferLabelEvaluate, BorderLayout.WEST);
		stackFieldEvaluate_Buffer.add(sbEvaluate, BorderLayout.CENTER);
		s.add(stackFieldEvaluate_Key)	;
		s.add(stackFieldEvaluate_Buffer);
		stackFieldEvaluate.add(stackLabelEvaluate, BorderLayout.NORTH);
		stackFieldEvaluate.add(s, BorderLayout.CENTER);
		
		Image pauseImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("pauseIcon.png"));
		Image playImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("playIcon.png"));
		Image stopImg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("stopIcon.png"));
		pauseIcon = new ImageIcon(pauseImg);
		playIcon = new ImageIcon(playImg);
		stopIcon = new ImageIcon(stopImg);
		
		pausePlay = new JButton();
		pausePlay.setBackground(operatorColor);
		pausePlay.setIcon(playIcon);
		stop = new JButton();
		stop.setBackground(operatorColor);
		stop.setIcon(stopIcon);
		
		Insets insets = getInsets();
		add(upperEvaluate);
		upperEvaluate.setBounds(insets.left+2, insets.top, 580, 55);
		add(arrayFieldEvaluate);
		arrayFieldEvaluate.setBounds(insets.left+2, insets.top+57, 580, 46);
		add(queueFieldEvaluate);
		queueFieldEvaluate.setBounds(insets.left+2, insets.top+105, 580, 68);
		add(stackFieldEvaluate);
		stackFieldEvaluate.setBounds(insets.left+2, insets.top+175, 580, 68);
		add(pausePlay);
		pausePlay.setBounds(insets.left+584, insets.top+100, 30, 25);
		add(stop);
		stop.setBounds(insets.left+ 584, insets.top+130, 30, 25);
		stop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				paused = false;
				evaluateTable = null;
			}
		}
		);
		pausePlay.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(runSignal && !(evaluateTable == null)){
					if(paused){
						pausePlay.setIcon(pauseIcon);
						paused = false;
					}else{
						pausePlay.setIcon(playIcon);
						paused = true;
						//issue pause
					}
				}
			}
		}
		);
	}
	
	public void play(String[][] evalTable){
		evaluateTable = new String[evalTable.length][];
		for(int i = 0; i < evalTable.length; i++){
			evaluateTable[i] = new String[evalTable[i].length];
			for(int j = 0; j < evalTable[i].length; j++){
				evaluateTable[i][j] = evalTable[i][j];
			}
		}
		
		for(int x = 0; x < evaluateTable.length; x++){
			if(paused){
				x--;
				continue;
			}
			parseFieldEvaluate.setText(evaluateTable[x][0]);
			try{
				Thread.sleep((int)((float)delay*2.0));
			}catch(InterruptedException e){}
			while(paused){System.out.print("");}
			readFieldEvaluate.setText(evaluateTable[x][1]);
			try{
				Thread.sleep((int)((float)delay*2.0));
			}catch(InterruptedException e){}
			while(paused){System.out.print("");}
			actionFieldEvaluate.setText(evaluateTable[x][2]);
			try{
				Thread.sleep((int)((float)delay*2.0));
			}catch(InterruptedException e){}
			try{
				Integer.parseInt(evaluateTable[x][1]);
			}catch(NumberFormatException ex){}
			for(int y = 0; y < evaluateTable[x][2].length()-4; y++){
				if(evaluateTable[x][2].substring(y, y+4).equals("Push")){
					y += 5;
					int z = y+1;
					while(evaluateTable[x][2].charAt(z) != '|'){
						z++;
					}
					String toPush = evaluateTable[x][2].substring(y+1, z);
					JLabel[] buffer;
					//Stack part start
					sbsEvaluate = null;
					sbsEvaluate = new JLabel[listLength];
					for(int i = 0; i < listLength; i++){
						System.out.print("");//magical statement
						if(paused){i--;continue;}
						sbsEvaluate[i] = new JLabel(sksEvaluate[i].getText());
						sbsEvaluate[i].setBorder(sksEvaluate[i].getBorder());
					}
					for(int i = 0; i < listLength; i++){
						System.out.print("");//magical statement
						if(paused){i--;continue;}
						skEvaluate.removeAll();
						repaint();
						revalidate();
						for(int j = 0; j < listLength-i-1; j++){
							System.out.print("");//magical statement
							if(paused){j--;continue;}
							skEvaluate.add(sksEvaluate[j]);
						}
						sbEvaluate.removeAll();
						repaint();
						revalidate();
						for(int j = listLength-i-1; j < listLength; j++){
							System.out.print("");//magical statement
							if(paused){j--;continue;}
							sbEvaluate.add(sbsEvaluate[j]);
						}
						boolean a = false;
						try{
							Thread.sleep((int)((float)delay/2.0));
						}catch(InterruptedException e){}
					}
					sksEvaluate = null;
					sksEvaluate = new JLabel[listLength+1];
					for(int i = 0; i < listLength; i++){
						System.out.print("");//magical statement
						if(paused){i--;continue;}
						sksEvaluate[i] = new JLabel(sbsEvaluate[i].getText());
						sksEvaluate[i].setBorder(sbsEvaluate[i].getBorder());
					}
					sksEvaluate[listLength] = new JLabel(toPush);
					sksEvaluate[listLength].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, digitColor));
					skEvaluate.removeAll();
					repaint();
					revalidate();
					skEvaluate.add(sksEvaluate[listLength]);
					try{
						Thread.sleep((int)((float)delay/2.0));
					}catch(InterruptedException e){}
					for(int i = 0; i < listLength; i++){
						System.out.print("");//magical statement
						if(paused){i--;continue;}
						sbEvaluate.removeAll();
						repaint();
						revalidate();
						for(int j = 0; j < listLength-i-1; j++){
							System.out.print("");//magical statement
							if(paused){j--;continue;}
							sbEvaluate.add(sbsEvaluate[j]);
							repaint();
							revalidate();
						}
						skEvaluate.removeAll();
						repaint();
						revalidate();
						for(int j = listLength-i-1; j < listLength+1; j++){
							System.out.print("");//magical statement
							if(paused){j--;continue;}
							skEvaluate.add(sksEvaluate[j]);
							repaint();
							revalidate();
						}
						try{
							Thread.sleep((int)((float)delay/2.0));
						}catch(InterruptedException e){}
					}
					sbsEvaluate = null;
					//Stack part end
					try{
						Thread.sleep(delay);
					}catch(InterruptedException e){}
					//Queue part start
					qbsEvaluate = new JLabel[listLength];
					for(int i = 0; i < listLength; i++){
						System.out.print("");//magical statement
						if(paused){i--;continue;}
						qbsEvaluate[i] = new JLabel(" ");
						qbsEvaluate[i].setBorder(qksEvaluate[i].getBorder());
						qbEvaluate.add(qbsEvaluate[i]);
						repaint();
						revalidate();
					}
					for(int i = 0; i < listLength; i++){
						System.out.print("");//magical statement
						if(paused){i--;continue;}
						try{
							Thread.sleep((int)((float)delay/2.0));
						}catch(InterruptedException e){}
						qbsEvaluate[i].setText(qksEvaluate[i].getText());
					}
					try{
						Thread.sleep((int)((float)delay/2.0));
					}catch(InterruptedException e){}
					qkEvaluate.removeAll();
					repaint();
					revalidate();
					qksEvaluate = null;
					qksEvaluate = new JLabel[listLength+1];
					for(int i = 0; i < listLength+1; i++){
						System.out.print("");//magical statement
						if(paused){i--;continue;}
						qksEvaluate[i] = new JLabel(" ");
						try{
							qksEvaluate[i].setBorder(qbsEvaluate[i].getBorder());
						}catch(ArrayIndexOutOfBoundsException e){
							qksEvaluate[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, digitColor));
						}
						qkEvaluate.add(qksEvaluate[i]);
						repaint();
						revalidate();
					}
					for(int i = 0; i < listLength; i++){
						System.out.print("");//magical statement
						if(paused){i--;continue;}
						try{
							Thread.sleep((int)((float)delay/2.0));
						}catch(InterruptedException e){}
						qksEvaluate[i].setText(qbsEvaluate[i].getText());
					}
					try{
						Thread.sleep((int)((float)delay/2.0));
					}catch(InterruptedException e){}
					qksEvaluate[listLength].setText(toPush);
					qbEvaluate.removeAll();
					qbsEvaluate = null;
					repaint();
					revalidate();
					//Queue part end
					try{
						Thread.sleep(delay);
					}catch(InterruptedException e){}
					//Array part start
					buffer = new JLabel[listLength];
					for(int i = 0; i < listLength; i++){
						System.out.print("");//magical statement
						if(paused){i--;continue;}
						buffer[i] = new JLabel(alsEvaluate[i].getText());
						buffer[i].setBorder(alsEvaluate[i].getBorder());
					}
					alsEvaluate = null;
					alsEvaluate = new JLabel[listLength+1];
					for(int i = 0; i < listLength; i++){
						System.out.print("");//magical statement
						if(paused){i--;continue;}
						alsEvaluate[i] = new JLabel(buffer[i].getText());
						alsEvaluate[i].setBorder(buffer[i].getBorder());
					}
					alsEvaluate[listLength] = new JLabel(toPush);
					alsEvaluate[listLength].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, digitColor));
					alEvaluate.add(alsEvaluate[listLength]);
					buffer = null;
					listLength++;
						repaint();
						revalidate();
					//Array part end
					try{
						Thread.sleep(delay);
					}catch(InterruptedException e){}
					y = z+1;
					if(y >= evaluateTable[x][2].length()){break;}
				}else if(evaluateTable[x][2].substring(y, y+3).equals("Pop")){
					y += 4;
					while(true){
						int z = y+1;
						if(evaluateTable[x][2].charAt(y) != '|'){break;}
						while(evaluateTable[x][2].charAt(z) != '|'){
							z++;
						}
						String toPop = evaluateTable[x][2].substring(y+1, z);
						try{
							Thread.sleep(delay);
						}catch(InterruptedException e){}
						//Stack part start
						skEvaluate.removeAll();
						repaint();
						revalidate();
						JLabel[] buffer = new JLabel[listLength-1];
						for(int i = 0; i < listLength-1; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							buffer[i] = new JLabel(sksEvaluate[i].getText()); 
							buffer[i].setBorder(sksEvaluate[i].getBorder());
						}
						sksEvaluate = null;
						sksEvaluate = new JLabel[listLength-1];
						for(int i = 0; i < listLength-1; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							sksEvaluate[i] = new JLabel(buffer[i].getText());
							sksEvaluate[i].setBorder(buffer[i].getBorder());
							skEvaluate.add(sksEvaluate[i]);
							repaint();
							revalidate();
						}
						buffer = null;
						//Stack part end
						try{
							Thread.sleep(delay);
						}catch(InterruptedException e){}
						//Queue part start
						qbsEvaluate = new JLabel[listLength-1];
						for(int i = 0; i < listLength-1; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							qbsEvaluate[i] = new JLabel("  ");
							qbsEvaluate[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, digitColor));
							qbEvaluate.add(qbsEvaluate[i]);
						}
						for(int i = 0; i < listLength-1; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							qbsEvaluate[i].setText(qksEvaluate[i].getText());
							try{
								Thread.sleep((int)((float)delay/2.0));
							}catch(InterruptedException e){}
						}
						
						qksEvaluate = null;
						qksEvaluate = new JLabel[listLength-1];
						qkEvaluate.removeAll();
						repaint();
						revalidate();
						for(int i = 0; i < listLength-1; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							qksEvaluate[i] = new JLabel("  ");
							qksEvaluate[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, digitColor));
							qkEvaluate.add(qksEvaluate[i]);
							repaint();
							revalidate();
						}
						for(int i = 0; i < listLength-1; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							qksEvaluate[i].setText(qbsEvaluate[i].getText());
							try{
								Thread.sleep((int)((float)delay/2.0));
							}catch(InterruptedException e){}
						}
						qbEvaluate.removeAll();
						qbsEvaluate = null;
						repaint();
						revalidate();
						//Queue part end
						try{
							Thread.sleep(delay);
						}catch(InterruptedException e){}
						//Array part start
						buffer = new JLabel[listLength-1];
						for(int i = 0; i < listLength-1; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							buffer[i] = new JLabel(alsEvaluate[i].getText());
							buffer[i].setBorder(alsEvaluate[i].getBorder());
						}
						alEvaluate.removeAll();
						repaint();
						revalidate();
						alsEvaluate = null;
						alsEvaluate = new JLabel[listLength-1];
						for(int i = 0; i < listLength-1; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							alsEvaluate[i] = new JLabel(buffer[i].getText());
							alsEvaluate[i].setBorder(buffer[i].getBorder());
							alEvaluate.add(alsEvaluate[i]);
							repaint();
							revalidate();
						}
						//Array part end
						listLength--;
						try{
							Thread.sleep(delay*2);
						}catch(InterruptedException e){}
						y = z+1;
					}
				}
			}
		}
	}
	
	public void clean(){
		evaluateTable = null;
		listLength = 0;
		skEvaluate.removeAll();
		sbEvaluate.removeAll();
		qkEvaluate.removeAll();
		qbEvaluate.removeAll();
		alEvaluate.removeAll();
		repaint();
		revalidate();
		
		parseFieldEvaluate.setText("");
		readFieldEvaluate.setText("");
		actionFieldEvaluate.setText("");
	}
}