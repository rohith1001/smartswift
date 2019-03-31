import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOptions } from 'app/shared/model/options.model';
import { OptionsService } from './options.service';

@Component({
    selector: 'jhi-options-delete-dialog',
    templateUrl: './options-delete-dialog.component.html'
})
export class OptionsDeleteDialogComponent {
    options: IOptions;

    constructor(private optionsService: OptionsService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.optionsService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'optionsListModification',
                content: 'Deleted an options'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-options-delete-popup',
    template: ''
})
export class OptionsDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ options }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(OptionsDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.options = options;
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
