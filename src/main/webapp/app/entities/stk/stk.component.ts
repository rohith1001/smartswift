import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStk } from 'app/shared/model/stk.model';
import { Principal } from 'app/core';
import { StkService } from './stk.service';

@Component({
    selector: 'jhi-stk',
    templateUrl: './stk.component.html',
    styles:[`
        .hd-ft{
            font-weight: 640;
        }
    `]
})
export class StkComponent implements OnInit, OnDestroy {
    stks: IStk[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private stkService: StkService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.stkService.query().subscribe(
            (res: HttpResponse<IStk[]>) => {
                this.stks = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInStks();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStk) {
        return item.id;
    }

    registerChangeInStks() {
        this.eventSubscriber = this.eventManager.subscribe('stkListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
