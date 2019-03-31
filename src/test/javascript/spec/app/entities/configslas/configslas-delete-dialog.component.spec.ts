/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SmartswiftTestModule } from '../../../test.module';
import { ConfigslasDeleteDialogComponent } from 'app/entities/configslas/configslas-delete-dialog.component';
import { ConfigslasService } from 'app/entities/configslas/configslas.service';

describe('Component Tests', () => {
    describe('Configslas Management Delete Component', () => {
        let comp: ConfigslasDeleteDialogComponent;
        let fixture: ComponentFixture<ConfigslasDeleteDialogComponent>;
        let service: ConfigslasService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SmartswiftTestModule],
                declarations: [ConfigslasDeleteDialogComponent]
            })
                .overrideTemplate(ConfigslasDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ConfigslasDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ConfigslasService);
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
