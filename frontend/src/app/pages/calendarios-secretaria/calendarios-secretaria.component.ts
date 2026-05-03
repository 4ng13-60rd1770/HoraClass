import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';

interface ProfesorBloque {
  profesor: string;
  iniciales: string;
  color: string;
  colorLight: string;
  dia: number;       // 0=Lun ... 4=Vie
  horaInicio: number; // ej 9 = 9:00
  horaFin: number;
  materia: string;
  salon: string;
}

interface Profesor {
  nombre: string;
  iniciales: string;
  color: string;
  colorLight: string;
  departamento: string;
  horasAsignadas: number;
  horasMax: number;
}

@Component({
  selector: 'app-calendarios-secretaria',
  standalone: true,
  imports: [CommonModule, FormsModule, TopbarComponent],
  templateUrl: './calendarios-secretaria.component.html',
  styleUrls: ['./calendarios-secretaria.component.scss']
})
export class CalendariosSecretariaComponent {
  vistaActiva: 'semana' | 'dia' = 'semana';
  diaSeleccionado = 0;
  filtroDepto = 'Todos';
  departamentos = ['Todos', 'Matemáticas', 'Ciencias', 'Humanidades', 'Idiomas', 'Artes'];

  horas = [8,9,10,11,12,13,14,15,16,17,18];
  dias = ['Lunes', 'Martes', 'Miércoles', 'Jueves', 'Viernes'];

  profesores: Profesor[] = [
    { nombre: 'Dra. Elena Ramos',   iniciales: 'ER', color: '#7c3aed', colorLight: '#ede9fe', departamento: 'Ciencias',     horasAsignadas: 18, horasMax: 24 },
    { nombre: 'Lic. Marco Polo',    iniciales: 'MP', color: '#b45309', colorLight: '#fef3c7', departamento: 'Humanidades',  horasAsignadas: 22, horasMax: 24 },
    { nombre: 'Mtra. Sofía Vega',   iniciales: 'SV', color: '#0369a1', colorLight: '#e0f2fe', departamento: 'Matemáticas',  horasAsignadas: 12, horasMax: 24 },
    { nombre: 'Mtra. Clara Luz',    iniciales: 'CL', color: '#be185d', colorLight: '#fce7f3', departamento: 'Artes',        horasAsignadas: 16, horasMax: 24 },
    { nombre: 'Dr. Roberto Méndez', iniciales: 'RM', color: '#065f46', colorLight: '#d1fae5', departamento: 'Idiomas',      horasAsignadas: 20, horasMax: 24 },
    { nombre: 'Prof. Wilson Rojas',  iniciales: 'WR', color: '#9a3412', colorLight: '#ffedd5', departamento: 'Ciencias',    horasAsignadas: 14, horasMax: 24 },
  ];

  bloques: ProfesorBloque[] = [
    { profesor: 'Dra. Elena Ramos',   iniciales: 'ER', color: '#7c3aed', colorLight: '#ede9fe', dia: 0, horaInicio: 8,  horaFin: 10, materia: 'Física II',            salon: 'Lab GF-101' },
    { profesor: 'Dra. Elena Ramos',   iniciales: 'ER', color: '#7c3aed', colorLight: '#ede9fe', dia: 2, horaInicio: 8,  horaFin: 10, materia: 'Física II',            salon: 'Lab GF-101' },
    { profesor: 'Dra. Elena Ramos',   iniciales: 'ER', color: '#7c3aed', colorLight: '#ede9fe', dia: 4, horaInicio: 9,  horaFin: 11, materia: 'Seminario Inv.',       salon: 'M-103' },

    { profesor: 'Lic. Marco Polo',    iniciales: 'MP', color: '#b45309', colorLight: '#fef3c7', dia: 1, horaInicio: 10, horaFin: 12, materia: 'Historia Universal',   salon: 'I-201' },
    { profesor: 'Lic. Marco Polo',    iniciales: 'MP', color: '#b45309', colorLight: '#fef3c7', dia: 3, horaInicio: 10, horaFin: 12, materia: 'Historia Universal',   salon: 'I-201' },
    { profesor: 'Lic. Marco Polo',    iniciales: 'MP', color: '#b45309', colorLight: '#fef3c7', dia: 0, horaInicio: 14, horaFin: 16, materia: 'Ética',                salon: 'B-104' },

    { profesor: 'Mtra. Sofía Vega',   iniciales: 'SV', color: '#0369a1', colorLight: '#e0f2fe', dia: 0, horaInicio: 9,  horaFin: 11, materia: 'Álgebra Lineal',      salon: 'F-404' },
    { profesor: 'Mtra. Sofía Vega',   iniciales: 'SV', color: '#0369a1', colorLight: '#e0f2fe', dia: 2, horaInicio: 9,  horaFin: 11, materia: 'Álgebra Lineal',      salon: 'F-404' },
    { profesor: 'Mtra. Sofía Vega',   iniciales: 'SV', color: '#0369a1', colorLight: '#e0f2fe', dia: 4, horaInicio: 14, horaFin: 16, materia: 'Cálculo II',          salon: 'F-301' },

    { profesor: 'Mtra. Clara Luz',    iniciales: 'CL', color: '#be185d', colorLight: '#fce7f3', dia: 1, horaInicio: 8,  horaFin: 10, materia: 'Arte Pintura',         salon: 'A-101' },
    { profesor: 'Mtra. Clara Luz',    iniciales: 'CL', color: '#be185d', colorLight: '#fce7f3', dia: 3, horaInicio: 8,  horaFin: 10, materia: 'Arte Pintura',         salon: 'A-101' },
    { profesor: 'Mtra. Clara Luz',    iniciales: 'CL', color: '#be185d', colorLight: '#fce7f3', dia: 2, horaInicio: 15, horaFin: 17, materia: 'Arte Literatura',      salon: 'I-303' },

    { profesor: 'Dr. Roberto Méndez', iniciales: 'RM', color: '#065f46', colorLight: '#d1fae5', dia: 0, horaInicio: 11, horaFin: 13, materia: 'Alemán Avanzado',     salon: 'L-202' },
    { profesor: 'Dr. Roberto Méndez', iniciales: 'RM', color: '#065f46', colorLight: '#d1fae5', dia: 2, horaInicio: 11, horaFin: 13, materia: 'Alemán Avanzado',     salon: 'L-202' },
    { profesor: 'Dr. Roberto Méndez', iniciales: 'RM', color: '#065f46', colorLight: '#d1fae5', dia: 4, horaInicio: 11, horaFin: 13, materia: 'Francés I',           salon: 'L-101' },

    { profesor: 'Prof. Wilson Rojas',  iniciales: 'WR', color: '#9a3412', colorLight: '#ffedd5', dia: 1, horaInicio: 14, horaFin: 16, materia: 'Prog. Avanzada',      salon: 'Sist-301' },
    { profesor: 'Prof. Wilson Rojas',  iniciales: 'WR', color: '#9a3412', colorLight: '#ffedd5', dia: 3, horaInicio: 14, horaFin: 16, materia: 'Prog. Avanzada',      salon: 'Sist-301' },
    { profesor: 'Prof. Wilson Rojas',  iniciales: 'WR', color: '#9a3412', colorLight: '#ffedd5', dia: 0, horaInicio: 16, horaFin: 18, materia: 'Complejidad Alg.',    salon: 'Sist-201' },
  ];

  get profesoresFiltrados(): Profesor[] {
    if (this.filtroDepto === 'Todos') return this.profesores;
    return this.profesores.filter(p => p.departamento === this.filtroDepto);
  }

  bloquesEnCelda(dia: number, hora: number): ProfesorBloque[] {
    const profs = this.profesoresFiltrados.map(p => p.nombre);
    return this.bloques.filter(b =>
      b.dia === dia &&
      hora >= b.horaInicio &&
      hora < b.horaFin &&
      profs.includes(b.profesor)
    );
  }

  esPrimerHora(bloque: ProfesorBloque, hora: number): boolean {
    return bloque.horaInicio === hora;
  }

  duracion(bloque: ProfesorBloque): number {
    return bloque.horaFin - bloque.horaInicio;
  }

  estaLibre(profesor: Profesor, dia: number, hora: number): boolean {
    return !this.bloques.some(b =>
      b.profesor === profesor.nombre &&
      b.dia === dia &&
      hora >= b.horaInicio &&
      hora < b.horaFin
    );
  }

  disponibilidadHoy(dia: number): number {
    const profs = this.profesoresFiltrados;
    let libres = 0;
    profs.forEach(p => {
      const ocupado = this.horas.some(h => !this.estaLibre(p, dia, h));
      if (!ocupado) libres++;
    });
    return Math.round((libres / profs.length) * 100) || 0;
  }

  formatHora(h: number): string {
    return h <= 12 ? `${h}:00 AM` : `${h - 12}:00 PM`;
  }

  get totalDisponibles(): number {
    return this.profesoresFiltrados.filter(p =>
      !this.bloques.some(b => b.profesor === p.nombre && b.dia === this.diaSeleccionado &&
        this.horas.some(h => h >= b.horaInicio && h < b.horaFin))
    ).length;
  }
}
