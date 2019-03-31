import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IL2query } from 'app/shared/model/l-2-query.model';
import { Principal } from 'app/core';
import { L2queryService } from './l-2-query.service';

@Component({
    selector: 'jhi-l-2-query',
    templateUrl: './l-2-query.component.html',
    styles:[`
        .hd-ft{
            font-weight: 640;
        }
    `]
})
export class L2queryComponent implements OnInit, OnDestroy {
    l2queries: IL2query[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private l2queryService: L2queryService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.l2queryService.query().subscribe(
            (res: HttpResponse<IL2query[]>) => {
                this.l2queries = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInL2queries();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IL2query) {
        return item.id;
    }

    registerChangeInL2queries() {
        this.eventSubscriber = this.eventManager.subscribe('l2queryListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
