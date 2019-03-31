import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IGroup } from 'app/shared/model/group.model';
import { Principal } from 'app/core';
import { GroupService } from './group.service';

@Component({
    selector: 'jhi-group',
    templateUrl: './group.component.html'
})
export class GroupComponent implements OnInit, OnDestroy {
    groups: IGroup[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private groupService: GroupService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.groupService.query().subscribe(
            (res: HttpResponse<IGroup[]>) => {
                this.groups = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInGroups();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IGroup) {
        return item.id;
    }

    registerChangeInGroups() {
        this.eventSubscriber = this.eventManager.subscribe('groupListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
