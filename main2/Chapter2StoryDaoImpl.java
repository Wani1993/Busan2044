package main2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.green.BusanUtil;

public class Chapter2StoryDaoImpl implements Chapter2StoryDao {

	private Chapter2Story resultMapping(ResultSet rs) throws SQLException {

		int storyId = rs.getInt("storyId");
		String storyNum = rs.getString("storyNum");
		String storyMain = rs.getString("StoryMain");
		String storyTime = rs.getString("storyTime");

		return new Chapter2Story(storyId, storyNum, storyMain, storyTime);
	}

	@Override
	public List<Chapter2Story> read() throws SQLException {

		String query = "Select * From origin_chapter2";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Chapter2Story> list = new ArrayList<>();

		try {
			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			rs = pstmt.executeQuery();

			while (rs.next()) {
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
	public Chapter2Story read(int storyId) throws SQLException {

		String query = "SELECT * FROM origin_chapter2 WHERE storyId = ?";

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = BusanUtil.getConnection();
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, storyId);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				return resultMapping(rs);
			}
		} finally {
			BusanUtil.closeRS(rs);
			BusanUtil.closeStmt(pstmt);
			BusanUtil.closeConn(conn);
		}

		return null;
	}



}
