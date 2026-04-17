package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.EstudioEmpleadoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EstudioEmpleadoResponseDto;
import co.edu.unbosque.horaclass.academy.model.EstudioEmpleado;
import co.edu.unbosque.horaclass.academy.repository.EstudioEmpleadoRepository;
import co.edu.unbosque.horaclass.academy.service.EstudioEmpleadoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EstudioEmpleadoServiceImpl implements EstudioEmpleadoService {

    private final EstudioEmpleadoRepository repository;
    private final ModelMapper modelMapper;

    public EstudioEmpleadoServiceImpl(EstudioEmpleadoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EstudioEmpleadoResponseDto crear(EstudioEmpleadoRequestDto request) {
        if (repository.existsById(request.getIdEstudio())) {
            throw new RuntimeException("Ya existe un EstudioEmpleado con ese ID");
        }
        EstudioEmpleado entity = modelMapper.map(request, EstudioEmpleado.class);
        repository.save(entity);
        return modelMapper.map(entity, EstudioEmpleadoResponseDto.class);
    }

    @Override
    public EstudioEmpleadoResponseDto actualizar(int id, EstudioEmpleadoRequestDto request) {
        EstudioEmpleado entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("EstudioEmpleado no encontrado"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, EstudioEmpleadoResponseDto.class);
    }

    @Override
    public List<EstudioEmpleadoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, EstudioEmpleadoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EstudioEmpleadoResponseDto obtenerPorId(int id) {
        EstudioEmpleado entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("EstudioEmpleado no encontrado"));
        return modelMapper.map(entity, EstudioEmpleadoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("EstudioEmpleado no encontrado");
        }
        repository.deleteById(id);
    }
}