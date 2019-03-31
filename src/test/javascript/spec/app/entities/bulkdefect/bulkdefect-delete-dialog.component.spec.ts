/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { BulkdefectDeleteDialogComponent } from 'app/entities/bulkdefect/bulkdefect-delete-dialog.component';
import { BulkdefectService } from 'app/entities/bulkdefect/bulkdefect.service';

describe('Component Tests', () => {
    describe('Bulkdefect Management Delete Component', () => {
        let comp: BulkdefectDeleteDialogComponent;
        let fixture: ComponentFixture<BulkdefectDeleteDialogComponent>;
        let service: BulkdefectService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [BulkdefectDeleteDialogComponent]
            })
                .overrideTemplate(BulkdefectDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BulkdefectDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BulkdefectService);
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
