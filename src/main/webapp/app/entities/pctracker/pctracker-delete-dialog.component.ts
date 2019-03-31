import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPctracker } from 'app/shared/model/pctracker.model';
import { PctrackerService } from './pctracker.service';

@Component({
    selector: 'jhi-pctracker-delete-dialog',
    templateUrl: './pctracker-delete-dialog.component.html'
})
export class PctrackerDeleteDialogComponent {
    pctracker: IPctracker;

    constructor(private pctrackerService: PctrackerService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pctrackerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pctrackerListModification',
                content: 'Deleted an pctracker'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pctracker-delete-popup',
    template: ''
})
export class PctrackerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pctracker }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PctrackerDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.pctracker = pctracker;
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
