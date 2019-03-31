import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPc_tracker } from 'app/shared/model/pc-tracker.model';
import { Pc_trackerService } from './pc-tracker.service';

@Component({
    selector: 'jhi-pc-tracker-delete-dialog',
    templateUrl: './pc-tracker-delete-dialog.component.html'
})
export class Pc_trackerDeleteDialogComponent {
    pc_tracker: IPc_tracker;

    constructor(private pc_trackerService: Pc_trackerService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pc_trackerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pc_trackerListModification',
                content: 'Deleted an pc_tracker'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pc-tracker-delete-popup',
    template: ''
})
export class Pc_trackerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pc_tracker }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(Pc_trackerDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.pc_tracker = pc_tracker;
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
