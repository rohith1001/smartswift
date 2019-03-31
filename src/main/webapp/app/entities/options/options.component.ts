import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOptions } from 'app/shared/model/options.model';
import { Principal } from 'app/core';
import { OptionsService } from './options.service';

@Component({
    selector: 'jhi-options',
    templateUrl: './options.component.html'
})
export class OptionsComponent implements OnInit, OnDestroy {
    options: IOptions[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private optionsService: OptionsService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.optionsService.query().subscribe(
            (res: HttpResponse<IOptions[]>) => {
                this.options = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOptions();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOptions) {
        return item.id;
    }

    registerChangeInOptions() {
        this.eventSubscriber = this.eventManager.subscribe('optionsListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
