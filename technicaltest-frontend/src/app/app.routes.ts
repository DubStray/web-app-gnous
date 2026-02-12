import { Routes } from '@angular/router';
import { TaskComponent } from './task/task.component';
import { WalletHistoryComponent } from './wallet-history/wallet-history.component';
import { LogsComponent } from './logs/logs.component';

export const routes: Routes = [
  { path: '', component: TaskComponent },
  { path: 'wallet-history', component: WalletHistoryComponent },
  { path: 'logs', component: LogsComponent },
  { path: '**', redirectTo: '' },
];
