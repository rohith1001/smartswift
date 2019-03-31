import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBulkincident } from 'app/shared/model/bulkincident.model';
import { BulkincidentService } from './bulkincident.service';

@Component({
    selector: 'jhi-bulkincident-delete-dialog',
    templateUrl: './bulkincident-delete-dialog.component.html'
})
export class BulkincidentDeleteDialogComponent {
    bulkincident: IBulkincident;

    constructor(
        private bulkincidentService: BulkincidentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bulkincidentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bulkincidentListModification',
                content: 'Deleted an bulkincident'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bulkincident-delete-popup',
    template: ''
})
export class BulkincidentDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bulkincident }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BulkincidentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.bulkincident = bulkincident;
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
