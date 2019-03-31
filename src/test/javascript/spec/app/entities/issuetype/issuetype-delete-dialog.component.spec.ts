/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { IssuetypeDeleteDialogComponent } from 'app/entities/issuetype/issuetype-delete-dialog.component';
import { IssuetypeService } from 'app/entities/issuetype/issuetype.service';

describe('Component Tests', () => {
    describe('Issuetype Management Delete Component', () => {
        let comp: IssuetypeDeleteDialogComponent;
        let fixture: ComponentFixture<IssuetypeDeleteDialogComponent>;
        let service: IssuetypeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [IssuetypeDeleteDialogComponent]
            })
                .overrideTemplate(IssuetypeDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IssuetypeDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IssuetypeService);
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
