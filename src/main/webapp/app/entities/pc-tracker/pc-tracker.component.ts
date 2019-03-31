import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPc_tracker } from 'app/shared/model/pc-tracker.model';
import { Principal } from 'app/core';
import { Pc_trackerService } from './pc-tracker.service';

@Component({
    selector: 'jhi-pc-tracker',
    templateUrl: './pc-tracker.component.html'
})
export class Pc_trackerComponent implements OnInit, OnDestroy {
    pc_trackers: IPc_tracker[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pc_trackerService: Pc_trackerService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.pc_trackerService.query().subscribe(
            (res: HttpResponse<IPc_tracker[]>) => {
                this.pc_trackers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPc_trackers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPc_tracker) {
        return item.id;
    }

    registerChangeInPc_trackers() {
        this.eventSubscriber = this.eventManager.subscribe('pc_trackerListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
