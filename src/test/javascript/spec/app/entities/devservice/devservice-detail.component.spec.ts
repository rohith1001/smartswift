/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { DevserviceDetailComponent } from 'app/entities/devservice/devservice-detail.component';
import { Devservice } from 'app/shared/model/devservice.model';

describe('Component Tests', () => {
    describe('Devservice Management Detail Component', () => {
        let comp: DevserviceDetailComponent;
        let fixture: ComponentFixture<DevserviceDetailComponent>;
        const route = ({ data: of({ devservice: new Devservice(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [DevserviceDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DevserviceDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DevserviceDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.devservice).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
