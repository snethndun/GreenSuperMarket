package com.green.greensupermarket.controller;

import com.paypal.base.rest.PayPalRESTException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AuthorizePaymentServlet", value = "/authorize_payment")
public class AuthorizePaymentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String product = request.getParameter("product");
            String subtotal = request.getParameter("subtotal");
            String shipping = request.getParameter("shipping");
            String tax = request.getParameter("tax");
            String total = request.getParameter("total");

            try{
                OrderDetails orderDetails = new OrderDetails(product, subtotal, shipping, tax, total);
                PaymentServices paymentServices = new PaymentServices();
                String approvalLink = paymentServices.authorizePayment(orderDetails);
                System.out.println(approvalLink);
                response.sendRedirect(approvalLink);

            }catch (PayPalRESTException ex){
                ex.printStackTrace();
                request.setAttribute("errorMessage", ex.getMessage());
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }



    }

}