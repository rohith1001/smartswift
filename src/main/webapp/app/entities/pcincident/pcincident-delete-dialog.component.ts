import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPcincident } from 'app/shared/model/pcincident.model';
import { PcincidentService } from './pcincident.service';

@Component({
    selector: 'jhi-pcincident-delete-dialog',
    templateUrl: './pcincident-delete-dialog.component.html'
})
export class PcincidentDeleteDialogComponent {
    pcincident: IPcincident;

    constructor(private pcincidentService: PcincidentService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pcincidentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pcincidentListModification',
                content: 'Deleted an pcincident'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pcincident-delete-popup',
    template: ''
})
export class PcincidentDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pcincident }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PcincidentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.pcincident = pcincident;
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
