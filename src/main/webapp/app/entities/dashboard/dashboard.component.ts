import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDashboard } from 'app/shared/model/dashboard.model';
import { Principal } from 'app/core';
import { DashboardService } from './dashboard.service';

@Component({
    selector: 'jhi-dashboard',
    templateUrl: './dashboard.component.html',
    styles: [`
        .bg{
            background: silver;
        }
    `]
})
export class DashboardComponent implements OnInit, OnDestroy {
    dashboards: IDashboard[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private dashboardService: DashboardService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.dashboardService.query().subscribe(
            (res: HttpResponse<IDashboard[]>) => {
                this.dashboards = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInDashboards();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDashboard) {
        return item.id;
    }

    registerChangeInDashboards() {
        this.eventSubscriber = this.eventManager.subscribe('dashboardListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    getBGColor(id, sla, expected, minimum){
        let style = {
            'background': ''
        };
        if(sla && sla != null && sla != '' && sla != undefined
            && expected && expected != null && expected != '' && expected != undefined
            && minimum && minimum != null && minimum != '' && minimum != undefined
			&& id != 15 && id != 37){
                if(sla == "NA"){
                    style['background'] = 'forestgreen';
                }else{
                    if(id == 10){
						if(parseInt(sla) < -30 || parseInt(sla) > 30){
							style['background'] = 'orangered';
						}else{
							style['background'] = 'forestgreen';
						}						
					}else if(id == 19 && id == 20){
						if(id == 19){
							if(parseInt(sla) <= 6){
								style['background'] = 'forestgreen';
							}else if(parseInt(sla) > 8){
								style['background'] = 'orangered';
							}else{
								style['background'] = 'orange';
							}
						}else{
							if(parseInt(sla) <= 8){
								style['background'] = 'forestgreen';
							}else if(parseInt(sla) > 12){
								style['background'] = 'orangered';
							}else{
								style['background'] = 'orange';
							}
						}						
					}else if(sla.endsWith('%')){
                        if(parseInt((sla).substr(0, (sla).length - 1)) >= parseInt((expected).substr(0, (expected).length - 1))) style['background'] = 'forestgreen';
                        else if(parseInt((sla).substr(0, (sla).length - 1)) < parseInt((minimum).substr(0, (minimum).length - 1))) style['background'] = 'orangered';
                        else style['background'] = 'orange';
                    }else{
                        if(parseInt(sla) <= parseInt(expected)) style['background'] = 'forestgreen';
                        else if(parseInt(sla) > parseInt(minimum)) style['background'] = 'orangered';
                        else style['background'] = 'orange';
                    }
                }
        }
        return style;
    }
}
