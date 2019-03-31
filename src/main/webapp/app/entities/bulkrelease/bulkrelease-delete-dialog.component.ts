import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBulkrelease } from 'app/shared/model/bulkrelease.model';
import { BulkreleaseService } from './bulkrelease.service';

@Component({
    selector: 'jhi-bulkrelease-delete-dialog',
    templateUrl: './bulkrelease-delete-dialog.component.html'
})
export class BulkreleaseDeleteDialogComponent {
    bulkrelease: IBulkrelease;

    constructor(
        private bulkreleaseService: BulkreleaseService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bulkreleaseService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bulkreleaseListModification',
                content: 'Deleted an bulkrelease'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bulkrelease-delete-popup',
    template: ''
})
export class BulkreleaseDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bulkrelease }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BulkreleaseDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.bulkrelease = bulkrelease;
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
