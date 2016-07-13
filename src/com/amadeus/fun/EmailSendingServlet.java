package com.amadeus.fun;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that takes message details from user and send it as a new e-mail through an SMTP server.
 * 
 * @author www.codejava.net
 * 
 */
@WebServlet("/EmailSendingServlet")
public class EmailSendingServlet extends HttpServlet {
  private String host;
  private String port;
  private String from;

  @Override
  public void init() {
    // reads SMTP server setting from web.xml file
    ServletContext context = getServletContext();
    host = context.getInitParameter("host");
    port = context.getInitParameter("port");
    from = context.getInitParameter("from");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // reads form fields
    String ccrecipient = "";
    String recipient = request.getParameter("recipient");
    if (request.getParameter("ccrecipient") != null) {
      ccrecipient = request.getParameter("ccrecipient");
    }
    String subject = request.getParameter("subject");
    String content = request.getParameter("content");

    String resultMessage = "";

    try {
      EmailUtility.sendEmail(host, port, recipient, from, ccrecipient, subject, content);
      resultMessage = "The e-mail was sent successfully";
    }
    catch (Exception ex) {
      ex.printStackTrace();
      resultMessage = "There were an error: " + ex.getMessage();
    }
    finally {
      request.setAttribute("Message", resultMessage);
      getServletContext().getRequestDispatcher("/Result.jsp").forward(request, response);
    }
  }
}