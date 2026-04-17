package co.edu.unbosque.horaclass.academy.service.impl;

import co.edu.unbosque.horaclass.academy.dto.CargoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.CargoResponseDto;
import co.edu.unbosque.horaclass.academy.model.Cargo;
import co.edu.unbosque.horaclass.academy.repository.CargoRepository;
import co.edu.unbosque.horaclass.academy.service.CargoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CargoServiceImpl implements CargoService {

    private final CargoRepository repository;
    private final ModelMapper modelMapper;

    public CargoServiceImpl(CargoRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CargoResponseDto crear(CargoRequestDto request) {
        if (repository.existsById(request.getIdCargo())) {
            throw new RuntimeException("Ya existe un Cargo con ese ID");
        }
        Cargo entity = modelMapper.map(request, Cargo.class);
        repository.save(entity);
        return modelMapper.map(entity, CargoResponseDto.class);
    }

    @Override
    public CargoResponseDto actualizar(int id, CargoRequestDto request) {
        Cargo entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado"));

        entity.setNombre(request.getNombre());
        repository.save(entity);

        return modelMapper.map(entity, CargoResponseDto.class);
    }

    @Override
    public List<CargoResponseDto> listarTodos() {
        return repository.findAll().stream()
                .map(entity -> modelMapper.map(entity, CargoResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CargoResponseDto obtenerPorId(int id) {
        Cargo entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado"));
        return modelMapper.map(entity, CargoResponseDto.class);
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Cargo no encontrado");
        }
        repository.deleteById(id);
    }
}