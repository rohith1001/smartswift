import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IPctracker } from 'app/shared/model/pctracker.model';
import { Principal } from 'app/core';
import { PctrackerService } from './pctracker.service';

@Component({
    selector: 'jhi-pctracker',
    templateUrl: './pctracker.component.html'
})
export class PctrackerComponent implements OnInit, OnDestroy {
    spctrackers: IPctracker[];
    searchText: string = '';
    pctrackers: IPctracker[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private pctrackerService: PctrackerService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) { }

    loadAll() {
        this.pctrackerService.query().subscribe(
            (res: HttpResponse<IPctracker[]>) => {
                this.pctrackerService.spctrackers = res.body;
                this.spctrackers = res.body;
                this.pctrackers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        if(this.pctrackerService.searchText) this.searchText = this.pctrackerService.searchText;
        if(this.pctrackerService.spctrackers) {
            this.spctrackers = this.pctrackerService.spctrackers;
            this.pctrackers = this.pctrackerService.spctrackers;
        } else this.loadAll();
        if(this.searchText && this.spctrackers) this.filterData('F');
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPctrackers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IPctracker) {
        return item.id;
    }

    registerChangeInPctrackers() {
        this.eventSubscriber = this.eventManager.subscribe('pctrackerListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    filterData(type) {
        if (type === 'C') {
            this.searchText = '';
            this.pctrackerService.searchText = '';
            this.pctrackers = this.spctrackers;
        } else {
            this.pctrackers = [];
            this.pctrackerService.searchText = this.searchText;
            if (this.searchText && this.spctrackers) {
                this.pctrackers = this.spctrackers.filter(pct => 
                    (pct.elf_id).toUpperCase() === (this.searchText.trim()).toUpperCase());
            }
        }
    }
}
