/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { BulkreleaseDeleteDialogComponent } from 'app/entities/bulkrelease/bulkrelease-delete-dialog.component';
import { BulkreleaseService } from 'app/entities/bulkrelease/bulkrelease.service';

describe('Component Tests', () => {
    describe('Bulkrelease Management Delete Component', () => {
        let comp: BulkreleaseDeleteDialogComponent;
        let fixture: ComponentFixture<BulkreleaseDeleteDialogComponent>;
        let service: BulkreleaseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [BulkreleaseDeleteDialogComponent]
            })
                .overrideTemplate(BulkreleaseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BulkreleaseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BulkreleaseService);
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
