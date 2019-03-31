import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IState } from 'app/shared/model/state.model';
import { Principal } from 'app/core';
import { StateService } from './state.service';

@Component({
    selector: 'jhi-state',
    templateUrl: './state.component.html'
})
export class StateComponent implements OnInit, OnDestroy {
    states: IState[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private stateService: StateService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.stateService.query().subscribe(
            (res: HttpResponse<IState[]>) => {
                this.states = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInStates();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IState) {
        return item.id;
    }

    registerChangeInStates() {
        this.eventSubscriber = this.eventManager.subscribe('stateListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
