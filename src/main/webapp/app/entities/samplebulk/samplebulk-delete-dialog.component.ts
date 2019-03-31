import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISamplebulk } from 'app/shared/model/samplebulk.model';
import { SamplebulkService } from './samplebulk.service';

@Component({
    selector: 'jhi-samplebulk-delete-dialog',
    templateUrl: './samplebulk-delete-dialog.component.html'
})
export class SamplebulkDeleteDialogComponent {
    samplebulk: ISamplebulk;

    constructor(private samplebulkService: SamplebulkService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.samplebulkService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'samplebulkListModification',
                content: 'Deleted an samplebulk'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-samplebulk-delete-popup',
    template: ''
})
export class SamplebulkDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ samplebulk }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SamplebulkDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.samplebulk = samplebulk;
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
