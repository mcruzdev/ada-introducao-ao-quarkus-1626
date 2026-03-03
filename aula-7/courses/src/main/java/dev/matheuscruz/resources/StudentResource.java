package dev.matheuscruz.resources;

import dev.matheuscruz.Student;
import dev.matheuscruz.services.StudentService;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/student")
public class StudentResource {

    @Inject
    private StudentService studentService;

    @GET
    public Response findAll(){
        return Response.ok(
                Student.listAll()
        ).build();
    }

    @POST
    public Response createStudent(@Valid StudentDTO studentDTO){
        return Response
                .status(201)
                .entity(studentService.createStudent(studentDTO))
                .build();

    }

}
