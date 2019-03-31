/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { IssuetypeDetailComponent } from 'app/entities/issuetype/issuetype-detail.component';
import { Issuetype } from 'app/shared/model/issuetype.model';

describe('Component Tests', () => {
    describe('Issuetype Management Detail Component', () => {
        let comp: IssuetypeDetailComponent;
        let fixture: ComponentFixture<IssuetypeDetailComponent>;
        const route = ({ data: of({ issuetype: new Issuetype(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [IssuetypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IssuetypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IssuetypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.issuetype).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
