package sda.poznan.java12.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/test.html")
public class Test1 extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        System.out.printf("given ID=" + id);

        request.setAttribute("user", "Marek");

        RequestDispatcher view = request.getRequestDispatcher("/test.jsp");
        view.include(request, response);
    }


}
