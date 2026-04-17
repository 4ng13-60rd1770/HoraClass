package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.EdificioRequestDto;
import co.edu.unbosque.horaclass.academy.dto.EdificioResponseDto;
import co.edu.unbosque.horaclass.academy.model.Edificio;
import co.edu.unbosque.horaclass.academy.repository.EdificioRepository;
import co.edu.unbosque.horaclass.academy.service.EdificioService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EdificioServiceImpl implements EdificioService {

    private final EdificioRepository repository;
    private final ModelMapper modelMapper;

    public EdificioServiceImpl(EdificioRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EdificioResponseDto crear(EdificioRequestDto request) {
        if (repository.existsById(request.getIdEdificio())) {
            throw new RuntimeException("Ya existe un Edificio con ese ID");
        }
        Edificio entity = modelMapper.map(request, Edificio.class);
        repository.save(entity);
        return modelMapper.map(entity, EdificioResponseDto.class);
    }

    @Override
    public EdificioResponseDto actualizar(int id, EdificioRequestDto request) {
        Edificio entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edificio no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, EdificioResponseDto.class);
    }

    @Override
    public List<EdificioResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, EdificioResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public EdificioResponseDto obtenerPorId(int id) {
        Edificio entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Edificio no encontrado"));
        return modelMapper.map(entity, EdificioResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Edificio no encontrado");
        }
        repository.deleteById(id);
    }
}