import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPcrelease } from 'app/shared/model/pcrelease.model';
import { PcreleaseService } from './pcrelease.service';

@Component({
    selector: 'jhi-pcrelease-delete-dialog',
    templateUrl: './pcrelease-delete-dialog.component.html'
})
export class PcreleaseDeleteDialogComponent {
    pcrelease: IPcrelease;

    constructor(private pcreleaseService: PcreleaseService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pcreleaseService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pcreleaseListModification',
                content: 'Deleted an pcrelease'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pcrelease-delete-popup',
    template: ''
})
export class PcreleaseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pcrelease }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PcreleaseDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.pcrelease = pcrelease;
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
