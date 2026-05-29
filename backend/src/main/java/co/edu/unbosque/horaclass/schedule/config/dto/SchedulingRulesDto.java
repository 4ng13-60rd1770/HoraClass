package co.edu.unbosque.horaclass.schedule.config.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SchedulingRulesDto {

    private int grupoCupoBase;
    private int grupoCupoToleranciaPorcentaje;
    private int grupoCupoMinimo;
    private int sesionesSemanalesMin;
    private int sesionesSemanalesMax;
    private int duracionSesionHoras;
    private String horaInicioSemana;
    private String horaFinSemana;
    private String horaInicioSabado;
    private String horaFinSabado;
    private String horaInicioAlmuerzo;
    private String horaFinAlmuerzo;
    private int cargaHorasTiempoCompleto;
    private int cargaHorasTresCuartos;
    private int cargaHorasMedioTiempo;
    private int cargaHorasCuartoTiempo;
}
