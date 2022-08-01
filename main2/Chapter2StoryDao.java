package main2;
import java.sql.SQLException;
import java.util.List;



public interface Chapter2StoryDao {
		List<Chapter2Story> read() throws SQLException;
		Chapter2Story read(int storyId) throws SQLException;
}
