<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html class="no-js" lang="zxx">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>Order Success</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/x-icon" href="view/assets/home/img/favicon.png">
    <%@include file="../../common/web/add_css.jsp"%>
</head>
<body>
    <div class="pos_page">
        <div class="container">
            <div class="pos_page_inner">
                <%@include file="../../common/web/header.jsp"%>
                <div class="breadcrumbs_area">
                    <div class="row">
                        <div class="col-12">
                            <div class="breadcrumb_content">
                                <ul>
                                    <li><a href="DispatchServlet">home</a></li>
                                    <li><i class="fa fa-angle-right"></i></li>
                                    <li>order success</li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="order_success" style="text-align: center; padding: 50px;">
                    <h2>Thank You for Your Order!</h2>
                    <p>Your order has been successfully placed.</p>
                    <p>We will process your order soon. You can check your order history in <a href="ProfileServlet">My Account</a>.</p>
                    <div class="order_button">
                        <a href="DispatchServlet" class="btn btn-primary">Continue Shopping</a>
                    </div>
                </div>
                <%@include file="../../common/web/footer.jsp"%>
            </div>
        </div>
    </div>
    <%@include file="../../common/web/add_js.jsp"%>
</body>
</html>




 	


