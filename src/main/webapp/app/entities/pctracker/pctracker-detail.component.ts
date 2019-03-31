import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPctracker } from 'app/shared/model/pctracker.model';

@Component({
    selector: 'jhi-pctracker-detail',
    templateUrl: './pctracker-detail.component.html',
    styles: [`
        .dtc{
            margin-bottom: 0.2rem !important;
            font-size: 13px !important;
            font-weight: 640;
        }
        .ddc{
            height: unset !important;
            font-family: inherit !important;
            font-size: 12px !important;
        }
    `]
})
export class PctrackerDetailComponent implements OnInit {
    pctracker: IPctracker;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ pctracker }) => {
            this.pctracker = pctracker;
        });
    }

    previousState() {
        window.history.back();
    }
}
