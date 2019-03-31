import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IBulkrelease } from 'app/shared/model/bulkrelease.model';
import { Principal } from 'app/core';
import { BulkreleaseService } from './bulkrelease.service';

@Component({
    selector: 'jhi-bulkrelease',
    templateUrl: './bulkrelease.component.html'
})
export class BulkreleaseComponent implements OnInit, OnDestroy {
    bulkreleases: IBulkrelease[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bulkreleaseService: BulkreleaseService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.bulkreleaseService.query().subscribe(
            (res: HttpResponse<IBulkrelease[]>) => {
                this.bulkreleases = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBulkreleases();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBulkrelease) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInBulkreleases() {
        this.eventSubscriber = this.eventManager.subscribe('bulkreleaseListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
