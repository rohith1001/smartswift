import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITest_tracker } from 'app/shared/model/test-tracker.model';
import { Principal } from 'app/core';
import { Test_trackerService } from './test-tracker.service';

@Component({
    selector: 'jhi-test-tracker',
    templateUrl: './test-tracker.component.html'
})
export class Test_trackerComponent implements OnInit, OnDestroy {
    test_trackers: ITest_tracker[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private test_trackerService: Test_trackerService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.test_trackerService.query().subscribe(
            (res: HttpResponse<ITest_tracker[]>) => {
                this.test_trackers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInTest_trackers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITest_tracker) {
        return item.id;
    }

    registerChangeInTest_trackers() {
        this.eventSubscriber = this.eventManager.subscribe('test_trackerListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
