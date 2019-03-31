import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IElf_status } from 'app/shared/model/elf-status.model';

@Component({
    selector: 'jhi-elf-status-detail',
    templateUrl: './elf-status-detail.component.html'
})
export class Elf_statusDetailComponent implements OnInit {
    elf_status: IElf_status;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ elf_status }) => {
            this.elf_status = elf_status;
        });
    }

    previousState() {
        window.history.back();
    }
}
