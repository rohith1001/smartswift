import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IConfigtype } from 'app/shared/model/configtype.model';
import { Principal } from 'app/core';
import { ConfigtypeService } from './configtype.service';

@Component({
    selector: 'jhi-configtype',
    templateUrl: './configtype.component.html'
})
export class ConfigtypeComponent implements OnInit, OnDestroy {
    configtypes: IConfigtype[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private configtypeService: ConfigtypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.configtypeService.query().subscribe(
            (res: HttpResponse<IConfigtype[]>) => {
                this.configtypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInConfigtypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IConfigtype) {
        return item.id;
    }

    registerChangeInConfigtypes() {
        this.eventSubscriber = this.eventManager.subscribe('configtypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
