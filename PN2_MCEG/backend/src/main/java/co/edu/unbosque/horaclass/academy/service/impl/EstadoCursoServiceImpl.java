package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.EstadoCursoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EstadoCursoResponseDto;
import co.edu.unbosque.horaclass.academy.model.EstadoCurso;
import co.edu.unbosque.horaclass.academy.repository.EstadoCursoRepository;
import co.edu.unbosque.horaclass.academy.service.EstadoCursoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstadoCursoServiceImpl implements EstadoCursoService {

    private final EstadoCursoRepository repository;
    private final ModelMapper modelMapper;

    public EstadoCursoServiceImpl(EstadoCursoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EstadoCursoResponseDto crear(EstadoCursoRequestDto request) {
        if (repository.existsById(request.getIdEstadoCurso())) {
            throw new RuntimeException("Ya existe un EstadoCurso con ese ID");
        }
        EstadoCurso entity = modelMapper.map(request, EstadoCurso.class);
        repository.save(entity);
        return modelMapper.map(entity, EstadoCursoResponseDto.class);
    }

    @Override
    public EstadoCursoResponseDto actualizar(int id, EstadoCursoRequestDto request) {
        EstadoCurso entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("EstadoCurso no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, EstadoCursoResponseDto.class);
    }

    @Override
    public List<EstadoCursoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, EstadoCursoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoCursoResponseDto obtenerPorId(int id) {
        EstadoCurso entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("EstadoCurso no encontrado"));
        return modelMapper.map(entity, EstadoCursoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("EstadoCurso no encontrado");
        }
        repository.deleteById(id);
    }
}