import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHld } from 'app/shared/model/hld.model';
import { Principal } from 'app/core';
import { HldService } from './hld.service';

@Component({
    selector: 'jhi-hld',
    templateUrl: './hld.component.html'
})
export class HldComponent implements OnInit, OnDestroy {
    hlds: IHld[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private hldService: HldService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.hldService.query().subscribe(
            (res: HttpResponse<IHld[]>) => {
                this.hlds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHlds();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHld) {
        return item.id;
    }

    registerChangeInHlds() {
        this.eventSubscriber = this.eventManager.subscribe('hldListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
