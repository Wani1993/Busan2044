package savehere;

public class SaveHere {
	private int num;
	private String userId;
	private String stroyPath;

	public SaveHere() {
		super();
	}

	public SaveHere(int num, String userId, String stroyPath) {
		super();
		this.num = num;
		this.userId = userId;
		this.stroyPath = stroyPath;
		
	}
	
	
	public SaveHere(String userId, String stroyPath) {
		super();
		this.userId = userId;
		this.stroyPath = stroyPath;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStroyPath() {
		return stroyPath;
	}

	public void setStroyPath(String stroyPath) {
		this.stroyPath = stroyPath;
	}


	@Override
	public String toString() {
		return "SaveHere [num=" + num + ", userId=" + userId + ", stroyPath=" + stroyPath + "]";
	}
	
		
	
	
}
