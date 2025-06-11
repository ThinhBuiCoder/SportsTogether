package controller.web.cart_wishlist;

import utils.CartUtil;
import dao.OrderDAO;
import dao.OrderItemDAO;
import dao.PaymentDAO;
import dao.ProductDAO;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.CartItem;
import model.Email;
import model.OrderDTO;
import model.PaymentDTO;
import model.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    private static final String CHECKOUT_PAGE = "view/jsp/home/checkout.jsp";
    private static final String SUCCESS_PAGE = "view/jsp/home/order_success.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = CHECKOUT_PAGE;
        PaymentDAO pmDAO = new PaymentDAO();
        ProductDAO pDAO = new ProductDAO();
        OrderDAO oDAO = new OrderDAO();
        OrderItemDAO oiDAO = new OrderItemDAO();
        CartUtil cUtil = new CartUtil();
        OrderDTO orderLatest = null;
        double total = 0;
        int totalQuantity = 0;
        String message = "";
        String check = "false";
        String emptyCart = "[]";
        Email emailHandle = new Email();

        try {
            HttpSession session = request.getSession();
            Cookie cookie = null;
            List<PaymentDTO> pms = pmDAO.getPaymentData();

            // Check out
            String paymentId = request.getParameter("check_method");
            UserDTO user = (UserDTO) session.getAttribute("account");
            List<CartItem> carts = (List<CartItem>) session.getAttribute("CART");

            if (carts == null || carts.isEmpty()) {
                message = "Your cart is empty.";
                request.setAttribute("MESSAGE", message);
                request.setAttribute("CHECK", check);
                request.setAttribute("PAYMENTS", pms);
                return;
            }

            if (user == null) {
                message = "You need to log in to your account to checkout";
            } else if (user.getRoleID() == 1) {
                message = "Admin cannot perform this task";
            } else if (paymentId == null) {
                message = "Please select a payment method";
            } else {
                PaymentDTO payment = pmDAO.getPaymentById(Integer.parseInt(paymentId));

                // Kiểm tra stock của từng sản phẩm
                boolean stockValid = true;
                for (CartItem cart : carts) {
                    int stock = pDAO.getStock(cart.getProduct().getId());
                    if (cart.getQuantity() > stock) {
                        stockValid = false;
                        message = "Product " + cart.getProduct().getName() + " is out of stock. Available: " + stock;
                        break;
                    }
                    total += (cart.getQuantity() * cart.getProduct().getSalePrice());
                    totalQuantity += cart.getQuantity();
                }

                if (stockValid) {
                    LocalDateTime daynow = LocalDateTime.now();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    String date = daynow.format(format);

                    // Tạo đơn hàng mới
                    if (oDAO.CreateNewOrder(date, total, payment, user)) {
                        message = "Order Success";
                        check = "true";
                        url = SUCCESS_PAGE; // Chuyển đến trang xác nhận

                        // Tạo chi tiết đơn hàng
                        orderLatest = oDAO.getTheLatestOrder();
                        for (CartItem cart : carts) {
                            oiDAO.createNewOrderDetail(cart, orderLatest);
                            // Cập nhật số lượng sản phẩm
                            pDAO.updateQuanityProduct(cart);
                        }

                        // Gửi email thông báo
                        try {
                            if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
                                log("CheckoutServlet: Cannot send email - User email is null or empty for user: " + user.getId());
                                message += " (Note: We couldn't send the confirmation email due to missing email address.)";
                            } else {
                                String subEmail = emailHandle.subjectNewOrder();
                                String messageEmail = emailHandle.messageNewOrder(user.getFirstName(), totalQuantity, orderLatest.getTotalPrice());
                                emailHandle.sendEmail(subEmail, messageEmail, user.getEmail());
                                log("CheckoutServlet: Confirmation email sent to: " + user.getEmail());
                            }
                        } catch (Exception ex) {
                            log("CheckoutServlet: Failed to send confirmation email to " + user.getEmail() + ": " + ex.getMessage());
                            message += " (Note: We couldn't send the confirmation email, but your order was placed successfully.)";
                        }

                        // Xóa giỏ hàng
                        carts = null;
                        cookie = cUtil.getCookieByName(request, "Cart");
                        cUtil.saveCartToCookie(request, response, emptyCart);
                        session.setAttribute("CART", carts);
                    } else {
                        message = "Order failed";
                    }
                }
            }

            request.setAttribute("PAYMENTS", pms);
            request.setAttribute("MESSAGE", message);
            request.setAttribute("CHECK", check);
        } catch (Exception e) {
            log("CheckoutServlet Error:" + e.getMessage());
            request.setAttribute("MESSAGE", "An error occurred during checkout: " + e.getMessage());
            request.setAttribute("CHECK", "false");
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
