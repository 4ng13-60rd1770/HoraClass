package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.EstadoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EstadoResponseDto;
import co.edu.unbosque.horaclass.academy.model.Estado;
import co.edu.unbosque.horaclass.academy.repository.EstadoRepository;
import co.edu.unbosque.horaclass.academy.service.EstadoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstadoServiceImpl implements EstadoService {

    private final EstadoRepository repository;
    private final ModelMapper modelMapper;

    public EstadoServiceImpl(EstadoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EstadoResponseDto crear(EstadoRequestDto request) {
        if (repository.existsById(request.getIdEstado())) {
            throw new RuntimeException("Ya existe un Estado con ese ID");
        }
        Estado entity = modelMapper.map(request, Estado.class);
        repository.save(entity);
        return modelMapper.map(entity, EstadoResponseDto.class);
    }

    @Override
    public EstadoResponseDto actualizar(int id, EstadoRequestDto request) {
        Estado entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, EstadoResponseDto.class);
    }

    @Override
    public List<EstadoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, EstadoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EstadoResponseDto obtenerPorId(int id) {
        Estado entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estado no encontrado"));
        return modelMapper.map(entity, EstadoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Estado no encontrado");
        }
        repository.deleteById(id);
    }
}