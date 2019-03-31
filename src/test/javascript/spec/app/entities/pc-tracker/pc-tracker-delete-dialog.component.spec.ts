/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { Pc_trackerDeleteDialogComponent } from 'app/entities/pc-tracker/pc-tracker-delete-dialog.component';
import { Pc_trackerService } from 'app/entities/pc-tracker/pc-tracker.service';

describe('Component Tests', () => {
    describe('Pc_tracker Management Delete Component', () => {
        let comp: Pc_trackerDeleteDialogComponent;
        let fixture: ComponentFixture<Pc_trackerDeleteDialogComponent>;
        let service: Pc_trackerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Pc_trackerDeleteDialogComponent]
            })
                .overrideTemplate(Pc_trackerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Pc_trackerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Pc_trackerService);
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
