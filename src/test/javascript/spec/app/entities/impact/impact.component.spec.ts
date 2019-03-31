/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { ImpactComponent } from 'app/entities/impact/impact.component';
import { ImpactService } from 'app/entities/impact/impact.service';
import { Impact } from 'app/shared/model/impact.model';

describe('Component Tests', () => {
    describe('Impact Management Component', () => {
        let comp: ImpactComponent;
        let fixture: ComponentFixture<ImpactComponent>;
        let service: ImpactService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ImpactComponent],
                providers: []
            })
                .overrideTemplate(ImpactComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ImpactComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImpactService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Impact(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.impacts[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
