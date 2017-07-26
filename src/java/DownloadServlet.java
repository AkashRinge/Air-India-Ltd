/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import oracleconnection.OracleConnection;

/**
 *
 * @author joker96
 */
@WebServlet(urlPatterns = {"/download"})
public class DownloadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

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
        
        int id = Integer.parseInt(request.getParameter("id"));
        response.setContentType("text/html;charset=UTF-8");
        try{
            String name = OracleConnection.retrieveName(id);
            InputStream in = OracleConnection.retrieveFile2(id);
            System.out.println(in);
            ServletContext sc = this.getServletContext();
            String mimeType = sc.getMimeType(name);
            if (mimeType == null) {        
                // set to binary type if MIME mapping not found
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", "attachment; filename=\""
                            + name + "\"");

            

            OutputStream out = response.getOutputStream();

            
            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            in.close();
            out.flush();
            out.close();
        }
        catch(Exception e)
        {e.printStackTrace();}
        // use inline if you want to view the content in browser, helpful for
        // pdf file
        // response.setHeader("Content-Disposition","inline; filename=\"" +
        // filename + "\"");
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
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
