package main2;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;

import bulletin.Bulletin;
import main.UserInfo;

public class Chapter2 extends JPanel {
	public JPanel pnlTxt2;

	private JPanel choicePnl;
	private JPanel startPnl;

	private JButton[] btnChoice = new JButton[4];
	private JButton rest;
	private JButton shop;
	private JButton search;

	private JScrollPane jsp2;

	private JTextArea textArea;

	private JPanel battleBattle;

	public UserInfo user;

	private URL morningImg = Bulletin.class.getClassLoader().getResource("bg1.png");
	private URL afternoonImg = Bulletin.class.getClassLoader().getResource("bg2.png");
	private URL nightImg = Bulletin.class.getClassLoader().getResource("bg3.png");
	private ImageIcon morning = new ImageIcon(morningImg);
	private ImageIcon afternoon = new ImageIcon(afternoonImg);
	private ImageIcon night = new ImageIcon(nightImg);
	private JLabel dayImg;

	// 다음 화면으로 넘길 때 필요한 int
	private int ok = 0;

	

	public JButton getShop() {
		return shop;
	}

	public void setShop(JButton shop) {
		this.shop = shop;
	}

	public URL getMorningImg() {
		return morningImg;
	}

	public void setMorningImg(URL morningImg) {
		this.morningImg = morningImg;
	}

	public ImageIcon getMorning() {
		return morning;
	}

	public void setMorning(ImageIcon morning) {
		this.morning = morning;
	}

	public JLabel getDayImg() {
		return dayImg;
	}

	public void setDayImg(JLabel dayImg) {
		this.dayImg = dayImg;
	}

	public JPanel getStartPnl() {
		return startPnl;
	}
// ------------------------------------------------------------------------ //		
	// 전투 닫는 버튼 액션리스너입니다
	// TODO
//	ActionListener btEnd = new ActionListener() {
//		Battle_App a = new Battle_App();
//
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			user = a.getBtEndUserStatus();
//			/////// TODO 탐색화면으로 돌아가기
//		}
//	};

// ------------------------------------------------------------------------ //	
	// ev10 밤 이벤트(41, 42, 43) 배열로 받아서 조건걸기
	// 얘가 되긴 되는데.... 잘 되는지를 모르겠음...

	private void nigntEv10() {
		Chapter2StoryDao dao = new Chapter2StoryDaoImpl();
		Chapter2ChoiceDao Cdao = new Chapter2ChoiceDaoImpl();
		List<String> ev10 = new ArrayList<String>();
		// 선택지에 조건 가지고 오기 위한 객체

		// 조건(StoryPath) 가지고 오기 위한 객체
		Pathget p = new Pathget();

		try {

			ev10.add(dao.read(41).getStoryMain()); // 0 정신력-2
			ev10.add(dao.read(42).getStoryMain()); // 1 칼빵맞음
			ev10.add(dao.read(43).getStoryMain()); // 2 권총 얻음
			// 새로 추가해서 뒷번호
			ev10.add(dao.read(72).getStoryMain()); // 3 식량 갈취
			ev10.add(dao.read(73).getStoryMain()); // 4 같이 총으로 협박
			ev10.add(dao.read(74).getStoryMain()); // 5 살인남
			ev10.add(dao.read(75).getStoryMain()); // 6 찾아온 군인

			// choiceId "13"을 가지고 있을때
			if (p.getBoolean("yyyy", "13")) {

				textArea.setText(ev10.get(0));
				// 정신력 -2
				System.out.println("정신력 -2");
			}

			// choiceId "14"을 가지고 있을때
			if (p.getBoolean("yyyy", "14")) {
				textArea.setText(ev10.get(2));
				// 권총 얻음
				System.out.println("권총 얻음");
			}

			// choiceId "11" 가지고 있을때 -> 식량 갈취
			if (p.getBoolean("yyyy", "11")) {
				textArea.setText(ev10.get(6));

				// 선택지가 나와야함
				choicePnl.setVisible(true);
				btnChoice[0].setText(Cdao.read(39).getChoiceMain());
				btnChoice[1].setText(Cdao.read(40).getChoiceMain());
				btnChoice[2].setText(Cdao.read(41).getChoiceMain());
				btnChoice[3].setVisible(false);

				if (user.getInventory().contains("4") == true) {
					btnChoice[2].setEnabled(true);
					btnChoice[3].setEnabled(true);
				}

				btnChoice[0].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							textArea.setText(dao.read(72).getStoryMain());
							choicePnl.setVisible(false);
							// "식량 -3 없으면 체력 -1"
							System.out.println("식량 -3 없으면 체력 -1");

							if (textArea.getText().equals(dao.read(72).getStoryMain())) {
								checkEnd(true);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});

				btnChoice[1].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							textArea.setText(dao.read(73).getStoryMain());
							choicePnl.setVisible(false);
							// 스탯 증감 없음

							if (textArea.getText().equals(dao.read(73).getStoryMain())) {
								checkEnd(true);
							}

						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});

				btnChoice[2].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						try {
							textArea.setText(dao.read(74).getStoryMain());
							choicePnl.setVisible(false);
							// 스탯 증감 없음

							if (textArea.getText().equals(dao.read(74).getStoryMain())) {
								checkEnd(true);
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

// ------------------------------------------------------------------------ //	
	// ev11 낮 GM 이벤트 (StoryId = 46, 47, 49, 51, 52) 배열로 받아서 랜덤 출력
	// 스탯 변화 있음
	private void GmEvDay() {
		Random random = new Random();

		Chapter2StoryDao dao = new Chapter2StoryDaoImpl();
		List<String> gmDay = new ArrayList<String>();

		try {
			gmDay.add(dao.read(46).getStoryMain()); // 정신력 -2
			gmDay.add(dao.read(47).getStoryMain()); // 체력 +1
			gmDay.add(dao.read(49).getStoryMain()); // 정신력 -1
			gmDay.add(dao.read(51).getStoryMain()); // 아이템 효자손 +1
			gmDay.add(dao.read(52).getStoryMain()); // 체력 +1 정신력 +1

		} catch (SQLException e) {
			e.printStackTrace();
		}

		int a = random.nextInt(gmDay.size());
		textArea.setText(gmDay.get(a));

		checkEnd(true);

		// 아이템과 스탯 반영하는 부분
		if (a == 0) {
			// 정신력 -2
			System.out.println("정신력 -2");
		}

		if (a == 1) {
			// 체력 +1
			System.out.println("체력 + 1");
		}

		if (a == 2) {
			// 정신력 -1
			System.out.println("정신력 -1");
		}

		if (a == 3) {
			// 아이템 효자손 +
			System.out.println("아이템 효자손 +1");
		}

		if (a == 4) {
			// 체력 +1 정신력 +1
			System.out.println("체력 +1 정신력 +1");
		}

		// 한 번 뜬 이벤트는 뜨지 않도록 하기
		gmDay.remove(a);

	}

// ------------------------------------------------------------------------ //
	// ev11 밤 GM 이벤트 (StoryId = 45, 48, 50) 배열로 받아서 랜덤 출력
	// 스탯 변화 있음
	private List<String> GmEvNight() {
		Random random = new Random();

		Chapter2StoryDao dao = new Chapter2StoryDaoImpl();
		List<String> gmNight = new ArrayList<String>();

		try {
			gmNight.add(dao.read(45).getStoryMain()); // 체력 +1
			gmNight.add(dao.read(48).getStoryMain()); // 체력 -1
			gmNight.add(dao.read(50).getStoryMain()); // 체력 +1

		} catch (SQLException e) {
			e.printStackTrace();
		}

		int b = random.nextInt(gmNight.size());
		textArea.setText(gmNight.get(b));
		checkEnd(true);

		// 아이템과 스탯 반영하는 부분
		if (b == 0) {
			// 정신력 -2
			System.out.println("체력+1");
		}

		if (b == 1) {
			// 체력 +1
			System.out.println("체력-1");
		}

		if (b == 2) {
			// 정신력 -1
			System.out.println("체력+1");
		}

		// 똑같은 이벤트 다시 안나오게 하기
		gmNight.remove(b);
		return gmNight;
	}

// ------------------------------------------------------------------------ //
	// ev12 밤 (StoryId = 53, 54, 55, 57, 58 )
	// 스탯 변화 있음
	private void nightEv12() {
		Chapter2StoryDao dao = new Chapter2StoryDaoImpl();
		Chapter2ChoiceDao Cdao = new Chapter2ChoiceDaoImpl();

		try {
			textArea.setText(dao.read(53).getStoryMain());
			choicePnl.setVisible(true);

			btnChoice[0].setText(Cdao.read(23).getChoiceMain());
			btnChoice[1].setText(Cdao.read(24).getChoiceMain()); // 정신력 -2
			btnChoice[2].setText(Cdao.read(25).getChoiceMain());
			btnChoice[3].setVisible(false);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// 테스트 하려고 임의로 쿠키와 한은둔을 넣은 것
//			user.setParty(Arrays.asList(9, 8));
		if (user.getParty().contains(9) == false) {
			btnChoice[0].setEnabled(false);
		}

		if (user.getParty().contains(8) == false) {
			btnChoice[1].setEnabled(false);
		}

		// 선택지 눌렀을 때 화면 넘기기
		btnChoice[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText(dao.read(54).getStoryMain());
					choicePnl.setVisible(false);

					if (textArea.getText().equals(dao.read(54).getStoryMain())) {
						checkEnd(true);
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnChoice[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText(dao.read(55).getStoryMain());
					choicePnl.setVisible(false);
					// 정신력 -1
					System.out.println("정신력-1");

					if (textArea.getText().equals(dao.read(55).getStoryMain())) {
						checkEnd(true);
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnChoice[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText(dao.read(57).getStoryMain());
					choicePnl.setVisible(false);

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					if (ok == 0) {
						ok++;
					}

					if (ok == 1 && textArea.getText().equals(dao.read(57).getStoryMain())) {
						textArea.setText(dao.read(58).getStoryMain());
						choicePnl.setVisible(false);

						if (textArea.getText().equals(dao.read(58).getStoryMain())) {
							checkEnd(true);
						}
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

// ------------------------------------------------------------------------ //	
	// ev14 밤 (StoryId = 64, 65, 66)
	// 스탯 증감 있음
	// 나오는 확률 조정이 필요할지 테스트 해보기
	private void nigntEv14() {

		Chapter2StoryDao dao = new Chapter2StoryDaoImpl();
		Chapter2ChoiceDao Cdao = new Chapter2ChoiceDaoImpl();

		try {
			textArea.setText(dao.read(64).getStoryMain());
			choicePnl.setVisible(true);

			btnChoice[0].setText(Cdao.read(37).getChoiceMain()); // 체력 -2, 정신력 -2, 감기+
			btnChoice[1].setText(Cdao.read(38).getChoiceMain()); // 체력 -1
			btnChoice[2].setVisible(false);
			btnChoice[3].setVisible(false);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		btnChoice[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText(dao.read(65).getStoryMain());
					choicePnl.setVisible(false);
					// 체력 -2 정신력 -2 감기+
					System.out.println("체력-2 정신력-2 걍 죽어라 이말이에용");

					if (textArea.getText().equals(dao.read(65).getStoryMain())) {
						checkEnd(true);
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnChoice[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText(dao.read(66).getStoryMain());
					choicePnl.setVisible(false);

					if (textArea.getText().equals(dao.read(66).getStoryMain())) {
						checkEnd(true);
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

// ------------------------------------------------------------------------ //	
	// ev15 낮 (StoryId = 68, 69, 70, 71)
	// 정보를 주는 이벤트라서 스탯 증감 없음!!!
	private void nigntEv15() {

		Chapter2StoryDao dao = new Chapter2StoryDaoImpl();
		Chapter2ChoiceDao Cdao = new Chapter2ChoiceDaoImpl();

		try {
			textArea.setText(dao.read(67).getStoryMain());
			choicePnl.setVisible(true);

			btnChoice[0].setText(Cdao.read(34).getChoiceMain());
			btnChoice[1].setText(Cdao.read(35).getChoiceMain());
			btnChoice[2].setText(Cdao.read(36).getChoiceMain());
			btnChoice[3].setVisible(false);

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		btnChoice[0].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText(dao.read(68).getStoryMain());
					choicePnl.setVisible(false);

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		textArea.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {

					if (ok == 0) {
						ok++;
					}
					if (ok == 1 && textArea.getText().equals(dao.read(68).getStoryMain())) {
						textArea.setText(dao.read(69).getStoryMain());
						choicePnl.setVisible(false);

						if (textArea.getText().equals(dao.read(69).getStoryMain())) {
							checkEnd(true);
						}

					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnChoice[1].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText(dao.read(70).getStoryMain());
					choicePnl.setVisible(false);

					if (textArea.getText().equals(dao.read(70).getStoryMain())) {
						checkEnd(true);
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});

		btnChoice[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					textArea.setText(dao.read(71).getStoryMain());
					choicePnl.setVisible(false);

					if (textArea.getText().equals(dao.read(71).getStoryMain())) {
						checkEnd(true);
					}

				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}

// ------------------------------------------------------------------------ //	
	// TODO
	// 옮긴 패널에서 어떤게 버튼부분인지 보고 하도록 하기
	private void checkEnd(boolean check) {

		if (check = true) {
			textArea.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					startPnl.setVisible(true);
					jsp2.setVisible(false);

				}
			});
		}

	}

// ------------------------------------------------------------------------ //	
	public Chapter2(UserInfo user, StoryPnl2 StoryPnl2) {
		// 오타나있어서 고쳤움
		this.user = user;
		// 인하 ---- 필요한 값 필드로 뺐음!!!
//		Battle_Algo ba = new Battle_Algo();
//		user = ba.setUserData();
//		Enemy_Dao ed = new Enemy_Dao();
		//////////////////////////////////////

		pnlTxt2 = new JPanel();
		pnlTxt2.setBounds(0, 0, 824, 841); // 크기를 정해줘야 다른 class에서 사용 가능
		pnlTxt2.setBorder(new LineBorder(new Color(0, 0, 0)));

		dayImg = new JLabel();
		dayImg.setBounds(10, 12, 830, 90);
		dayImg.setIcon(morning);
		pnlTxt2.add(dayImg);

		jsp2 = new JScrollPane();
		jsp2.setBounds(12, 100, 800, 480);
		pnlTxt2.add(jsp2);

		textArea = new JTextArea();
		jsp2.setViewportView(textArea);
		textArea.setEditable(false);
		jsp2.setVisible(false);

//		선택지 시작
		choicePnl = new JPanel();
		choicePnl.setBounds(12, 579, 800, 252);
		pnlTxt2.add(choicePnl);
		choicePnl.setLayout(null);
		choicePnl.setVisible(false);

		int a = 10;
		for (int i = 0; i < btnChoice.length; i++) {
			btnChoice[i] = new JButton("선택지1");
			btnChoice[i].setBounds(12, a, 776, 52);
			a += 62;
			choicePnl.add(btnChoice[i]);
		}
//		선택지 끝

		add(pnlTxt2);
		pnlTxt2.setLayout(null);

		startPnl = new JPanel();
		startPnl.setBounds(413, 76, 383, 374);
		pnlTxt2.add(startPnl);
		startPnl.setLayout(null);

//		// 효정이꺼 넣기
//		ShopPnl sp = new ShopPnl(user, StoryPnl2);
//		pnlTxt2.add(sp);
//		sp.setVisible(false);

		search = new JButton("탐색");
		search.setBounds(44, 82, 300, 64);
		startPnl.add(search);

		// 다시 스타트패널 뜨게 하는게 좀...
		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				startPnl.setVisible(false);
				jsp2.setVisible(true);

				// 낮인지 밤인지 구분
				Random random = new Random();

				int prob = random.nextInt(100) + 1; // 이거는 확률
				System.out.println("확률입니당 : " + prob);

				List<Integer> eventHome = new ArrayList<Integer>();
				eventHome.add(0);
				eventHome.add(1);
				eventHome.add(2);
				eventHome.add(3);
				eventHome.add(4);
				eventHome.add(5);

				System.out.println("배열을 출력해봅니당 : " + eventHome);

				int evOut = random.nextInt(6); // 이벤트 갯수
				System.out.println("랜덤으로 나온 숫자입니당 :  " + evOut);

				if (prob >= 20) {
					System.out.println("확률입니다. :" + prob);

					eventHome.get(evOut);
					System.out.println("eventHome.get한거 : " + eventHome.get(evOut));

					// 이벤트
					if (evOut == 0) {
						GmEvNight();
						System.out.println("evout 0");

//						textArea.addAncestorListener(listener);
//						checkEnd(true);

						if (GmEvNight() == null) {
							// 전투로간다!!
							// TODO
//							System.out.println("전투다!!!");
//
//							Battle_Algo ba = new Battle_Algo();
//							Enemy_Dao ed = new Enemy_Dao();
//							Enemy enemy = ed.selectRandomEnemy(1);
//
//							Battle_App bt = new Battle_App(enemy, user);
//							add(bt.getPnl());
//							textArea.setVisible(false);
//							pnlTxt2.setVisible(false);

						}

					}

					if (evOut == 1) {
						nightEv12();
						System.out.println("evout 1");
						eventHome.remove(1);
					} else if (eventHome.get(evOut) == null) {
						System.out.println("한 번 나왔던 이벤트라 전투입니당!");
					}

					if (evOut == 2) {
						nigntEv14();
						System.out.println("evout 2");
						eventHome.remove(2);
					} else if (eventHome.get(evOut) == null) {
						System.out.println("한 번 나왔던 이벤트라 전투입니당!");
					}

					if (evOut == 3) {
						nigntEv15();
						System.out.println("evout 3");
						eventHome.remove(3);
					} else if (eventHome.get(evOut) == null) {
						System.out.println("한 번 나왔던 이벤트라 전투입니당!");
					}

					if (evOut == 4) {
						System.out.println("evout 4");
						Pathget p = new Pathget();
						// p가 choice 9, 10 둘 중 하나라도 가지고 있을때,
						try {
							if (p.getBoolean("id", "10") || p.getBoolean("id", "9")) {
								nigntEv10();
							} else {
								// 전투로 간다!!
								System.out.println("전투!");
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}

					} else if (eventHome.get(evOut) == null) {
						System.out.println("한 번 나왔던 이벤트라 전투입니당!");
					}

					if (evOut == 5) {
						GmEvDay();
						System.out.println("evout 5");
						eventHome.remove(5);
					}

					if (user.getDate() < 5) {
						user.findplus();
						if (user.getFind() == 1) {
							dayImg.setIcon(afternoon);
						} else if (user.getFind() == 2) {
							dayImg.setIcon(night);
						} else if (user.getFind() == 3) {
							dayImg.setIcon(morning);
							user.setFind(0);
							user.dateplus();
//							lblDate.setText(user.getDate() + "일");
						}
						System.out.println(user.getDate() + "일이다");
					} else {
						System.out.println("게임끝");
					}

				} else {
					// 전투
					// TODO
				}

				// GmDay 하나만 남음!!!!
//				GmEvDay();
				if (user.getFind() == 2) {
					shop.setEnabled(true);
				} else {
					shop.setEnabled(false);
				}

			}
		});

		ShopPnl sp = new ShopPnl(user, StoryPnl2, Chapter2.this);
		pnlTxt2.add(sp);
		sp.setVisible(false);
		
		shop = new JButton("상점");
		shop.setEnabled(false);
		shop.setBounds(44, 156, 300, 64);
		startPnl.add(shop);
		shop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startPnl.setVisible(false); // 이게 탐색 상점 휴식 버튼 있는 애임
				
				jsp2.setVisible(false); // textArea에 스크롤패인 단거
				textArea.setEditable(false); // text부분
				choicePnl.setVisible(false); // 선택지부분
				
				sp.setVisible(true);
			}

		});

		rest = new JButton("휴식");
		rest.setBounds(44, 230, 300, 64);
		startPnl.add(rest);

		rest.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (user.getDate() < 5) {
					user.sleepplus();
					if (user.getSleep() == 1) {
						dayImg.setIcon(morning);
						user.setSleep(0);
						user.setFind(0);
						user.dateplus();
//						lblDate.setText(user.getDate() + "일");
					}
				} else {
					System.out.println("게임끝");
				}
			}
		});

		setSize(830, 870);

	}

	public static void main(String[] args) {
//		new Chapter2_Screen().setVisible(true);

	}
}
