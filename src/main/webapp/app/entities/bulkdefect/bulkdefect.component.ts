import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IBulkdefect } from 'app/shared/model/bulkdefect.model';
import { Principal } from 'app/core';
import { BulkdefectService } from './bulkdefect.service';

@Component({
    selector: 'jhi-bulkdefect',
    templateUrl: './bulkdefect.component.html'
})
export class BulkdefectComponent implements OnInit, OnDestroy {
    bulkdefects: IBulkdefect[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bulkdefectService: BulkdefectService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.bulkdefectService.query().subscribe(
            (res: HttpResponse<IBulkdefect[]>) => {
                this.bulkdefects = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBulkdefects();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBulkdefect) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInBulkdefects() {
        this.eventSubscriber = this.eventManager.subscribe('bulkdefectListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
