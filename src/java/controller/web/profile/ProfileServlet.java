package controller.web.profile;

import dao.OrderDAO;
import java.io.IOException;
import java.util.List;
import model.OrderDTO;
import model.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ProfileServlet extends HttpServlet {

    private final String PROFILE = "view/jsp/home/my-account.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            OrderDAO oDao = new OrderDAO();
            HttpSession session = request.getSession();
            UserDTO user = (UserDTO) session.getAttribute("account");
            List<OrderDTO> listOrders = oDao.getOrdersByUsername(user.getUserName());

            request.setAttribute("LISTORDERS", listOrders);
        } catch (Exception ex) {
            log("ProfileServlet error:" + ex.getMessage());
        } finally {
            request.getRequestDispatcher(PROFILE).forward(request, response);
        }
    }

   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

 
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
