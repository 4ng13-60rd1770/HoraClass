package co.edu.unbosque.horaclass.academy.service;

import co.edu.unbosque.horaclass.academy.dto.TipoDocumentoRequestDto;
import co.edu.unbosque.horaclass.academy.dto.TipoDocumentoResponseDto;

import java.util.List;

public interface TipoDocumentoService {
    TipoDocumentoResponseDto crear(TipoDocumentoRequestDto request);
    TipoDocumentoResponseDto actualizar(int id, TipoDocumentoRequestDto request);
    List<TipoDocumentoResponseDto> listarTodos();
    TipoDocumentoResponseDto obtenerPorId(int id);
    void eliminar(int id);
}
