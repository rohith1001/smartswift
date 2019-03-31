import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMonthlyreport } from 'app/shared/model/monthlyreport.model';
import { MonthlyreportService } from './monthlyreport.service';

@Component({
    selector: 'jhi-monthlyreport-delete-dialog',
    templateUrl: './monthlyreport-delete-dialog.component.html'
})
export class MonthlyreportDeleteDialogComponent {
    monthlyreport: IMonthlyreport;

    constructor(
        private monthlyreportService: MonthlyreportService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.monthlyreportService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'monthlyreportListModification',
                content: 'Deleted an monthlyreport'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-monthlyreport-delete-popup',
    template: ''
})
export class MonthlyreportDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ monthlyreport }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MonthlyreportDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.monthlyreport = monthlyreport;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
