/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { PcreleaseDeleteDialogComponent } from 'app/entities/pcrelease/pcrelease-delete-dialog.component';
import { PcreleaseService } from 'app/entities/pcrelease/pcrelease.service';

describe('Component Tests', () => {
    describe('Pcrelease Management Delete Component', () => {
        let comp: PcreleaseDeleteDialogComponent;
        let fixture: ComponentFixture<PcreleaseDeleteDialogComponent>;
        let service: PcreleaseService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [PcreleaseDeleteDialogComponent]
            })
                .overrideTemplate(PcreleaseDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(PcreleaseDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PcreleaseService);
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
