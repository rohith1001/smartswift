import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIssuetype } from 'app/shared/model/issuetype.model';
import { Principal } from 'app/core';
import { IssuetypeService } from './issuetype.service';

@Component({
    selector: 'jhi-issuetype',
    templateUrl: './issuetype.component.html'
})
export class IssuetypeComponent implements OnInit, OnDestroy {
    issuetypes: IIssuetype[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private issuetypeService: IssuetypeService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.issuetypeService.query().subscribe(
            (res: HttpResponse<IIssuetype[]>) => {
                this.issuetypes = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInIssuetypes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIssuetype) {
        return item.id;
    }

    registerChangeInIssuetypes() {
        this.eventSubscriber = this.eventManager.subscribe('issuetypeListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
