import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IImpact } from 'app/shared/model/impact.model';
import { Principal } from 'app/core';
import { ImpactService } from './impact.service';

@Component({
    selector: 'jhi-impact',
    templateUrl: './impact.component.html'
})
export class ImpactComponent implements OnInit, OnDestroy {
    impacts: IImpact[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private impactService: ImpactService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.impactService.query().subscribe(
            (res: HttpResponse<IImpact[]>) => {
                this.impacts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInImpacts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IImpact) {
        return item.id;
    }

    registerChangeInImpacts() {
        this.eventSubscriber = this.eventManager.subscribe('impactListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
