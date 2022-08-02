package main;
import java.util.ArrayList;
import java.util.List;
 //추가할것 맘대로 넣으셈요
public class UserInfo {
	public static final int FULL_HP = 5; 
	public static final int FULL_MENTAL = 5; 
	private int hp = 5;
	private int mental = 5;
	private int bleed = 3;
	private int blue = 0;
	//밤낮/일자
	private int find = 0;
	private int buy = 0;
	private int sleep = 0;
	private int date = 1;
	private String userid;
	
	private List<Integer> party = new ArrayList<>();
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	private int npc_likability;
	private List<Item> inventory = new ArrayList<>();
	//item_id에 따라서 durability, count를 개별로 관리할 수 있게 정보 처리 -> 저장필요한 정보 inventory: list<Item>
//	private map<진행상황, 선택사항>
	
	public void findplus() {
		this.find += 1;
	}

	public void buyplus() {
		this.buy += 1;
	}
	public void sleepplus() {
		this.sleep += 1;
	}
	public void dateplus() {
		this.date += 1;
	}
	
	public void mentalRefresh(String updown, int num) {
		if (updown.equals("up")) {
			this.mental += num;
			if (mental > 5) {
				this.mental = 5;
			}
		} else {
			this.mental -= num;
			if (mental <= 0) {
				this.mental = 0;
			}
		}
	}
	
	
	public void HPRefresh(String updown, int num) {
		if (updown.equals("up")) {
			this.hp += num;
			if (hp > 5) {
				this.hp = 5;
			}
		} else {
			this.hp -= num;
			if (hp <= 0) {
				this.hp = 0;
			}
		}
	}
	
	public int getFind() {
		return find;
	}


	public void setFind(int find) {
		this.find = find;
	}


	public int getBuy() {
		return buy;
	}


	public void setBuy(int buy) {
		this.buy = buy;
	}


	public int getSleep() {
		return sleep;
	}


	public void setSleep(int sleep) {
		this.sleep = sleep;
	}


	public int getDate() {
		return date;
	}


	public void setDate(int date) {
		this.date = date;
	}


	public int getBleed() {
		return bleed;
	}

	public void setBleed(int bleed) {
		this.bleed = bleed;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	
	public int getHp() {
		return hp;
	}
	public void setHp(int hp) {
		this.hp = hp;
	}
	public int getMental() {
		return mental;
	}
	public void setMental(int mental) {
		this.mental = mental;
	}
	public List<Integer> getParty() {
		return party;
	}
	public void setParty(List<Integer> party) {
		this.party = party;
	}
	public int getNpc_likability() {
		return npc_likability;
	}
	public void setNpc_likability(int npc_likability) {
		this.npc_likability = npc_likability;
	}
	public List<Item> getInventory() {
		return inventory;
	}
	public void setInventory(List<Item> inventory) {
		this.inventory = inventory;
	}
}
 //사용예) SaveInfo user1 = new SaveInfo();
