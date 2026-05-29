package co.edu.unbosque.horaclass.schedule.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.scheduling")
public class SchedulingProperties {

    private int grupoCupoBase = 40;
    private int grupoCupoToleranciaPorcentaje = 10;
    private int grupoCupoMinimo = 10;
    private int sesionesSemanalesMin = 1;
    private int sesionesSemanalesMax = 4;
    private int duracionSesionHoras = 2;

    private String horaInicioSemana = "07:00";
    private String horaFinSemana = "22:00";
    private String horaInicioSabado = "07:00";
    private String horaFinSabado = "13:00";
    private String horaInicioAlmuerzo = "12:00";
    private String horaFinAlmuerzo = "13:00";

    private int cargaHorasTiempoCompleto = 20;
    private int cargaHorasTresCuartos = 15;
    private int cargaHorasMedioTiempo = 10;
    private int cargaHorasCuartoTiempo = 5;

    public int getCupoMaximoPermitido() {
        return grupoCupoBase + (grupoCupoBase * grupoCupoToleranciaPorcentaje / 100);
    }
}
