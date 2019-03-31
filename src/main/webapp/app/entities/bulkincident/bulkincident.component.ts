import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IBulkincident } from 'app/shared/model/bulkincident.model';
import { Principal } from 'app/core';
import { BulkincidentService } from './bulkincident.service';

@Component({
    selector: 'jhi-bulkincident',
    templateUrl: './bulkincident.component.html'
})
export class BulkincidentComponent implements OnInit, OnDestroy {
    bulkincidents: IBulkincident[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private bulkincidentService: BulkincidentService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.bulkincidentService.query().subscribe(
            (res: HttpResponse<IBulkincident[]>) => {
                this.bulkincidents = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInBulkincidents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBulkincident) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInBulkincidents() {
        this.eventSubscriber = this.eventManager.subscribe('bulkincidentListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
