import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/init")
public class SQLInit extends HttpServlet {
    String dbUrl = System.getenv("JDBC_DATABASE_URL");

    String[] initData = {
            "DROP TABLE IF EXISTS users",
            "CREATE TABLE users (name VARCHAR PRIMARY KEY, passwd VARCHAR NOT NULL, tel VARCHAR NOT NULL)",
            "INSERT INTO users VALUES ('admin', 'admin123', '666 777 888')",
            "INSERT INTO users VALUES ('pepa', 'pepa123', '123 456 789')",
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter out = resp.getWriter();
        try (Connection conn = DriverManager.getConnection(dbUrl);
             Statement stmt = conn.createStatement();) {
            for (String sql : initData) {
                stmt.execute(sql);
            }

            out.println("<html><body>Databaze inicializována...</body></html>");
        } catch (SQLException e) {

            out.println("<html><body>Chyba: " + e.getMessage()+ "</body></html>");
        }

    }
}
