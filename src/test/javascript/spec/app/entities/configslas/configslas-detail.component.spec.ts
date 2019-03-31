/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { ConfigslasDetailComponent } from 'app/entities/configslas/configslas-detail.component';
import { Configslas } from 'app/shared/model/configslas.model';

describe('Component Tests', () => {
    describe('Configslas Management Detail Component', () => {
        let comp: ConfigslasDetailComponent;
        let fixture: ComponentFixture<ConfigslasDetailComponent>;
        const route = ({ data: of({ configslas: new Configslas(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ConfigslasDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ConfigslasDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConfigslasDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.configslas).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
