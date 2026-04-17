package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.EstudianteRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EstudianteResponseDto;
import co.edu.unbosque.horaclass.academy.model.Estudiante;
import co.edu.unbosque.horaclass.academy.repository.EstudianteRepository;
import co.edu.unbosque.horaclass.academy.service.EstudianteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository repository;
    private final ModelMapper modelMapper;

    public EstudianteServiceImpl(EstudianteRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EstudianteResponseDto crear(EstudianteRequestDto request) {
        if (repository.existsById(request.getIdEstudiante())) {
            throw new RuntimeException("Ya existe un Estudiante con ese ID");
        }
        Estudiante entity = modelMapper.map(request, Estudiante.class);
        repository.save(entity);
        return modelMapper.map(entity, EstudianteResponseDto.class);
    }

    @Override
    public EstudianteResponseDto actualizar(int id, EstudianteRequestDto request) {
        Estudiante entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, EstudianteResponseDto.class);
    }

    @Override
    public List<EstudianteResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, EstudianteResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EstudianteResponseDto obtenerPorId(int id) {
        Estudiante entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return modelMapper.map(entity, EstudianteResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Estudiante no encontrado");
        }
        repository.deleteById(id);
    }
}