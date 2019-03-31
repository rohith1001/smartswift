import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDevservice } from 'app/shared/model/devservice.model';
import { Principal } from 'app/core';
import { DevserviceService } from './devservice.service';

@Component({
    selector: 'jhi-devservice',
    templateUrl: './devservice.component.html'
})
export class DevserviceComponent implements OnInit, OnDestroy {
    devservices: IDevservice[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private devserviceService: DevserviceService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.devserviceService.query().subscribe(
            (res: HttpResponse<IDevservice[]>) => {
                this.devservices = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDevservices();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDevservice) {
        return item.id;
    }

    registerChangeInDevservices() {
        this.eventSubscriber = this.eventManager.subscribe('devserviceListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
