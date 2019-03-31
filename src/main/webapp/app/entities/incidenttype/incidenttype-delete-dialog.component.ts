import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IIncidenttype } from 'app/shared/model/incidenttype.model';
import { IncidenttypeService } from './incidenttype.service';

@Component({
    selector: 'jhi-incidenttype-delete-dialog',
    templateUrl: './incidenttype-delete-dialog.component.html'
})
export class IncidenttypeDeleteDialogComponent {
    incidenttype: IIncidenttype;

    constructor(
        private incidenttypeService: IncidenttypeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.incidenttypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'incidenttypeListModification',
                content: 'Deleted an incidenttype'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-incidenttype-delete-popup',
    template: ''
})
export class IncidenttypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ incidenttype }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(IncidenttypeDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.incidenttype = incidenttype;
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
