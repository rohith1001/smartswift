/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { BulkreleaseDetailComponent } from 'app/entities/bulkrelease/bulkrelease-detail.component';
import { Bulkrelease } from 'app/shared/model/bulkrelease.model';

describe('Component Tests', () => {
    describe('Bulkrelease Management Detail Component', () => {
        let comp: BulkreleaseDetailComponent;
        let fixture: ComponentFixture<BulkreleaseDetailComponent>;
        const route = ({ data: of({ bulkrelease: new Bulkrelease(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [BulkreleaseDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BulkreleaseDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BulkreleaseDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bulkrelease).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
