package lk.ijse.studentmanagementsystemee;

import java.io.*;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(urlPatterns = "/Student")
public class StudentController extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo:*/
        if (!req.getContentType().toLowerCase().startsWith("application/json")) {
            /*Send Error*/
            resp.sendError(HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE);
        }

        /*Process*/
//        BufferedReader reader = req.getReader();
//        StringBuilder sb = new StringBuilder();
//        PrintWriter writer = resp.getWriter();
//        reader.lines().forEach(line -> sb.append(line + "\n"));
//        System.out.println(sb);
//        writer.write(sb.toString());
//        writer.close();

//      JSON manipulate with Parson
        PrintWriter writer = resp.getWriter();
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        System.out.println(jsonObject.toString());
        System.out.println("Name: "+jsonObject.getString("name"));
        System.out.println("Address: "+jsonObject.getString("address"));
        System.out.println("Institute: "+jsonObject.getString("institute"));
        writer.write(jsonObject.toString());
    }
}