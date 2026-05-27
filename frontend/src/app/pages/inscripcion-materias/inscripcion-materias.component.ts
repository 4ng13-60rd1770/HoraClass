import { ChangeDetectorRef, Component, OnDestroy, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Subscription } from 'rxjs';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { StatsRowComponent, StatData } from '../../organisms/stats-row/stats-row.component';
import { MateriasService, MateriaShared } from '../../core/services/materias.service';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-inscripcion-materias',
  standalone: true,
  imports: [CommonModule, FormsModule, TopbarComponent, StatsRowComponent],
  templateUrl: './inscripcion-materias.component.html',
  styleUrls: ['./inscripcion-materias.component.scss']
})
export class InscripcionMateriasComponent implements OnInit, OnDestroy {

  username = '';
  filtroCarrera = '';
  materias: MateriaShared[] = [];
  private sub = new Subscription();

  stats: StatData[] = [
    { label: 'Materias Asignadas',   value: '0', subtitle: 'Como docente',          hasDonut: true, donutPercent: 0 },
    { label: 'Materias Disponibles', value: '0', subtitle: 'Sin docente asignado' }
  ];

  modalConfirmar: MateriaShared | null = null;
  modalLiberar: MateriaShared | null = null;

  constructor(
    public svc: MateriasService,
    private auth: AuthService,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.username = this.auth.getUser()?.username ?? 'Docente';
    this.sub.add(
      this.svc.materias$.subscribe(list => {
        this.materias = list;
        this.actualizarStats();
        this.cdr.detectChanges();
      })
    );
  }

  ngOnDestroy() { this.sub.unsubscribe(); }

  get carreras(): string[] {
    return [...new Set(this.materias.map(m => m.carrera).filter(Boolean))];
  }

  get materiasFiltradas(): MateriaShared[] {
    return this.filtroCarrera
      ? this.materias.filter(m => m.carrera === this.filtroCarrera)
      : this.materias;
  }

  get misAsignadas(): MateriaShared[] {
    return this.materias.filter(m => m.docente === this.username);
  }

  get disponibles(): MateriaShared[] {
    return this.materias.filter(m => !m.docente);
  }

  esMio(m: MateriaShared): boolean { return m.docente === this.username; }
  tieneDotro(m: MateriaShared): boolean { return !!m.docente && m.docente !== this.username; }
  progreso(m: MateriaShared): number {
    return m.cupoMax ? Math.min(100, Math.round((m.inscritos / m.cupoMax) * 100)) : 0;
  }

  abrirConfirmar(m: MateriaShared) { this.modalConfirmar = m; }
  abrirLiberar(m: MateriaShared)   { this.modalLiberar = m; }
  cerrarModales() { this.modalConfirmar = null; this.modalLiberar = null; }

  confirmarInscripcion() {
    if (!this.modalConfirmar) return;
    const id = this.modalConfirmar.id;
    this.modalConfirmar = null;
    // Persiste en BD vía PATCH /api/materias/{id}/inscribir-docente
    this.svc.asignarDocente(id).subscribe({
      next: () => this.cdr.detectChanges(),
      error: () => alert('Error al asignarse la materia. Inténtalo de nuevo.')
    });
  }

  confirmarLiberar() {
    if (!this.modalLiberar) return;
    const id = this.modalLiberar.id;
    this.modalLiberar = null;
    // Persiste en BD vía PATCH /api/materias/{id}/desinscribir-docente
    this.svc.liberarDocente(id).subscribe({
      next: () => this.cdr.detectChanges(),
      error: () => alert('Error al liberar la materia. Inténtalo de nuevo.')
    });
  }

  private actualizarStats() {
    const asignadas   = this.misAsignadas.length;
    const disponibles = this.disponibles.length;
    const total       = this.materias.length;
    const pct = total > 0 ? Math.round((asignadas / total) * 100) : 0;
    this.stats = [
      { label: 'Materias Asignadas',   value: asignadas.toString(),   subtitle: 'Como docente',          hasDonut: true, donutPercent: pct },
      { label: 'Materias Disponibles', value: disponibles.toString(), subtitle: 'Sin docente asignado' }
    ];
  }
}
