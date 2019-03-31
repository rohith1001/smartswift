/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { NewreportDeleteDialogComponent } from 'app/entities/newreport/newreport-delete-dialog.component';
import { NewreportService } from 'app/entities/newreport/newreport.service';

describe('Component Tests', () => {
    describe('Newreport Management Delete Component', () => {
        let comp: NewreportDeleteDialogComponent;
        let fixture: ComponentFixture<NewreportDeleteDialogComponent>;
        let service: NewreportService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [NewreportDeleteDialogComponent]
            })
                .overrideTemplate(NewreportDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NewreportDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NewreportService);
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
