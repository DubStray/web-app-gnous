import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TaskService } from '../services/task.service';
import { WalletService } from '../services/wallet.service';
import { Task, TaskStatus, UpdateTaskStatus, UpdateTask, TaskPriority } from '../models/models';

@Component({
  selector: 'app-task',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './task.component.html',
  styleUrl: './task.component.css',
})
export class TaskComponent implements OnInit {
  tasks = signal<Task[]>([]);

  editDialogVisible = signal(false);
  selectedTask = signal<Task | null>(null);
  editedTask: UpdateTask = { title: '', description: '', priority: TaskPriority.MEDIUM };

  statusDialogVisible = signal(false);
  taskToUpdateStatus = signal<Task | null>(null);
  newStatus: TaskStatus = TaskStatus.TODO;

  createDialogVisible = signal(false);
  newTask: any = { title: '', description: '', status: TaskStatus.TODO, priority: 'MEDIUM' };

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

  ngOnInit() {
    this.loadTasks();
  }

  loadTasks() {
    this.taskService.getAllTasks().subscribe({
      next: (data) => {
        this.tasks.set(data);
      },
      error: (err) => console.error('Error loading tasks', err),
    });
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

  deleteTask(task: Task) {
    this.taskService.deleteTask(task.id).subscribe({
      next: () => {
        this.loadTasks();
        this.walletService.refreshBalance();
      },
      error: (err) => console.error('Error deleting task', err),
    });
  }

  openCreateDialog() {
    this.newTask = { title: '', description: '', status: TaskStatus.TODO, priority: 'MEDIUM' };
    this.createDialogVisible.set(true);
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
