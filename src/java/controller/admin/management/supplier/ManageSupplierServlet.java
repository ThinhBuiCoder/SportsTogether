package clothingstore.controller.admin.management.supplier;

import clothingstore.dao.SupplierDAO;
import java.io.IOException;
import java.util.List;
import clothingstore.model.SupplierDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "ManageSupplierServlet", urlPatterns = {"/ManageSupplierServlet"})
public class ManageSupplierServlet extends HttpServlet {

    private final String MANAGESUPPLIERPAGE = "view/jsp/admin/admin_suppliers.jsp";

 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            SupplierDAO sDao = new SupplierDAO();
            List<SupplierDTO> list = sDao.getData();

            request.setAttribute("LISTSUPPLIERS", list);
            request.setAttribute("action", "MNGSUPPLIER");
            request.setAttribute("CURRENTSERVLET", "Supplier");
        } catch (Exception ex) {
            log("ManageSupplierServlet error:" + ex.getMessage());
        } finally {
            request.getRequestDispatcher(MANAGESUPPLIERPAGE).forward(request, response);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
