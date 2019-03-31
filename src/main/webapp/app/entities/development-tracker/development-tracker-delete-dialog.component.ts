import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDevelopment_tracker } from 'app/shared/model/development-tracker.model';
import { Development_trackerService } from './development-tracker.service';

@Component({
    selector: 'jhi-development-tracker-delete-dialog',
    templateUrl: './development-tracker-delete-dialog.component.html'
})
export class Development_trackerDeleteDialogComponent {
    development_tracker: IDevelopment_tracker;

    constructor(
        private development_trackerService: Development_trackerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.development_trackerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'development_trackerListModification',
                content: 'Deleted an development_tracker'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-development-tracker-delete-popup',
    template: ''
})
export class Development_trackerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ development_tracker }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(Development_trackerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.development_tracker = development_tracker;
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
