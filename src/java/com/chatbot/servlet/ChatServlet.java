package com.chatbot.servlet;

import dao.ProductDAO;
import model.ProductDTO;
import com.chatbot.model.ChatMessage;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "ChatServlet", urlPatterns = {"/chat"})
public class ChatServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/chat.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userMessage = request.getParameter("message");

        if (userMessage != null && !userMessage.trim().isEmpty()) {
            HttpSession session = request.getSession();
            List<ChatMessage> chatHistory = (List<ChatMessage>) session.getAttribute("chatHistory");
            if (chatHistory == null) {
                chatHistory = new ArrayList<>();
                session.setAttribute("chatHistory", chatHistory);
            }

            // Thêm tin nhắn của người dùng vào lịch sử
            chatHistory.add(new ChatMessage(userMessage, "user"));

            // Xử lý tin nhắn và tạo phản hồi từ AI
            String aiResponse = generateAIResponse(userMessage);
            chatHistory.add(new ChatMessage(aiResponse, "ai"));

            // Nếu là yêu cầu AJAX, trả về JSON
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                response.setContentType("application/json");
                response.getWriter().write("{\"message\": \"" + 
                    aiResponse.replace("\"", "\\\"").replace("\n", "\\n") + "\"}");
                return;
            }
        }

        request.getRequestDispatcher("/WEB-INF/chat.jsp").forward(request, response);
    }

private String generateAIResponse(String userMessage) {
    try {
        ProductDAO productDAO = new ProductDAO();
        String lowerCaseMessage = userMessage.toLowerCase();

        // Xử lý câu hỏi về nơi mua đồ thể thao
        if (lowerCaseMessage.contains("mua đồ thể thao ở đâu") || 
            lowerCaseMessage.contains("mua ở đâu") || 
            lowerCaseMessage.contains("nơi nào tốt nhất")) {
            return "Chào mừng bạn đến với Sport Together - điểm đến hoàn hảo cho mọi tín đồ thể thao! " +
                   "Chúng tôi tự hào là cửa hàng hàng đầu cung cấp trang phục và phụ kiện thể thao chất lượng cao, " +
                   "được thiết kế tinh tế, bền bỉ và đậm chất năng động. Với cam kết mang đến sự an tâm tuyệt đối, " +
                   "Sport Together không chỉ đem lại sản phẩm chính hãng từ các thương hiệu danh tiếng, " +
                   "mà còn đảm bảo dịch vụ tận tâm, giá cả hợp lý và trải nghiệm mua sắm đẳng cấp. " +
                   "Hãy để chúng tôi đồng hành cùng bạn trên hành trình chinh phục mọi thử thách thể thao! " +
                   "Bạn đang tìm kiếm sản phẩm nào hôm nay?";
        }
        // Ưu tiên tìm kiếm khi có từ "tìm" hoặc "search"
        else if (lowerCaseMessage.contains("tìm") || lowerCaseMessage.contains("search")) {
            return searchProducts(lowerCaseMessage, productDAO);
        }
        // Kiểm tra các từ khóa liên quan đến loại sản phẩm
        else if (lowerCaseMessage.contains("bóng đá") || lowerCaseMessage.contains("football")) {
            return suggestProducts(productDAO.getProductByTypeId(1), "bóng đá");
        } else if (lowerCaseMessage.contains("cầu lông") || lowerCaseMessage.contains("badminton")) {
            return suggestProducts(productDAO.getProductByTypeId(2), "cầu lông");
        } else if (lowerCaseMessage.contains("tenis") || lowerCaseMessage.contains("tennis")) {
            return suggestProducts(productDAO.getProductByTypeId(3), "tenis");
        } else if (lowerCaseMessage.contains("bóng rổ") || lowerCaseMessage.contains("basketball")) {
            return suggestProducts(productDAO.getProductByTypeId(4), "bóng rổ");
        } else if (lowerCaseMessage.contains("pickle ball") || lowerCaseMessage.contains("pickleball")) {
            return suggestProducts(productDAO.getProductByTypeId(5), "pickle ball");
        } else if (lowerCaseMessage.contains("bán chạy") || lowerCaseMessage.contains("best seller")) {
            return suggestProducts(productDAO.getProductsBestSeller(), "bán chạy nhất");
        } else if (lowerCaseMessage.contains("mới") || lowerCaseMessage.contains("new")) {
            return suggestProducts(productDAO.getProductNew(), "mới nhất 2024");
        } else if (lowerCaseMessage.contains("giá") || lowerCaseMessage.contains("price")) {
            return suggestProductsByPrice(lowerCaseMessage, productDAO);
        } else {
            return "Xin chào! Tôi có thể giúp bạn tìm sản phẩm thể thao như bóng đá, cầu lông, tenis, bóng rổ, hoặc pickle ball. Bạn muốn mua gì hôm nay?";
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return "Xin lỗi, tôi gặp lỗi khi truy vấn dữ liệu. Vui lòng thử lại!";
    }
}

    private String suggestProducts(List<ProductDTO> products, String category) {
        if (products.isEmpty()) {
            return "Hiện tại không có sản phẩm nào trong danh mục " + category + ".";
        }

        StringBuilder response = new StringBuilder("Dưới đây là một số sản phẩm " + category + " nổi bật:\n");
        int count = Math.min(3, products.size()); // Giới hạn tối đa 3 sản phẩm
        for (int i = 0; i < count; i++) {
            ProductDTO product = products.get(i);
            response.append("- ").append(product.getName())
                    .append(": Giá ").append(product.getSalePrice()).append(" VNĐ")
                    .append(" (").append(product.getDescription()).append(")\n");
        }
        response.append("Bạn muốn biết thêm về sản phẩm nào?");
        return response.toString();
    }

    private String suggestProductsByPrice(String message, ProductDAO productDAO) throws SQLException {
        List<ProductDTO> allProducts = productDAO.getData();
        double priceFrom = extractPrice(message, "từ", "from");
        double priceTo = extractPrice(message, "đến", "to");

        List<ProductDTO> filteredProducts = productDAO.searchByPrice(allProducts, priceFrom, priceTo);
        if (filteredProducts.isEmpty()) {
            return "Không tìm thấy sản phẩm nào trong khoảng giá bạn yêu cầu.";
        }

        StringBuilder response = new StringBuilder("Dưới đây là các sản phẩm trong khoảng giá:\n");
        int count = Math.min(3, filteredProducts.size());
        for (int i = 0; i < count; i++) {
            ProductDTO product = filteredProducts.get(i);
            response.append("- ").append(product.getName())
                    .append(": Giá ").append(product.getSalePrice()).append(" VNĐ\n");
        }
        return response.toString();
    }

    private String searchProducts(String message, ProductDAO productDAO) throws SQLException {
        String[] words = message.split("\\s+");
        String searchTerm = "";
        for (String word : words) {
            if (!word.equalsIgnoreCase("tìm") && !word.equalsIgnoreCase("search")) {
                searchTerm += word + " ";
            }
        }
        searchTerm = searchTerm.trim();

        if (searchTerm.isEmpty()) {
            return "Vui lòng cung cấp từ khóa để tìm kiếm sản phẩm!";
        }

        List<ProductDTO> products = productDAO.getProductBySearch(searchTerm);
        if (products.isEmpty()) {
            return "Không tìm thấy sản phẩm nào với từ khóa '" + searchTerm + "'.";
        }

        StringBuilder response = new StringBuilder("Kết quả tìm kiếm cho '" + searchTerm + "':\n");
        int count = Math.min(3, products.size());
        for (int i = 0; i < count; i++) {
            ProductDTO product = products.get(i);
            response.append("- ").append(product.getName())
                    .append(": Giá ").append(product.getSalePrice()).append(" VNĐ\n");
        }
        return response.toString();
    }

    private double extractPrice(String message, String fromKeyword, String alternativeKeyword) {
        String[] words = message.split("\\s+");
        for (int i = 0; i < words.length - 1; i++) {
            if (words[i].equalsIgnoreCase(fromKeyword) || words[i].equalsIgnoreCase(alternativeKeyword)) {
                try {
                    return Double.parseDouble(words[i + 1]);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }
        }
        return 0;
    }
}

