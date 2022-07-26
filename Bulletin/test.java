package Bulletin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Panel;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JScrollBar;

public class test extends JFrame {
	private BulletinInfo blt;
	
	


	public test() {
		
		
		
		setLocationRelativeTo(null);
		setVisible(true);
		setSize(1000, 800);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel starPnl = new JPanel();
		starPnl.setBounds(12, 10, 970, 212);
		getContentPane().add(starPnl);
		starPnl.setLayout(null);
		
		JLabel five = new JLabel("5점");
		five.setBounds(230, 25, 57, 15);
		starPnl.add(five);
		
		JLabel four = new JLabel("4점");
		four.setBounds(230, 60, 57, 15);
		starPnl.add(four);
		
		JLabel three = new JLabel("3점");
		three.setBounds(230, 95, 57, 15);
		starPnl.add(three);
		
		JLabel two = new JLabel("2점");
		two.setBounds(230, 130, 57, 15);
		starPnl.add(two);
		
		JLabel one = new JLabel("1점");
		one.setBounds(230, 165, 57, 15);
		starPnl.add(one);
		
		JLabel img = new JLabel("이미지");
		img.setBounds(34, 24, 164, 159);
		starPnl.add(img);
		
		JPanel mainPnl = new JPanel();
		mainPnl.setBounds(12, 232, 970, 470);
		getContentPane().add(mainPnl);
		mainPnl.setLayout(null);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollBar.setBounds(941, 0, 17, 470);
		mainPnl.add(scrollBar);
		
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
