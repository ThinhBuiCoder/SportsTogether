package controller.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatServlet extends HttpServlet {

    private List<Message> messages = new ArrayList<>();

    @Override
    public void init() {
        // Dữ liệu có sẵn
        messages.add(new Message("Admin", "Chào mừng bạn đến với hệ thống chat!"));
        messages.add(new Message("User1", "Xin chào!"));
        messages.add(new Message("User2", "Chào mọi người!"));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("messages", messages);
        RequestDispatcher dispatcher = request.getRequestDispatcher("chatbox.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String sender = request.getParameter("sender");
        String content = request.getParameter("content");

        if (sender != null && content != null && !sender.isEmpty() && !content.isEmpty()) {
            messages.add(new Message(sender, content));
        }

        response.sendRedirect("view/jsp/home/chatbox.jsp");

    }
}
