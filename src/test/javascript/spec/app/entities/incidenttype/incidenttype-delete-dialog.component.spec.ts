/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { IncidenttypeDeleteDialogComponent } from 'app/entities/incidenttype/incidenttype-delete-dialog.component';
import { IncidenttypeService } from 'app/entities/incidenttype/incidenttype.service';

describe('Component Tests', () => {
    describe('Incidenttype Management Delete Component', () => {
        let comp: IncidenttypeDeleteDialogComponent;
        let fixture: ComponentFixture<IncidenttypeDeleteDialogComponent>;
        let service: IncidenttypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [IncidenttypeDeleteDialogComponent]
            })
                .overrideTemplate(IncidenttypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IncidenttypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IncidenttypeService);
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
