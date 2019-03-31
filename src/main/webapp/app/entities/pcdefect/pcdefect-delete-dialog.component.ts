import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPcdefect } from 'app/shared/model/pcdefect.model';
import { PcdefectService } from './pcdefect.service';

@Component({
    selector: 'jhi-pcdefect-delete-dialog',
    templateUrl: './pcdefect-delete-dialog.component.html'
})
export class PcdefectDeleteDialogComponent {
    pcdefect: IPcdefect;

    constructor(private pcdefectService: PcdefectService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.pcdefectService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'pcdefectListModification',
                content: 'Deleted an pcdefect'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pcdefect-delete-popup',
    template: ''
})
export class PcdefectDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pcdefect }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(PcdefectDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.pcdefect = pcdefect;
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
