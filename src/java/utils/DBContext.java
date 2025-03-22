package clothingstore.utils;

import java.sql.Connection;
import java.sql.DriverManager;


public class DBContext {

    public Connection getConnection() throws Exception {


        //1. Load Driver(driver is available)
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        //2. Create Connection String
        String url = "jdbc:sqlserver://LAPTOP-MBT88TH7\\SQLEXPRESS;databaseName=SportTogether_a;encrypt=false;trustServerCertificate=false;loginTimeout=30"; 
        //3. Open connection
        Connection con = DriverManager.getConnection(url, "sa", "123");
        return con;
    }

}
