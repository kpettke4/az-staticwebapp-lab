import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UserDAO {
	static Connection conn=null;
	static Statement s=null;
	
		private final String url = "jdbc:postgresql://localhost:5432/postgres?currentSchema='users'";
			private final String user = "postgres";
			private final String password = "";
			// koniec - parametrów połączenia z PGSQL
			
			// read / select data - all users
			private static final String SELECT_ALL_USERS = "select * from users.users ORDER BY ID asc";
			// read / select data user by id
			private static final String SELECT_USER_BY_ID = "select id, first_name ,last_name,email, unique_person_number, country from users.users where id =?";
			// insert data
			private static final String INSERT_USERS_SQL = "INSERT INTO users.users" + "(id,first_name,last_name,email,unique_person_number,country) VALUES (?, ?, ?, ?, ?,?);";
			// update data by id
			//private final String UPDATE_USERS_SQL = "update users.users set where  first_name = ?, last_name=?, email= ?, unique_person_number=?, country =?, id = ? ;";
			private final String UPDATE_USERS_SQL = "update users.users set first_name = ?, last_name=?, email= ?, unique_person_number=?, country =? where id = ?;";
			// delete user by id
			private static final String DELETE_USERS_SQL = "delete from users.users where id = ?;";

			Connection connection = null;
			/**
			* Connect to the PostgreSQL database
			*
			* @return a Connection object
			*/
			//rozpoczęcie polączenia
			public Connection DBSQLConnection() {
			 try {
			 Class.forName("org.postgresql.Driver");
			 connection = DriverManager.getConnection(url, user, password);
			if(connection.isValid(5)) System.out.println("Connection is working");
			 }
			 catch (SQLException e) {
			 e.printStackTrace();
			 }
			 catch (ClassNotFoundException e) {
			 e.printStackTrace();
			 }
			 return connection;
			}
			//zakończenie polączenia
			private void DBSQLConnectionClose(){
			 if(connection==null) return;
			 try{
			connection.close();
			if(!connection.isValid(5)) System.out.println("Connection closed");
			 }
			 catch(SQLException e){
			e.printStackTrace();}
			}
			// kontruktor, który nic nie wykonuje
			public UserDAO() {
			}
			// metoda main do testowania
		
			private void printSQLException(SQLException ex) {
				 for (Throwable e: ex) {
				 if (e instanceof SQLException) {
				 e.printStackTrace(System.err);
				 System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				 System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				 System.err.println("Message: " + e.getMessage());
				 Throwable t = ex.getCause();
				 while (t != null) {
				 System.out.println("Cause: " + t);
				 t = t.getCause();
				 }
				}
			}
		}
			/* metoda zwracająca konkretnego użytkownika za pomocą parametru id */
			public User selectUser(int id) {
			 User user = null;
			 // połączenie
			 try(Connection connection = DBSQLConnection();
					 
			 // Utworzenie statement z obiektu connection
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);)
			 {
			 preparedStatement.setInt(1, id);
			 System.out.println(preparedStatement);
			 // Wykonanie zpaytania
			 ResultSet rs = preparedStatement.executeQuery();
			 // Proces obsługi rezultatu.
			 while (rs.next()) {
			 String t_first_name = rs.getString("first_name");
			 String t_last_name = rs.getString("last_name");
			 String t_email = rs.getString("email");
			 Long t_unique_person_number= rs.getLong("unique_person_number");
			 String t_country = rs.getString("country");
			 user = new User(id, t_first_name,t_last_name, t_email, t_unique_person_number,t_country);
			 }
			 }
			 catch (SQLException e) { printSQLException(e); }
			 return user;
			}
			
			/* metoda zwracająca listę użytkowników */
			public List <User> selectAllUsers() {	
			 List<User> users = new ArrayList <User> ();
			 // nawiązanie połączenia
			 try (Connection connection = DBSQLConnection();
			 // utworzenie statement z obiektu connection
			 PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);)
			 {
			 System.out.println(preparedStatement);
			 // wykoananie zapytania
			 ResultSet rs = preparedStatement.executeQuery();
			 // odebranie wyników z obiektu ResultSet.
			 while (rs.next()) {
			 int t_id = rs.getInt("id");
			 String t_first_name = rs.getString("first_name");
			 String t_last_name = rs.getString("last_name");
			 String t_email = rs.getString("email");
			 Long t_unique_person_number= rs.getLong("unique_person_number");
			 String t_country = rs.getString("country");
			 users.add(new User(t_id, t_first_name,t_last_name, t_email,t_unique_person_number,t_country));
			 }
			 }
			 catch (SQLException e) { printSQLException(e);
			 }
			 return users;
			}
			/* metoda usuwająca dane wybranego użytkownika */
			public boolean deleteUser(int id) throws SQLException {
			 boolean rowDeleted=false;
			 try (Connection connection = DBSQLConnection();
			 PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);)
			 {
			 statement.setInt(1, id);
			 rowDeleted = statement.executeUpdate() > 0;
			 }
			 catch (SQLException e) { printSQLException(e);}
			 return rowDeleted;
			}

			public boolean updateUser(User user) throws SQLException {
				 boolean rowUpdated;
				 
			        try (Connection connection = DBSQLConnection(); 
			        		
			        		PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
			        	
			        	
			        	statement.setString(1, user.getFirst_name());
			        	statement.setString(2, user.getLast_name());
			        	statement.setString(3, user.getEmail());
			        	statement.setLong(4, user.getUnique_person_number());
			        	statement.setString(5, user.getCountry());
			        	statement.setInt(6, user.getId());

			            rowUpdated = statement.executeUpdate() > 0;
			        }
			        return rowUpdated;
			    }
		

			
		    public void insertUser(User user) throws SQLException {
		        System.out.println(INSERT_USERS_SQL);
		        // try-with-resource statement will auto close the connection.
		        try (Connection connection = DBSQLConnection(); 
		        	PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
		        	 preparedStatement.setLong(1, user.getId());
		            preparedStatement.setString(2, user.getFirst_name());
		            preparedStatement.setString(3, user.getLast_name());
		            preparedStatement.setString(4, user.getEmail());
		            preparedStatement.setLong(5, user.getUnique_person_number());
		            preparedStatement.setString(6, user.getCountry());
		            
		            
		            System.out.println(preparedStatement);
		            preparedStatement.executeUpdate();
		        } catch (SQLException e) {
		            printSQLException(e);
		        }
		    }

			public static void main(String[] args)
			{
			 UserDAO dao = new UserDAO();
			 dao.DBSQLConnection();
			 dao.DBSQLConnectionClose();
			
			}
			
			 
	}




