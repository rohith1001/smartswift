import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPcrelease } from 'app/shared/model/pcrelease.model';
import { Principal } from 'app/core';
import { PcreleaseService } from './pcrelease.service';

@Component({
    selector: 'jhi-pcrelease',
    templateUrl: './pcrelease.component.html'
})
export class PcreleaseComponent implements OnInit, OnDestroy {
    pcreleases: IPcrelease[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pcreleaseService: PcreleaseService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.pcreleaseService.query().subscribe(
            (res: HttpResponse<IPcrelease[]>) => {
                this.pcreleases = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPcreleases();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPcrelease) {
        return item.id;
    }

    registerChangeInPcreleases() {
        this.eventSubscriber = this.eventManager.subscribe('pcreleaseListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
