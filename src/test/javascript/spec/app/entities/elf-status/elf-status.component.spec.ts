/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SmartswiftTestModule } from '../../../test.module';
import { Elf_statusComponent } from 'app/entities/elf-status/elf-status.component';
import { Elf_statusService } from 'app/entities/elf-status/elf-status.service';
import { Elf_status } from 'app/shared/model/elf-status.model';

describe('Component Tests', () => {
    describe('Elf_status Management Component', () => {
        let comp: Elf_statusComponent;
        let fixture: ComponentFixture<Elf_statusComponent>;
        let service: Elf_statusService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Elf_statusComponent],
                providers: []
            })
                .overrideTemplate(Elf_statusComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(Elf_statusComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Elf_statusService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Elf_status(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.elf_statuses[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
