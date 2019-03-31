import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IElf_status } from 'app/shared/model/elf-status.model';
import { Elf_statusService } from './elf-status.service';

@Component({
    selector: 'jhi-elf-status-delete-dialog',
    templateUrl: './elf-status-delete-dialog.component.html'
})
export class Elf_statusDeleteDialogComponent {
    elf_status: IElf_status;

    constructor(private elf_statusService: Elf_statusService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.elf_statusService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'elf_statusListModification',
                content: 'Deleted an elf_status'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-elf-status-delete-popup',
    template: ''
})
export class Elf_statusDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ elf_status }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(Elf_statusDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.elf_status = elf_status;
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
