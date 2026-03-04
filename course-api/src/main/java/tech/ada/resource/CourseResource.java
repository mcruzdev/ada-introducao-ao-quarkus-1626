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
import tech.ada.dto.CourseResponse;
import tech.ada.dto.CreateCourseRequest;
import tech.ada.dto.CreateLessonRequest;
import tech.ada.model.Course;
import tech.ada.model.Lesson;

import java.net.URI;

@Path("/courses")
public class CourseResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createCourse(@Valid CreateCourseRequest request) {

        Course course = new Course(request.name());

        course.persist();

        return Response.created(URI.create("/courses/" + course.id)).header("Content-Type", "application/json")
                .entity(new CourseResponse(course.id, course.getName())).build();

    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateCourse(@PathParam("id") Long id, @Valid CreateCourseRequest request) {
        Log.info("Updating Course with ID " + id);

        Course course = Course.findById(id);

        if (course == null) {
            Log.info("Course with ID " + id + " not found");
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        course.changeName(request.name());

        Log.info("Course updated " + course);

        return Response.ok(new CourseResponse(course.id, course.getName())).build();
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
        return Response.ok(Course.listAll()).build();
    }

    @GET
    @Path("/{id}")
    public Response getCourseById(@PathParam("id") Long id) {
        Log.info("Getting course by ID: " + id);
        Course course = Course.findById(id);
        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(course).build();
    }

    @POST
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

        return Response.created(URI.create("/courses/" + course.id + "/lessons/" + lesson.id))
                .header("Content-Type", MediaType.APPLICATION_JSON).build();
    }

    @GET
    @Path("/{id}/lessons")
    public Response getLessonsByCourseId(@PathParam("id") Long id) {

        Course course = Course.findById(id);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(course.getLessons()).build();
    }
}
