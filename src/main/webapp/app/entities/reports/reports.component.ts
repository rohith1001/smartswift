import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IReports } from 'app/shared/model/reports.model';
import { Principal } from 'app/core';
import { ReportsService } from './reports.service';

@Component({
    selector: 'jhi-reports',
    templateUrl: './reports.component.html'
})
export class ReportsComponent implements OnInit, OnDestroy {
    reports: IReports[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private reportsService: ReportsService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.reportsService.query().subscribe(
            (res: HttpResponse<IReports[]>) => {
                this.reports = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInReports();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IReports) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInReports() {
        this.eventSubscriber = this.eventManager.subscribe('reportsListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
