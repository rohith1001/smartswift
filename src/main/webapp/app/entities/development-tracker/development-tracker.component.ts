import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDevelopment_tracker } from 'app/shared/model/development-tracker.model';
import { Principal } from 'app/core';
import { Development_trackerService } from './development-tracker.service';

@Component({
    selector: 'jhi-development-tracker',
    templateUrl: './development-tracker.component.html'
})
export class Development_trackerComponent implements OnInit, OnDestroy {
    development_trackers: IDevelopment_tracker[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private development_trackerService: Development_trackerService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.development_trackerService.query().subscribe(
            (res: HttpResponse<IDevelopment_tracker[]>) => {
                this.development_trackers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDevelopment_trackers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDevelopment_tracker) {
        return item.id;
    }

    registerChangeInDevelopment_trackers() {
        this.eventSubscriber = this.eventManager.subscribe('development_trackerListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
