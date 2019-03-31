/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { DevpriorityDetailComponent } from 'app/entities/devpriority/devpriority-detail.component';
import { Devpriority } from 'app/shared/model/devpriority.model';

describe('Component Tests', () => {
    describe('Devpriority Management Detail Component', () => {
        let comp: DevpriorityDetailComponent;
        let fixture: ComponentFixture<DevpriorityDetailComponent>;
        const route = ({ data: of({ devpriority: new Devpriority(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [DevpriorityDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DevpriorityDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DevpriorityDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.devpriority).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
