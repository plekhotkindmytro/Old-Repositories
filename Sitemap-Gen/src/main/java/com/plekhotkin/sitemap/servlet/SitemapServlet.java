package com.plekhotkin.sitemap.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.UrlValidator;

import com.plekhotkin.sitemap.exception.ServiceException;
import com.plekhotkin.sitemap.model.ChangeFrequency;
import com.plekhotkin.sitemap.model.LastModification;
import com.plekhotkin.sitemap.model.Priority;
import com.plekhotkin.sitemap.service.MailService;
import com.plekhotkin.sitemap.service.SitemapService;
import com.plekhotkin.sitemap.util.ApplicationProperties;

/**
 * Servlet implementation class SitemapServlet
 */
public class SitemapServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String sitemapFolder;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public SitemapServlet() {
        super();
        sitemapFolder = ApplicationProperties.getProperty("sitemapFolder");
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");
        response.setContentType("text/xml");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

        InputStream is = new BufferedInputStream(new FileInputStream(new File(sitemapFolder + fileName)));

        int read = 0;
        byte[] bytes = new byte[1024];
        OutputStream os = response.getOutputStream();

        while ((read = is.read(bytes)) != -1) {
            os.write(bytes, 0, read);
        }
        os.flush();
        os.close();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String websiteUrl = request.getParameter("websiteUrl");

        if (!validateUrl(websiteUrl)) {
            request.setAttribute("error", "URL is not valid");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
            return;
        }

        SitemapService sitemapService = new SitemapService();
        try {

            String freq = request.getParameter("freq");
            String lastmod = request.getParameter("lastmod");
            String priority = request.getParameter("priority");
            String sitemapFileName = sitemapService
                .generateSitemap(websiteUrl, ChangeFrequency.lookup(freq), LastModification.lookup(lastmod), Priority.lookup(priority));
            request.setAttribute("sitemapFileName", sitemapFileName);

            String email = request.getParameter("email");
            boolean isEmailSent = false;
            if (validateEmail(email)) {
                MailService mailService = new MailService();
                isEmailSent = mailService.sendSitemap(email, "Sitemap for " + websiteUrl, "Dear user, Please find sitemap attached.", sitemapFolder + sitemapFileName);
                request.setAttribute("email", email);
            }
            request.setAttribute("isEmailSent", isEmailSent);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/WEB-INF/result.jsp");
        dispatcher.forward(request, response);
    }

    private static boolean validateEmail(String email) {
        EmailValidator emailValidator = EmailValidator.getInstance();
        return emailValidator.isValid(email);

    }

    private static boolean validateUrl(String url) {
        UrlValidator urlValidator = new UrlValidator();
        return urlValidator.isValid(url);
    }

}
