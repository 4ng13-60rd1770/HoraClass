export interface SchedulingRules {
  grupoCupoBase: number;
  grupoCupoToleranciaPorcentaje: number;
  grupoCupoMinimo: number;
  sesionesSemanalesMin: number;
  sesionesSemanalesMax: number;
  duracionSesionHoras: number;
  horaInicioSemana: string;
  horaFinSemana: string;
  horaInicioSabado: string;
  horaFinSabado: string;
  horaInicioAlmuerzo: string;
  horaFinAlmuerzo: string;
  cargaHorasTiempoCompleto: number;
  cargaHorasTresCuartos: number;
  cargaHorasMedioTiempo: number;
  cargaHorasCuartoTiempo: number;
}

/** Valores por defecto según normativa UEB (parametrizables vía API). */
export const DEFAULT_SCHEDULING_RULES: SchedulingRules = {
  grupoCupoBase: 40,
  grupoCupoToleranciaPorcentaje: 10,
  grupoCupoMinimo: 10,
  sesionesSemanalesMin: 1,
  sesionesSemanalesMax: 4,
  duracionSesionHoras: 2,
  horaInicioSemana: '07:00',
  horaFinSemana: '22:00',
  horaInicioSabado: '07:00',
  horaFinSabado: '13:00',
  horaInicioAlmuerzo: '12:00',
  horaFinAlmuerzo: '13:00',
  cargaHorasTiempoCompleto: 20,
  cargaHorasTresCuartos: 15,
  cargaHorasMedioTiempo: 10,
  cargaHorasCuartoTiempo: 5,
};

export const TIPOS_VINCULACION = [
  { value: 'TIEMPO_COMPLETO', label: 'Tiempo completo' },
  { value: 'TRES_CUARTOS', label: 'Tres cuartos de tiempo' },
  { value: 'MEDIO_TIEMPO', label: 'Medio tiempo' },
  { value: 'CUARTO_TIEMPO', label: 'Cuarto de tiempo' },
] as const;

export const RESTRICCIONES_HORARIO = [
  { value: 'SIN_RESTRICCION', label: 'Sin restricción' },
  { value: 'SOLO_DIA', label: 'Solo horas del día (antes de las 4:00 PM)' },
  { value: 'DESPUES_16', label: 'Solo después de las 4:00 PM' },
  { value: 'DESPUES_18', label: 'Solo después de las 6:00 PM' },
] as const;

/** Escalafón docente (Estatuto Docente — Universidad El Bosque). */
export const ESCALAFONES = [
  { value: 'ASISTENTE', label: 'Asistente' },
  { value: 'ASOCIADO', label: 'Asociado' },
  { value: 'TITULAR', label: 'Titular' },
] as const;

export function cupoMaximoPermitido(reglas: SchedulingRules): number {
  return reglas.grupoCupoBase + Math.floor(reglas.grupoCupoBase * reglas.grupoCupoToleranciaPorcentaje / 100);
}

export function cargaHorasPorVinculacion(tipo: string, reglas: SchedulingRules): number {
  const map: Record<string, number> = {
    TIEMPO_COMPLETO: reglas.cargaHorasTiempoCompleto,
    TRES_CUARTOS: reglas.cargaHorasTresCuartos,
    MEDIO_TIEMPO: reglas.cargaHorasMedioTiempo,
    CUARTO_TIEMPO: reglas.cargaHorasCuartoTiempo,
  };
  return map[tipo] ?? 0;
}

export function labelVinculacion(value: string): string {
  return TIPOS_VINCULACION.find(v => v.value === value)?.label ?? value;
}

export function labelRestriccion(value: string): string {
  return RESTRICCIONES_HORARIO.find(r => r.value === value)?.label ?? value;
}

export function resumenReglas(reglas: SchedulingRules): string {
  return [
    `Grupos: máx. ${reglas.grupoCupoBase} estudiantes (+${reglas.grupoCupoToleranciaPorcentaje}% tolerancia = ${cupoMaximoPermitido(reglas)})`,
    `Sesiones: ${reglas.sesionesSemanalesMin}–${reglas.sesionesSemanalesMax} por semana de ${reglas.duracionSesionHoras} h`,
    `Lun–Vie ${reglas.horaInicioSemana}–${reglas.horaFinSemana} · Sáb ${reglas.horaInicioSabado}–${reglas.horaFinSabado}`,
    `Sin clases ${reglas.horaInicioAlmuerzo}–${reglas.horaFinAlmuerzo}`,
  ].join(' · ');
}
