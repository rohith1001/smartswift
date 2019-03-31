/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { StkDeleteDialogComponent } from 'app/entities/stk/stk-delete-dialog.component';
import { StkService } from 'app/entities/stk/stk.service';

describe('Component Tests', () => {
    describe('Stk Management Delete Component', () => {
        let comp: StkDeleteDialogComponent;
        let fixture: ComponentFixture<StkDeleteDialogComponent>;
        let service: StkService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [StkDeleteDialogComponent]
            })
                .overrideTemplate(StkDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StkDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StkService);
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
