import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDevservice } from 'app/shared/model/devservice.model';
import { DevserviceService } from './devservice.service';

@Component({
    selector: 'jhi-devservice-delete-dialog',
    templateUrl: './devservice-delete-dialog.component.html'
})
export class DevserviceDeleteDialogComponent {
    devservice: IDevservice;

    constructor(private devserviceService: DevserviceService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.devserviceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'devserviceListModification',
                content: 'Deleted an devservice'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-devservice-delete-popup',
    template: ''
})
export class DevserviceDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ devservice }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DevserviceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.devservice = devservice;
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
