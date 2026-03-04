package tech.ada.resource;

import io.quarkus.logging.Log;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import tech.ada.dto.CourseResponse;
import tech.ada.dto.CourseRequest;
import tech.ada.dto.CreateLessonRequest;
import tech.ada.dto.LessonResponse;
import tech.ada.model.Course;
import tech.ada.model.Lesson;

@Path("/courses")
public class CourseResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createCourse(@Valid CourseRequest request) {

        Course course = new Course(request.name());

        course.persist();

        URI location = URI.create("/courses/" + course.id);

        CourseResponse payload = new CourseResponse(course.id, course.getName(), List.of());

        return Response.created(location)
                .header("Content-Type", "application/json")
                .entity(payload)
                .build();

    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateCourse(@PathParam("id") Long id, @Valid CourseRequest request) {
        Log.info("Updating Course with ID " + id);

        Optional<Course> possibleCourse = Course.findByIdOptional(id);

        if (possibleCourse.isEmpty()) {
            Log.info("Course with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).build(); // early-return
        }

        Course course = possibleCourse.get();

        course.changeName(request.name());

        Log.info("Course updated " + course);

        return Response.ok(new CourseResponse(course.id, course.getName(), List.of())).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteCourse(@PathParam("id") Long id) {
        Course.deleteById(id);
        return Response.noContent().build();
    }

    @GET
    public Response getCourses() {
        List<Course> courses = Course.listAll();
        List<CourseResponse> response = courses
                .stream()
                .map((Course c) -> new CourseResponse(c.id, c.getName(), List.of()))
                .toList();

        return Response.ok(response).build();
    }

    @GET
    @Path("/{id}")
    public Response getCourseById(@PathParam("id") Long id) {
        Log.info("Getting course by ID: " + id);
        Course course = Course.findById(id);
        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(new CourseResponse(course.id, course.getName(), List.of())).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}/lessons")
    @Transactional
    public Response createLesson(@PathParam("id") Long id, @Valid CreateLessonRequest request) {

        Course course = Course.findById(id);
        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Lesson lesson = new Lesson(request.name());

        lesson.persist();

        course.addLesson(lesson);

        URI location = URI.create("/courses/" + course.id + "/lessons/" + lesson.id);

        LessonResponse response = new LessonResponse(lesson.id, lesson.getName());

        return Response.created(location)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .entity(response)
                .build();
    }

    @GET
    @Path("/{id}/lessons")
    public Response getLessonsByCourseId(@PathParam("id") Long id) {

        Course course = Course.findById(id);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        List<LessonResponse> response = course.getLessons()
                .stream()
                .map((Lesson l) -> new LessonResponse(l.id, l.getName()))
                .toList();

        return Response.ok(response).build();
    }
}
