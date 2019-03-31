import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { INewreport } from 'app/shared/model/newreport.model';
import { Principal } from 'app/core';
import { NewreportService } from './newreport.service';

@Component({
    selector: 'jhi-newreport',
    templateUrl: './newreport.component.html'
})
export class NewreportComponent implements OnInit, OnDestroy {
    newreports: INewreport[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private newreportService: NewreportService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.newreportService.query().subscribe(
            (res: HttpResponse<INewreport[]>) => {
                this.newreports = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInNewreports();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: INewreport) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInNewreports() {
        this.eventSubscriber = this.eventManager.subscribe('newreportListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
