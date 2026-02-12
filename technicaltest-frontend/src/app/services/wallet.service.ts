import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { API_URL } from '../constants';
import { WalletTransaction } from '../models/models';

interface WalletBalanceResponse {
  balance: number;
}

@Injectable({
  providedIn: 'root',
})
export class WalletService {
  private apiUrl = `${API_URL}/wallet`;

  // Signal per gestire lo stato del saldo in tempo reale
  balance = signal<number>(0);

  constructor(private http: HttpClient) {
    // Carica il saldo iniziale
    this.refreshBalance();
  }

  // Metodo pubblico per forzare l'aggiornamento
  refreshBalance(): void {
    this.http.get<WalletBalanceResponse>(`${this.apiUrl}/balance`).subscribe({
      next: (res) => this.balance.set(res.balance),
      error: (err) => console.error('Error refreshing balance', err),
    });
  }

  // Manteniamo il vecchio metodo per compatibilità
  getCurrentBalance(): Observable<number> {
    return this.http.get<WalletBalanceResponse>(`${this.apiUrl}/balance`).pipe(
      map((res) => {
        this.balance.set(res.balance);
        return res.balance;
      }),
    );
  }

  getTransactionHistory(): Observable<WalletTransaction[]> {
    return this.http.get<WalletTransaction[]>(`${this.apiUrl}/history`);
  }
}
