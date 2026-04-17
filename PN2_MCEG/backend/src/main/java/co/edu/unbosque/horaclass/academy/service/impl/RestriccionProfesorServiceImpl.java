package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.RestriccionProfesorRequestDto;
import co.edu.unbosque.horaclass.academy.dto.RestriccionProfesorResponseDto;
import co.edu.unbosque.horaclass.academy.model.RestriccionProfesor;
import co.edu.unbosque.horaclass.academy.repository.RestriccionProfesorRepository;
import co.edu.unbosque.horaclass.academy.service.RestriccionProfesorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RestriccionProfesorServiceImpl implements RestriccionProfesorService {

    private final RestriccionProfesorRepository repository;
    private final ModelMapper modelMapper;

    public RestriccionProfesorServiceImpl(RestriccionProfesorRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public RestriccionProfesorResponseDto crear(RestriccionProfesorRequestDto request) {
        if (repository.existsById(request.getIdRestriccion())) {
            throw new RuntimeException("Ya existe RestriccionProfesor");
        }
        RestriccionProfesor entity = modelMapper.map(request, RestriccionProfesor.class);
        repository.save(entity);
        return modelMapper.map(entity, RestriccionProfesorResponseDto.class);
    }

    @Override
    public RestriccionProfesorResponseDto actualizar(int id, RestriccionProfesorRequestDto request) {
        RestriccionProfesor entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("RestriccionProfesor no encontrado"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, RestriccionProfesorResponseDto.class);
    }

    @Override
    public List<RestriccionProfesorResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(e -> modelMapper.map(e, RestriccionProfesorResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public RestriccionProfesorResponseDto obtenerPorId(int id) {
        RestriccionProfesor entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("RestriccionProfesor no encontrado"));
        return modelMapper.map(entity, RestriccionProfesorResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("RestriccionProfesor no encontrado");
        }
        repository.deleteById(id);
    }
}