import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/sqlcmd")
public class SQLCmd extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        String cmd = request.getParameter("cmd");

        if (cmd == null) {
            out.println("<h1>Chyba!</h1><p>chybi parametr <em>cmd</em></p>");
        } else {
            String jdbcURL = System.getenv("JDBC_DATABASE_URL");

            try (Connection conn = DriverManager.getConnection(jdbcURL)) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.executeUpdate(cmd);
                    out.println("<h1>OK</h1>");
                }
            } catch (SQLException ex) {
               out.println("<h1>Chyba!</h1><p>chyba pri praci s databazi: <pre>" + ex + "</pre>");
            }
        }

        out.println("</body></html>");
    }
}
