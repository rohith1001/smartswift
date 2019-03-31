/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { ConfigtypeDeleteDialogComponent } from 'app/entities/configtype/configtype-delete-dialog.component';
import { ConfigtypeService } from 'app/entities/configtype/configtype.service';

describe('Component Tests', () => {
    describe('Configtype Management Delete Component', () => {
        let comp: ConfigtypeDeleteDialogComponent;
        let fixture: ComponentFixture<ConfigtypeDeleteDialogComponent>;
        let service: ConfigtypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ConfigtypeDeleteDialogComponent]
            })
                .overrideTemplate(ConfigtypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConfigtypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfigtypeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
