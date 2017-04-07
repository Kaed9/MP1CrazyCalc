import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;

public class TranslateAnimation extends JPanel{
	String[][] translateTable = null;
	JTextField readFieldTranslate = new JTextField(2), parseFieldTranslate = new JTextField(15), actionFieldTranslate = new JTextField(20), commitFieldTranslate = new JTextField(16);
	JLabel readLabelTranslate = new JLabel("Read: "), parseLabelTranslate = new JLabel("Parsed: "), actionLabelTranslate = new JLabel("Action: "), commitLabelTranslate = new JLabel("Committed:"), listLabelTranslate = new JLabel("LinkedList key:   "), bufferListLabelTranslate = new JLabel("LinkedList Buffer:"), queueKeyLabelTranslate = new JLabel("Array key:     "), queueBufferLabelTranslate = new JLabel("Array Buffer:"), stackKeyLabelTranslate = new JLabel("Queue key:     "), stackBufferLabelTranslate = new JLabel("Queue Buffer:");
	JLabel pseudoArrayLabelTranslate = new JLabel("PseudoArray", SwingConstants.CENTER), queueLabelTranslate = new JLabel("PseudoQueue", SwingConstants.CENTER), stackLabelTranslate = new JLabel("PseudoStack", SwingConstants.CENTER);
	JPanel upperTranslate, arrayFieldTranslate, queueFieldTranslate, stackFieldTranslate, commitedPanelTranslate, arrayFieldTranslate_List, arrayFieldTranslate_BufferList, queueFieldTranslate_Key, queueFieldTranslate_Buffer, stackFieldTranslate_Key, stackFieldTranslate_Buffer;
	JPanel alTranslate, qkTranslate, qbTranslate, sk, sb;
	JLabel[] alsTranslate, qksTranslate, qbsTranslate, sksTranslate, sbsTranslate;
	int listLength = 0, delay = 250;
	Color background = new Color(220,220,220), digitColor= new Color(100, 70, 60), operatorColor = new Color(82, 182, 172);
	Font font = new Font("Helvetica", Font.PLAIN, 15);
	JButton pausePlay, stop;
	ImageIcon pauseIcon, playIcon, stopIcon;
	boolean runSignal = false, paused = false;
	
	public TranslateAnimation(){
		setLayout(null);
		setBackground(background);
		upperTranslate = new JPanel(new BorderLayout());
		JPanel l = new JPanel(new GridLayout(3,1));
		JPanel f = new JPanel(new GridLayout(3,1));
		parseFieldTranslate.setEditable(false);
		parseFieldTranslate.setFont(font);
		parseFieldTranslate.setForeground(digitColor);
		readFieldTranslate.setEditable(false);
		readFieldTranslate.setFont(font);
		readFieldTranslate.setForeground(digitColor);
		actionFieldTranslate.setEditable(false);
		actionFieldTranslate.setFont(font);
		actionFieldTranslate.setForeground(digitColor);
		parseLabelTranslate.setFont(new Font("Helvetica", Font.PLAIN, 15));
		readLabelTranslate.setFont(new Font("Helvetica", Font.PLAIN, 15));
		actionLabelTranslate.setFont(new Font("Helvetica", Font.PLAIN, 15));
		parseLabelTranslate.setForeground(digitColor);
		readLabelTranslate.setForeground(digitColor);
		actionLabelTranslate.setForeground(digitColor);
		readFieldTranslate.setBackground(background);
		parseFieldTranslate.setBackground(background);
		actionFieldTranslate.setBackground(background);
		l.setBackground(background);
		l.add(parseLabelTranslate);
		f.add(parseFieldTranslate);
		l.add(readLabelTranslate);
		f.add(readFieldTranslate);
		l.add(actionLabelTranslate);
		f.add(actionFieldTranslate);
		upperTranslate.add(l, BorderLayout.WEST);
		upperTranslate.add(f, BorderLayout.CENTER);
		
		arrayFieldTranslate = new JPanel(new BorderLayout());
		arrayFieldTranslate.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, operatorColor));
		arrayFieldTranslate.setBackground(background);
		alTranslate = new JPanel(new FlowLayout(FlowLayout.LEADING, 2, 2));
		alTranslate.setBackground(background);
		arrayFieldTranslate_List = new JPanel(new BorderLayout());
		arrayFieldTranslate_List.setBackground(background);
		arrayFieldTranslate_List.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, operatorColor));
		listLabelTranslate.setFont(font);
		listLabelTranslate.setForeground(digitColor);
		pseudoArrayLabelTranslate.setFont(font);
		pseudoArrayLabelTranslate.setForeground(digitColor);
		
		arrayFieldTranslate_List.add(listLabelTranslate, BorderLayout.WEST);
		arrayFieldTranslate_List.add(alTranslate, BorderLayout.CENTER);
		arrayFieldTranslate.add(pseudoArrayLabelTranslate, BorderLayout.NORTH);
		arrayFieldTranslate.add(arrayFieldTranslate_List, BorderLayout.CENTER);
		
		queueFieldTranslate = new JPanel(new BorderLayout());
		queueFieldTranslate.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, operatorColor));
		queueFieldTranslate.setBackground(background);
		queueFieldTranslate_Key = new JPanel(new BorderLayout());
		queueFieldTranslate_Key.setBackground(background);
		queueFieldTranslate_Key.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, operatorColor));
		queueFieldTranslate_Buffer = new JPanel(new BorderLayout());
		queueFieldTranslate_Buffer.setBackground(background);
		queueFieldTranslate_Buffer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, operatorColor));
		JPanel q = new JPanel(new GridLayout(2,1));
		qkTranslate = new JPanel(new FlowLayout(FlowLayout.LEADING, 2, 2));
		qkTranslate.setBackground(background);
		qbTranslate = new JPanel(new FlowLayout(FlowLayout.LEADING, 2, 2));
		qbTranslate.setBackground(background);
		queueKeyLabelTranslate.setFont(font);
		queueKeyLabelTranslate.setForeground(digitColor);
		queueBufferLabelTranslate.setFont(font);
		queueBufferLabelTranslate.setForeground(digitColor);
		queueLabelTranslate.setFont(font);
		queueLabelTranslate.setForeground(digitColor);
		
		queueFieldTranslate_Key.add(queueKeyLabelTranslate, BorderLayout.WEST);
		queueFieldTranslate_Key.add(qkTranslate, BorderLayout.CENTER);
		queueFieldTranslate_Buffer.add(queueBufferLabelTranslate, BorderLayout.WEST);
		queueFieldTranslate_Buffer.add(qbTranslate, BorderLayout.CENTER);
		q.add(queueFieldTranslate_Key);
		q.add(queueFieldTranslate_Buffer);
		queueFieldTranslate.add(queueLabelTranslate, BorderLayout.NORTH);
		queueFieldTranslate.add(q, BorderLayout.CENTER);
		
		stackFieldTranslate = new JPanel(new BorderLayout());
		stackFieldTranslate.setBackground(background);
		stackFieldTranslate.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, operatorColor));
		stackFieldTranslate_Key = new JPanel(new BorderLayout());
		stackFieldTranslate_Key.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, operatorColor));
		stackFieldTranslate_Key.setBackground(background);
		stackFieldTranslate_Buffer = new JPanel(new BorderLayout());
		stackFieldTranslate_Buffer.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, operatorColor));
		stackFieldTranslate_Buffer.setBackground(background);
		JPanel s = new JPanel(new GridLayout(2,1));
		sk = new JPanel(new FlowLayout(FlowLayout.LEADING, 2, 2));
		sk.setBackground(background);
		sb = new JPanel(new FlowLayout(FlowLayout.LEADING, 2, 2));
		sb.setBackground(background);
		stackKeyLabelTranslate.setFont(font);
		stackKeyLabelTranslate.setForeground(digitColor);
		stackBufferLabelTranslate.setFont(font);
		stackBufferLabelTranslate.setForeground(digitColor);
		stackFieldTranslate.setFont(font);
		stackFieldTranslate.setForeground(digitColor);
		stackLabelTranslate.setFont(font);
		stackLabelTranslate.setForeground(digitColor);
		
		stackFieldTranslate_Key.add(stackKeyLabelTranslate, BorderLayout.WEST);
		stackFieldTranslate_Key.add(sk, BorderLayout.CENTER);
		stackFieldTranslate_Buffer.add(stackBufferLabelTranslate, BorderLayout.WEST);
		stackFieldTranslate_Buffer.add(sb, BorderLayout.CENTER);
		s.add(stackFieldTranslate_Key);
		s.add(stackFieldTranslate_Buffer);
		stackFieldTranslate.add(stackLabelTranslate, BorderLayout.NORTH);
		stackFieldTranslate.add(s, BorderLayout.CENTER);
		
		commitedPanelTranslate = new JPanel(new BorderLayout());
		commitedPanelTranslate.setBackground(background);
		commitFieldTranslate.setEditable(false);
		commitFieldTranslate.setBackground(background);
		commitFieldTranslate.setForeground(digitColor);
		commitFieldTranslate.setFont(font);
		commitLabelTranslate.setFont(font);
		commitLabelTranslate.setForeground(digitColor);
		commitedPanelTranslate.add(commitLabelTranslate, BorderLayout.WEST);
		commitedPanelTranslate.add(commitFieldTranslate, BorderLayout.CENTER);
		
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
		add(upperTranslate);
		upperTranslate.setBounds(insets.left+2, insets.top, 580, 55);
		add(arrayFieldTranslate);
		arrayFieldTranslate.setBounds(insets.left+2, insets.top+57, 580, 46);
		add(queueFieldTranslate);
		queueFieldTranslate.setBounds(insets.left+2, insets.top+105, 580, 68);
		add(stackFieldTranslate);
		stackFieldTranslate.setBounds(insets.left+2, insets.top+175, 580, 68);
		add(commitedPanelTranslate);
		commitedPanelTranslate.setBounds(insets.left+2, insets.top+244, 580, 20);
		add(pausePlay);
		pausePlay.setBounds(insets.left+584, insets.top+105, 30, 25);
		add(stop);
		stop.setBounds(insets.left+ 584, insets.top+135, 30, 25);
		stop.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				paused = false;
				translateTable = null;
			}
		}
		);
		pausePlay.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(runSignal && !(translateTable == null)){
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
	
	public void play(String[][] transTable){
		translateTable = new String[transTable.length][];
		for(int i = 0; i < translateTable.length; i++){
			translateTable[i] = new String[transTable[i].length];
			for(int j = 0; j < translateTable[i].length; j++){
				translateTable[i][j] = transTable[i][j];
			}
		}
		
		for(int x = 0 ; x < translateTable.length; x++){
			if(paused){
				x--;
				continue;
			}
			parseFieldTranslate.setText(translateTable[x][0]);
			try{
				Thread.sleep((int)((float)delay*2.0));
			}catch(InterruptedException e){}
			while(paused){System.out.print("");}
			readFieldTranslate.setText(translateTable[x][1]);
			try{
				Thread.sleep((int)((float)delay*2.0));
			}catch(InterruptedException e){}
			while(paused){System.out.print("");}
			actionFieldTranslate.setText(translateTable[x][2]);
			try{
				Thread.sleep((int)((float)delay*2.0));
			}catch(InterruptedException e){}
			try{
				Integer.parseInt(translateTable[x][1]);
			}catch(NumberFormatException ex){
				for(int y = 0; y < translateTable[x][2].length()-4; y++){
					if(translateTable[x][2].substring(y, y+4).equals("Push")){
						y += 5;
						//pushMechanism(translateTable[i][2].substring(j+1, j+2));
						String toPush = translateTable[x][2].substring(y+1, y+2);
						JLabel[] buffer;
						//Stack part start
						sbsTranslate = null;
						sbsTranslate = new JLabel[listLength];
						for(int i = 0; i < listLength; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							sbsTranslate[i] = new JLabel(sksTranslate[i].getText());
							sbsTranslate[i].setBorder(sksTranslate[i].getBorder());
							sbsTranslate[i].setFont(sksTranslate[i].getFont());
						}
						for(int i = 0; i < listLength; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							sk.removeAll();
							repaint();
							revalidate();
							for(int j = 0; j < listLength-i-1; j++){
								if(paused){j--;continue;}
								sk.add(sksTranslate[j]);
							}
							sb.removeAll();
							repaint();
							revalidate();
							for(int j = listLength-i-1; j < listLength; j++){
								System.out.print("");//magical statement
								if(paused){j--;continue;}
								sb.add(sbsTranslate[j]);
							}
							boolean a = false;
							try{
								Thread.sleep((int)((float)delay/2.0));
							}catch(InterruptedException e){}
						}
						sksTranslate = null;
						sksTranslate = new JLabel[listLength+1];
						for(int i = 0; i < listLength; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							sksTranslate[i] = new JLabel(sbsTranslate[i].getText());
							sksTranslate[i].setBorder(sbsTranslate[i].getBorder());
							sksTranslate[i].setFont(sbsTranslate[i].getFont());
						}
						sksTranslate[listLength] = new JLabel(toPush);
						sksTranslate[listLength].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, digitColor));
						sksTranslate[listLength].setFont(new Font("Monospace", Font.PLAIN, 12));
						sk.removeAll();
						repaint();
						revalidate();
						sk.add(sksTranslate[listLength]);
						try{
							Thread.sleep((int)((float)delay/2.0));
						}catch(InterruptedException e){}
						for(int i = 0; i < listLength; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							sb.removeAll();
							repaint();
							revalidate();
							for(int j = 0; j < listLength-i-1; j++){
								System.out.print("");//magical statement
								if(paused){j--;continue;}
								sb.add(sbsTranslate[j]);
								repaint();
								revalidate();
							}
							sk.removeAll();
							repaint();
							revalidate();
							for(int j = listLength-i-1; j < listLength+1; j++){
								System.out.print("");//magical statement
								if(paused){j--;continue;}
								sk.add(sksTranslate[j]);
								repaint();
								revalidate();
							}
							try{
								Thread.sleep((int)((float)delay/2.0));
							}catch(InterruptedException e){}
						}
						sbsTranslate = null;
						//Stack part end
						try{
							Thread.sleep(delay);
						}catch(InterruptedException e){}
						//Queue part start
						qbsTranslate = new JLabel[listLength];
						for(int i = 0; i < listLength; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							qbsTranslate[i] = new JLabel(" ");
							qbsTranslate[i].setBorder(qksTranslate[i].getBorder());
							qbsTranslate[i].setFont(qksTranslate[i].getFont());
							qbTranslate.add(qbsTranslate[i]);
							repaint();
							revalidate();
						}
						for(int i = 0; i < listLength; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							try{
								Thread.sleep((int)((float)delay/2.0));
							}catch(InterruptedException e){}
							qbsTranslate[i].setText(qksTranslate[i].getText());
						}
						try{
							Thread.sleep((int)((float)delay/2.0));
						}catch(InterruptedException e){}
						qkTranslate.removeAll();
						repaint();
						revalidate();
						qksTranslate = null;
						qksTranslate = new JLabel[listLength+1];
						for(int i = 0; i < listLength+1; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							qksTranslate[i] = new JLabel(" ");
							try{
								qksTranslate[i].setBorder(qbsTranslate[i].getBorder());
								qksTranslate[i].setFont(qbsTranslate[i].getFont());
							}catch(ArrayIndexOutOfBoundsException e){
								qksTranslate[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, digitColor));
								qksTranslate[i].setFont(new Font("Monospace", Font.PLAIN, 12));
							}
							qkTranslate.add(qksTranslate[i]);
							repaint();
							revalidate();
						}
						for(int i = 0; i < listLength; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							try{
								Thread.sleep((int)((float)delay/2.0));
							}catch(InterruptedException e){}
							qksTranslate[i].setText(qbsTranslate[i].getText());
						}
						try{
							Thread.sleep((int)((float)delay/2.0));
						}catch(InterruptedException e){}
						qksTranslate[listLength].setText(toPush);
						qbTranslate.removeAll();
						qbsTranslate = null;
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
							buffer[i] = new JLabel(alsTranslate[i].getText());
							buffer[i].setBorder(alsTranslate[i].getBorder());
							buffer[i].setFont(alsTranslate[i].getFont());
						}
						alsTranslate = null;
						alsTranslate = new JLabel[listLength+1];
						for(int i = 0; i < listLength; i++){
							System.out.print("");//magical statement
							if(paused){i--;continue;}
							alsTranslate[i] = new JLabel(buffer[i].getText());
							alsTranslate[i].setBorder(buffer[i].getBorder());
							alsTranslate[i].setFont(buffer[i].getFont());
						}
						alsTranslate[listLength] = new JLabel(toPush);
						alsTranslate[listLength].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, digitColor));
						alsTranslate[listLength].setFont(new Font("Monospace", Font.PLAIN, 12));
						alTranslate.add(alsTranslate[listLength]);
						buffer = null;
						listLength++;
						repaint();
						revalidate();
						//Array part end
						try{
							Thread.sleep(delay);
						}catch(InterruptedException e){}
						y += 2;
					}else if(translateTable[x][2].substring(y, y+3).equals("Pop")){
						y += 4;
						String toPop = translateTable[x][2].substring(y, y+3);
						while(toPop.startsWith("|") && toPop.endsWith("|")){
							//popMechanism();
							try{
								Thread.sleep(delay);
							}catch(InterruptedException e){}
							//Stack part start
							sk.removeAll();
							repaint();
							revalidate();
							JLabel[] buffer = new JLabel[listLength-1];
							for(int i = 0; i < listLength-1; i++){
								System.out.print("");//magical statement
								if(paused){i--;continue;}
								buffer[i] = new JLabel(sksTranslate[i].getText()); 
								buffer[i].setBorder(sksTranslate[i].getBorder());
								buffer[i].setFont(sksTranslate[i].getFont());
							}
							sksTranslate = null;
							sksTranslate = new JLabel[listLength-1];
							for(int i = 0; i < listLength-1; i++){
								System.out.print("");//magical statement
								if(paused){i--;continue;}
								sksTranslate[i] = new JLabel(buffer[i].getText());
								sksTranslate[i].setBorder(buffer[i].getBorder());
								sksTranslate[i].setFont(buffer[i].getFont());
								sk.add(sksTranslate[i]);
								repaint();
								revalidate();
							}
							buffer = null;
							//Stack part end
							try{
								Thread.sleep(delay);
							}catch(InterruptedException e){}
							//Queue part start
							qbsTranslate = new JLabel[listLength-1];
							for(int i = 0; i < listLength-1; i++){
								System.out.print("");//magical statement
								if(paused){i--;continue;}
								qbsTranslate[i] = new JLabel(" ");
								qbsTranslate[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, digitColor));
								qbsTranslate[i].setFont(new Font("Monospace", Font.PLAIN, 12));
								qbTranslate.add(qbsTranslate[i]);
							}
							try{
								Thread.sleep((int)((float)delay/2.0));
							}catch(InterruptedException e){}
							for(int i = 0; i < listLength-1; i++){
								System.out.print("");//magical statement
								if(paused){i--;continue;}
								qbsTranslate[i].setText(qksTranslate[i].getText());
								try{
									Thread.sleep((int)((float)delay/2.0));
								}catch(InterruptedException e){}
							}
							
							qksTranslate = null;
							qksTranslate = new JLabel[listLength-1];
							qkTranslate.removeAll();
							repaint();
							revalidate();
							for(int i = 0; i < listLength-1; i++){
								System.out.print("");//magical statement
								if(paused){i--;continue;}
								qksTranslate[i] = new JLabel(" ");
								qksTranslate[i].setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, digitColor));
								qksTranslate[i].setFont(new Font("Monospace", Font.PLAIN, 12));
								qkTranslate.add(qksTranslate[i]);
								repaint();
								revalidate();
							}
							try{
								Thread.sleep((int)((float)delay/2.0));
							}catch(InterruptedException e){}
							for(int i = 0; i < listLength-1; i++){
								System.out.print("");//magical statement
								if(paused){i--;continue;}
								qksTranslate[i].setText(qbsTranslate[i].getText());
								try{
									Thread.sleep((int)((float)delay/2.0));
								}catch(InterruptedException e){}
							}
							qbTranslate.removeAll();
							qbsTranslate = null;
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
								buffer[i] = new JLabel(alsTranslate[i].getText());
								buffer[i].setBorder(alsTranslate[i].getBorder());
								buffer[i].setFont(new Font("Monospace", Font.PLAIN, 12));
							}
							alTranslate.removeAll();
							repaint();
							revalidate();
							alsTranslate = null;
							alsTranslate = new JLabel[listLength-1];
							for(int i = 0; i < listLength-1; i++){
								System.out.print("");//magical statement
								if(paused){i--;continue;}
								alsTranslate[i] = new JLabel(buffer[i].getText());
								alsTranslate[i].setBorder(buffer[i].getBorder());
								alsTranslate[i].setFont(buffer[i].getFont());
								alTranslate.add(alsTranslate[i]);
								repaint();
								revalidate();
							}
							//Array part end
							listLength--;
							try{
								Thread.sleep(delay);
							}catch(InterruptedException e){}
							y += 3;
							if(y >= translateTable[x][2].length()){
								break;
							}
							toPop = translateTable[x][2].substring(y, y+3);
						}
					}
				}
			}
			while(paused){System.out.print("");}
			commitFieldTranslate.setText(translateTable[x][3]);
			try{
				Thread.sleep(delay*2);
			}catch(InterruptedException e){}
		}
	}
	
	public void clean(){
		translateTable = null;
		listLength = 0;
		sk.removeAll();
		sb.removeAll();
		qkTranslate.removeAll();
		qbTranslate.removeAll();
		alTranslate.removeAll();
		repaint();
		revalidate();
		
		parseFieldTranslate.setText("");
		readFieldTranslate.setText("");
		actionFieldTranslate.setText("");
		commitFieldTranslate.setText("");
	}
}