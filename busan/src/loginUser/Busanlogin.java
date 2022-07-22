package loginUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.co.greenart.dbutil.BusanUtil;


public class Busanlogin {
	private BusanUser user;
	
	
	private BusanUser resultMapping(ResultSet rs) throws SQLException {
		int userNum = rs.getInt("userNum");
		String id = rs.getString("id");
		String password = rs.getString("password");

		return new BusanUser(userNum, id, password);
	}

	public int create(String id, String password, String name, String birthDay) throws SQLException {
		String query = "INSERT INTO login_info (id, password, name, birthDay) VALUES (?, ?, ?, ?)";

		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			pstmt.setString(3, name);
			pstmt.setString(4, birthDay);

			return pstmt.executeUpdate();

		} finally {
			BusanUtil.closeStmt(pstmt);
			BusanUtil.closeConn(conn);
		}
	}

	public List<BusanUser> read() throws SQLException {
		String query = "SELECT * FROM login_info";

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;

		List<BusanUser> list = new ArrayList<BusanUser>();

		try {
			conn = BusanUtil.getConnection();
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);

			while (rs.next()) {
				int userNum = rs.getInt("userNum");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String birthDay = rs.getString("birthDay");
				user = new BusanUser(userNum, id, password, name, birthDay);
				list.add(user);
			}

		} finally {
			BusanUtil.closeRS(rs);
			BusanUtil.closeStmt(stmt);
			BusanUtil.closeConn(conn);
		}
		return list;
	}
	
	
	public int matchId(String id) throws SQLException {
		String query = "SELECT * FROM login_info WHERE id = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		

		

		try {
			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
//			pstmt.setString(2, password);
			return pstmt.executeUpdate();
			
		} finally {
			
			BusanUtil.closeStmt(pstmt);
			BusanUtil.closeConn(conn);
		}
	}

	public static void main(String[] args) {
		Busanlogin log = new Busanlogin();
		
		

		try {
			System.out.println(log.matchId("mmmm"));
			
//			for (int i = 0; i < log.read().size(); i++) {
//				if(log.read().get(i).getPassword().equals("aaaa")) {
//					System.out.println(log.read().get(i).getId());
//				}
//			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
