import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPriority } from 'app/shared/model/priority.model';
import { Principal } from 'app/core';
import { PriorityService } from './priority.service';

@Component({
    selector: 'jhi-priority',
    templateUrl: './priority.component.html'
})
export class PriorityComponent implements OnInit, OnDestroy {
    priorities: IPriority[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private priorityService: PriorityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.priorityService.query().subscribe(
            (res: HttpResponse<IPriority[]>) => {
                this.priorities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPriorities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPriority) {
        return item.id;
    }

    registerChangeInPriorities() {
        this.eventSubscriber = this.eventManager.subscribe('priorityListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
