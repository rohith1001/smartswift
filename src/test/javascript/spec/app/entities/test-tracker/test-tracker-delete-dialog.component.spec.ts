/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { Test_trackerDeleteDialogComponent } from 'app/entities/test-tracker/test-tracker-delete-dialog.component';
import { Test_trackerService } from 'app/entities/test-tracker/test-tracker.service';

describe('Component Tests', () => {
    describe('Test_tracker Management Delete Component', () => {
        let comp: Test_trackerDeleteDialogComponent;
        let fixture: ComponentFixture<Test_trackerDeleteDialogComponent>;
        let service: Test_trackerService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [Test_trackerDeleteDialogComponent]
            })
                .overrideTemplate(Test_trackerDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(Test_trackerDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(Test_trackerService);
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
