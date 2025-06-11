package controller.web.login;

import model.UserGoogleDTO;
import model.Constants;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.UserDAO;
import model.UserDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    private final String WELCOME = "DispatchServlet";
    private final String LOGIN = "view/jsp/home/login.jsp";
    private final String ADMIN_DASHBOARD = "AdminServlet";
    private final String REGISTER_CONTROLLER = "RegisterServlet";

    public static String getToken(String code) throws IOException {
        String response = org.apache.http.client.fluent.Request.Post(Constants.GOOGLE_LINK_GET_TOKEN)
                .bodyForm(org.apache.http.client.fluent.Form.form()
                        .add("client_id", Constants.GOOGLE_CLIENT_ID)
                        .add("client_secret", Constants.GOOGLE_CLIENT_SECRET)
                        .add("redirect_uri", Constants.GOOGLE_REDIRECT_URI)
                        .add("code", code)
                        .add("grant_type", Constants.GOOGLE_GRANT_TYPE).build())
                .execute().returnContent().asString();

        JsonObject jobj = new Gson().fromJson(response, JsonObject.class);
        return jobj.get("access_token").getAsString();
    }

    public static UserGoogleDTO getUserInfo(final String accessToken) throws IOException {
        String link = Constants.GOOGLE_LINK_GET_USER_INFO + accessToken;
        String response = org.apache.http.client.fluent.Request.Get(link).execute().returnContent().asString();
        return new Gson().fromJson(response, UserGoogleDTO.class);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String url = WELCOME;
        try {
            if (request.getParameter("btnAction") != null) {
                Cookie arr[] = request.getCookies();
                if (arr != null) {
                    for (Cookie cookie : arr) {
                        switch (cookie.getName()) {
                            case "cUName":
                                request.setAttribute("uName", cookie.getValue());
                                break;
                            case "cUPass":
                                request.setAttribute("uPass", cookie.getValue());
                                break;
                            case "reMem":
                                request.setAttribute("reMem", cookie.getValue());
                                break;
                        }
                    }
                }
                url = LOGIN;
            } else {
                String code = request.getParameter("code");
                if (code != null) {
                    String accessToken = getToken(code);
                    UserGoogleDTO userGG = getUserInfo(accessToken);
                    if (userGG != null) {
                        UserDAO dao = new UserDAO();
                        UserDTO account = dao.getUserByEmail(userGG.getEmail());
                        if (account != null) {
                            HttpSession session = request.getSession();
                            session.setAttribute("account", account);
                            if (account.getRoleID() == 1) {
                                response.sendRedirect(ADMIN_DASHBOARD);
                            } else {
                                response.sendRedirect(WELCOME);
                            }
                            return;
                        } else {
                            request.setAttribute("msgRegisterGG", "You need register your account!");
                            request.setAttribute("emailGG", userGG.getEmail());
                            request.setAttribute("firstNameGoogleAccount", userGG.getGiven_name());
                            request.setAttribute("lastNameGoogleAccount", userGG.getFamily_name());
                            request.setAttribute("avatar", userGG.getPicture());
                            url = REGISTER_CONTROLLER;
                        }
                    }
                } else {
                    url = LOGIN;
                }
            }
        } catch (Exception ex) {
            log("LoginServlet error: " + ex.getMessage());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String url = WELCOME;
        try {
            String username = request.getParameter("txtUsername");
            String password = request.getParameter("txtPassword");
            String remember = request.getParameter("remember");
            UserDAO udao = new UserDAO();
            UserDTO user = udao.checkLogin(username, password);
            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("account", user);
                Cookie u = new Cookie("cUName", username);
                Cookie p = new Cookie("cUPass", password);
                Cookie r = new Cookie("reMem", remember);

                u.setMaxAge(60 * 60 * 24 * 30 * 3);
                if (remember != null) {
                    p.setMaxAge(60 * 60 * 24 * 30 * 3);
                    r.setMaxAge(60 * 60 * 24 * 30 * 3);
                } else {
                    p.setMaxAge(0);
                    r.setMaxAge(0);
                }

                response.addCookie(u);
                response.addCookie(p);
                response.addCookie(r);
                if (user.getRoleID() == 1) {
                    response.sendRedirect(ADMIN_DASHBOARD);
                } else {
                    response.sendRedirect(WELCOME);
                }
            } else {
                request.setAttribute("msg", "Invalid username or password!");
                request.getRequestDispatcher(LOGIN).forward(request, response);
            }
        } catch (Exception ex) {
            log("LoginServlet error: " + ex.getMessage());
        } finally {
            out.close();
        }
    }

    @Override
    public String getServletInfo() {
        return "Login Servlet";
    }
}
