/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { Elf_statusDetailComponent } from 'app/entities/elf-status/elf-status-detail.component';
import { Elf_status } from 'app/shared/model/elf-status.model';

describe('Component Tests', () => {
    describe('Elf_status Management Detail Component', () => {
        let comp: Elf_statusDetailComponent;
        let fixture: ComponentFixture<Elf_statusDetailComponent>;
        const route = ({ data: of({ elf_status: new Elf_status(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Elf_statusDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(Elf_statusDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Elf_statusDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.elf_status).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
