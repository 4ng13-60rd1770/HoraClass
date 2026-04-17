package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.ProfesorRequestDto;
import co.edu.unbosque.horaclass.academy.dto.ProfesorResponseDto;
import co.edu.unbosque.horaclass.academy.model.Profesor;
import co.edu.unbosque.horaclass.academy.repository.ProfesorRepository;
import co.edu.unbosque.horaclass.academy.service.ProfesorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorRepository repository;
    private final ModelMapper modelMapper;

    public ProfesorServiceImpl(ProfesorRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProfesorResponseDto crear(ProfesorRequestDto request) {
        if (repository.existsById(request.getIdEmpleado())) {
            throw new RuntimeException("Ya existe un Profesor con ese ID");
        }
        Profesor entity = modelMapper.map(request, Profesor.class);
        repository.save(entity);
        return modelMapper.map(entity, ProfesorResponseDto.class);
    }

    @Override
    public ProfesorResponseDto actualizar(int id, ProfesorRequestDto request) {
        Profesor entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, ProfesorResponseDto.class);
    }

    @Override
    public List<ProfesorResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(e -> modelMapper.map(e, ProfesorResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProfesorResponseDto obtenerPorId(int id) {
        Profesor entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
        return modelMapper.map(entity, ProfesorResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Profesor no encontrado");
        }
        repository.deleteById(id);
    }
}