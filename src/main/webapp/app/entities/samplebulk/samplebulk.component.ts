import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ISamplebulk } from 'app/shared/model/samplebulk.model';
import { Principal } from 'app/core';
import { SamplebulkService } from './samplebulk.service';

@Component({
    selector: 'jhi-samplebulk',
    templateUrl: './samplebulk.component.html'
})
export class SamplebulkComponent implements OnInit, OnDestroy {
    samplebulks: ISamplebulk[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private samplebulkService: SamplebulkService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.samplebulkService.query().subscribe(
            (res: HttpResponse<ISamplebulk[]>) => {
                this.samplebulks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSamplebulks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISamplebulk) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInSamplebulks() {
        this.eventSubscriber = this.eventManager.subscribe('samplebulkListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
