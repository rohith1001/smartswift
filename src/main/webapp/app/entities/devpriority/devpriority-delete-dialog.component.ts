import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDevpriority } from 'app/shared/model/devpriority.model';
import { DevpriorityService } from './devpriority.service';

@Component({
    selector: 'jhi-devpriority-delete-dialog',
    templateUrl: './devpriority-delete-dialog.component.html'
})
export class DevpriorityDeleteDialogComponent {
    devpriority: IDevpriority;

    constructor(
        private devpriorityService: DevpriorityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.devpriorityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'devpriorityListModification',
                content: 'Deleted an devpriority'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-devpriority-delete-popup',
    template: ''
})
export class DevpriorityDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ devpriority }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DevpriorityDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.devpriority = devpriority;
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
