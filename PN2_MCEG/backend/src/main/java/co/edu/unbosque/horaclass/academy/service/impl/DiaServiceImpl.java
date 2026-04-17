package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.DiaRequestDto;
import co.edu.unbosque.horaclass.academy.dto.DiaResponseDto;
import co.edu.unbosque.horaclass.academy.model.Dia;
import co.edu.unbosque.horaclass.academy.repository.DiaRepository;
import co.edu.unbosque.horaclass.academy.service.DiaService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DiaServiceImpl implements DiaService {

    private final DiaRepository repository;
    private final ModelMapper modelMapper;

    public DiaServiceImpl(DiaRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DiaResponseDto crear(DiaRequestDto request) {
        if (repository.existsById(request.getIdDia())) {
            throw new RuntimeException("Ya existe un Dia con ese ID");
        }
        Dia entity = modelMapper.map(request, Dia.class);
        repository.save(entity);
        return modelMapper.map(entity, DiaResponseDto.class);
    }

    @Override
    public DiaResponseDto actualizar(int id, DiaRequestDto request) {
        Dia entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dia no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, DiaResponseDto.class);
    }

    @Override
    public List<DiaResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, DiaResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DiaResponseDto obtenerPorId(int id) {
        Dia entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Dia no encontrado"));
        return modelMapper.map(entity, DiaResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Dia no encontrado");
        }
        repository.deleteById(id);
    }
}