import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IElf_status } from 'app/shared/model/elf-status.model';
import { Elf_statusService } from './elf-status.service';

@Component({
    selector: 'jhi-elf-status-update',
    templateUrl: './elf-status-update.component.html'
})
export class Elf_statusUpdateComponent implements OnInit {
    elf_status: IElf_status;
    isSaving: boolean;

    constructor(private elf_statusService: Elf_statusService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ elf_status }) => {
            this.elf_status = elf_status;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.elf_status.id !== undefined) {
            this.subscribeToSaveResponse(this.elf_statusService.update(this.elf_status));
        } else {
            this.subscribeToSaveResponse(this.elf_statusService.create(this.elf_status));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IElf_status>>) {
        result.subscribe((res: HttpResponse<IElf_status>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
}
