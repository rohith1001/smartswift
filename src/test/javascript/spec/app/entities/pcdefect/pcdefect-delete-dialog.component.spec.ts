/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { PcdefectDeleteDialogComponent } from 'app/entities/pcdefect/pcdefect-delete-dialog.component';
import { PcdefectService } from 'app/entities/pcdefect/pcdefect.service';

describe('Component Tests', () => {
    describe('Pcdefect Management Delete Component', () => {
        let comp: PcdefectDeleteDialogComponent;
        let fixture: ComponentFixture<PcdefectDeleteDialogComponent>;
        let service: PcdefectService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PcdefectDeleteDialogComponent]
            })
                .overrideTemplate(PcdefectDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PcdefectDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PcdefectService);
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
