package savehere;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kr.co.greenart.dbutil.BusanUtil;
import loginUser.Busanlogin;


public class SaveHereDaoImpl implements SaveHereDao {

	private SaveHere resultMapping(ResultSet rs) throws SQLException {
		int num = rs.getInt("num");
		String userId = rs.getString("userId");
		String stroyPath = rs.getString("stroyPath");
		

		return new SaveHere(num, userId, stroyPath);
	}

	@Override
	public int create(String userId, String storyPath) throws SQLException {
		String query = "INSERT INTO savehere (userId , storyPath) VALUES (?, ?)";
		

		Connection conn = null;
		PreparedStatement pstmt = null; // statement 의 자식 클래스
										// 쿼리문에 넣을 값을 ? 로 만들어놓고 이후에 내가 원하는  값으로 세팅해준다
		
		 
		try {
			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			 // 첫번째 인덱스에 값을 넣는다
			pstmt.setString(1, userId);
			pstmt.setString(2, storyPath);
			
			
			return pstmt.executeUpdate();  // 위에서 쿼리문을 다 설정을 해놓은 상태라 파라미터를 비워놓는다.
			
		} finally {
			BusanUtil.closeStmt(pstmt);
			BusanUtil.closeConn(conn);
		}
	}

	@Override
	public List<SaveHere> read() throws SQLException {
	String query = "SELECT * FROM savehere";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<SaveHere> list = new ArrayList<>();
		try {
			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(resultMapping(rs));
			}
			
		} finally {
			BusanUtil.closeRS(rs);
			BusanUtil.closeStmt(pstmt);
			BusanUtil.closeConn(conn);
		}
		return list;
	}

	@Override
	public SaveHere read(int num) throws SQLException {
		String query = "SELECT * FROM savehere WHERE companyNum = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, num);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				return resultMapping(rs);
			}
			
		} finally {
			BusanUtil.closeRS(rs);
			BusanUtil.closeStmt(pstmt);
			BusanUtil.closeConn(conn);
		}
		return null;
	}

	@Override
	public int update(int num, String userId, String stroyPath) throws SQLException {
		String query = "UPDATE savehere SET userId = ?, stroyPath = ? WHERE num = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userId);
			pstmt.setString(2, stroyPath);
			
			pstmt.setInt(3, num);
			
			return pstmt.executeUpdate();
		} finally {
			BusanUtil.closeStmt(pstmt);
			BusanUtil.closeConn(conn);
		}
	}

	@Override
	public int delete(int num) throws SQLException {
		String query = "DELETE FROM savehere WHERE num = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, num);
			
			return pstmt.executeUpdate();
		} finally {
			BusanUtil.closeStmt(pstmt);
			BusanUtil.closeConn(conn);
		}
	}
	
	public static void main(String[] args) {
		SaveHereDaoImpl sh = new SaveHereDaoImpl();
		Busanlogin l = new Busanlogin();
		Random ran = new Random();
		try {
			l.read();
			for (int i = 0; i < 5; i++) {
				sh.create(l.getList().get(i).getId(), i + "-" + (i + ran.nextInt(5) + 1) + "-" + (i + ran.nextInt(8) + 1) + "-");
			}
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
	}

}
