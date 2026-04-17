package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.SesionRequestDto;
import co.edu.unbosque.horaclass.academy.dto.SesionResponseDto;
import co.edu.unbosque.horaclass.academy.model.Sesion;
import co.edu.unbosque.horaclass.academy.repository.SesionRepository;
import co.edu.unbosque.horaclass.academy.service.SesionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SesionServiceImpl implements SesionService {

    private final SesionRepository repository;
    private final ModelMapper modelMapper;

    public SesionServiceImpl(SesionRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public SesionResponseDto crear(SesionRequestDto request) {
        if (repository.existsById(request.getIdSesion())) {
            throw new RuntimeException("Ya existe Sesion");
        }
        Sesion entity = modelMapper.map(request, Sesion.class);
        repository.save(entity);
        return modelMapper.map(entity, SesionResponseDto.class);
    }

    @Override
    public SesionResponseDto actualizar(int id, SesionRequestDto request) {
        Sesion entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesion no encontrada"));

        modelMapper.map(request, entity);
        repository.save(entity);

        return modelMapper.map(entity, SesionResponseDto.class);
    }

    @Override
    public List<SesionResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(e -> modelMapper.map(e, SesionResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public SesionResponseDto obtenerPorId(int id) {
        Sesion entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sesion no encontrada"));
        return modelMapper.map(entity, SesionResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Sesion no encontrada");
        }
        repository.deleteById(id);
    }
}