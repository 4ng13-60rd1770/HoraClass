import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TopbarComponent } from '../../organisms/topbar/topbar.component';
import { StatsRowComponent, StatData } from '../../organisms/stats-row/stats-row.component';
import { ClassroomsTableComponent, ClassroomRow } from '../../organisms/classrooms-table/classrooms-table.component';
import { BadgeVariant } from '../../atoms/badge/badge.component';
import { ClassroomService } from '../../core/services/classroom.service';
import { ClassroomRequest, ClassroomResponse } from '../../core/models/classroom.models';

@Component({
  selector: 'app-salones',
  standalone: true,
  imports: [CommonModule, FormsModule, TopbarComponent, StatsRowComponent, ClassroomsTableComponent],
  templateUrl: './salones.component.html',
  styleUrls: ['./salones.component.scss']
})
export class SalonesComponent implements OnInit {
  searchQuery = '';
  loading = false;
  error = '';

  stats: StatData[] = [
    { label: 'Total de Salones',     value: '—', subtitle: 'Registrados en el sistema', icon: 'assets/icons/building.png' },
    { label: 'Disponibilidad de Aula', value: '—', subtitle: 'Disponible',          icon: 'assets/icons/classroom.png' },
    { label: 'Capacidad Máxima',     value: '—', subtitle: 'Asientos máximos',      icon: 'assets/icons/graduation.png' }
  ];

  classrooms: ClassroomRow[] = [];
  private rawData: ClassroomResponse[] = [];

  modalAbierto = false;
  modalEditar  = false;
  guardando    = false;
  errorModal   = '';
  idEditando: number | null = null;

  form: ClassroomRequest = this.formVacio();
  formEdit: ClassroomRequest = this.formVacio();

  constructor(private classroomService: ClassroomService, private cdr: ChangeDetectorRef) {}

  ngOnInit() { this.cargarSalones(); }

  cargarSalones() {
    this.loading = true;
    this.classroomService.getAll().subscribe({
      next: (data) => {
        this.rawData = data;
        this.actualizarVista(data);
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'No se pudieron cargar los salones';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  private actualizarVista(data: ClassroomResponse[]) {
    const total = data.length;
    const disponibles = data.filter(s => s.disponible).length;
    const maxCap = total > 0 ? Math.max(...data.map(s => s.capacidad)) : 0;

    this.stats = [
      { label: 'Total de Salones',      value: total.toString(),              subtitle: 'Registrados en el sistema', icon: 'assets/icons/building.png' },
      { label: 'Disponibilidad de Aula', value: `${disponibles}/${total}`,   subtitle: 'Disponible',                icon: 'assets/icons/classroom.png' },
      { label: 'Capacidad Máxima',      value: maxCap.toString(),             subtitle: 'Asientos máximos',          icon: 'assets/icons/graduation.png' }
    ];

    this.classrooms = data.map(s => ({
      idSalon:       s.idSalon,
      salonId:       [s.edificio, s.nombre].filter(Boolean).join(' - '),
      department:    s.departamento ?? 'General',
      name:          s.tipo ?? s.nombre,
      capacity:      s.capacidad,
      status:        s.disponible ? 'Libre' : 'En Uso',
      statusVariant: (s.disponible ? 'libre' : 'en-uso') as BadgeVariant
    }));
  }

  // ── Crear ──────────────────────────────────────────────
  abrirModal() {
    this.form = this.formVacio();
    this.errorModal = '';
    this.modalAbierto = true;
  }

  cerrarModal() { this.modalAbierto = false; this.errorModal = ''; }

  guardar() {
    if (!this.form.nombre.trim() || !this.form.edificio.trim() || !this.form.capacidad) {
      this.errorModal = 'Nombre, edificio y capacidad son obligatorios.';
      return;
    }
    this.guardando = true;
    this.errorModal = '';
    this.classroomService.create(this.form).subscribe({
      next: () => {
        this.guardando = false;
        this.modalAbierto = false;
        this.cdr.detectChanges();
        this.cargarSalones();
      },
      error: (err) => {
        this.guardando = false;
        this.errorModal = err?.error?.message ?? 'Error al crear el salón.';
        this.cdr.detectChanges();
      }
    });
  }

  // ── Editar ─────────────────────────────────────────────
  abrirEditar(id: number) {
    const raw = this.rawData.find(s => s.idSalon === id);
    if (!raw) return;
    this.idEditando = id;
    this.formEdit = {
      nombre: raw.nombre, edificio: raw.edificio, capacidad: raw.capacidad,
      tipo: raw.tipo, departamento: raw.departamento, disponible: raw.disponible
    };
    this.errorModal = '';
    this.modalEditar = true;
  }

  cerrarEditar() { this.modalEditar = false; this.errorModal = ''; this.idEditando = null; }

  guardarEdicion() {
    if (!this.idEditando) return;
    this.guardando = true;
    this.classroomService.update(this.idEditando, this.formEdit).subscribe({
      next: () => {
        this.guardando = false;
        this.modalEditar = false;
        this.idEditando = null;
        this.cdr.detectChanges();
        this.cargarSalones();
      },
      error: (err) => {
        this.guardando = false;
        this.errorModal = err?.error?.message ?? 'Error al actualizar el salón.';
        this.cdr.detectChanges();
      }
    });
  }

  // ── Toggle disponibilidad ──────────────────────────────
  toggleDisponibilidad(id: number) {
    const raw = this.rawData.find(s => s.idSalon === id);
    if (!raw) return;
    const updated: ClassroomRequest = { ...raw, disponible: !raw.disponible };
    this.classroomService.update(id, updated).subscribe({
      next: () => { this.cdr.detectChanges(); this.cargarSalones(); },
      error: () => alert('Error al cambiar disponibilidad.')
    });
  }

  // ── Eliminar ───────────────────────────────────────────
  eliminar(id: number) {
    if (!confirm('¿Eliminar este salón permanentemente?')) return;
    this.classroomService.delete(id).subscribe({
      next: () => { this.cdr.detectChanges(); this.cargarSalones(); },
      error: () => alert('Error al eliminar el salón.')
    });
  }

  private formVacio(): ClassroomRequest {
    return { nombre: '', edificio: '', capacidad: 0, tipo: 'Aula', departamento: '', disponible: true };
  }
}
