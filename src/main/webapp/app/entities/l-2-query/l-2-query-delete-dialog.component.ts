import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IL2query } from 'app/shared/model/l-2-query.model';
import { L2queryService } from './l-2-query.service';

@Component({
    selector: 'jhi-l-2-query-delete-dialog',
    templateUrl: './l-2-query-delete-dialog.component.html'
})
export class L2queryDeleteDialogComponent {
    l2query: IL2query;

    constructor(private l2queryService: L2queryService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.l2queryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'l2queryListModification',
                content: 'Deleted an l2query'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-l-2-query-delete-popup',
    template: ''
})
export class L2queryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ l2query }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(L2queryDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.l2query = l2query;
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
