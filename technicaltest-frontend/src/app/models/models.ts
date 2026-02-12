export enum AuditLogEventType {
  TASK_CREATED = 'TASK_CREATED',
  TASK_UPDATED = 'TASK_UPDATED',
  TASK_STATUS_CHANGED = 'TASK_STATUS_CHANGED',
  TASK_DELETED = 'TASK_DELETED',
  WALLET_DEBIT = 'WALLET_DEBIT',
  WALLET_CREDIT = 'WALLET_CREDIT',
}

export enum TaskPriority {
  LOW = 'LOW',
  MEDIUM = 'MEDIUM',
  HIGH = 'HIGH',
}

export enum TaskStatus {
  TODO = 'TODO',
  DOING = 'DOING',
  DONE = 'DONE',
}

export interface AuditLog {
  eventType: AuditLogEventType;
  payload: string;
  taskId: number;
  walletDelta: number;
  timestamp: string;
}

export interface CreateTask {
  title: string;
  description?: string;
  status?: TaskStatus;
  priority?: TaskPriority;
}

export interface Task {
  id: number;
  title: string;
  description?: string;
  status: TaskStatus;
  priority: TaskPriority;
  user: any;
}

export interface UpdateTask {
  title: string;
  description?: string;
  status?: TaskStatus;
  priority?: TaskPriority;
}

export interface UpdateTaskStatus {
  status: TaskStatus;
}

export interface WalletTransaction {
  amount: number;
  balanceAfter: number;
  description: string;
  taskId: number;
  timestamp: string;
}
