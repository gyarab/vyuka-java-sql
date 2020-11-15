import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/demo1")
public class XSSDemo extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String name = request.getParameter("name");

            response.setContentType("text/html;charset=utf-8");

            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<a href=\"http://mbank.cz\">zaplatit</a>");
            out.println("Name: " + name);
            out.println("<body></html>");
    }
}
