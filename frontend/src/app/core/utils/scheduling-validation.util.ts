import { SchedulingRules, cupoMaximoPermitido } from '../models/scheduling.models';

const DIAS_SEMANA = ['LUNES', 'MARTES', 'MIERCOLES', 'JUEVES', 'VIERNES'];
const DIAS_LABEL = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes', 'Sábado'];

export function parseTimeToMinutes(time: string): number {
  const [h, m] = time.split(':').map(Number);
  return h * 60 + (m || 0);
}

export function formatMinutes(minutes: number): string {
  const h = Math.floor(minutes / 60);
  const m = minutes % 60;
  return `${String(h).padStart(2, '0')}:${String(m).padStart(2, '0')}`;
}

export function addHoursToTime(time: string, hours: number): string {
  return formatMinutes(parseTimeToMinutes(time) + hours * 60);
}

function overlaps(aStart: number, aEnd: number, bStart: number, bEnd: number): boolean {
  return aStart < bEnd && bStart < aEnd;
}

/** Valida docente: vinculación, carga horaria y cursos habilitados. */
export function validateTeacherForm(
  form: {
    cargaHoras?: number;
    tipoVinculacion?: string;
    cursosHabilitados?: number[];
  },
  reglas: SchedulingRules,
  totalCursosDisponibles: number,
): string | null {
  if (!form.tipoVinculacion) {
    return 'Selecciona el tipo de vinculación del docente.';
  }

  const cargaEsperada = getCargaEsperada(form.tipoVinculacion, reglas);
  const carga = form.cargaHoras && form.cargaHoras > 0 ? form.cargaHoras : cargaEsperada;

  if (carga <= 0) {
    return 'La carga horaria debe ser mayor a 0.';
  }

  if (carga > cargaEsperada) {
    return `La carga horaria no puede superar ${cargaEsperada} h/semana para ${labelVinculacionShort(form.tipoVinculacion)}.`;
  }

  if (totalCursosDisponibles > 0 && (form.cursosHabilitados?.length ?? 0) === 0) {
    return 'Selecciona al menos un curso que el docente esté habilitado a orientar.';
  }

  return null;
}

function getCargaEsperada(tipo: string, reglas: SchedulingRules): number {
  const map: Record<string, number> = {
    TIEMPO_COMPLETO: reglas.cargaHorasTiempoCompleto,
    TRES_CUARTOS: reglas.cargaHorasTresCuartos,
    MEDIO_TIEMPO: reglas.cargaHorasMedioTiempo,
    CUARTO_TIEMPO: reglas.cargaHorasCuartoTiempo,
  };
  return map[tipo] ?? 0;
}

function labelVinculacionShort(tipo: string): string {
  const labels: Record<string, string> = {
    TIEMPO_COMPLETO: 'tiempo completo',
    TRES_CUARTOS: 'tres cuartos de tiempo',
    MEDIO_TIEMPO: 'medio tiempo',
    CUARTO_TIEMPO: 'cuarto de tiempo',
  };
  return labels[tipo] ?? tipo;
}

/** Valida cupo de grupo/materia (máx. 40 + 10% tolerancia). */
export function validateGroupCapacity(cupoMax: number, reglas: SchedulingRules): string | null {
  if (!cupoMax || cupoMax < 1) {
    return 'El cupo debe ser al menos 1.';
  }

  if (cupoMax > cupoMaximoPermitido(reglas)) {
    return `El cupo máximo permitido es ${cupoMaximoPermitido(reglas)} (${reglas.grupoCupoBase} + ${reglas.grupoCupoToleranciaPorcentaje}% tolerancia).`;
  }

  return null;
}

/** Valida capacidad del aula: entre mínimo y máximo institucional (10–40 por defecto). */
export function validateClassroomCapacity(capacidad: number, reglas: SchedulingRules): string | null {
  if (!capacidad || capacidad < 1) {
    return 'La capacidad es obligatoria.';
  }

  if (capacidad < reglas.grupoCupoMinimo) {
    return `La capacidad mínima de un aula es ${reglas.grupoCupoMinimo} asientos.`;
  }

  if (capacidad > reglas.grupoCupoBase) {
    return `La capacidad máxima de un aula es ${reglas.grupoCupoBase} asientos.`;
  }

  return null;
}

/** Valida horario de materia: días, duración 2h y ventana horaria. */
export function validateMateriaHorario(
  dias: boolean[],
  inicio: string,
  fin: string,
  reglas: SchedulingRules,
): string | null {
  const diasSeleccionados = dias.filter(Boolean).length;

  if (diasSeleccionados < reglas.sesionesSemanalesMin) {
    return `Selecciona al menos ${reglas.sesionesSemanalesMin} día(s) de clase por semana.`;
  }

  if (diasSeleccionados > reglas.sesionesSemanalesMax) {
    return `Máximo ${reglas.sesionesSemanalesMax} sesiones semanales permitidas.`;
  }

  if (!inicio || !fin) {
    return 'Define hora inicio y hora fin de la sesión.';
  }

  const start = parseTimeToMinutes(inicio);
  const end = parseTimeToMinutes(fin);
  const expected = reglas.duracionSesionHoras * 60;

  if (end - start !== expected) {
    return `Cada sesión debe durar ${reglas.duracionSesionHoras} horas (ej. 08:00–10:00).`;
  }

  const dayStart = parseTimeToMinutes(reglas.horaInicioSemana);
  const dayEnd = parseTimeToMinutes(reglas.horaFinSemana);

  if (start < dayStart || end > dayEnd) {
    return `El horario debe estar entre ${reglas.horaInicioSemana} y ${reglas.horaFinSemana} (lun–vie).`;
  }

  const lunchStart = parseTimeToMinutes(reglas.horaInicioAlmuerzo);
  const lunchEnd = parseTimeToMinutes(reglas.horaFinAlmuerzo);
  if (overlaps(start, end, lunchStart, lunchEnd)) {
    return `No se programan clases entre ${reglas.horaInicioAlmuerzo} y ${reglas.horaFinAlmuerzo}.`;
  }

  return null;
}

/** Valida que el salón cubra el cupo y requisitos del curso. */
export function validateSalonParaCupo(
  capacidadSalon: number,
  cupoMax: number,
  requiereComputadores: boolean,
  requiereSillasMoviles: boolean,
  salon?: { tieneComputadores?: boolean; sillasMoviles?: boolean },
): string | null {
  if (salon && capacidadSalon < cupoMax) {
    return `El salón tiene ${capacidadSalon} asientos; el cupo es ${cupoMax}.`;
  }

  if (salon && requiereComputadores && !salon.tieneComputadores) {
    return 'Este curso requiere un aula con computadores.';
  }

  if (salon && requiereSillasMoviles && !salon.sillasMoviles) {
    return 'Este curso requiere sillas móviles para trabajo en grupo.';
  }

  return null;
}

/** Valida el formulario de parametrización de reglas. */
export function validateSchedulingRulesForm(reglas: SchedulingRules): string | null {
  if (reglas.grupoCupoBase < 1) {
    return 'El cupo base debe ser al menos 1.';
  }
  if (reglas.grupoCupoToleranciaPorcentaje < 0 || reglas.grupoCupoToleranciaPorcentaje > 100) {
    return 'La tolerancia debe estar entre 0 y 100%.';
  }
  if (reglas.sesionesSemanalesMin < 1) {
    return 'Las sesiones mínimas deben ser al menos 1.';
  }
  if (reglas.sesionesSemanalesMax < reglas.sesionesSemanalesMin) {
    return 'Las sesiones máximas no pueden ser menores que las mínimas.';
  }
  if (reglas.duracionSesionHoras < 1) {
    return 'La duración de sesión debe ser al menos 1 hora.';
  }
  const ventanas: [string, string, string][] = [
    ['Lunes a viernes', reglas.horaInicioSemana, reglas.horaFinSemana],
    ['Sábado', reglas.horaInicioSabado, reglas.horaFinSabado],
    ['Almuerzo', reglas.horaInicioAlmuerzo, reglas.horaFinAlmuerzo],
  ];
  for (const [label, ini, fin] of ventanas) {
    if (!ini || !fin) {
      return `Define hora inicio y fin para ${label}.`;
    }
    if (parseTimeToMinutes(fin) <= parseTimeToMinutes(ini)) {
      return `En ${label}, la hora fin debe ser posterior a la hora inicio.`;
    }
  }
  return null;
}

export function validateTeacherScheduleRestriction(
  horaInicio: string,
  restriccion?: string,
): string | null {
  if (!restriccion || restriccion === 'SIN_RESTRICCION') {
    return null;
  }

  const start = parseTimeToMinutes(horaInicio);
  const limit16 = parseTimeToMinutes('16:00');
  const limit18 = parseTimeToMinutes('18:00');

  switch (restriccion) {
    case 'SOLO_DIA':
      if (start >= limit16) {
        return 'Este docente solo puede dictar clases antes de las 4:00 PM.';
      }
      break;
    case 'DESPUES_16':
      if (start < limit16) {
        return 'Este docente solo puede dictar clases después de las 4:00 PM.';
      }
      break;
    case 'DESPUES_18':
      if (start < limit18) {
        return 'Este docente solo puede dictar clases después de las 6:00 PM.';
      }
      break;
  }

  return null;
}

export { DIAS_SEMANA, DIAS_LABEL };
