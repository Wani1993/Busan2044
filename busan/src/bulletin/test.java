package bulletin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class test extends JFrame {
	private BulletinInfo blt;
	private JPanel[] pnlC = new JPanel[20];
	private JLabel[] lblC = new JLabel[20];
	private JLabel[] lblD = new JLabel[20];
	private int i = 5;
	


	public test() {
		BulletinBord BB = new BulletinBord();
		
		
		setLocationRelativeTo(null);
		setVisible(true);
		setSize(1000, 800);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel starPnl = new JPanel();
		starPnl.setBounds(12, 10, 970, 212);
		starPnl.setBackground(Color.pink);
		getContentPane().add(starPnl);
		starPnl.setLayout(null);
		
		JLabel five = new JLabel("5점");
		five.setBounds(230, 25, 57, 15);
		starPnl.add(five);
		JLabel fiveG = new JLabel("■■■■■■■■■■■■■■■■■■");
		fiveG.setBounds(273, 25, 674, 15);
		starPnl.add(fiveG);
		
		JLabel four = new JLabel("4점");
		four.setBounds(230, 60, 57, 15);
		starPnl.add(four);
		JLabel fourG = new JLabel("■■■■■■■■■■");
		fourG.setBounds(273, 60, 674, 15);
		starPnl.add(fourG);
		
		JLabel three = new JLabel("3점");
		three.setBounds(230, 95, 57, 15);
		starPnl.add(three);
		JLabel threeG = new JLabel("■■■■■■");
		threeG.setBounds(273, 95, 674, 15);
		starPnl.add(threeG);
		
		JLabel two = new JLabel("2점");
		two.setBounds(230, 130, 57, 15);
		starPnl.add(two);
		JLabel twoG = new JLabel("■■■■■■");
		twoG.setBounds(273, 130, 674, 15);
		starPnl.add(twoG);
		
		JLabel one = new JLabel("1점");
		one.setBounds(230, 165, 57, 15);
		starPnl.add(one);
		JLabel oneG = new JLabel("■■");
		oneG.setBounds(273, 165, 674, 15);
		starPnl.add(oneG);
		
		JLabel img = new JLabel("이미지");
		img.setBounds(34, 24, 164, 159);
		starPnl.add(img);
		
		
		
		JPanel mainPnl = new JPanel();
		mainPnl.setBounds(12, 232, 970, 470);
		getContentPane().add(mainPnl);
		mainPnl.setLayout(null);
		
//		JScrollPane scroll = new JScrollPane(mainPnl);
//		scroll.setViewportView(mainPnl);
//		mainPnl.add(scroll);
		
		JPanel pnlComment = new JPanel();
		pnlComment.setBounds(12, 712, 970, 49);
		getContentPane().add(pnlComment);
		pnlComment.setLayout(null);
		
		JTextField textField = new JTextField();
		textField.setBounds(12, 10, 846, 29);
		textField.setColumns(10);
		pnlComment.add(textField);
		
		JButton upload = new JButton("등록하기");
		upload.setBackground(Color.WHITE);
		upload.setBounds(868, 10, 90, 29);
		pnlComment.add(upload);
		
		BoxLayout box = new BoxLayout(mainPnl, BoxLayout.Y_AXIS);
		mainPnl.setLayout(box);
		
	
		
		for (int i = 0; i < pnlC.length; i++) {
			pnlC[i] = new JPanel();
//			pnlC[i].setBounds(0, 0, 100, 70);
			pnlC[i].setBackground(Color.white);
			mainPnl.add(pnlC[i]);
			lblC[i] = new JLabel("wani");
			lblD[i] = new JLabel("이지합니다 이지해요");
			pnlC[i].add(lblC[i]);
			pnlC[i].add(lblD[i]);			
		}
//		lblC[0] = new JLabel("뒤집어놓으셨다~~!!!!!");
//		lblC[1] = new JLabel("뒤집어놓으셨다~~!!!!!");
//		lblC[2] = new JLabel("뒤집어놓으셨다~~!!!!!");
//		lblC[3] = new JLabel("뒤집어놓으셨다~~!!!!!");
//		lblC[4] = new JLabel("뒤집어놓으셨다~~!!!!!");
//		mainPnl.add(lblC[0]);
//		mainPnl.add(lblC[1]);
//		mainPnl.add(lblC[2]);
//		mainPnl.add(lblC[3]);
//		mainPnl.add(lblC[4]);
//		lblC[0].setText("wani");
//		lblD[0].setText("너무이지해서 노잼");
//		lblC[1].setText("이것이 맞는것이냐");
//		lblD[1].setText("아무렴 맞는것이지");
//		lblC[2].setText("이것이 맞는것이냐");
//		lblD[2].setText("아무렴 맞는것이지");
//		lblC[3].setText("이것이 맞는것이냐");
//		lblD[3].setText("아무렴 맞는것이지");
		
		
		upload.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
//				pnlC[i] = new JPanel();
//				lblC[i] = new JLabel();
//				pnlC[i].setBounds(0, 0, 940, 70);
//				pnlC[i].setBackground(Color.black);
//				mainPnl.add(pnlC[i]);
//				pnlC[i].add(lblC[i]);
//				lblC[i].setLayout(null);
				lblD[i].setText(textField.getText());
				i += 1;
//				BB.BulletCreate(BB.BulletInputId(i), BB.BulletInputName(i), textField.getText(), 5);
			}
		});
		
		
				
		
	}

//	public void paint(Graphics g) {
////        g.clearRect(0,0,getWidth(),getHeight());
//		g.setColor(Color.GREEN);
//		g.fillRect(250, 50, 300, 10);
//		g.fillRect(250, 65, 250, 10);
//		g.fillRect(250, 80, 200, 10);
//		g.fillRect(250, 95, 150, 10);
//		g.fillRect(250, 110, 100, 10);
//		g.dispose();
//	}
	
	public static void main(String[] args) {
		new test().setVisible(true);
	}
}
