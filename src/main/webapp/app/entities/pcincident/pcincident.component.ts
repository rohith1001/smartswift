import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPcincident } from 'app/shared/model/pcincident.model';
import { Principal } from 'app/core';
import { PcincidentService } from './pcincident.service';

@Component({
    selector: 'jhi-pcincident',
    templateUrl: './pcincident.component.html'
})
export class PcincidentComponent implements OnInit, OnDestroy {
    pcincidents: IPcincident[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pcincidentService: PcincidentService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.pcincidentService.query().subscribe(
            (res: HttpResponse<IPcincident[]>) => {
                this.pcincidents = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPcincidents();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPcincident) {
        return item.id;
    }

    registerChangeInPcincidents() {
        this.eventSubscriber = this.eventManager.subscribe('pcincidentListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
