package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.AulaRequestDto;
import co.edu.unbosque.horaclass.academy.dto.AulaResponseDto;
import co.edu.unbosque.horaclass.academy.model.Aula;
import co.edu.unbosque.horaclass.academy.repository.AulaRepository;
import co.edu.unbosque.horaclass.academy.service.AulaService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AulaServiceImpl implements AulaService {

    private final AulaRepository repository;
    private final ModelMapper modelMapper;

    public AulaServiceImpl(AulaRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public AulaResponseDto crear(AulaRequestDto request) {
        if (repository.existsById(request.getIdAula())) {
            throw new RuntimeException("Ya existe un Aula con ese ID");
        }
        Aula entity = modelMapper.map(request, Aula.class);
        repository.save(entity);
        return modelMapper.map(entity, AulaResponseDto.class);
    }

    @Override
    public AulaResponseDto actualizar(int id, AulaRequestDto request) {
        Aula entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula no encontrada"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, AulaResponseDto.class);
    }

    @Override
    public List<AulaResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, AulaResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public AulaResponseDto obtenerPorId(int id) {
        Aula entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula no encontrada"));
        return modelMapper.map(entity, AulaResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Aula no encontrada");
        }
        repository.deleteById(id);
    }
}