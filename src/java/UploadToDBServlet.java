

import java.io.InputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse; 
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Part;
import oracleconnection.OracleConnection;
/**
 *
 * @author joker96
 */
@WebServlet("/uploadToDB")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 16, // 16MB
maxRequestSize = 1024 * 1024 * 50) // 50MB
public class UploadToDBServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    private static final long serialVersionUID = 1L;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            
            Part part = request.getPart("myFile");
            String disposition = part.getHeader("Content-Disposition");
            String filename = disposition.replaceFirst("(?i)^.*filename=\"([^\"]+)\".*$", "$1");
            InputStream in = part.getInputStream();
            System.out.println(in);
            if(!filename.endsWith("pdf") && !filename.endsWith("xlsx") && !filename.endsWith("xls"))
                throw new Exception("Please make sure the file is of pdf/xlsx format");
            
            boolean isUp = OracleConnection.uploadFile(in, filename);
            // Upload successfully!
            if(isUp)
                response.sendRedirect(request.getContextPath() + "/uploadToDBResults");
            else
                throw new Exception("Server Error. Please try 5 minutes later");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error: " + e.getMessage());
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(request, response);
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
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
