/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { BulkincidentDeleteDialogComponent } from 'app/entities/bulkincident/bulkincident-delete-dialog.component';
import { BulkincidentService } from 'app/entities/bulkincident/bulkincident.service';

describe('Component Tests', () => {
    describe('Bulkincident Management Delete Component', () => {
        let comp: BulkincidentDeleteDialogComponent;
        let fixture: ComponentFixture<BulkincidentDeleteDialogComponent>;
        let service: BulkincidentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [BulkincidentDeleteDialogComponent]
            })
                .overrideTemplate(BulkincidentDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BulkincidentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BulkincidentService);
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
