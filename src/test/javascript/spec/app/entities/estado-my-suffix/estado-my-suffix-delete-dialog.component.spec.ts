/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FrTestModule } from '../../../test.module';
import { EstadoMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/estado-my-suffix/estado-my-suffix-delete-dialog.component';
import { EstadoMySuffixService } from '../../../../../../main/webapp/app/entities/estado-my-suffix/estado-my-suffix.service';

describe('Component Tests', () => {

    describe('EstadoMySuffix Management Delete Component', () => {
        let comp: EstadoMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<EstadoMySuffixDeleteDialogComponent>;
        let service: EstadoMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [EstadoMySuffixDeleteDialogComponent],
                providers: [
                    EstadoMySuffixService
                ]
            })
            .overrideTemplate(EstadoMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EstadoMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EstadoMySuffixService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
