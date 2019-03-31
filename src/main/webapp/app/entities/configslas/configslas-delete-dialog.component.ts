import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IConfigslas } from 'app/shared/model/configslas.model';
import { ConfigslasService } from './configslas.service';

@Component({
    selector: 'jhi-configslas-delete-dialog',
    templateUrl: './configslas-delete-dialog.component.html'
})
export class ConfigslasDeleteDialogComponent {
    configslas: IConfigslas;

    constructor(private configslasService: ConfigslasService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.configslasService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'configslasListModification',
                content: 'Deleted an configslas'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-configslas-delete-popup',
    template: ''
})
export class ConfigslasDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ configslas }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ConfigslasDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.configslas = configslas;
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
