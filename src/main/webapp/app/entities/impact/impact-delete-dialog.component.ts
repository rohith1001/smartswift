import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IImpact } from 'app/shared/model/impact.model';
import { ImpactService } from './impact.service';

@Component({
    selector: 'jhi-impact-delete-dialog',
    templateUrl: './impact-delete-dialog.component.html'
})
export class ImpactDeleteDialogComponent {
    impact: IImpact;

    constructor(private impactService: ImpactService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.impactService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'impactListModification',
                content: 'Deleted an impact'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-impact-delete-popup',
    template: ''
})
export class ImpactDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ impact }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ImpactDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.impact = impact;
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
