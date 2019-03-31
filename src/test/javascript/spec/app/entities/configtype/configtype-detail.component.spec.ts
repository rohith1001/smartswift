/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SmartswiftTestModule } from '../../../test.module';
import { ConfigtypeDetailComponent } from 'app/entities/configtype/configtype-detail.component';
import { Configtype } from 'app/shared/model/configtype.model';

describe('Component Tests', () => {
    describe('Configtype Management Detail Component', () => {
        let comp: ConfigtypeDetailComponent;
        let fixture: ComponentFixture<ConfigtypeDetailComponent>;
        const route = ({ data: of({ configtype: new Configtype(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ConfigtypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ConfigtypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConfigtypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.configtype).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
