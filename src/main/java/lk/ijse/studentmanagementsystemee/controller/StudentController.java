package lk.ijse.studentmanagementsystemee.controller;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mysql.cj.jdbc.Driver;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import lk.ijse.studentmanagementsystemee.dto.StudentDTO;

@WebServlet(urlPatterns = "/Student")
public class StudentController extends HttpServlet {
    Connection connection;
    static String SAVE_STUDENT = "INSERT INTO student(id, name, city, email, level) VALUES(?,?,?,?,?)";
    static String GET_STUDENT = "SELECT * FROM student WHERE id = ?";
    static String UPDATE_STUDENT = "UPDATE student SET name = ?, city = ?, email = ?, level = ? WHERE id = ?";
    static String DELETE_STUDENT = "DELETE FROM student WHERE id = ?";
    @Override
    public void init() throws ServletException {
        try {
            String driver = getServletContext().getInitParameter("driver-class");
            String url = getServletContext().getInitParameter("dbURL");
            String name = getServletContext().getInitParameter("dbUser");
            String password = getServletContext().getInitParameter("dbPassword");
            System.out.println(url+name+password);
            System.out.println("ado");
            Class.forName(driver);
            this.connection = DriverManager.getConnection(
                    url,
                    name,
                    password
            );
        }catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json")) {
            /*Send Error*/
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        try {
            var ps = this.connection.prepareStatement(UPDATE_STUDENT);
//            String stuId = req.getParameter("id");
            Jsonb jsonb = JsonbBuilder.create();
            var updatedStudent = jsonb.fromJson(req.getReader(), StudentDTO.class);
            ps.setString(1, updatedStudent.getName());
            ps.setString(2, updatedStudent.getCity());
            ps.setString(3, updatedStudent.getEmail());
            ps.setString(4, updatedStudent.getLevel());
            ps.setString(5, updatedStudent.getId());
            if (ps.executeUpdate() != 0){
                resp.getWriter().write("Update Success");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.getWriter().write("Update Fail");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    protected void doPatch(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (!req.getContentType().toLowerCase().startsWith("application/json")) {
            /*Send Error*/
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
        try {
            var ps = this.connection.prepareStatement(UPDATE_STUDENT);
//            String stuId = req.getParameter("id");
            Jsonb jsonb = JsonbBuilder.create();
            var updatedStudent = jsonb.fromJson(req.getReader(), StudentDTO.class);
            ps.setString(1, updatedStudent.getName());
            ps.setString(2, updatedStudent.getCity());
            ps.setString(3, updatedStudent.getEmail());
            ps.setString(4, updatedStudent.getLevel());
            ps.setString(5, updatedStudent.getId());
            if (ps.executeUpdate() != 0){
                resp.getWriter().write("Update Success");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                resp.getWriter().write("Update Fail");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo*/
        if (!req.getContentType().toLowerCase().startsWith("application/json")) {
            /*Send Error*/
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

        /*Process*/
//        BufferedReader reader = req.getReader();
//        StringBuilder sb = new StringBuilder();
//        PrintWriter writer = resp.getWriter();
//        reader.lines().forEach(line -> sb.append(line + "\n"));
//        System.out.println(sb);
//        writer.write(sb.toString());
//        writer.close();


        //PrintWriter writer = resp.getWriter();

      //JSON manipulate with Parson
//        JsonReader reader = Json.createReader(req.getReader()); //Reads the request and passes to a special type reader called Json reader.
//        JsonObject jsonObject = reader.readObject(); // Reads the object and assigns it to a JsonObject type variable.
//        System.out.println(jsonObject.toString()); //Prints the JsonObject variable to the console

//
//
//        System.out.println("Name: "+jsonObject.getString("name")); //Prints a specific data from a JsonObject variable to the console

//        for (int i = 0; i < jsonArray.size(); i++) {
//            JsonObject jsonObject = jsonArray.getJsonObject(i);
//            System.out.println(jsonObject.getString("name"));
//        }

        String id = UUID.randomUUID().toString();
        Jsonb jsonb = JsonbBuilder.create();
//        List<StudentDTO> studentDTOList = jsonb.fromJson(req.getReader(), new ArrayList<StudentDTO>() {
//        }.getClass().getGenericSuperclass());
//        studentDTOList.forEach(System.out::println);

        StudentDTO studentDTO = jsonb.fromJson(req.getReader(), StudentDTO.class);
        studentDTO.setId(id);

        try (var writer = resp.getWriter()){
            PreparedStatement ps = connection.prepareStatement(SAVE_STUDENT);
            ps.setString(1, studentDTO.getId());
            ps.setString(2, studentDTO.getName());
            ps.setString(3, studentDTO.getCity());
            ps.setString(4, studentDTO.getEmail());
            ps.setString(5, studentDTO.getLevel());
            if (ps.executeUpdate() != 0){
                writer.write("Insert Success");
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } else {
                writer.write("Insert Fail");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println(studentDTO);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var studentDTO = new StudentDTO();
        var studentId = req.getParameter("id");
        try(var writer = resp.getWriter()) {
            var ps = connection.prepareStatement(GET_STUDENT);
            ps.setString(1, studentId);
            var rs = ps.executeQuery();
            while(rs.next()){
                studentDTO.setId(rs.getString("id"));
                studentDTO.setName(rs.getString("name"));
                studentDTO.setCity(rs.getString("city"));
                studentDTO.setEmail(rs.getString("email"));
                studentDTO.setLevel(rs.getString("level"));
            }
            writer.write(studentDTO.toString());
            System.out.println(studentDTO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var id = req.getParameter("id");
        try (var writer = resp.getWriter()){
            PreparedStatement ps = this.connection.prepareStatement(DELETE_STUDENT);
            ps.setString(1, id);
            if (ps.executeUpdate() != 0){
                writer.write("Delete Success");
                resp.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                writer.write("Delete Fail");
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}