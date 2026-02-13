import { Component, OnInit, inject, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { TaskService } from '../services/task.service';
import { WalletService } from '../services/wallet.service';
import { Task, TaskStatus, UpdateTaskStatus, UpdateTask, TaskPriority } from '../models/models';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-task',
  standalone: true,
  imports: [CommonModule, FormsModule, ToastModule],
  providers: [MessageService],
  templateUrl: './task.component.html',
  styleUrl: './task.component.css',
})
export class TaskComponent implements OnInit {
  tasks = signal<Task[]>([]);
  currentPage = signal(1);
  pageSize = signal(10);

  paginatedTasks = computed(() => {
    const startIndex = (this.currentPage() - 1) * this.pageSize();
    return this.tasks().slice(startIndex, startIndex + this.pageSize());
  });

  totalPages = computed(() => Math.ceil(this.tasks().length / this.pageSize()));

  nextPage() {
    if (this.currentPage() < this.totalPages()) {
      this.currentPage.update((p) => p + 1);
    }
  }

  prevPage() {
    if (this.currentPage() > 1) {
      this.currentPage.update((p) => p - 1);
    }
  }

  editDialogVisible = signal(false);
  selectedTask = signal<Task | null>(null);
  editedTask: UpdateTask = { title: '', description: '', priority: TaskPriority.MEDIUM };

  statusDialogVisible = signal(false);
  taskToUpdateStatus = signal<Task | null>(null);
  newStatus: TaskStatus = TaskStatus.TODO;

  createDialogVisible = signal(false);
  newTask: { title: string; description: string; status: TaskStatus; priority: TaskPriority } = {
    title: '',
    description: '',
    status: TaskStatus.TODO,
    priority: TaskPriority.MEDIUM,
  };

  statusOptions = [
    { label: 'TODO', value: TaskStatus.TODO },
    { label: 'DOING', value: TaskStatus.DOING },
    { label: 'DONE', value: TaskStatus.DONE },
  ];

  priorityOptions = [
    { label: 'LOW', value: 'LOW' },
    { label: 'MEDIUM', value: 'MEDIUM' },
    { label: 'HIGH', value: 'HIGH' },
  ];

  private taskService = inject(TaskService);
  private walletService = inject(WalletService);
  private messageService = inject(MessageService);

  ngOnInit() {
    this.loadTasks();
  }

  openCreateDialog() {
    console.log('Current balance:', this.walletService.balance());
    if (this.walletService.balance() <= 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Insufficient Funds',
        detail: 'You need at least 1 credit to create a task.',
        life: 3000,
      });
      return;
    }
    this.newTask = { title: '', description: '', status: TaskStatus.TODO, priority: TaskPriority.MEDIUM };
    this.createDialogVisible.set(true);
  }

  currentSort: 'STATUS' | 'PRIORITY' | 'DATE' | null = null;
  isAscending = true;

  loadTasks() {
    this.taskService.getAllTasks().subscribe({
      next: (data) => {
        this.tasks.set(data);
        this.currentSort = null;
        this.currentPage.set(1);
      },
      error: (err) => console.error('Error loading tasks', err),
    });
  }

  private sortTasks(type: 'STATUS' | 'PRIORITY' | 'DATE', source$: Observable<Task[]>) {
    if (this.currentSort === type) {
      this.tasks.update((tasks) => [...tasks].reverse());
      this.isAscending = !this.isAscending;
    } else {
      source$.subscribe({
        next: (data) => {
          this.tasks.set(data);
          this.currentSort = type;
          this.isAscending = true;
          this.currentPage.set(1);
        },
        error: (err) => console.error(`Error ordering by ${type}`, err),
      });
    }
  }

  orderByStatus() {
    this.sortTasks('STATUS', this.taskService.getTasksByStatus());
  }

  orderByPriority() {
    this.sortTasks('PRIORITY', this.taskService.getTasksByPriority());
  }

  orderByDate() {
    this.sortTasks('DATE', this.taskService.getTasksByDate());
  }



  getPriorityClass(priority: string): string {
    if (priority === 'HIGH') return 'badge-danger';
    if (priority === 'MEDIUM') return 'badge-warning';
    return 'badge-info';
  }

  getStatusClass(status: string): string {
    if (status === 'DONE') return 'badge-success';
    if (status === 'DOING') return 'badge-info';
    return 'badge-secondary';
  }

  openEditDialog(task: Task) {
    this.selectedTask.set(task);
    this.editedTask = {
      title: task.title,
      description: task.description,
      priority: task.priority,
      status: task.status,
    };
    this.editDialogVisible.set(true);
  }

  saveTask() {
    const task = this.selectedTask();
    if (!task) return;

    this.taskService.updateTask(task.id, this.editedTask).subscribe({
      next: () => {
        this.loadTasks();
        this.editDialogVisible.set(false);
      },
      error: (err) => console.error('Error updating task', err),
    });
  }

  openStatusDialog(task: Task) {
    this.taskToUpdateStatus.set(task);
    this.newStatus = task.status;
    this.statusDialogVisible.set(true);
  }

  confirmStatusChange() {
    const task = this.taskToUpdateStatus();
    if (!task) return;

    const updatePayload: UpdateTaskStatus = { status: this.newStatus };
    this.taskService.updateTaskStatus(task.id, updatePayload).subscribe({
      next: () => {
        this.loadTasks();
        this.walletService.refreshBalance();
        this.statusDialogVisible.set(false);
      },
      error: (err) => console.error('Error updating status', err),
    });
  }

  deleteDialogVisible = signal(false);
  taskToDelete = signal<Task | null>(null);

  openDeleteDialog(task: Task) {
    this.taskToDelete.set(task);
    this.deleteDialogVisible.set(true);
  }

  confirmDelete() {
    const task = this.taskToDelete();
    if (!task) return;

    this.taskService.deleteTask(task.id).subscribe({
      next: () => {
        this.loadTasks();
        this.walletService.refreshBalance();
        this.deleteDialogVisible.set(false);
        this.taskToDelete.set(null);
      },
      error: (err) => console.error('Error deleting task', err),
    });
  }



  saveNewTask() {
    this.taskService.createTask(this.newTask).subscribe({
      next: () => {
        this.loadTasks();
        this.walletService.refreshBalance();
        this.createDialogVisible.set(false);
      },
      error: (err) => console.error('Error creating task', err),
    });
  }
}
