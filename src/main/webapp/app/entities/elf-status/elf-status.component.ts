import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IElf_status } from 'app/shared/model/elf-status.model';
import { Principal } from 'app/core';
import { Elf_statusService } from './elf-status.service';

@Component({
    selector: 'jhi-elf-status',
    templateUrl: './elf-status.component.html'
})
export class Elf_statusComponent implements OnInit, OnDestroy {
    elf_statuses: IElf_status[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private elf_statusService: Elf_statusService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.elf_statusService.query().subscribe(
            (res: HttpResponse<IElf_status[]>) => {
                this.elf_statuses = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInElf_statuses();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IElf_status) {
        return item.id;
    }

    registerChangeInElf_statuses() {
        this.eventSubscriber = this.eventManager.subscribe('elf_statusListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
