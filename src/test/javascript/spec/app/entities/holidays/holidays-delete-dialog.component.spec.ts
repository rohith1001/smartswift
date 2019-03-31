/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { HolidaysDeleteDialogComponent } from 'app/entities/holidays/holidays-delete-dialog.component';
import { HolidaysService } from 'app/entities/holidays/holidays.service';

describe('Component Tests', () => {
    describe('Holidays Management Delete Component', () => {
        let comp: HolidaysDeleteDialogComponent;
        let fixture: ComponentFixture<HolidaysDeleteDialogComponent>;
        let service: HolidaysService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [HolidaysDeleteDialogComponent]
            })
                .overrideTemplate(HolidaysDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HolidaysDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HolidaysService);
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
