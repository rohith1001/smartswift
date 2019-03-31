import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IResolved } from 'app/shared/model/resolved.model';
import { Principal } from 'app/core';
import { ResolvedService } from './resolved.service';

@Component({
    selector: 'jhi-resolved',
    templateUrl: './resolved.component.html'
})
export class ResolvedComponent implements OnInit, OnDestroy {
    resolveds: IResolved[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private resolvedService: ResolvedService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.resolvedService.query().subscribe(
            (res: HttpResponse<IResolved[]>) => {
                this.resolveds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInResolveds();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IResolved) {
        return item.id;
    }

    registerChangeInResolveds() {
        this.eventSubscriber = this.eventManager.subscribe('resolvedListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
