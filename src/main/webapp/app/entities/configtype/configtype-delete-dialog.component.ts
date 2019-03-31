import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConfigtype } from 'app/shared/model/configtype.model';
import { ConfigtypeService } from './configtype.service';

@Component({
    selector: 'jhi-configtype-delete-dialog',
    templateUrl: './configtype-delete-dialog.component.html'
})
export class ConfigtypeDeleteDialogComponent {
    configtype: IConfigtype;

    constructor(private configtypeService: ConfigtypeService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.configtypeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'configtypeListModification',
                content: 'Deleted an configtype'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-configtype-delete-popup',
    template: ''
})
export class ConfigtypeDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ configtype }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ConfigtypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.configtype = configtype;
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
