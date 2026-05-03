package co.edu.unbosque.horaclass.config;

import co.edu.unbosque.horaclass.academy.course.dto.CourseRespondeDto;
import co.edu.unbosque.horaclass.academy.course.model.Course;
import co.edu.unbosque.horaclass.academy.course.model.CourseType;
import co.edu.unbosque.horaclass.academy.course.model.Mode;
import co.edu.unbosque.horaclass.academy.group.dto.GroupStateResponseDto;
import co.edu.unbosque.horaclass.academy.group.model.GroupState;
import co.edu.unbosque.horaclass.academy.teacher.dto.TeacherResponseDto;
import co.edu.unbosque.horaclass.academy.teacher.model.Teacher;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        Converter<Teacher, String> teacherNombreConverter =
                ctx -> ctx.getSource() != null && ctx.getSource().getUsuario() != null
                        ? ctx.getSource().getUsuario().getPrimerNombre() + " " +
                        ctx.getSource().getUsuario().getPrimerApellido()
                        : null;

        Converter<Teacher, String> teacherUsernameConverter =
                ctx -> ctx.getSource() != null && ctx.getSource().getUsuario() != null
                        ? ctx.getSource().getUsuario().getUsername()
                        : null;
        // =========================
        // Teacher → TeacherResponseDTO (con campos de Usuario)
        // =========================
        modelMapper.addMappings(new PropertyMap<Teacher, TeacherResponseDto>() {
            @Override
            protected void configure() {
                using(teacherNombreConverter).map(source, destination.getNombre());
                using(teacherUsernameConverter).map(source, destination.getUsername());
            }
        });


        modelMapper.addMappings(new PropertyMap<Course, CourseRespondeDto>() {
            @Override
            protected void configure() {
                map().setModalidad(null);  // Se mapeará vía TypeMap
                map().setTipoCurso(null);   // Se mapeará vía TypeMap
            }
        });

//TODO
//        modelMapper.createTypeMap(Mode.class, ModalidadDto.class);
//        modelMapper.createTypeMap(CourseType.class, CourseTypeDto.class);
        modelMapper.createTypeMap(GroupState.class, GroupStateResponseDto.class);

        return modelMapper;
    }
}