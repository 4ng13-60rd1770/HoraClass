package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.DepartamentoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.DepartamentoResponseDto;
import co.edu.unbosque.horaclass.academy.model.Departamento;
import co.edu.unbosque.horaclass.academy.repository.DepartamentoRepository;
import co.edu.unbosque.horaclass.academy.service.DepartamentoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartamentoServiceImpl implements DepartamentoService {

    private final DepartamentoRepository repository;
    private final ModelMapper modelMapper;

    public DepartamentoServiceImpl(DepartamentoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public DepartamentoResponseDto crear(DepartamentoRequestDto request) {
        if (repository.existsById(request.getIdDepartamento())) {
            throw new RuntimeException("Ya existe un Departamento con ese ID");
        }
        Departamento entity = modelMapper.map(request, Departamento.class);
        repository.save(entity);
        return modelMapper.map(entity, DepartamentoResponseDto.class);
    }

    @Override
    public DepartamentoResponseDto actualizar(int id, DepartamentoRequestDto request) {
        Departamento entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, DepartamentoResponseDto.class);
    }

    @Override
    public List<DepartamentoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, DepartamentoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public DepartamentoResponseDto obtenerPorId(int id) {
        Departamento entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Departamento no encontrado"));
        return modelMapper.map(entity, DepartamentoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Departamento no encontrado");
        }
        repository.deleteById(id);
    }
}