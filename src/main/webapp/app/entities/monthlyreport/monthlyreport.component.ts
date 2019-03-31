import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMonthlyreport } from 'app/shared/model/monthlyreport.model';
import { Principal } from 'app/core';
import { MonthlyreportService } from './monthlyreport.service';

@Component({
    selector: 'jhi-monthlyreport',
    templateUrl: './monthlyreport.component.html'
})
export class MonthlyreportComponent implements OnInit, OnDestroy {
    monthlyreports: IMonthlyreport[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private monthlyreportService: MonthlyreportService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.monthlyreportService.query().subscribe(
            (res: HttpResponse<IMonthlyreport[]>) => {
                this.monthlyreports = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMonthlyreports();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMonthlyreport) {
        return item.id;
    }

    registerChangeInMonthlyreports() {
        this.eventSubscriber = this.eventManager.subscribe('monthlyreportListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
