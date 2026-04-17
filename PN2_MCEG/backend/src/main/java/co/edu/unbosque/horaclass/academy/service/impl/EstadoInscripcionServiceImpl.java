package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.EstadoInscripcionRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EstadoInscripcionResponseDto;
import co.edu.unbosque.horaclass.academy.model.EstadoInscripcion;
import co.edu.unbosque.horaclass.academy.repository.EstadoInscripcionRepository;
import co.edu.unbosque.horaclass.academy.service.EstadoInscripcionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstadoInscripcionServiceImpl implements EstadoInscripcionService {

    private final EstadoInscripcionRepository repository;
    private final ModelMapper modelMapper;

    public EstadoInscripcionServiceImpl(EstadoInscripcionRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EstadoInscripcionResponseDto crear(EstadoInscripcionRequestDto request) {
        if (repository.existsById(request.getIdEstadoInscripcion())) {
            throw new RuntimeException("Ya existe un EstadoInscripcion con ese ID");
        }
        EstadoInscripcion entity = modelMapper.map(request, EstadoInscripcion.class);
        repository.save(entity);
        return modelMapper.map(entity, EstadoInscripcionResponseDto.class);
    }

    @Override
    public EstadoInscripcionResponseDto actualizar(int id, EstadoInscripcionRequestDto request) {
        EstadoInscripcion entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("EstadoInscripcion no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, EstadoInscripcionResponseDto.class);
    }

    @Override
    public List<EstadoInscripcionResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, EstadoInscripcionResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoInscripcionResponseDto obtenerPorId(int id) {
        EstadoInscripcion entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("EstadoInscripcion no encontrado"));
        return modelMapper.map(entity, EstadoInscripcionResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("EstadoInscripcion no encontrado");
        }
        repository.deleteById(id);
    }
}