import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IHolidays } from 'app/shared/model/holidays.model';
import { HolidaysService } from './holidays.service';

@Component({
    selector: 'jhi-holidays-delete-dialog',
    templateUrl: './holidays-delete-dialog.component.html'
})
export class HolidaysDeleteDialogComponent {
    holidays: IHolidays;

    constructor(private holidaysService: HolidaysService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.holidaysService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'holidaysListModification',
                content: 'Deleted an holidays'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-holidays-delete-popup',
    template: ''
})
export class HolidaysDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ holidays }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(HolidaysDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.holidays = holidays;
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
