import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDevpriority } from 'app/shared/model/devpriority.model';
import { Principal } from 'app/core';
import { DevpriorityService } from './devpriority.service';

@Component({
    selector: 'jhi-devpriority',
    templateUrl: './devpriority.component.html'
})
export class DevpriorityComponent implements OnInit, OnDestroy {
    devpriorities: IDevpriority[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private devpriorityService: DevpriorityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.devpriorityService.query().subscribe(
            (res: HttpResponse<IDevpriority[]>) => {
                this.devpriorities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDevpriorities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDevpriority) {
        return item.id;
    }

    registerChangeInDevpriorities() {
        this.eventSubscriber = this.eventManager.subscribe('devpriorityListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
