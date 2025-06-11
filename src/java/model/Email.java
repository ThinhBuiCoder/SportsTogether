package model;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class Email {

    private final String eFrom = "hieutdde181022@fpt.edu.vn"; // Email gửi
    private final String ePass = "mtaq avfr tyed rjbv"; // Thay bằng App Password thực tế của bạn

    // Kiểm tra định dạng email
    public boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Phương thức gửi email
    public void sendEmail(String subject, String message, String to) throws MessagingException {
        if (to == null || !isValidEmail(to)) {
            throw new MessagingException("Địa chỉ email không hợp lệ: " + to);
        }

        // Cấu hình SMTP
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        // Xác thực tài khoản
        Authenticator au = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(eFrom, ePass);
            }
        };

        // Tạo phiên làm việc
        Session session = Session.getInstance(props, au);

        // Tạo và gửi email
        MimeMessage msg = new MimeMessage(session);
        msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
        msg.setFrom(new InternetAddress(eFrom));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
        msg.setSubject(subject, "UTF-8");
        msg.setContent(message, "text/html; charset=UTF-8");
        Transport.send(msg); // Nếu lỗi, ngoại lệ sẽ được ném ra
    }

    // Tiêu đề cho email liên hệ
    public String subjectContact(String name) {
        return "SportsTogether Shop - Hi " + name + " cảm ơn bạn vì đã liên hệ với chúng tôi";
    }

    // Tiêu đề cho đơn hàng mới
    public String subjectNewOrder() {
        return "SportsTogether Shop- Đặt hàng thành công";
    }

    // Tiêu đề cho đăng ký nhận tin
    public String subjectSubsribe() {
        return "SportsTogether Shop - Bạn có thông báo mới";
    }

    // Tiêu đề cho quên mật khẩu
    public String subjectForgotPass() {
        return "SportsTogether Shop - Mã code xác nhận";
    }

    // Nội dung email liên hệ
    public String messageContact(String name) {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Email Contact</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body style=\"font-family:arial, helvetica, sans-serif;\n"
                + "          font-size:14px;\n"
                + "          line-height:20px;\n"
                + "          color: #444;\n"
                + "          background:#f2f2f2;\">\n"
                + "        <table width=\"100%\" class=\"wrapper\" style=\" margin:20px 0;\">\n"
                + "            <tr>\n"
                + "                <td class=\"container\"> \n"
                + "                    <div class=\"content\" style=\"display: block!important;\n"
                + "                         max-width: 600px!important;\n"
                + "                         margin: 0 auto!important;\n"
                + "                         clear: both!important;\n"
                + "                         background:white;\">\n"
                + "                        <table cellspacing=\"20\" width=\"100%\">\n"
                + "                            <tr>\n"
                + "                                <td>\n"
                + "                                    <p class=\"brand\" style=\"margin:5px 0 0; font-size:30px;\n"
                + "                                       margin:20px 0;\"><span style=\"color:#e67e22;\">Clothes</span>Shop</p> \n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr>\n"
                + "                                <td class=\"border\" style=\"border-top:2px solid #e67e22;\n"
                + "                                    border-bottom:2px solid #e67e22;\">\n"
                + "                                    <h1 style=\" font-size:24px;\n"
                + "                                        color:#e67e22;\n"
                + "                                        margin:30px 0;\">CẢM ƠN VÌ BẠN ĐÃ LIÊN HỆ SPORTS TOGETHER</h1>\n"
                + "                                    <p style=\"margin:5px 0 0\">Chào " + name + ",</p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Cảm ơn bạn đã tin tưởng và sử dụng dịch vụ, chính sách mua hàng của chúng tôi. </p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Chúng tôi đã nhận được yêu cầu của bạn, chúng tôi sẽ gửi đến bạn thông tin sớm nhất. </p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Nếu bạn cần hỗ trợ gấp hãy liên hệ hotline: 1900 9090. </p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Xin cảm ơn. </p>\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr class=\"contact\" style=\"font-size:11px; color:#999;\">\n"
                + "                                <td align=\"center\"> \n"
                + "                                    SportsTogether Shop Da Nang - 0123 456 789 - sportstogether@gmail.com\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                        </table>\n"
                + "                    </div> \n"
                + "                </td>\n"
                + "            </tr>\n"
                + "        </table>\n"
                + "    </body>\n"
                + "</html>\n";
    }

    // Nội dung email đơn hàng mới
    public String messageNewOrder(String name, int sl, double total) {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Email Contact</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body style=\"font-family:arial, helvetica, sans-serif;\n"
                + "          font-size:14px;\n"
                + "          line-height:20px;\n"
                + "          color: #444;\n"
                + "          background:#f2f2f2;\">\n"
                + "        <table width=\"100%\" class=\"wrapper\" style=\" margin:20px 0;\">\n"
                + "            <tr>\n"
                + "                <td class=\"container\"> \n"
                + "                    <div class=\"content\" style=\"display: block!important;\n"
                + "                         max-width: 600px!important;\n"
                + "                         margin: 0 auto!important;\n"
                + "                         clear: both!important;\n"
                + "                         background:white;\">\n"
                + "                        <table cellspacing=\"20\" width=\"100%\">\n"
                + "                            <tr>\n"
                + "                                <td>\n"
                + "                                    <p class=\"brand\" style=\"margin:5px 0 0; font-size:30px;\n"
                + "                                       margin:20px 0;\"><span style=\"color:#e67e22;\">Clothes</span>Shop</p> \n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr>\n"
                + "                                <td class=\"border\" style=\"border-top:2px solid #e67e22;\n"
                + "                                    border-bottom:2px solid #e67e22;\">\n"
                + "                                    <h1 style=\" font-size:24px;\n"
                + "                                        color:#e67e22;\n"
                + "                                        margin:30px 0;\">ĐẶT HÀNG THÀNH CÔNG</h1>\n"
                + "                                    <p style=\"margin:5px 0 0\">Khách hàng " + name + ",</p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Cảm ơn bạn đã tin tưởng và sử dụng dịch vụ, chính sách mua hàng của chúng tôi. </p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Thông tin đến bạn: </p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Số lượng sản phẩm: <span style=\"color:#e67e22;font-weight: bold;\"> " + sl + " </span></p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Số tiền sẽ thanh toán: <span style=\"color:#e67e22;font-weight: bold;\">" + total + " VND</span></p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Đơn hàng dự kiến sẽ giao đến bạn trong vòng 3 - 7 ngày tới. </p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Nếu bạn cần hỗ trợ gấp hãy liên hệ hotline: 1900 9090. </p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Xin cảm ơn. </p>\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr class=\"contact\" style=\"font-size:11px; color:#999;\">\n"
                + "                                <td align=\"center\"> \n"
                + "                                    SportsTogether Shop Da Nang - 0123 456 789 - sportstogether@gmail.com\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                        </table>\n"
                + "                    </div> \n"
                + "                </td>\n"
                + "            </tr>\n"
                + "        </table>\n"
                + "    </body>\n"
                + "</html>\n";
    }

    // Nội dung email quên mật khẩu
    public String messageFogot(String name, int code) {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Email Contact</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body style=\"font-family:arial, helvetica, sans-serif;\n"
                + "          font-size:14px;\n"
                + "          line-height:20px;\n"
                + "          color: #444;\n"
                + "          background:#f2f2f2;\">\n"
                + "        <table width=\"100%\" class=\"wrapper\" style=\" margin:20px 0;\">\n"
                + "            <tr>\n"
                + "                <td class=\"container\"> \n"
                + "                    <div class=\"content\" style=\"display: block!important;\n"
                + "                         max-width: 600px!important;\n"
                + "                         margin: 0 auto!important;\n"
                + "                         clear: both!important;\n"
                + "                         background:white;\">\n"
                + "                        <table cellspacing=\"20\" width=\"100%\">\n"
                + "                            <tr>\n"
                + "                                <td>\n"
                + "                                    <p class=\"brand\" style=\"margin:5px 0 0; font-size:30px;\n"
                + "                                       margin:20px 0;\"><span style=\"color:#e67e22;\">Clothes</span>Shop</p> \n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr>\n"
                + "                                <td class=\"border\" style=\"border-top:2px solid #e67e22;\n"
                + "                                    border-bottom:2px solid #e67e22;\">\n"
                + "                                    <h1 style=\" font-size:24px;\n"
                + "                                        color:#e67e22;\n"
                + "                                        margin:30px 0;\">MÃ XÁC NHẬN CỦA BẠN</h1>\n"
                + "                                    <p style=\"margin:5px 0 0\">Chào " + name + ",</p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Mã khôi phục mật khẩu của bạn là: <span style=\"color:#e67e22;font-weight: bold;\">" + code + "</span></p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Nếu bạn cần hỗ trợ gấp hãy liên hệ hotline: 1900 9090. </p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Xin cảm ơn. </p>\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr class=\"contact\" style=\"font-size:11px; color:#999;\">\n"
                + "                                <td align=\"center\"> \n"
                + "                                    SportsTogether Shop Da Nang - 0123 456 789 - sportstogether@gmail.com\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                        </table>\n"
                + "                    </div> \n"
                + "                </td>\n"
                + "            </tr>\n"
                + "        </table>\n"
                + "    </body>\n"
                + "</html>\n";
    }

    // Nội dung email đăng ký nhận tin
    public String messageSubscribe() {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "    <head>\n"
                + "        <title>Email Contact</title>\n"
                + "        <meta charset=\"UTF-8\">\n"
                + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                + "    </head>\n"
                + "    <body style=\"font-family:arial, helvetica, sans-serif;\n"
                + "          font-size:14px;\n"
                + "          line-height:20px;\n"
                + "          color: #444;\n"
                + "          background:#f2f2f2;\">\n"
                + "        <table width=\"100%\" class=\"wrapper\" style=\" margin:20px 0;\">\n"
                + "            <tr>\n"
                + "                <td class=\"container\"> \n"
                + "                    <div class=\"content\" style=\"display: block!important;\n"
                + "                         max-width: 600px!important;\n"
                + "                         margin: 0 auto!important;\n"
                + "                         clear: both!important;\n"
                + "                         background:white;\">\n"
                + "                        <table cellspacing=\"20\" width=\"100%\">\n"
                + "                            <tr>\n"
                + "                                <td>\n"
                + "                                    <p class=\"brand\" style=\"margin:5px 0 0; font-size:30px;\n"
                + "                                       margin:20px 0;\"><span style=\"color:#e67e22;\">Clothes</span>Shop</p> \n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr>\n"
                + "                                <td class=\"border\" style=\"border-top:2px solid #e67e22;\n"
                + "                                    border-bottom:2px solid #e67e22;\">\n"
                + "                                    <h1 style=\" font-size:24px;\n"
                + "                                        color:#e67e22;\n"
                + "                                        text-height:36px;\n"
                + "                                        margin:30px 0;\">CHÀO MỪNG BẠN ĐÃ ĐẾN VỚI SPORTS TOGETHER</h1>\n"
                + "                                    <p style=\"margin:5px 0 0\">Hello clother,</p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Cảm ơn bạn đã tin tưởng và sử dụng dịch vụ, chính sách mua hàng của chúng tôi. </p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Bạn sẽ là một trong những người được nhận thông báo <span style=\"color:#e67e22;\">SALE, NEW PRODUCT</span> sớm nhất. </p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Nếu bạn cần hỗ trợ gấp hãy liên hệ hotline: 1900 9090. </p>\n"
                + "                                    <p style=\"margin:5px 0 0\">Xin cảm ơn. </p>\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                            <tr class=\"contact\" style=\"font-size:11px; color:#999;\">\n"
                + "                                <td align=\"center\"> \n"
                + "                                    SportsTogether Shop Da Nang - 0123 456 789 - sportstogether@gmail.com\n"
                + "                                </td>\n"
                + "                            </tr>\n"
                + "                        </table>\n"
                + "                    </div> \n"
                + "                </td>\n"
                + "            </tr>\n"
                + "        </table>\n"
                + "    </body>\n"
                + "</html>\n";
    }

    // Phương thức main để kiểm tra
    public static void main(String[] args) {
        Email handleEmail = new Email();
        UserDTO user = new UserDTO();
        user.setEmail("bthinh2011@gmail.com"); // Thay bằng email bạn muốn thử
        user.setFirstName("bthinh");
        user.setId(1);

        int totalQuantity = 3;
        double totalPrice = 150000;

        try {
            String subEmail = handleEmail.subjectNewOrder();
            String messageEmail = handleEmail.messageNewOrder(user.getFirstName(), totalQuantity, totalPrice);
            handleEmail.sendEmail(subEmail, messageEmail, user.getEmail());
            System.out.println("Email gửi thành công đến: " + user.getEmail());
        } catch (MessagingException e) {
            System.err.println("Gửi email thất bại: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
 
