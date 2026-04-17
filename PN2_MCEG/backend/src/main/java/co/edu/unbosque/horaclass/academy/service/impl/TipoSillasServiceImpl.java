package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.TipoSillasRequestDto;
import co.edu.unbosque.horaclass.academy.dto.TipoSillasResponseDto;
import co.edu.unbosque.horaclass.academy.model.TipoSillas;
import co.edu.unbosque.horaclass.academy.repository.TipoSillasRepository;
import co.edu.unbosque.horaclass.academy.service.TipoSillasService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TipoSillasServiceImpl implements TipoSillasService {

    private final TipoSillasRepository repository;
    private final ModelMapper modelMapper;

    public TipoSillasServiceImpl(TipoSillasRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TipoSillasResponseDto crear(TipoSillasRequestDto request) {
        if (repository.existsById(request.getIdTipoSillas())) {
            throw new RuntimeException("Ya existe TipoSillas");
        }
        TipoSillas entity = modelMapper.map(request, TipoSillas.class);
        repository.save(entity);
        return modelMapper.map(entity, TipoSillasResponseDto.class);
    }

    @Override
    public TipoSillasResponseDto actualizar(int id, TipoSillasRequestDto request) {
        TipoSillas entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoSillas no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, TipoSillasResponseDto.class);
    }

    @Override
    public List<TipoSillasResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(e -> modelMapper.map(e, TipoSillasResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TipoSillasResponseDto obtenerPorId(int id) {
        TipoSillas entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("TipoSillas no encontrado"));
        return modelMapper.map(entity, TipoSillasResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("TipoSillas no encontrado");
        }
        repository.deleteById(id);
    }
}