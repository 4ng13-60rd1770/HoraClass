package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.EmpleadoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EmpleadoResponseDto;
import co.edu.unbosque.horaclass.academy.model.Empleado;
import co.edu.unbosque.horaclass.academy.repository.EmpleadoRepository;
import co.edu.unbosque.horaclass.academy.service.EmpleadoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmpleadoServiceImpl implements EmpleadoService {

    private final EmpleadoRepository repository;
    private final ModelMapper modelMapper;

    public EmpleadoServiceImpl(EmpleadoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmpleadoResponseDto crear(EmpleadoRequestDto request) {
        if (repository.existsById(request.getIdEmpleado())) {
            throw new RuntimeException("Ya existe un Empleado con ese ID");
        }
        Empleado entity = modelMapper.map(request, Empleado.class);
        repository.save(entity);
        return modelMapper.map(entity, EmpleadoResponseDto.class);
    }

    @Override
    public EmpleadoResponseDto actualizar(int id, EmpleadoRequestDto request) {
        Empleado entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, EmpleadoResponseDto.class);
    }

    @Override
    public List<EmpleadoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, EmpleadoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EmpleadoResponseDto obtenerPorId(int id) {
        Empleado entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
        return modelMapper.map(entity, EmpleadoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Empleado no encontrado");
        }
        repository.deleteById(id);
    }
}