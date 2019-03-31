/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { ImpactDeleteDialogComponent } from 'app/entities/impact/impact-delete-dialog.component';
import { ImpactService } from 'app/entities/impact/impact.service';

describe('Component Tests', () => {
    describe('Impact Management Delete Component', () => {
        let comp: ImpactDeleteDialogComponent;
        let fixture: ComponentFixture<ImpactDeleteDialogComponent>;
        let service: ImpactService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ImpactDeleteDialogComponent]
            })
                .overrideTemplate(ImpactDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ImpactDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ImpactService);
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
