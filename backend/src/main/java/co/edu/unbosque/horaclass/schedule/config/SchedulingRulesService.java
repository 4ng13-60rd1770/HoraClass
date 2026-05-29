package co.edu.unbosque.horaclass.schedule.config;

import co.edu.unbosque.horaclass.schedule.config.dto.SchedulingRulesDto;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
public class SchedulingRulesService {

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("H:mm");

    private final SchedulingProperties properties;

    public SchedulingRulesService(SchedulingProperties properties) {
        this.properties = properties;
    }

    public SchedulingRulesDto obtenerReglas() {
        return toDto(properties);
    }

    public SchedulingRulesDto actualizarReglas(SchedulingRulesDto dto) {
        validar(dto);
        apply(dto, properties);
        return toDto(properties);
    }

    public SchedulingRulesDto restablecerDefecto() {
        SchedulingRulesDto defaults = defaultsDto();
        apply(defaults, properties);
        return toDto(properties);
    }

    private void validar(SchedulingRulesDto dto) {
        if (dto.getGrupoCupoBase() < 1) {
            throw new IllegalArgumentException("El cupo base debe ser al menos 1.");
        }
        if (dto.getGrupoCupoToleranciaPorcentaje() < 0 || dto.getGrupoCupoToleranciaPorcentaje() > 100) {
            throw new IllegalArgumentException("La tolerancia debe estar entre 0 y 100%.");
        }
        if (dto.getGrupoCupoMinimo() < 1) {
            throw new IllegalArgumentException("El cupo mínimo debe ser al menos 1.");
        }
        if (dto.getSesionesSemanalesMin() < 1) {
            throw new IllegalArgumentException("Las sesiones mínimas deben ser al menos 1.");
        }
        if (dto.getSesionesSemanalesMax() < dto.getSesionesSemanalesMin()) {
            throw new IllegalArgumentException("Las sesiones máximas no pueden ser menores que las mínimas.");
        }
        if (dto.getDuracionSesionHoras() < 1) {
            throw new IllegalArgumentException("La duración de sesión debe ser al menos 1 hora.");
        }
        validarVentana("Lunes a viernes", dto.getHoraInicioSemana(), dto.getHoraFinSemana());
        validarVentana("Sábado", dto.getHoraInicioSabado(), dto.getHoraFinSabado());
        validarVentana("Almuerzo", dto.getHoraInicioAlmuerzo(), dto.getHoraFinAlmuerzo());
        if (dto.getCargaHorasTiempoCompleto() < 1
                || dto.getCargaHorasTresCuartos() < 1
                || dto.getCargaHorasMedioTiempo() < 1
                || dto.getCargaHorasCuartoTiempo() < 1) {
            throw new IllegalArgumentException("Todas las cargas horarias deben ser mayores a 0.");
        }
    }

    private void validarVentana(String label, String inicio, String fin) {
        LocalTime start = parseTime(inicio, label + " (inicio)");
        LocalTime end = parseTime(fin, label + " (fin)");
        if (!end.isAfter(start)) {
            throw new IllegalArgumentException("En " + label + ", la hora fin debe ser posterior a la hora inicio.");
        }
    }

    private LocalTime parseTime(String value, String field) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("El campo " + field + " es obligatorio.");
        }
        try {
            return LocalTime.parse(value.trim(), TIME_FMT);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("Formato de hora inválido en " + field + " (use HH:mm).");
        }
    }

    private SchedulingRulesDto defaultsDto() {
        SchedulingRulesDto dto = new SchedulingRulesDto();
        dto.setGrupoCupoBase(40);
        dto.setGrupoCupoToleranciaPorcentaje(10);
        dto.setGrupoCupoMinimo(10);
        dto.setSesionesSemanalesMin(1);
        dto.setSesionesSemanalesMax(4);
        dto.setDuracionSesionHoras(2);
        dto.setHoraInicioSemana("07:00");
        dto.setHoraFinSemana("22:00");
        dto.setHoraInicioSabado("07:00");
        dto.setHoraFinSabado("13:00");
        dto.setHoraInicioAlmuerzo("12:00");
        dto.setHoraFinAlmuerzo("13:00");
        dto.setCargaHorasTiempoCompleto(20);
        dto.setCargaHorasTresCuartos(15);
        dto.setCargaHorasMedioTiempo(10);
        dto.setCargaHorasCuartoTiempo(5);
        return dto;
    }

    private void apply(SchedulingRulesDto dto, SchedulingProperties target) {
        target.setGrupoCupoBase(dto.getGrupoCupoBase());
        target.setGrupoCupoToleranciaPorcentaje(dto.getGrupoCupoToleranciaPorcentaje());
        target.setGrupoCupoMinimo(dto.getGrupoCupoMinimo());
        target.setSesionesSemanalesMin(dto.getSesionesSemanalesMin());
        target.setSesionesSemanalesMax(dto.getSesionesSemanalesMax());
        target.setDuracionSesionHoras(dto.getDuracionSesionHoras());
        target.setHoraInicioSemana(dto.getHoraInicioSemana());
        target.setHoraFinSemana(dto.getHoraFinSemana());
        target.setHoraInicioSabado(dto.getHoraInicioSabado());
        target.setHoraFinSabado(dto.getHoraFinSabado());
        target.setHoraInicioAlmuerzo(dto.getHoraInicioAlmuerzo());
        target.setHoraFinAlmuerzo(dto.getHoraFinAlmuerzo());
        target.setCargaHorasTiempoCompleto(dto.getCargaHorasTiempoCompleto());
        target.setCargaHorasTresCuartos(dto.getCargaHorasTresCuartos());
        target.setCargaHorasMedioTiempo(dto.getCargaHorasMedioTiempo());
        target.setCargaHorasCuartoTiempo(dto.getCargaHorasCuartoTiempo());
    }

    private SchedulingRulesDto toDto(SchedulingProperties source) {
        SchedulingRulesDto dto = new SchedulingRulesDto();
        dto.setGrupoCupoBase(source.getGrupoCupoBase());
        dto.setGrupoCupoToleranciaPorcentaje(source.getGrupoCupoToleranciaPorcentaje());
        dto.setGrupoCupoMinimo(source.getGrupoCupoMinimo());
        dto.setSesionesSemanalesMin(source.getSesionesSemanalesMin());
        dto.setSesionesSemanalesMax(source.getSesionesSemanalesMax());
        dto.setDuracionSesionHoras(source.getDuracionSesionHoras());
        dto.setHoraInicioSemana(source.getHoraInicioSemana());
        dto.setHoraFinSemana(source.getHoraFinSemana());
        dto.setHoraInicioSabado(source.getHoraInicioSabado());
        dto.setHoraFinSabado(source.getHoraFinSabado());
        dto.setHoraInicioAlmuerzo(source.getHoraInicioAlmuerzo());
        dto.setHoraFinAlmuerzo(source.getHoraFinAlmuerzo());
        dto.setCargaHorasTiempoCompleto(source.getCargaHorasTiempoCompleto());
        dto.setCargaHorasTresCuartos(source.getCargaHorasTresCuartos());
        dto.setCargaHorasMedioTiempo(source.getCargaHorasMedioTiempo());
        dto.setCargaHorasCuartoTiempo(source.getCargaHorasCuartoTiempo());
        return dto;
    }
}
