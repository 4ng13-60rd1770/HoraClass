package co.edu.unbosque.horaclass.academy.classroom.service.impl;

import co.edu.unbosque.horaclass.academy.classroom.dto.ClassroomRequestDto;
import co.edu.unbosque.horaclass.academy.classroom.dto.ClassroomResponseDto;
import co.edu.unbosque.horaclass.academy.classroom.model.Classroom;
import co.edu.unbosque.horaclass.academy.classroom.repository.ClassroomRepository;
import co.edu.unbosque.horaclass.academy.classroom.service.ClassroomService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClassroomServiceImpl implements ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ModelMapper mapper;

    public ClassroomServiceImpl(ClassroomRepository classroomRepository, ModelMapper mapper) {
        this.classroomRepository = classroomRepository;
        this.mapper = mapper;
    }

    @Override
    public ClassroomResponseDto crearSalon(ClassroomRequestDto request) {
        Classroom salon = mapper.map(request, Classroom.class);
        if (salon.getDisponible() == null) salon.setDisponible(true);
        return mapper.map(classroomRepository.save(salon), ClassroomResponseDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomResponseDto> listarTodosLosSalones() {
        return classroomRepository.findAll().stream()
                .map(s -> mapper.map(s, ClassroomResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ClassroomResponseDto obtenerSalonPorId(Integer id) {
        Classroom salon = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salón no encontrado con ID: " + id));
        return mapper.map(salon, ClassroomResponseDto.class);
    }

    @Override
    public ClassroomResponseDto actualizarSalon(Integer id, ClassroomRequestDto request) {
        Classroom salon = classroomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Salón no encontrado con ID: " + id));
        mapper.map(request, salon);
        return mapper.map(classroomRepository.save(salon), ClassroomResponseDto.class);
    }

    @Override
    public void eliminarSalon(Integer id) {
        if (!classroomRepository.existsById(id)) {
            throw new RuntimeException("Salón no encontrado con ID: " + id);
        }
        classroomRepository.deleteById(id);
    }
}
