import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDomain } from 'app/shared/model/domain.model';
import { Principal } from 'app/core';
import { DomainService } from './domain.service';

@Component({
    selector: 'jhi-domain',
    templateUrl: './domain.component.html'
})
export class DomainComponent implements OnInit, OnDestroy {
    domains: IDomain[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private domainService: DomainService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.domainService.query().subscribe(
            (res: HttpResponse<IDomain[]>) => {
                this.domains = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDomains();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDomain) {
        return item.id;
    }

    registerChangeInDomains() {
        this.eventSubscriber = this.eventManager.subscribe('domainListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
