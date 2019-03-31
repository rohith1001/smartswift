/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { BulkreleaseComponent } from 'app/entities/bulkrelease/bulkrelease.component';
import { BulkreleaseService } from 'app/entities/bulkrelease/bulkrelease.service';
import { Bulkrelease } from 'app/shared/model/bulkrelease.model';

describe('Component Tests', () => {
    describe('Bulkrelease Management Component', () => {
        let comp: BulkreleaseComponent;
        let fixture: ComponentFixture<BulkreleaseComponent>;
        let service: BulkreleaseService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [BulkreleaseComponent],
                providers: []
            })
                .overrideTemplate(BulkreleaseComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BulkreleaseComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BulkreleaseService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Bulkrelease(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bulkreleases[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
