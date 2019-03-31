/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { ResolvedDetailComponent } from 'app/entities/resolved/resolved-detail.component';
import { Resolved } from 'app/shared/model/resolved.model';

describe('Component Tests', () => {
    describe('Resolved Management Detail Component', () => {
        let comp: ResolvedDetailComponent;
        let fixture: ComponentFixture<ResolvedDetailComponent>;
        const route = ({ data: of({ resolved: new Resolved(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ResolvedDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ResolvedDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ResolvedDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.resolved).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
