import { Component, OnInit, inject, Signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ToolbarModule } from 'primeng/toolbar';
import { ButtonModule } from 'primeng/button';
import { RouterLink } from '@angular/router';
import { WalletService } from '../services/wallet.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [CommonModule, ToolbarModule, ButtonModule, RouterLink],
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css',
})
export class NavbarComponent implements OnInit {
  walletBalance: Signal<number>;

  private walletService = inject(WalletService);

  constructor() {
    this.walletBalance = this.walletService.balance;
  }

  ngOnInit() {
    this.walletService.refreshBalance();
  }
}
