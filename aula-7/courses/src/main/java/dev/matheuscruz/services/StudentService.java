package dev.matheuscruz.services;

import dev.matheuscruz.Student;
import dev.matheuscruz.resources.StudentDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;

@ApplicationScoped
public class StudentService {

    @Transactional
    public Student createStudent(StudentDTO studentDTO){

        Student student = mapDTOtoEntity(studentDTO);

        student.persist();

        return student;
    }

    private static Student mapDTOtoEntity(StudentDTO studentDTO) {
        Student student = new Student(
                studentDTO.name(),
                studentDTO.age(),
                studentDTO.documentoCpf(),
                studentDTO.documentoCnpj(),
                studentDTO.email(),
                new BigDecimal(studentDTO.mensalidade())
        );
        return student;
    }
}
