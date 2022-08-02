package main2;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ToolTipManager;
import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import main.ItemConsole;
import main.ItemDao;
import main.Talk;
import main.Trade;
import main.UserInfo;

public class ShopPnl extends JPanel{
	private JPanel btnPnl;
	
	private int count1 = 0;
	private int count2 = 0;
	public JPanel getBtnPnl() {
		return btnPnl;
	}

	public void setBtnPnl(JPanel btnPnl) {
		this.btnPnl = btnPnl;
	}

	
	public ShopPnl(UserInfo user, StoryPnl2 StoryPnl2, Chapter2 chapter2) {
		setBounds(0, 100, 824, 841);
		setLayout(null);
		
		btnPnl = new JPanel();
		btnPnl.setLayout(null);
		btnPnl.setBounds(0, 0, 824, 841);
		
		
		JButton talkBtn = new JButton("대화하기");
        talkBtn.setFont(new Font("HY목각파임B", Font.BOLD, 40));
        talkBtn.setBackground(Color.gray);
        talkBtn.setForeground(Color.white);
        talkBtn.setBounds(200, 200, 200, 100);
       
        
        JButton outTradeBtn = new JButton("거래하기");
        outTradeBtn.setFont(new Font("HY목각파임B", Font.BOLD, 40));
        outTradeBtn.setBackground(Color.gray);
        outTradeBtn.setForeground(Color.white);
        outTradeBtn.setBounds(400, 200, 200, 100);
        
        btnPnl.add(outTradeBtn);
        btnPnl.add(talkBtn);
        add(btnPnl);
       
        
        talkBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (count2 == 0) {
					Talk talk = new Talk(user.getDate(), user, ShopPnl.this);
					talk.setVisible(true);
					talk.setBounds(14, 100, 800, 600);
					talkBtn.setEnabled(false);
					count2++;
				}
			}
		});
//        
        
        outTradeBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               if (count1 == 0) {
                  Trade trade = new Trade(new ItemConsole(new ItemDao(), user), ShopPnl.this, StoryPnl2);
                  trade.setVisible(true);
                  trade.setBounds(14, 12, 800, 600);

                  trade.setStoreItem(3, 1, 1);
                  trade.setMyItem();
                  for (int i = 0; i < trade.getStoreItem().size(); i++) {
                     StoryPnl2.getItemConsole().imgHover(trade.getStoreItem().get(i));
                  }
                  for (int i = 0; i < trade.getMyItem().size(); i++) {
                     StoryPnl2.getItemConsole().imgHover(trade.getMyItem().get(i));
                  }
                  ToolTipManager m = ToolTipManager.sharedInstance();
                  m.setInitialDelay(0);
                  outTradeBtn.setEnabled(false);
                  count1++;

                  // 220801 3:57 수정//
               }

            }
         });
        
        JButton exitBtn = new JButton("나가기");
        exitBtn.setBounds(600, 600, 100, 30);
        btnPnl.add(exitBtn);
        exitBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				chapter2.getStartPnl().setVisible(true);
				
//				new Chapter2(user, StoryPnl2).setVisible(true);
//				
//				container.getPnlTxt2().setVisible(true);
				ShopPnl.this.setVisible(false);
				user.dateplus();
				user.setFind(0);
				user.setSleep(0);
				chapter2.getDayImg().setIcon(chapter2.getMorning());
				System.out.println(user.getFind() + "탐색" + user.getDate() + "일");
				chapter2.getShop().setEnabled(false);
				talkBtn.setEnabled(true);
				outTradeBtn.setEnabled(true);
				count1 = 0;
				count2 = 0;
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~민트초코 개싫어
				StoryPnl2.getDateBtn().setText(user.getDate() + "일");
				StoryPnl2.getItemConsole().eatRcv();
				StoryPnl2.userInven();
				StoryPnl2.hpmp();
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~민트초코 개싫어
				
				
			}
		});
        

	}
}
