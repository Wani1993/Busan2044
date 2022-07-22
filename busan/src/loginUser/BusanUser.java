package loginUser;

public class BusanUser {
	private int userNum;
	private String id;
	private String password;
	private String name;
	private String birthDay;
	
	public BusanUser(int userNum, String id, String password) {
		super();
		this.userNum = userNum;
		this.id = id;
		this.password = password;
	}

	public BusanUser(String id, String password) {
		super();
		this.id = id;
		this.password = password;
	}

	public BusanUser(String id, String password, String name, String birthDay) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.birthDay = birthDay;
	}

	public BusanUser(int userNum, String id, String password, String name, String birthDay) {
		super();
		this.userNum = userNum;
		this.id = id;
		this.password = password;
		this.name = name;
		this.birthDay = birthDay;
	}

	public int getUserNum() {
		return userNum;
	}

	public void setUserNum(int userNum) {
		this.userNum = userNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	@Override
	public String toString() {
		return "BusanUser [userNum=" + userNum + ", id=" + id + ", password=" + password + ", name=" + name
				+ ", birthDay=" + birthDay + "]";
	}


	
}
