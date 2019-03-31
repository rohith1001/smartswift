/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { L2queryDeleteDialogComponent } from 'app/entities/l-2-query/l-2-query-delete-dialog.component';
import { L2queryService } from 'app/entities/l-2-query/l-2-query.service';

describe('Component Tests', () => {
    describe('L2query Management Delete Component', () => {
        let comp: L2queryDeleteDialogComponent;
        let fixture: ComponentFixture<L2queryDeleteDialogComponent>;
        let service: L2queryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [L2queryDeleteDialogComponent]
            })
                .overrideTemplate(L2queryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(L2queryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(L2queryService);
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
