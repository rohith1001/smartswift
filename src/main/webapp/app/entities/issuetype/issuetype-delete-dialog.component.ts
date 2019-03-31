import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIssuetype } from 'app/shared/model/issuetype.model';
import { IssuetypeService } from './issuetype.service';

@Component({
    selector: 'jhi-issuetype-delete-dialog',
    templateUrl: './issuetype-delete-dialog.component.html'
})
export class IssuetypeDeleteDialogComponent {
    issuetype: IIssuetype;

    constructor(private issuetypeService: IssuetypeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.issuetypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'issuetypeListModification',
                content: 'Deleted an issuetype'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-issuetype-delete-popup',
    template: ''
})
export class IssuetypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ issuetype }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IssuetypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.issuetype = issuetype;
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
