/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { FrTestModule } from '../../../test.module';
import { InmuebleMySuffixDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/inmueble-my-suffix/inmueble-my-suffix-delete-dialog.component';
import { InmuebleMySuffixService } from '../../../../../../main/webapp/app/entities/inmueble-my-suffix/inmueble-my-suffix.service';

describe('Component Tests', () => {

    describe('InmuebleMySuffix Management Delete Component', () => {
        let comp: InmuebleMySuffixDeleteDialogComponent;
        let fixture: ComponentFixture<InmuebleMySuffixDeleteDialogComponent>;
        let service: InmuebleMySuffixService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [FrTestModule],
                declarations: [InmuebleMySuffixDeleteDialogComponent],
                providers: [
                    InmuebleMySuffixService
                ]
            })
            .overrideTemplate(InmuebleMySuffixDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(InmuebleMySuffixDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(InmuebleMySuffixService);
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
