import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPcdefect } from 'app/shared/model/pcdefect.model';
import { Principal } from 'app/core';
import { PcdefectService } from './pcdefect.service';

@Component({
    selector: 'jhi-pcdefect',
    templateUrl: './pcdefect.component.html'
})
export class PcdefectComponent implements OnInit, OnDestroy {
    pcdefects: IPcdefect[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pcdefectService: PcdefectService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.pcdefectService.query().subscribe(
            (res: HttpResponse<IPcdefect[]>) => {
                this.pcdefects = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPcdefects();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPcdefect) {
        return item.id;
    }

    registerChangeInPcdefects() {
        this.eventSubscriber = this.eventManager.subscribe('pcdefectListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
