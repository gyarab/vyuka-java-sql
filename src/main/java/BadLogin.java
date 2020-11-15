import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class BadLogin extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        String name = request.getParameter("name");
        String pass = request.getParameter("pass");

        if (name == null || pass == null) {
            out.println("<h1>Chyba!</h1><p>chybi parametr</p>");
        } else {
            String jdbcURL = System.getenv("JDBC_DATABASE_URL");

            try (Connection conn = DriverManager.getConnection(jdbcURL)) {
                try (Statement stmt = conn.createStatement()) {
                    String sql = "SELECT * FROM users WHERE name = '" + name + "' AND passwd = '" + pass + "'";
                    out.println("SQL prikaz:" + sql);
                    ResultSet rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        out.println("<h1>OK</h1>");
                    } else {
                        out.println("<h1>spatne jmeno nebo heslo</h1>");
                    }
                }
            } catch (SQLException ex) {
               out.println("<h1>Chyba!</h1><p>chyba pri praci s databazi: <pre>" + ex + "</pre>");
            }
        }

        out.println("</body></html>");
    }
}
