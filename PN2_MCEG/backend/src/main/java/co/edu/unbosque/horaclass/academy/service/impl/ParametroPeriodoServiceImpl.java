package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.ParametroPeriodoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.ParametroPeriodoResponseDto;
import co.edu.unbosque.horaclass.academy.model.ParametroPeriodo;
import co.edu.unbosque.horaclass.academy.repository.ParametroPeriodoRepository;
import co.edu.unbosque.horaclass.academy.service.ParametroPeriodoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ParametroPeriodoServiceImpl implements ParametroPeriodoService {

    private final ParametroPeriodoRepository repository;
    private final ModelMapper modelMapper;

    public ParametroPeriodoServiceImpl(ParametroPeriodoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ParametroPeriodoResponseDto crear(ParametroPeriodoRequestDto request) {
        if (repository.existsById(request.getIdPeriodo())) {
            throw new RuntimeException("Ya existe un ParametroPeriodo con ese ID");
        }
        ParametroPeriodo entity = modelMapper.map(request, ParametroPeriodo.class);
        repository.save(entity);
        return modelMapper.map(entity, ParametroPeriodoResponseDto.class);
    }

    @Override
    public ParametroPeriodoResponseDto actualizar(int id, ParametroPeriodoRequestDto request) {
        ParametroPeriodo entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ParametroPeriodo no encontrado"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, ParametroPeriodoResponseDto.class);
    }

    @Override
    public List<ParametroPeriodoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, ParametroPeriodoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ParametroPeriodoResponseDto obtenerPorId(int id) {
        ParametroPeriodo entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ParametroPeriodo no encontrado"));
        return modelMapper.map(entity, ParametroPeriodoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("ParametroPeriodo no encontrado");
        }
        repository.deleteById(id);
    }
}