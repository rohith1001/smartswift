import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITest_tracker } from 'app/shared/model/test-tracker.model';
import { Test_trackerService } from './test-tracker.service';

@Component({
    selector: 'jhi-test-tracker-delete-dialog',
    templateUrl: './test-tracker-delete-dialog.component.html'
})
export class Test_trackerDeleteDialogComponent {
    test_tracker: ITest_tracker;

    constructor(
        private test_trackerService: Test_trackerService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.test_trackerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'test_trackerListModification',
                content: 'Deleted an test_tracker'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-test-tracker-delete-popup',
    template: ''
})
export class Test_trackerDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ test_tracker }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(Test_trackerDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.test_tracker = test_tracker;
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
