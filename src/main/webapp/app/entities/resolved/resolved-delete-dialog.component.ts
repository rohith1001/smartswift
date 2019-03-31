import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IResolved } from 'app/shared/model/resolved.model';
import { ResolvedService } from './resolved.service';

@Component({
    selector: 'jhi-resolved-delete-dialog',
    templateUrl: './resolved-delete-dialog.component.html'
})
export class ResolvedDeleteDialogComponent {
    resolved: IResolved;

    constructor(private resolvedService: ResolvedService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.resolvedService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'resolvedListModification',
                content: 'Deleted an resolved'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-resolved-delete-popup',
    template: ''
})
export class ResolvedDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ resolved }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ResolvedDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.resolved = resolved;
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
