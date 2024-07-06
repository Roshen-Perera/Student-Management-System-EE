package lk.ijse.studentmanagementsystemee.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*Todo*/
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


        PrintWriter writer = resp.getWriter();

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

        //String id = UUID.randomUUID().toString();
        Jsonb jsonb = JsonbBuilder.create();
        List<StudentDTO> studentDTOList = jsonb.fromJson(req.getReader(), new ArrayList<StudentDTO>() {
        }.getClass().getGenericSuperclass());
        studentDTOList.forEach(System.out::println);

//        StudentDTO studentDTO = jsonb.fromJson(req.getReader(), StudentDTO.class);
//        studentDTO.setId(id);
//        System.out.println(studentDTO);
        writer.write(studentDTOList.toString());
    }
}