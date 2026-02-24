package dev.matheuscruz.resources;


import dev.matheuscruz.Course;
import dev.matheuscruz.Lesson;
import dev.matheuscruz.services.CourseService;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.panache.common.Parameters;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.ResponseStatus;

@Path("/courses")
public class CourseResource {

    private final CourseService service;

    public CourseResource(CourseService service) {
        this.service = service;
    }

    @POST
    public Response createCourse(
            CourseDTO dto
    ) {
        this.service.createCourse(new Course(dto.name()));
        return Response.status(201).build();
    }

    @GET
    public Response listAll() {
        // active record (Ruby)
        // repository / DAO
        return Response.ok(
                Course.listAll()
        ).build();
    }

    @GET
    @Path("/{id}")
    public Response courseById(@PathParam("id") Long id) {
        Course byId = Course.findById(id);
        return Response.ok(
                 byId // DTO :)
        ).build();
    }


    @POST
    // courses/{id}/lessons
    @Path("/{id}/lessons")
    @Transactional
    public Response createLesson(@PathParam("id") Long id, LessonDTO dto) {
        Course course = Course.findById(id);

        if (course == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        Lesson lesson = new Lesson(
                dto.name()
        );

        lesson.persist();

        course.addLesson(lesson);

        return Response.status(Response.Status.CREATED).build();
    }

//    @Entity
//public class Cliente {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
//    private Endereco endereco;
//}


}
