package co.edu.unbosque.horaclass.academy.course.service.impl;

import co.edu.unbosque.horaclass.academy.course.dto.CourseRequestDto;
import co.edu.unbosque.horaclass.academy.course.dto.CourseRespondeDto;
import co.edu.unbosque.horaclass.academy.course.model.Course;
import co.edu.unbosque.horaclass.academy.course.model.CourseType;
import co.edu.unbosque.horaclass.academy.course.model.Mode;
import co.edu.unbosque.horaclass.academy.course.repositiry.CourseRepository;
import co.edu.unbosque.horaclass.academy.course.repositiry.CourseTypeRepository;
import co.edu.unbosque.horaclass.academy.course.repositiry.ModeRepository;
import co.edu.unbosque.horaclass.academy.course.service.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository cursoRepository;
    private final ModeRepository modalidadRepository;
    private final CourseTypeRepository tipoCursoRepository;

    public CourseServiceImpl(CourseRepository cursoRepository, ModeRepository modalidadRepository, CourseTypeRepository tipoCursoRepository) {
        this.cursoRepository = cursoRepository;
        this.modalidadRepository = modalidadRepository;
        this.tipoCursoRepository = tipoCursoRepository;
    }


    @Override
    public CourseRespondeDto crearCurso(CourseRequestDto request) {
        if (cursoRepository.existsCourseByIdCurso(request.getIdCurso())) {
            throw new RuntimeException("Ya existe un curso con ese ID");
        }
        Mode modalidad = modalidadRepository.findById(request.getIdModalidad())
                .orElseThrow(() -> new RuntimeException("Modalidad No encontrada"));
        CourseType tipoCurso = tipoCursoRepository.findById(request.getIdTipoCurso())
                .orElseThrow(() -> new RuntimeException("Tipo de curso no encontrado"));
        if (request.getSemestre() < 1) {
            throw new RuntimeException("Entrada invalida");
        }
        ModelMapper mapper = new ModelMapper();
        Course curso = mapper.map(request, Course.class);
        cursoRepository.save(curso);
        return mapToResponse(request, modalidad, tipoCurso);
    }

    @Override
    public CourseRespondeDto actualizarCurso(int idCurso, CourseRequestDto request) {
        Course curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Validar nombre único (excluyendo el curso actual)
        if (cursoRepository.existsCourseByNombre(request.getNombre())) {
            Course cursoExistente = cursoRepository.findCourseByNombre(request.getNombre()).get();
            if (cursoExistente.getIdCurso() != idCurso) {
                throw new RuntimeException("Ya existe un curso con ese nombre");
            }
        }

        Mode modalidad = modalidadRepository.findById(request.getIdModalidad())
                .orElseThrow(() -> new RuntimeException("Modalidad no encontrada"));

        CourseType tipoCurso = tipoCursoRepository.findById(request.getIdTipoCurso())
                .orElseThrow(() -> new RuntimeException("Tipo de curso no encontrado"));

        ModelMapper mapper = new ModelMapper();
        cursoRepository.save(mapper.map(request, Course.class));

        return mapToResponse(request, modalidad, tipoCurso);
    }

    @Override
    public List<CourseRespondeDto> listarTodosLosCursos() {
        ModelMapper mapper = new ModelMapper();


        return cursoRepository.findAll()
                .stream()
                .map(curso -> mapper.map(curso, CourseRespondeDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CourseRespondeDto obtenerCursoPorId(int idCurso) {
        Course curso = cursoRepository.findById(idCurso)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        ModelMapper mapper = new ModelMapper();
        CourseRespondeDto res = mapper.map(curso,CourseRespondeDto.class);
        return res;
    }

    @Override
    public void eliminarCurso(int idCurso) {
        if(!cursoRepository.existsById(idCurso)){
            throw new RuntimeException("Curso no encontrado");
        }
        cursoRepository.deleteById(idCurso);
    }

    private CourseRespondeDto mapToResponse(CourseRequestDto dto, Mode modalidad, CourseType tipoCurso) {
        CourseRespondeDto res = CourseRespondeDto.builder()
                .idCurso(dto.getIdCurso())
                .nombre(dto.getNombre())
                .modalidad(modalidad.getNombre())
                .tipoCurso(tipoCurso.getNombre())
                .creditos(dto.getCreditos())
                .semestre(dto.getSemestre())
                .build();
        return res;
    }
}

