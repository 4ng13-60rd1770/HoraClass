package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.PeriodoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.PeriodoResponseDto;
import co.edu.unbosque.horaclass.academy.model.Periodo;
import co.edu.unbosque.horaclass.academy.repository.PeriodoRepository;
import co.edu.unbosque.horaclass.academy.service.PeriodoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PeriodoServiceImpl implements PeriodoService {

    private final PeriodoRepository repository;
    private final ModelMapper modelMapper;

    public PeriodoServiceImpl(PeriodoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PeriodoResponseDto crear(PeriodoRequestDto request) {
        if (repository.existsById(request.getIdPeriodo())) {
            throw new RuntimeException("Ya existe un Periodo con ese ID");
        }
        Periodo entity = modelMapper.map(request, Periodo.class);
        repository.save(entity);
        return modelMapper.map(entity, PeriodoResponseDto.class);
    }

    @Override
    public PeriodoResponseDto actualizar(int id, PeriodoRequestDto request) {
        Periodo entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Periodo no encontrado"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, PeriodoResponseDto.class);
    }

    @Override
    public List<PeriodoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, PeriodoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PeriodoResponseDto obtenerPorId(int id) {
        Periodo entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Periodo no encontrado"));
        return modelMapper.map(entity, PeriodoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Periodo no encontrado");
        }
        repository.deleteById(id);
    }
}