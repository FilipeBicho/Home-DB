package home;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AddItem
 */
@WebServlet("/AddItem")
public class AddItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddItem() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Database URL
		String url = "jdbc:mysql://localhost:3306/Home?useSSL=false";
		// User name
		String user = "Filipe";
		// Password
		String pass = "Popcorn00!";
		
		//Initialize variables that keep item values
		String name, store, color;
		Float price;
		int quantity;
		
		CRUD database = null;
		
		try {
			database = new CRUD(url, user, pass);
		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		//1. Set content type
		response.setContentType("text/html");
		
		//2. Get the printWriter
		PrintWriter out = response.getWriter();
		
		// Get values
		name = request.getParameter("name");
		store = request.getParameter("store");
		price = Float.parseFloat(request.getParameter("price"));
		quantity = Integer.parseInt(request.getParameter("qtd"));
		color = request.getParameter("color");
		
		//Initialize new object with new values
		Bedroom item = new Bedroom(name, store, price, quantity, color);
		
		//Insert into database
		try {
			database.insertInto(item);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//3. Generate HTML content
		out.println("<html><body>");
		out.println("<h4>You inserted: </h4>");
		out.println(name + " " + " " + store + " " + price + " " + quantity + " " + color + " !!");
		out.println("<hr>");
		out.println("<a href=\"http://localhost:9080/Home_DB/HomeDB\"> Go back to the table </a>");
		out.println("</body></html>");		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
