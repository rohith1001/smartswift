import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBulkdefect } from 'app/shared/model/bulkdefect.model';
import { BulkdefectService } from './bulkdefect.service';

@Component({
    selector: 'jhi-bulkdefect-delete-dialog',
    templateUrl: './bulkdefect-delete-dialog.component.html'
})
export class BulkdefectDeleteDialogComponent {
    bulkdefect: IBulkdefect;

    constructor(private bulkdefectService: BulkdefectService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bulkdefectService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bulkdefectListModification',
                content: 'Deleted an bulkdefect'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bulkdefect-delete-popup',
    template: ''
})
export class BulkdefectDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bulkdefect }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BulkdefectDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.bulkdefect = bulkdefect;
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
