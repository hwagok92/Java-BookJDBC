import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookDao {

	private String driver = "oracle.jdbc.driver.OracleDriver" ;
	private String url = "jdbc:oracle:thin:@localhost:1521:orcl" ;
	private String username = "jspid" ;
	private String password = "jsppw" ;

	Connection conn = null;
	PreparedStatement ps = null;
	ResultSet rs = null;

	BookDao(){
		try {
			Class.forName(driver) ;			
		} catch (ClassNotFoundException e) {
			System.out.println("클래스가 발견되지 않습니다(jar 파일 누락)"); 
			e.printStackTrace();		
		}
	}

	void getConnection() {
		try {
			conn = DriverManager.getConnection(url, username, password) ;
		} catch (SQLException e) {
			System.out.println("커넥션 생성 오류");
			e.printStackTrace();
		}
	}

	public ArrayList<BookBean> getAllBook() {
		//2.
		getConnection();

		String sql = "select * from book order by no asc";
		ArrayList<BookBean> lists = new ArrayList<BookBean>();
		try {
			// 3.
			ps = conn.prepareStatement(sql);

			// 4.
			rs = ps.executeQuery();
			while(rs.next()) {

				//int no = rs.getInt("no");

				BookBean bean = new BookBean();
				//bean.setNo(no);
				bean.setNo(rs.getInt("no"));
				bean.setTitle(rs.getString("title"));
				bean.setAuthor(rs.getString("author"));
				bean.setPublisher(rs.getString("publisher"));
				bean.setPrice(rs.getInt("price"));
				bean.setPub_day(String.valueOf(rs.getDate("pub_day")));  

				lists.add(bean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null)
					rs.close();

				if(ps!=null)
					ps.close();

				if(conn!=null)
					conn.close();

			}catch(SQLException e) {

			}
		}
		return lists;
	}

	public int insertData(BookBean book) {

		System.out.println(book.getNo());
		
		getConnection();

		String sql = "insert into book(no,title,author,publisher,price,pub_day) " ;
		sql += " values(bseq.nextval, ?, ?, ?, ?, to_date(?, 'yyyy/mm/dd')) " ; 
		int cnt = -1;

		try {
			ps = conn.prepareStatement(sql);
			System.out.println(1);
			ps.setString( 1, book.getTitle() );
			ps.setString( 2, book.getAuthor() );
			ps.setString( 3, book.getPublisher() );
			ps.setInt( 4, book.getPrice() );			
			ps.setString( 5, book.getPub_day() );
			System.out.println(2);
			
			cnt = ps.executeUpdate();
			System.out.println(3);
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(ps!=null)
					ps.close();

				if(conn!=null)
					conn.close();

			}catch(SQLException e) {

			}
		}

		return cnt;
	}

	public int updateData(BookBean book) {

		getConnection();

		String sql = "update book set title=?, author=?, publisher=?, price=?, pub_day=to_date(?, 'yyyy/mm/dd') " ;
		sql += " where no = ?" ;
		int cnt = -1;
		try {
			
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(sql);

			ps.setString( 1, book.getTitle() );
			ps.setString( 2, book.getAuthor() );
			ps.setString( 3, book.getPublisher() );
			ps.setInt( 4, book.getPrice() );			
			ps.setString( 5, book.getPub_day() );
			ps.setInt( 6, book.getNo() );

			cnt = ps.executeUpdate();
			conn.commit();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		} finally {
			try {
				if(ps!=null)
					ps.close();

				if(conn!=null)
					conn.close();

			}catch(SQLException e) {

			}
		}

		return cnt;

	}

	public int deleteData(int no) {
		getConnection();
		String sql = "delete from book where no=?";
		int cnt = -1;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			cnt = ps.executeUpdate();
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(ps!=null)
					ps.close();

				if(conn!=null)
					conn.close();

			}catch(SQLException e) {

			}
		}
		
		return cnt;
	}

	public ArrayList<BookBean> getBookBySearch(String column, String search_word) {
//		column : title, author, publisher
		System.out.println();
		getConnection();
		
		ArrayList<BookBean> lists = new ArrayList<BookBean>();
		//String sql = "select * from book where " +  column + " like ? order by no";
//		String sql = "select * from book where " +  column + " like '%"+search_word+"%' order by no";
		String sql = "select * from book where " + column + " like '%' || ? || '%' order by no"; // 3
		try {
			ps = conn.prepareStatement(sql);
//			ps.setString(1, "%"+search_word+"%"); // where like %정% 
			ps.setString(1, search_word); // 3
			
			rs = ps.executeQuery();
			while(rs.next()) {
				
				BookBean bean = new BookBean();
				bean.setNo(rs.getInt("no"));
				bean.setTitle(rs.getString("title"));
				bean.setAuthor(rs.getString("author"));
				bean.setPublisher(rs.getString("publisher"));
				bean.setPrice(rs.getInt("price"));
				bean.setPub_day(String.valueOf(rs.getDate("pub_day")));  

				lists.add(bean);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
		} finally {
			try {
				if(rs!=null)
					rs.close();
				
				if(ps!=null)
					ps.close();

				if(conn!=null)
					conn.close();

			}catch(SQLException e) {

			}
		}
		
		return lists;
	}



}








