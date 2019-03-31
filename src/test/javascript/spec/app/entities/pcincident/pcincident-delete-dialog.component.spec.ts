/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { PcincidentDeleteDialogComponent } from 'app/entities/pcincident/pcincident-delete-dialog.component';
import { PcincidentService } from 'app/entities/pcincident/pcincident.service';

describe('Component Tests', () => {
    describe('Pcincident Management Delete Component', () => {
        let comp: PcincidentDeleteDialogComponent;
        let fixture: ComponentFixture<PcincidentDeleteDialogComponent>;
        let service: PcincidentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PcincidentDeleteDialogComponent]
            })
                .overrideTemplate(PcincidentDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PcincidentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PcincidentService);
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
