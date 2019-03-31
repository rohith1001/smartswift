import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIia } from 'app/shared/model/iia.model';
import { Principal } from 'app/core';
import { IiaService } from './iia.service';

@Component({
    selector: 'jhi-iia',
    templateUrl: './iia.component.html'
})
export class IiaComponent implements OnInit, OnDestroy {
    iias: IIia[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private iiaService: IiaService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.iiaService.query().subscribe(
            (res: HttpResponse<IIia[]>) => {
                this.iias = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInIias();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIia) {
        return item.id;
    }

    registerChangeInIias() {
        this.eventSubscriber = this.eventManager.subscribe('iiaListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
