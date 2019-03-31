import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IConfigslas } from 'app/shared/model/configslas.model';
import { Principal } from 'app/core';
import { ConfigslasService } from './configslas.service';

@Component({
    selector: 'jhi-configslas',
    templateUrl: './configslas.component.html'
})
export class ConfigslasComponent implements OnInit, OnDestroy {
    configslas: IConfigslas[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private configslasService: ConfigslasService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.configslasService.query().subscribe(
            (res: HttpResponse<IConfigslas[]>) => {
                this.configslas = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInConfigslas();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IConfigslas) {
        return item.id;
    }

    registerChangeInConfigslas() {
        this.eventSubscriber = this.eventManager.subscribe('configslasListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
