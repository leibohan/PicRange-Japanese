package pintu.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
    public static void main(String[] args) {
        //声明Connection对象
        Connection con;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://localhost:3306/sqltestdb";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "_(VP>zfPO4>p";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            con = DriverManager.getConnection(url,user,password);
            if(!con.isClosed())
                System.out.println("Succeeded connecting to the Database!");
            //2.创建statement类对象，用来执行SQL语句！！
            Statement statement = con.createStatement();
            //要执行的SQL语句
            String sql = "select * from emp";
            //3.ResultSet类，用来存放获取的结果集！！
            ResultSet rs = statement.executeQuery(sql);
            System.out.println("-----------------");
            System.out.println("执行结果如下所示:");  
            System.out.println("-----------------");  
            System.out.println("姓名" + "\t" + "职称");  
            System.out.println("-----------------");  
             
            String job = null;
            String id = null;
            while(rs.next()){
                //获取stuname这列数据
                job = rs.getString("job");
                //获取stuid这列数据
                id = rs.getString("ename");

                //输出结果
                System.out.println(id + "\t" + job);
            }
            rs.close();
            con.close();
        } catch(ClassNotFoundException e) {   
            //数据库驱动类异常处理
            System.out.println("Sorry,can`t find the Driver!");   
            e.printStackTrace();   
            } catch(SQLException e) {
            //数据库连接失败异常处理
            e.printStackTrace();  
            }catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }finally{
            System.out.println("数据库数据成功获取！！");
        }
    }

}
*/
public class DBUtil {
	
	public static Connection conn = null;
	private static Statement stmt = null;
	
	public static void myClose(Connection conn, Statement stat, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				rs = null;
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (stat != null) {
			try {
				stat.close();
				stat = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static Connection getConn() {
/*        //声明Connection对象
        Connection conn;
        //驱动程序名
        String driver = "com.mysql.jdbc.Driver";
        //URL指向要访问的数据库名mydata
        String url = "jdbc:mysql://localhost:3306/sqltestdb";
        //MySQL配置时的用户名
        String user = "root";
        //MySQL配置时的密码
        String password = "68583161";
        //遍历查询结果集
        try {
            //加载驱动程序
            Class.forName(driver);
            //1.getConnection()方法，连接MySQL数据库！！
            conn = DriverManager.getConnection(url,user,password);
            if(!conn.isClosed())
                System.out.println("Succeeded connecting to the Database!");
			return conn;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		}
        System.out.println("Failed");
		return null;
		*/
	    try {
	      Class.forName("org.sqlite.JDBC");
	      conn = DriverManager.getConnection("jdbc:sqlite:test.db");
          if(!conn.isClosed())
              System.out.println("Succeeded connecting to the Database!");
		      stmt = conn.createStatement();
//		      try {
//		      String sql = "CREATE TABLE timeorder " +
//		                   "(ID INT PRIMARY KEY     AUTOINCREMENT," +
//		                   " NAME           CHAR(10)    ," + 
//		                   " TIME            INT     NOT NULL)"; 
//		      stmt.executeUpdate(sql);
//		      stmt.close();
//		      }catch (Exception e){
//		    	  	e.printStackTrace();
//		      }
			return conn;
	    } catch ( Exception e ) {
	    		e.printStackTrace();
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	    System.out.println("Opened database successfully");
	    	return  null;
	}
	
	public static void main(String[] args) {
		
	}

}
