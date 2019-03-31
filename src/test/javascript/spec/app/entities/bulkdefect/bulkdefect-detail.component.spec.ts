/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { BulkdefectDetailComponent } from 'app/entities/bulkdefect/bulkdefect-detail.component';
import { Bulkdefect } from 'app/shared/model/bulkdefect.model';

describe('Component Tests', () => {
    describe('Bulkdefect Management Detail Component', () => {
        let comp: BulkdefectDetailComponent;
        let fixture: ComponentFixture<BulkdefectDetailComponent>;
        const route = ({ data: of({ bulkdefect: new Bulkdefect(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [BulkdefectDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BulkdefectDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BulkdefectDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bulkdefect).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
