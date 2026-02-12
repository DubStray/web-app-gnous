import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuditService } from '../services/audit.service';
import { AuditLog, AuditLogEventType } from '../models/models';

@Component({
  selector: 'app-logs',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './logs.component.html',
  styleUrl: './logs.component.css',
})
export class LogsComponent implements OnInit {
  logs = signal<AuditLog[]>([]);
  selectedEventType: AuditLogEventType | null = null;

  eventTypes = [
    { label: 'All Events', value: null },
    { label: 'Task Created', value: AuditLogEventType.TASK_CREATED },
    { label: 'Task Updated', value: AuditLogEventType.TASK_UPDATED },
    { label: 'Task Status Changed', value: AuditLogEventType.TASK_STATUS_CHANGED },
    { label: 'Task Deleted', value: AuditLogEventType.TASK_DELETED },
    { label: 'Wallet Credit', value: AuditLogEventType.WALLET_CREDIT },
    { label: 'Wallet Debit', value: AuditLogEventType.WALLET_DEBIT },
  ];

  constructor(private auditService: AuditService) {}

  ngOnInit(): void {
    this.loadLogs();
  }

  loadLogs(): void {
    if (this.selectedEventType) {
      this.auditService.getLogsByType(this.selectedEventType).subscribe({
        next: (data) => {
          this.logs.set(data);
        },
        error: (err) => {
          console.error('Error loading logs:', err);
        },
      });
    } else {
      this.auditService.getLogs().subscribe({
        next: (data) => {
          this.logs.set(data);
        },
        error: (err) => {
          console.error('Error loading logs:', err);
        },
      });
    }
  }

  onFilterChange(): void {
    this.loadLogs();
  }

  getEventClass(eventType: AuditLogEventType): string {
    switch (eventType) {
      case AuditLogEventType.TASK_CREATED:
        return 'badge-success';
      case AuditLogEventType.TASK_UPDATED:
        return 'badge-info';
      case AuditLogEventType.TASK_STATUS_CHANGED:
        return 'badge-warning';
      case AuditLogEventType.TASK_DELETED:
        return 'badge-danger';
      case AuditLogEventType.WALLET_CREDIT:
        return 'badge-success';
      case AuditLogEventType.WALLET_DEBIT:
        return 'badge-danger';
      default:
        return 'badge-secondary';
    }
  }

  getWalletDeltaClass(delta: number): string {
    if (delta > 0) return 'text-green-600';
    if (delta < 0) return 'text-red-600';
    return 'text-gray-500';
  }
}
