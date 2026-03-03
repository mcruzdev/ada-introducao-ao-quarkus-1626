package dev.matheuscruz.resources;


import dev.matheuscruz.Course;
import dev.matheuscruz.Lesson;
import dev.matheuscruz.services.CourseService;
import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.logging.Log;
import io.quarkus.panache.common.Page;

import io.quarkus.security.Authenticated; //

import jakarta.annotation.security.RolesAllowed;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/courses")
public class CourseResource {

    private final CourseService service;

    public CourseResource(CourseService service) {
        this.service = service;
    }

    @POST
    @Transactional
    public Response createCourse(
            @Valid CourseDTO dto
    ) {
        this.service.createCourse(new Course(dto.name()));
        return Response.status(201).build();
    }

    @GET
    @RolesAllowed({"admin"})
    public Response listAll(@QueryParam("page") @DefaultValue("0") Integer page, @DefaultValue("10") @QueryParam("size") Integer size) {

        List<Course> list = Course.findAll().page(Page.of(page, size)).list();

        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    public Response courseById(@PathParam("id") Long id) {
        Course course = Course.findById(id);
        return Response.ok(course).build();
    }

    @POST
    @Path("/{id}/lessons")
    @Transactional
    public Response createLesson(@PathParam("id") Long id, @Valid LessonDTO dto) {
        Course course = Course.findById(id);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Lesson lesson = new Lesson(
                dto.name()
        );

        lesson.persist();

        course.addLesson(lesson);

        return Response.status(Response.Status.CREATED)
                .header("Content-Type", "application/json")
                .build();
    }


    @PUT
    @Path("/{id}")
    public Response updateCourse(
            @PathParam("id") Long id,
            CourseDTO courseDTO
    ) {

        Course possibleCourse = Course.findById(id);
        if (possibleCourse == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        possibleCourse.setName(courseDTO.name());
        return Response.ok()
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .build();
    }

    @GET
    @Path("/{id}/lessons")
    public Response findAllLessonsByCourseId(
            @PathParam("id") Long id
    ) {

        Log.info("Antes de buscar um curso");
        // SELECT * FROM Course c WHERE c.id = 1;
        Course possibleCourse = Course.findById(id);
        if (possibleCourse == null) {
            return Response.status(404).build();
        }

        Log.info("Antes de buscar todos as aulas de um curso");
        // SELECT ...
        List<Lesson> lessons = possibleCourse.getLessons();

        List<LessonDTO> list = lessons.stream()
                .map(lesson -> new LessonDTO(lesson.getName(), lesson.id))
                .toList();

        return Response.ok(list).build();
    }


}
