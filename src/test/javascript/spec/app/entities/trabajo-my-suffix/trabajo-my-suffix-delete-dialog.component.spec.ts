/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FrTestModule } from '../../../test.module';
import { TrabajoMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/trabajo-my-suffix/trabajo-my-suffix-delete-dialog.component';
import { TrabajoMySuffixService } from '../../../../../../main/webapp/app/entities/trabajo-my-suffix/trabajo-my-suffix.service';

describe('Component Tests', () => {

    describe('TrabajoMySuffix Management Delete Component', () => {
        let comp: TrabajoMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<TrabajoMySuffixDeleteDialogComponent>;
        let service: TrabajoMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [TrabajoMySuffixDeleteDialogComponent],
                providers: [
                    TrabajoMySuffixService
                ]
            })
            .overrideTemplate(TrabajoMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TrabajoMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TrabajoMySuffixService);
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
