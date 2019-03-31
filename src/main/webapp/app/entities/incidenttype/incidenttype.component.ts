import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIncidenttype } from 'app/shared/model/incidenttype.model';
import { Principal } from 'app/core';
import { IncidenttypeService } from './incidenttype.service';

@Component({
    selector: 'jhi-incidenttype',
    templateUrl: './incidenttype.component.html'
})
export class IncidenttypeComponent implements OnInit, OnDestroy {
    incidenttypes: IIncidenttype[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private incidenttypeService: IncidenttypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.incidenttypeService.query().subscribe(
            (res: HttpResponse<IIncidenttype[]>) => {
                this.incidenttypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInIncidenttypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIncidenttype) {
        return item.id;
    }

    registerChangeInIncidenttypes() {
        this.eventSubscriber = this.eventManager.subscribe('incidenttypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
